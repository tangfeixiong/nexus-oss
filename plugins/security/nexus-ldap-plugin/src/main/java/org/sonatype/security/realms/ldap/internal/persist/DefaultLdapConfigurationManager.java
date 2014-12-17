/*
 * Sonatype Nexus (TM) Open Source Version
 * Copyright (c) 2007-2014 Sonatype, Inc.
 * All rights reserved. Includes the third-party code listed at http://links.sonatype.com/products/nexus/oss/attributions.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License Version 1.0,
 * which accompanies this distribution and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Sonatype Nexus (TM) Professional Version is available from Sonatype, Inc. "Sonatype" and "Sonatype Nexus" are trademarks
 * of Sonatype, Inc. Apache Maven is a trademark of the Apache Software Foundation. M2eclipse is a trademark of the
 * Eclipse Foundation. All other trademarks are the property of their respective owners.
 */

package org.sonatype.security.realms.ldap.internal.persist;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.util.DigesterUtils;
import org.sonatype.security.SecuritySystem;
import org.sonatype.security.realms.ldap.LdapPlugin;
import org.sonatype.security.realms.ldap.internal.events.LdapClearCacheEvent;
import org.sonatype.security.realms.ldap.internal.persist.entity.LdapConfiguration;
import org.sonatype.security.realms.ldap.internal.persist.entity.Validator;
import org.sonatype.security.realms.ldap.internal.realms.LdapRealm;
import org.sonatype.sisu.goodies.common.ComponentSupport;
import org.sonatype.sisu.goodies.eventbus.EventBus;

import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Default implementation of {@link LdapConfigurationManager}.
 */
@Named
@Singleton
public class DefaultLdapConfigurationManager
    extends ComponentSupport
    implements LdapConfigurationManager
{
  private final LdapConfigurationSource configurationSource;

  private final EventBus eventBus;

  private final SecuritySystem securitySystem;

  private final Random rand = new Random(System.currentTimeMillis());

  private final Validator validator;

  /**
   * Configuration cache.
   */
  private final LinkedHashMap<String, LdapConfiguration> cache;

  private boolean cachePrimed;

  @Inject
  public DefaultLdapConfigurationManager(final LdapConfigurationSource configurationSource,
                                         final EventBus eventBus,
                                         final SecuritySystem securitySystem)
  {
    this.configurationSource = checkNotNull(configurationSource);
    this.eventBus = checkNotNull(eventBus);
    this.securitySystem = checkNotNull(securitySystem);
    this.validator = new Validator();
    this.cache = Maps.newLinkedHashMap();
    this.cachePrimed = false;
  }

  @Override
  public synchronized void clearCache() {
    cache.clear();
    cachePrimed = false;
    eventBus.post(new LdapClearCacheEvent(this));
  }

  @Override
  public synchronized void setServerOrder(final List<String> orderdServerIds)
      throws IllegalArgumentException
  {
    checkNotNull(orderdServerIds);
    final LinkedHashMap<String, LdapConfiguration> configuration = getConfiguration();
    final Set<String> newOrder = Sets.newHashSet(orderdServerIds);
    final Set<String> configIds = configuration.keySet();
    checkArgument(newOrder.equals(configIds), "Bad ordering: %s (existing IDs %s)", newOrder, configIds);

    for (LdapConfiguration config : configuration.values()) {
      config.setOrder(orderdServerIds.indexOf(config.getId()));
      configurationSource.update(config);
    }
    clearCache();
  }

  @Override
  public synchronized List<LdapConfiguration> listLdapServerConfigurations() {
    return ImmutableList.copyOf(getConfiguration().values());
  }

  @Override
  public synchronized LdapConfiguration getLdapServerConfiguration(final String id)
      throws LdapServerNotFoundException
  {
    checkNotNull(id);
    final LdapConfiguration configuration = getConfiguration().get(id);
    if (configuration != null) {
      return configuration;
    }
    throw new LdapServerNotFoundException("Ldap Server: '" + id + "' was not found.");
  }

  @Override
  public synchronized void addLdapServerConfiguration(final LdapConfiguration ldapServerConfiguration)
      throws IllegalArgumentException
  {
    checkNotNull(ldapServerConfiguration);
    validator.validate(ldapServerConfiguration);
    final LinkedHashMap<String, LdapConfiguration> config = getConfiguration();
    final boolean firstEntry = config.isEmpty();
    // TODO: I just want a "nicer" but URL safe ID, and, to get away from base-config
    final String id = DigesterUtils.getSha1Digest(Long.toString(
        System.identityHashCode(ldapServerConfiguration) + System.nanoTime() + rand.nextInt(2008)));
    ldapServerConfiguration.setId(id);
    configurationSource.create(ldapServerConfiguration);
    clearCache();
    if (firstEntry) {
      mayActivateLdapRealm();
    }
  }


  @Override
  public synchronized void updateLdapServerConfiguration(final LdapConfiguration ldapServerConfiguration)
      throws IllegalArgumentException, LdapServerNotFoundException
  {
    checkNotNull(ldapServerConfiguration);
    validator.validate(ldapServerConfiguration);
    checkArgument(Strings.isNullOrEmpty(ldapServerConfiguration.getId()), "'id' is null, cannot update");
    final LinkedHashMap<String, LdapConfiguration> config = getConfiguration();
    if (!config.containsKey(ldapServerConfiguration.getId())) {
      throw new LdapServerNotFoundException("Ldap Server: '" + ldapServerConfiguration.getId() + "' was not found.");
    }
    configurationSource.update(ldapServerConfiguration);
    clearCache();
  }

  @Override
  public synchronized void deleteLdapServerConfiguration(final String id)
      throws LdapServerNotFoundException
  {
    checkNotNull(id);
    final LinkedHashMap<String, LdapConfiguration> config = getConfiguration();
    final boolean lastEntry = config.size() == 1;
    if (config.containsKey(id)) {
      configurationSource.delete(id);
      clearCache();
      if (lastEntry) {
        mayDeactivateLdapRealm();
      }
      return;
    }
    throw new LdapServerNotFoundException("Ldap Server: '" + id + "' was not found.");
  }

  /**
   * Activates, if not activated already, the {@link LdapRealm} as last realm in system.
   *
   * @since 2.7.0
   */
  private void mayActivateLdapRealm() {
    try {
      final List<String> activeRealms = securitySystem.getRealms();
      if (!activeRealms.contains(LdapPlugin.REALM_NAME)) {
        activeRealms.add(LdapPlugin.REALM_NAME);
        securitySystem.setRealms(activeRealms);
      }
    }
    catch (Exception e) {
      throw Throwables.propagate(e);
    }
  }

  /**
   * Deactivates, if not deactivated already, the {@link LdapRealm realm in system.
   *
   * @since 2.7.0
   */
  private void mayDeactivateLdapRealm() {
    try {
      final List<String> activeRealms = securitySystem.getRealms();
      if (activeRealms.contains(LdapPlugin.REALM_NAME)) {
        activeRealms.remove(LdapPlugin.REALM_NAME);
        securitySystem.setRealms(activeRealms);
      }
    }
    catch (Exception e) {
      throw Throwables.propagate(e);
    }
  }

  /**
   * Primes the cache if not primed and returns it, so to say "lazily" loads. Should be called only from synchronized
   * method.
   */
  private LinkedHashMap<String, LdapConfiguration> getConfiguration() {
    if (!cachePrimed) {
      try {
        cache.clear();
        final List<LdapConfiguration> ldapConfigurations = configurationSource.loadAll();
        Collections.sort(ldapConfigurations, new Comparator<LdapConfiguration>()
        {
          @Override
          public int compare(final LdapConfiguration o1, final LdapConfiguration o2) {
            return o1.getOrder() - o2.getOrder();
          }
        });
        for (LdapConfiguration ldapConfiguration : ldapConfigurations) {
          cache.put(ldapConfiguration.getId(), ldapConfiguration);
        }
      }
      catch (Exception e) {
        log.warn("Cannot retrieve LDAP configuration", e);
        throw Throwables.propagate(e);
      }
      finally {
        cachePrimed = true;
      }
    }
    return cache;
  }
}
