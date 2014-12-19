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
package org.sonatype.security.realms.ldap.internal.ui

import groovy.transform.ToString

/**
 * LDAP Schema Template exchange object.
 *
 * @since 3.0
 */
@ToString(includePackage = false, includeNames = true)
class LdapSchemaTemplateXO
{
  String name
  String userBaseDn
  Boolean userSubtree
  String userObjectClass
  String userLdapFilter
  String userIdAttribute
  String userRealNameAttribute
  String userEmailAddressAttribute
  String userPasswordAttribute
  Boolean ldapGroupsAsRoles
  String groupType
  String groupBaseDn
  Boolean groupSubtree
  String groupObjectClass
  String groupIdAttribute
  String groupMemberAttribute
  String groupMemberFormat
  String userMemberOfAttribute
}
