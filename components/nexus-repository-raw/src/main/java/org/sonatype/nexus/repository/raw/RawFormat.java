package org.sonatype.nexus.repository.raw;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.Format;

/**
 * RAW repository format.
 *
 * @since 3.0
 */
@Named(RawFormat.NAME)
@Singleton
public class RawFormat
  extends Format
{
  public static final String NAME = "raw";

  public RawFormat() {
    super(NAME);
  }
}
