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

package org.sonatype.security.realms.ldap.internal.connector.dao.password;

import org.sonatype.security.realms.ldap.internal.connector.dao.password.hash.MD5Crypt;
import org.sonatype.sisu.litmus.testsupport.TestSupport;

import junit.framework.Assert;
import org.junit.Test;

public class MD5CryptPasswordEncoderTest
    extends TestSupport
{
  @Test
  public void testEncryptAndVerify()
      throws Exception
  {
    PasswordEncoder encoder = new MD5CryptPasswordEncoder();

    String crypted = encoder.encodePassword("test", null);

    // System.out.println( "Crypted password: \'" + crypted + "\'" );

    int lastIdx = crypted.lastIndexOf('$');
    int firstIdx = crypted.indexOf('$');

    String salt = crypted.substring(firstIdx + "$1$".length(), lastIdx);

    String check = "{CRYPT}" + new MD5Crypt().crypt("test", salt);

    // System.out.println( "Check value: \'" + check + "\'" );

    Assert.assertEquals(check, crypted);

    Assert.assertTrue(encoder.isPasswordValid(crypted, "test", null));
  }
}
