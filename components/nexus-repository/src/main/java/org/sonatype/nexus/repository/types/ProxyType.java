package org.sonatype.nexus.repository.types;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.Type;

/**
 * Proxy repository type.
 *
 * @since 3.0
 */
@Named(ProxyType.NAME)
@Singleton
public class ProxyType
  extends Type
{
  public static final String NAME = "proxy";

  public ProxyType() {
    super(NAME);
  }
}
