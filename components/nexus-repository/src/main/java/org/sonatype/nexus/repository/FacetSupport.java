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

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * Support for {@link Facet} implementations.
 *
 * @since 3.0
 */
public abstract class FacetSupport
    extends ComponentSupport
    implements Facet
{
  private Repository repository;

  // TODO: Sort out synchronization needed to ensure state sanity
  private volatile boolean started;

  protected boolean isStarted() {
    return started;
  }

  protected void ensureStarted() {
    checkState(started, "Not started");
  }

  protected Repository getRepository() {
    checkState(repository != null, "Not initialized");
    return repository;
  }

  //
  // Lifecycle
  //

  @Override
  public final void init(final Repository repository) throws Exception {
    checkState(this.repository == null, "Already initialized");
    this.repository = checkNotNull(repository);
    doInit(repository);
  }

  protected void doInit(final Repository repository) throws Exception {
    // nop
  }

  @Override
  public final void start() throws Exception {
    checkState(!started, "Already started");
    doStart();
    started = true;
  }

  protected void doStart() throws Exception {
    // nop
  }

  @Override
  public final void stop() throws Exception {
    checkState(started, "Not started");
    doStop();
    started = false;
  }

  private void doStop() throws Exception {
    // nop
  }

  @Override
  public final void dispose() throws Exception {
    if (started) {
      stop();
    }
    doDispose();
    repository = null;
  }

  private void doDispose() throws Exception {
    // nop
  }
}
