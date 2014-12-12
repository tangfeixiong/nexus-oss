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
package org.sonatype.nexus.repository.view.http;

import java.util.Map;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;

import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.view.Headers;
import org.sonatype.nexus.repository.view.Parameters;
import org.sonatype.nexus.repository.view.Payload;
import org.sonatype.nexus.repository.view.Request;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * ???
 *
 * @since 3.0
 */
public class HttpRequest
  implements Request
{
  private final Repository repository;

  private final HttpServletRequest httpServletRequest;

  public HttpRequest(final Repository repository, final HttpServletRequest httpServletRequest) {
    this.repository = checkNotNull(repository);
    this.httpServletRequest = checkNotNull(httpServletRequest);
  }

  @Override
  public Repository getRepository() {
    return repository;
  }

  @Override
  public String getAction() {
    return null;
  }

  @Override
  public String getPath() {
    return null;
  }

  @Override
  public Map<String, Object> getAttributes() {
    return null;
  }

  @Override
  public Parameters getParameters() {
    return null;
  }

  @Override
  public Headers getHeaders() {
    return null;
  }

  @Nullable
  @Override
  public Payload getPayload() {
    return null;
  }
}