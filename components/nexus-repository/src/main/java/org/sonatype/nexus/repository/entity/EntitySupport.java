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
package org.sonatype.nexus.repository.entity;

import javax.annotation.Nullable;

/**
 * {@link Entity} support.
 *
 * @since 3.0
 */
public abstract class EntitySupport
  implements Entity
{
  private EntityMetadata metadata;

  @Nullable
  @Override
  public EntityMetadata getEntityMetadata() {
    return metadata;
  }

  // FIXME: Sort out if there is any valid use-case to null a metadata or not?
  // FIXME: If not make this non-nullable and add precondition for sanity

  public void setEntityMetadata(final @Nullable EntityMetadata metadata) {
    this.metadata = metadata;
  }
}
