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

package org.sonatype.nexus.repository.config;

import java.util.Map;

import org.sonatype.nexus.repository.entity.EntitySupport;

import com.google.common.collect.Maps;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Repository configuration.
 *
 * @since 3.0
 */
public class Configuration
    extends EntitySupport
{
  private String recipeName;

  private String repositoryName;

  private Map<String, Map<String, Object>> attributes = Maps.newHashMap();

  public String getRecipeName() {
    return recipeName;
  }

  public void setRecipeName(final String recipeName) {
    this.recipeName = checkNotNull(recipeName);
  }

  public String getRepositoryName() {
    return repositoryName;
  }

  public void setRepositoryName(final String repositoryName) {
    this.repositoryName = checkNotNull(repositoryName);
  }

  public Map<String, Map<String, Object>> getAttributes() {
    return attributes;
  }

  public void setAttributes(final Map<String, Map<String, Object>> attributes) {
    this.attributes = checkNotNull(attributes);
  }

  public Attributes attributes(final String key) {
    checkNotNull(key);

    Map<String,Object> map = attributes.get(key);
    if (map == null) {
      map = Maps.newHashMap();
      attributes.put(key, map);
    }

    return new Attributes(key, map);
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" +
        "recipeName='" + recipeName + '\'' +
        ", repositoryName='" + repositoryName + '\'' +
        ", attributes=" + attributes +
        '}';
  }
}
