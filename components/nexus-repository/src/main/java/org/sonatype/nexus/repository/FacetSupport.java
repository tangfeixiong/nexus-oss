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
package org.sonatype.nexus.repository;

import org.sonatype.sisu.goodies.common.ComponentSupport;

/**
 * Support class for implementations of {@link Facet}.
 *
 * @since 3.0
 */
public abstract class FacetSupport
    extends ComponentSupport
    implements Facet
{
  @Override
  public void init(final Repository repository) throws Exception {
  }

  @Override
  public void start() throws Exception {
  }

  @Override
  public void stop() throws Exception {
  }

  @Override
  public void dispose() throws Exception {
  }
}
