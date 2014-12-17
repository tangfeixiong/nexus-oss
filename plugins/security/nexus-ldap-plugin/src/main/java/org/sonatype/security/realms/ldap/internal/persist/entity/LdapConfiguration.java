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
package org.sonatype.security.realms.ldap.internal.persist.entity;

import static com.google.common.base.Preconditions.checkNotNull;

public final class LdapConfiguration
{
  /**
   * An URL safe "identifier" of the configuration.
   */
  private String id;

  /**
   * Human editable description.
   */
  private String name;

  /**
   * The order of the configuration (natural int order).
   */
  private int order;

  private Connection connection;

  private Mapping mapping;

  public LdapConfiguration() {
  }

  public LdapConfiguration(final String id,
                           final String name,
                           final Connection connection,
                           final Mapping mapping)
  {
    setId(id);
    setName(name);
    setConnection(connection);
    setMapping(mapping);
  }

  public String getId() {
    return id;
  }

  public void setId(final String id) {
    this.id = checkNotNull(id);
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = checkNotNull(name);
  }

  public int getOrder() {
    return order;
  }

  public void setOrder(final int order) {
    this.order = order;
  }

  public Connection getConnection() {
    return connection;
  }

  public void setConnection(final Connection connection) {
    this.connection = checkNotNull(connection);
  }

  public Mapping getMapping() {
    return mapping;
  }

  public void setMapping(final Mapping mapping) {
    this.mapping = checkNotNull(mapping);
  }
}
