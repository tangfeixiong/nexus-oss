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
package org.sonatype.nexus.repository.config;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

import org.sonatype.nexus.orient.DatabaseInstance;
import org.sonatype.nexus.orient.RecordIdObfuscator;
import org.sonatype.nexus.repository.entity.EntityIdFactory;
import org.sonatype.sisu.goodies.lifecycle.LifecycleSupport;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.metadata.schema.OClass;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link Configuration} store.
 *
 * @since 3.0
 */
public class ConfigurationStoreImpl
  extends LifecycleSupport
  implements ConfigurationStore
{
  private final Provider<DatabaseInstance> databaseInstance;

  private final RecordIdObfuscator recordIdObfuscator;

  private final EntityIdFactory entityIdFactory;

  private final ConfigurationEntityAdapter entityAdapter;

  private OClass entityType;

  @Inject
  public ConfigurationStoreImpl(final @Named("config") Provider<DatabaseInstance> databaseInstance,
                                final RecordIdObfuscator recordIdObfuscator,
                                final EntityIdFactory entityIdFactory,
                                final ConfigurationEntityAdapter entityAdapter)
  {
    this.databaseInstance = checkNotNull(databaseInstance);
    this.recordIdObfuscator = checkNotNull(recordIdObfuscator);
    this.entityIdFactory = checkNotNull(entityIdFactory);
    this.entityAdapter = checkNotNull(entityAdapter);
  }

  @Override
  protected void doStart() throws Exception {
    try (ODatabaseDocumentTx db = databaseInstance.get().connect()) {
      // register schema
      entityType = entityAdapter.register(db);
    }
  }

  @Override
  protected void doStop() throws Exception {
    entityType = null;
  }

  // TODO: Hookup to NX lifecycle

  private ODatabaseDocumentTx openDb() {
    ensureStarted();
    return databaseInstance.get().acquire();
  }

  @Override
  public Iterable<Configuration> browse() {
    return null;
  }

  @Nullable
  @Override
  public Configuration read(final String identifier) {
    return null;
  }

  @Override
  public Configuration create(final Configuration configuration) throws Exception {
    return null;
  }

  @Override
  public Configuration edit(final Configuration configuration) throws Exception {
    return null;
  }

  @Override
  public void delete(final String identifier) throws Exception {

  }
}
