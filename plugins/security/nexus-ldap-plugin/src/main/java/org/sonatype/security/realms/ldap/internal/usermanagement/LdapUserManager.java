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

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.security.realms.ldap.LdapPlugin;

import org.sonatype.security.realms.ldap.internal.connector.dao.LdapDAOException;
import org.sonatype.security.realms.ldap.internal.connector.dao.LdapUser;
import org.sonatype.security.realms.ldap.internal.connector.dao.NoSuchLdapUserException;
import org.sonatype.security.realms.ldap.internal.realms.LdapManager;
import org.sonatype.security.usermanagement.AbstractReadOnlyUserManager;
import org.sonatype.security.usermanagement.User;
import org.sonatype.security.usermanagement.RoleIdentifier;
import org.sonatype.security.usermanagement.UserNotFoundException;
import org.sonatype.security.usermanagement.UserNotFoundTransientException;
import org.sonatype.security.usermanagement.UserSearchCriteria;
import org.sonatype.security.usermanagement.UserStatus;

import static com.google.common.base.Preconditions.checkNotNull;

@Singleton
@Named(LdapPlugin.USER_SOURCE)
public class LdapUserManager
    extends AbstractReadOnlyUserManager
{
  private final LdapManager ldapManager;

  @Inject
  public LdapUserManager(final LdapManager ldapManager) {
    this.ldapManager = checkNotNull(ldapManager);
  }

  @Override
  public User getUser(String userId)
      throws UserNotFoundException
  {
    if (this.isEnabled()) {
      try {
        return toPlexusUser(this.ldapManager.getUser(userId));
      }
      catch (NoSuchLdapUserException e) {
        log.debug("User: " + userId + " not found.", e);
      }
      catch (LdapDAOException e) {
        log.debug("User: " + userId + " not found, cause: " + e.getMessage(), e);
        throw new UserNotFoundTransientException(userId, e.getMessage(), e);
      }
    }
    throw new UserNotFoundException(userId);
  }


  @Override
  public Set<String> listUserIds() {
    Set<String> userIds = new TreeSet<String>();
    for (User User : this.listUsers()) {
      userIds.add(User.getUserId());
    }
    return userIds;
  }

  @Override
  public Set<User> listUsers() {
    Set<User> users = new TreeSet<User>();
    if (this.isEnabled()) {
      try {
        Collection<LdapUser> ldapUsers = this.ldapManager.getAllUsers();
        for (LdapUser ldapUser : ldapUsers) {
          users.add(this.toPlexusUser(ldapUser));
        }
      }
      catch (LdapDAOException e) {
        log.debug("Could not return LDAP users, LDAP Realm must not be configured.", e);
      }
    }
    return users;
  }

  private User toPlexusUser(LdapUser ldapUser) {
    User user = new User();

    String email = ldapUser.getEmail();
    if (email != null) {
      email = email.trim();
    }
    user.setEmailAddress(email);

    user.setName(ldapUser.getRealName());
    user.setUserId(ldapUser.getUsername());
    user.setSource(LdapPlugin.USER_SOURCE);
    user.setStatus(UserStatus.active);


    for (String roleId : ldapUser.getMembership()) {
      RoleIdentifier role = new RoleIdentifier(LdapPlugin.USER_SOURCE, roleId);
      user.addRole(role);
    }

    return user;
  }

  private boolean isEnabled() {
    return true;
  }

  @Override
  public String getSource() {
    return LdapPlugin.USER_SOURCE;
  }

  @Override
  public Set<User> searchUsers(UserSearchCriteria criteria) {
    //TODO, rename method, we are doing a starts with search, but thats not what this signature implies,
    // but I don't have a better idea right now.

    Set<User> users = new TreeSet<User>();
    if (this.isEnabled()) {
      try {
        Set<LdapUser> ldapUsers = this.ldapManager.searchUsers(criteria.getUserId(), criteria.getOneOfRoleIds());

        for (LdapUser ldapUser : ldapUsers) {
          users.add(this.toPlexusUser(ldapUser));
        }
      }
      catch (LdapDAOException e) {
        log.debug("Could not return LDAP users, LDAP Realm must not be configured.", e);
      }
    }

    // we can filter the lists in memory to weed out the non effective users
    // we can not *easily* do this with a LDAP query.  It would be easy for
    // users with dynamic groups, but not static
    return this.filterListInMemeory(users, criteria);
  }


  @Override
  public String getAuthenticationRealmName() {
    return LdapPlugin.REALM_NAME;
  }

}
