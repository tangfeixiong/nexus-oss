package org.sonatype.nexus.repository.types;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.Type;

/**
 * Hosted repository type.
 *
 * @since 3.0
 */
@Named(HostedType.NAME)
@Singleton
public class HostedType
  extends Type
{
  public static final String NAME = "hosted";

  public HostedType() {
    super(NAME);
  }
}
