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
package org.sonatype.nexus.repository.view.matchers;

import java.util.regex.Pattern;

import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.Matcher;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * ???
 *
 * @since 3.0
 */
public class RegexMatcher
  implements Matcher
{
  public static final String CTX_RESULT = RegexMatcher.class.getName() + ".result";

  private final Pattern pattern;

  public RegexMatcher(final Pattern pattern) {
    checkNotNull(pattern);
    this.pattern = pattern;
  }

  @Override
  public boolean matches(final Context context) {
    checkNotNull(context);
    java.util.regex.Matcher m = pattern.matcher(context.getRequest().getPath());
    if (m.matches()) {
      // expose match result in context
      context.set(CTX_RESULT, m);
      return true;
    }
    return false;
  }
}
