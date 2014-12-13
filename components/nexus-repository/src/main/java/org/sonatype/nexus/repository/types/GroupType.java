package org.sonatype.nexus.repository.types;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.Type;

/**
 * Group repository type.
 *
 * @since 3.0
 */
@Named(GroupType.NAME)
@Singleton
public class GroupType
  extends Type
{
  public static final String NAME = "group";

  public GroupType() {
    super(NAME);
  }
}
