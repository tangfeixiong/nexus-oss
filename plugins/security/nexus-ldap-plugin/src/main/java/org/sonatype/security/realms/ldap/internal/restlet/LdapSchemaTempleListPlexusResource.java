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

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.sonatype.nexus.ssl.plugin.TrustStore;
import org.sonatype.security.realms.ldap.api.dto.LdapSchemaTemplateDTO;
import org.sonatype.security.realms.ldap.api.dto.LdapSchemaTemplateListResponse;
import org.sonatype.security.realms.ldap.internal.templates.LdapSchemaTemplate;
import org.sonatype.security.realms.ldap.internal.templates.LdapSchemaTemplateManager;

import org.sonatype.plexus.rest.resource.PathProtectionDescriptor;

import org.restlet.Context;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.resource.ResourceException;
import org.restlet.resource.Variant;

import static com.google.common.base.Preconditions.checkNotNull;

@Path("/ldap/templates")
@Produces({"application/xml", "application/json"})
@Named("LdapSchemaTempleListPlexusResource")
@Singleton
public class LdapSchemaTempleListPlexusResource
    extends AbstractLdapPlexusResource
{

  private final LdapSchemaTemplateManager templateManager;

  @Inject
  public LdapSchemaTempleListPlexusResource(final TrustStore trustStore,
                                            final LdapSchemaTemplateManager templateManager)
  {
    super(trustStore);
    this.templateManager = checkNotNull(templateManager);
  }

  @Override
  public Object getPayloadInstance() {
    // Not modifiable
    return null;
  }

  @Override
  public PathProtectionDescriptor getResourceProtection() {
    return new PathProtectionDescriptor(getResourceUri(), "authcBasic,perms[security:ldapconfig]");
  }

  @Override
  public String getResourceUri() {
    return "/ldap/templates";
  }

  /**
   * Get the list of schema templates available.
   */
  @Override
  @GET
  public LdapSchemaTemplateListResponse get(Context context, Request request, Response response, Variant variant)
      throws ResourceException
  {
    List<LdapSchemaTemplate> templates = this.templateManager.getSchemaTemplates();

    LdapSchemaTemplateListResponse templateResponse = new LdapSchemaTemplateListResponse();
    templateResponse.setData(new ArrayList<LdapSchemaTemplateDTO>());

    for (LdapSchemaTemplate ldapSchemaTemplate : templates) {
      LdapSchemaTemplateDTO dto = new LdapSchemaTemplateDTO();
      dto.setName(ldapSchemaTemplate.getName());
      dto.setUserAndGroupConfig(this.toDto(ldapSchemaTemplate.getUserAndGroupAuthConfig()));
      templateResponse.getData().add(dto);
    }

    return templateResponse;
  }

}
