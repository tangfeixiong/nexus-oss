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
package org.sonatype.nexus.component.source;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A normalized request for one or more Components from a {@link ComponentSource}.
 *
 * @since 3.0
 */
public class ComponentRequest
{
  private final Map<String, String> query;

  /**
   * Formats encode specific types of request and their parameters into the query map.
   */
  public ComponentRequest(final Map<String, String> query)
  {
    this.query = ImmutableMap.copyOf(checkNotNull(query));
  }

  public Map<String, String> getQuery() {
    return query;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ComponentRequest)) {
      return false;
    }

    ComponentRequest that = (ComponentRequest) o;

    return query.equals(that.query);
  }

  @Override
  public int hashCode() {
    return query.hashCode();
  }
}
