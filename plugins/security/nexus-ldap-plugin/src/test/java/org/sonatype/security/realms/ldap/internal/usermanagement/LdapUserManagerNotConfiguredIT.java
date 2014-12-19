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
package org.sonatype.security.realms.ldap.internal.usermanagement;

import org.sonatype.security.realms.ldap.internal.LdapTestSupport;
import org.sonatype.security.usermanagement.UserManager;
import org.sonatype.security.usermanagement.UserNotFoundException;

import junit.framework.Assert;
import org.junit.Test;

public class LdapUserManagerNotConfiguredIT
    extends LdapTestSupport
{
  @Override
  public void setUp()
      throws Exception
  {
    super.setUp();
    getLdapRealmConfig().delete();
  }

  @Test
  public void testNotConfigured()
      throws Exception
  {
    UserManager userManager = this.lookup(UserManager.class, "LDAP");
    try {
      userManager.getUser("cstamas");

      Assert.fail("Expected UserNotFoundTransientException");
    }
    catch (UserNotFoundException e) {
      // OSS LDAP did throw transient when not configured, but Pro does not, it thrown only on error
      // catch (UserNotFoundTransientException e) {
      // expect transient error due to misconfiguration
    }
  }
}
