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
