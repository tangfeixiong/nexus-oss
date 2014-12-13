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

import org.junit.Test
import org.sonatype.sisu.litmus.testsupport.TestSupport

import static org.sonatype.nexus.repository.config.Attributes.GRANDPARENT_SEPARATOR

/**
 * Tests for {@link Attributes}.
 */
class AttributesTest
  extends TestSupport
{
  @Test
  void 'parentKey null when no parent'() {
    Attributes child = new Attributes('foo', [:])
    assert child.key == 'foo'
    assert child.parentKey == null
  }

  @Test
  void 'parentKey includes grandparent'() {
    Attributes grandparent = new Attributes('foo', [:])
    Attributes parent = grandparent.child('bar')
    Attributes child = parent.child('baz')

    assert child.key == 'baz'
    assert "foo${GRANDPARENT_SEPARATOR}bar" == child.parentKey
  }
}
