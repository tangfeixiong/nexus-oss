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
package org.sonatype.nexus.repository.raw;

import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.Handler;
import org.sonatype.nexus.repository.view.Request;
import org.sonatype.nexus.repository.view.Response;
import org.sonatype.nexus.repository.view.Responses;

/**
 * This handler sets up the context to contain a request for a remote resource.
 *
 * @since 3.0
 */
public class RemoteRequestHandler
    implements Handler
{
  @Override
  public Response handle(final Context context) throws Exception {

    final Request request = context.getRequest();

    if(!"GET".equals(request.getAction())){
      return  Responses.methodNotAllowed();
    }

    Object remoteRequest = new Object();
    context.set("remoteRequest", remoteRequest);

    return context.proceed();
  }
}
