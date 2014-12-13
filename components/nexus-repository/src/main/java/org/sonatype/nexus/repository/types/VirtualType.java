package org.sonatype.nexus.repository.types;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.Type;

/**
 * Virtual repository type.
 *
 * @since 3.0
 */
@Named(VirtualType.NAME)
@Singleton
public class VirtualType
  extends Type
{
  public static final String NAME = "virtual";

  public VirtualType() {
    super(NAME);
  }
}
