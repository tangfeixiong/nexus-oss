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
package org.sonatype.nexus.views.rawbinaries.source;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.component.source.ComponentSource;
import org.sonatype.nexus.component.source.config.ComponentSourceConfig;
import org.sonatype.nexus.component.source.config.ComponentSourceFactory;
import org.sonatype.nexus.component.source.http.HttpClientConfigMarshaller;
import org.sonatype.nexus.component.source.http.HttpClientFactory;
import org.sonatype.nexus.component.source.support.HttpComponentResponseBuilder;

import org.apache.http.impl.client.CloseableHttpClient;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A factory for converting {@link ComponentSourceConfig} into {@link RawBinaryComponentSource}.
 *
 * @since 3.0
 */
@Named(RawComponentSourceFactory.NAME)
@Singleton
public class RawComponentSourceFactory
    implements ComponentSourceFactory
{
  public static final String NAME = "RawComponentSourceFactory";

  public static final String REMOTE_URL_PARAM = "remote-url";

  private final HttpClientFactory httpClientFactory;

  private final HttpClientConfigMarshaller httpClientConfigMarshaller;

  private final HttpComponentResponseBuilder responseBuilder;

  @Inject
  public RawComponentSourceFactory(final HttpClientFactory httpClientFactory,
                                   final HttpClientConfigMarshaller httpClientConfigMarshaller,
                                   final HttpComponentResponseBuilder responseBuilder)
  {
    this.httpClientFactory = checkNotNull(httpClientFactory);
    this.httpClientConfigMarshaller = checkNotNull(httpClientConfigMarshaller);
    this.responseBuilder = checkNotNull(responseBuilder);
  }

  @Override
  public ComponentSource createSource(final ComponentSourceConfig config) {
    checkNotNull(config);
    return new RawBinaryComponentSource(
        config.getSourceId(),
        (CloseableHttpClient) httpClientFactory.create(httpClientConfigMarshaller.fromMap(config.getConfiguration())),
        (String) config.getConfiguration().get(REMOTE_URL_PARAM),
        responseBuilder);
  }
}
