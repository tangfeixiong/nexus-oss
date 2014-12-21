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

package org.sonatype.nexus.repository.manager;

import java.util.Map;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.Recipe;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.config.Configuration;
import org.sonatype.nexus.repository.config.ConfigurationStore;
import org.sonatype.sisu.goodies.lifecycle.LifecycleSupport;

import com.google.common.collect.Maps;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Default {@link RepositoryManager} implementation.
 *
 * @since 3.0
 */
@Named
@Singleton
public class RepositoryManagerImpl
    extends LifecycleSupport
    implements RepositoryManager
{
  private final ConfigurationStore store;

  private final Map<String,Recipe> recipes;

  private final Map<String, Repository> repositories = Maps.newHashMap();

  @Inject
  public RepositoryManagerImpl(final ConfigurationStore store,
                               final Map<String,Recipe> recipes)
  {
    this.store = checkNotNull(store);
    this.recipes = checkNotNull(recipes);
  }

  @Override
  protected void doStart() throws Exception {
    // TODO:
  }

  @Override
  protected void doStop() throws Exception {
    // TODO:
  }

  @Override
  public Iterable<Repository> browse() {
    // TODO:
    return null;
  }

  @Nullable
  @Override
  public Repository read(final String name) {
    checkNotNull(name);
    // TODO:
    return null;
  }

  @Override
  public Repository create(final Configuration configuration) throws Exception {
    checkNotNull(configuration);
    // TODO:
    return null;
  }

  @Override
  public Repository edit(final Configuration configuration) throws Exception {
    checkNotNull(configuration);
    // TODO:
    return null;
  }

  @Override
  public void delete(final String name) throws Exception {
    checkNotNull(name);
    // TODO:
  }
}
