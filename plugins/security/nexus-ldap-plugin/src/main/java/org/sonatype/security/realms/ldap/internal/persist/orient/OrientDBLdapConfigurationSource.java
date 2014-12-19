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

package org.sonatype.security.realms.ldap.internal.persist.orient;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

import org.sonatype.nexus.component.services.id.EntityIdFactory;
import org.sonatype.nexus.orient.DatabaseInstance;
import org.sonatype.security.realms.ldap.internal.persist.LdapConfigurationSource;
import org.sonatype.security.realms.ldap.internal.persist.entity.LdapConfiguration;
import org.sonatype.sisu.goodies.lifecycle.LifecycleSupport;

import com.google.common.collect.Lists;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.record.impl.ODocument;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Orient {@link LdapConfigurationSource}.
 *
 * @since 3.0
 */
public class OrientDBLdapConfigurationSource
    extends LifecycleSupport
    implements LdapConfigurationSource
{
  private final Provider<DatabaseInstance> databaseInstance;

  private final EntityIdFactory entityIdFactory;

  private final LdapConfigurationEntityAdapter entityAdapter;

  @Inject
  public OrientDBLdapConfigurationSource(final @Named("config") Provider<DatabaseInstance> databaseInstance,
                                         final EntityIdFactory entityIdFactory,
                                         final LdapConfigurationEntityAdapter entityAdapter)
  {
    this.databaseInstance = checkNotNull(databaseInstance);
    this.entityIdFactory = checkNotNull(entityIdFactory);
    this.entityAdapter = checkNotNull(entityAdapter);
  }

  @Override
  protected void doStart() throws Exception {
    try (ODatabaseDocumentTx db = databaseInstance.get().connect()) {
      // register schema
      entityAdapter.register(db);
    }
  }

  @Override
  protected void doStop() throws Exception {
  }

  private ODatabaseDocumentTx openDb() {
    ensureStarted();
    return databaseInstance.get().acquire();
  }

  @Override
  public List<LdapConfiguration> loadAll() {
    try (ODatabaseDocumentTx db = openDb()) {
      db.begin();
      try {
        final Iterable<ODocument> documents = entityAdapter.browse(db);
        final List<LdapConfiguration> result = Lists.newArrayList();
        for (ODocument document : documents) {
          result.add(entityAdapter.read(document));
        }
        return result;
      }
      finally {
        db.commit();
      }
    }
  }

  @Override
  public void create(final LdapConfiguration ldapConfiguration) {
    try (ODatabaseDocumentTx db = openDb()) {
      db.begin();
      try {
        ldapConfiguration.setId(entityIdFactory.newId().asUniqueString());
        entityAdapter.add(db, ldapConfiguration);
      }
      finally {
        db.commit();
      }
    }
  }

  @Override
  public boolean update(final LdapConfiguration ldapConfiguration) {
    try (ODatabaseDocumentTx db = openDb()) {
      db.begin();
      try {
        final ODocument doc = entityAdapter.selectById(db, ldapConfiguration.getId());
        if (doc != null) {
          entityAdapter.edit(doc, ldapConfiguration);
          return true;
        }
      }
      finally {
        db.commit();
      }
    }
    return false;
  }

  @Override
  public boolean delete(final String id) {
    try (ODatabaseDocumentTx db = openDb()) {
      db.begin();
      try {
        final ODocument doc = entityAdapter.selectById(db, id);
        if (doc != null) {
          db.delete(doc);
          return true;
        }
      }
      finally {
        db.commit();
      }
    }
    return false;
  }
}