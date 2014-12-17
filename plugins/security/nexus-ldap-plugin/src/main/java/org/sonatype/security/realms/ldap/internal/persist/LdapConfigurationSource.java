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
package org.sonatype.security.realms.ldap.internal.persist;

import java.util.List;

import org.sonatype.security.realms.ldap.internal.persist.entity.LdapConfiguration;

/**
 * Actual persistence mechanism of {@link LdapConfiguration}.
 */
public interface LdapConfigurationSource
{
  /**
   * Loads all the configuration entries as a modifiable list with undefined order. Modifications to the list are not
   * affecting actual underlying source (ie. is not persisted).
   */
  List<LdapConfiguration> loadAll();

  /**
   * Creates a new entry. Fails if ID already exists.
   */
  void create(LdapConfiguration ldapConfiguration);

  /**
   * Updates existing entry. Returns {@code true} if entry found and updated.
   */
  boolean update(LdapConfiguration ldapConfiguration);

  /**
   * Deletes existing entry. Returns {@code true} if entry found and deleted.
   */
  boolean delete(String id);
}
