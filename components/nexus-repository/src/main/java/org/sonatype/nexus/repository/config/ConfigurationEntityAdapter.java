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
package org.sonatype.nexus.repository.config;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.orient.OClassNameBuilder;
import org.sonatype.sisu.goodies.common.ComponentSupport;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OSchema;
import com.orientechnologies.orient.core.metadata.schema.OType;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link Configuration} entity adapter.
 *
 * @since 3.0
 */
@Named
@Singleton
public class ConfigurationEntityAdapter
  extends ComponentSupport
{
  public static final String DB_CLASS = new OClassNameBuilder()
      .prefix("repository")
      .type(Configuration.class)
      .build();

  public static final String P_RECIPE_NAME = "recipeName";

  public static final String P_REPOSITORY_NAME = "repositoryName";

  public static final String P_ATTRIBUTES = "attributes";

  public OClass register(final ODatabaseDocumentTx db) {
    checkNotNull(db);

    OSchema schema = db.getMetadata().getSchema();
    OClass type = schema.getClass(DB_CLASS);
    if (type == null) {
      type = schema.createClass(DB_CLASS);

      type.createProperty(P_RECIPE_NAME, OType.STRING).setNotNull(true);
      type.createProperty(P_REPOSITORY_NAME, OType.STRING).setNotNull(true);
      type.createProperty(P_ATTRIBUTES, OType.EMBEDDEDMAP);

      log.info("Created schema: {}, properties: {}", type, type.properties());
    }

    return type;
  }
}
