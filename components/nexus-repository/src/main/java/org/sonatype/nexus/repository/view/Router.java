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
package org.sonatype.nexus.repository.view;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Dispatches {@link Request}s to the first matching {@link Route}, otherwise the request is handled by a {@link
 * Router#setDefaultRoute(DefaultRoute)} default route}.
 *
 * @since 3.0
 */
public class Router
{
  private final List<Route> routes = new ArrayList<>();

  private Route defaultRoute;

  public Response dispatch(Request request) throws Exception {
    Context context = new Context(request);

    context.setHandlers(findRoute(context).getHandlers());

    return context.proceed();
  }

  public void addRoute(Route route) {
    routes.add(route);
  }

  /**
   * If none of the Routes match, the {@link Request} will be {@link Router#dispatch}ed to the default route.
   */
  public void setDefaultRoute(final DefaultRoute defaultRoute) {
    this.defaultRoute = checkNotNull(defaultRoute);
  }

  private Route findRoute(Context context) {
    for (Route route : routes) {
      if (route.getMatcher().matches(context)) {
        return route;
      }
    }
    return defaultRoute;
  }
}
