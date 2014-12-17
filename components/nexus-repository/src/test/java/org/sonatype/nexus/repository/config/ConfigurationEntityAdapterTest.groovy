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
package org.sonatype.nexus.repository.config

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.sonatype.nexus.internal.orient.MemoryDatabaseManager
import org.sonatype.nexus.internal.orient.MinimalDatabaseServer
import org.sonatype.nexus.orient.DatabaseInstance
import org.sonatype.sisu.litmus.testsupport.TestSupport

/**
 * Tests for {@link ConfigurationEntityAdapter}.
 */
class ConfigurationEntityAdapterTest
  extends TestSupport
{
  private MinimalDatabaseServer databaseServer

  private MemoryDatabaseManager databaseManager

  private DatabaseInstance databaseInstance

  private ConfigurationEntityAdapter underTest

  @Before
  void setUp() {
    databaseServer = new MinimalDatabaseServer()
    databaseServer.start()

    databaseManager = new MemoryDatabaseManager()
    databaseManager.start()

    databaseInstance = databaseManager.instance('test')

    underTest = new ConfigurationEntityAdapter()
  }

  @After
  void tearDown() {
    databaseInstance = null

    if (databaseManager != null) {
      databaseManager.stop()
      databaseManager = null
    }

    if (databaseServer != null) {
      databaseServer.stop()
      databaseServer = null
    }
  }

  @Test
  void 'register schema'() {
    def db = databaseInstance.connect()
    try {
      underTest.register(db)
    }
    finally {
      db.close()
    }
  }

  @Test
  void 'add entity'() {
    def db = databaseInstance.connect()
    try {
      underTest.register(db)

      def config = new Configuration()
      config.recipeName = 'foo'
      config.repositoryName = 'bar'

      def attr = config.attributes('baz')
      attr.set('a', 'b')

      def doc = underTest.add(db, config)
      log doc.toJSON()
    }
    finally {
      db.close()
    }
  }
}
