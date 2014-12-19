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
package org.sonatype.security.realms.ldap.internal.restlet;

import java.util.ArrayList;
import java.util.List;

import org.sonatype.security.realms.ldap.internal.MockLdapConnector;

import org.sonatype.plexus.rest.resource.PlexusResource;
import org.sonatype.security.realms.ldap.internal.connector.dao.LdapDAOException;
import org.sonatype.security.realms.ldap.internal.realms.LdapManager;
import org.sonatype.security.realms.ldap.internal.connector.LdapConnector;

import org.junit.Assert;
import org.junit.Test;
import org.restlet.data.Request;
import org.restlet.data.Response;

public class ServerClearCacheRestTest
    extends AbstractLdapRestTest
{

  private MockLdapConnector mainConnector;

  protected List<LdapConnector> getLdapConnectors() {
    List<LdapConnector> connectors = new ArrayList<LdapConnector>();
    this.mainConnector = this.buildMainMockServer("default");
    connectors.add(mainConnector);

    return connectors;
  }

  @Test
  public void testModifiable()
      throws Exception
  {
    PlexusResource pr = this.lookup(PlexusResource.class, "LdapClearCachePlexusResource");
    Assert.assertTrue(pr.isModifiable());
  }

  @Test
  public void testReadable()
      throws Exception
  {
    PlexusResource pr = this.lookup(PlexusResource.class, "LdapClearCachePlexusResource");
    Assert.assertFalse(pr.isReadable());
  }

  @Test
  public void testClearCache()
      throws Exception
  {
    LdapManager ldapManager = this.lookup(LdapManager.class);

    PlexusResource pr = this.lookup(PlexusResource.class, "LdapClearCachePlexusResource");
    Request request = this.buildRequest("default");
    Response response = new Response(request);

    // all systems are good
    Assert.assertNotNull(ldapManager.getUser("rwalker"));

    // This part is not true anymore, as Shiro caching is used, not homegrown in LdapManager
    // ==
    // stop the main server
    // this.mainConnector.stop();
    // make sure cache is active
    // Assert.assertNotNull(ldapManager.getUser("rwalker"));

    // delete the cache
    pr.delete(null, request, response);

    // fake out the connector again
    this.resetLdapConnectors();
    this.mainConnector.stop();

    try {
      ldapManager.getUser("rwalker");
      Assert.fail("expected LdapDAOException");
    }
    catch (LdapDAOException e) {
      // expected
    }
  }

}
