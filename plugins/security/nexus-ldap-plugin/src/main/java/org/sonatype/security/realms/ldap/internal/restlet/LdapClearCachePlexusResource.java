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

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;

import com.sonatype.nexus.ssl.plugin.TrustStore;

import org.sonatype.plexus.rest.resource.PathProtectionDescriptor;
import org.sonatype.security.realms.ldap.internal.persist.LdapConfigurationManager;

import org.restlet.Context;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.resource.ResourceException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Clear the authc/authz cache of the ldap server.
 */
@Path("/ldap/clearcache")
@Named("LdapClearCachePlexusResource")
@Singleton
public class LdapClearCachePlexusResource
    extends AbstractLdapPlexusResource
{

  private final LdapConfigurationManager ldapConfigurationManager;

  @Inject
  public LdapClearCachePlexusResource(final TrustStore trustStore,
                                      final LdapConfigurationManager ldapConfigurationManager)
  {
    super(trustStore);
    this.ldapConfigurationManager = checkNotNull(ldapConfigurationManager);
    this.setReadable(false);
    this.setModifiable(true);
  }

  @Override
  public Object getPayloadInstance() {
    return null;
  }

  @Override
  public PathProtectionDescriptor getResourceProtection() {
    return new PathProtectionDescriptor(getResourceUri(), "authcBasic,perms[security:ldapconfig]");
  }

  @Override
  public String getResourceUri() {
    return "/ldap/clearcache";
  }

  @Override
  @DELETE
  protected void doDelete(Context context, Request request, Response response)
      throws ResourceException
  {
    this.ldapConfigurationManager.clearCache();
  }
}
