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

package org.sonatype.security.realms.ldap.internal.persist.orient;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.orient.OClassNameBuilder;
import org.sonatype.security.realms.ldap.internal.persist.entity.Connection;
import org.sonatype.security.realms.ldap.internal.persist.entity.LdapConfiguration;
import org.sonatype.security.realms.ldap.internal.persist.entity.Mapping;
import org.sonatype.sisu.goodies.common.ComponentSupport;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OClass.INDEX_TYPE;
import com.orientechnologies.orient.core.metadata.schema.OSchema;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link LdapConfiguration} entity adapter.
 *
 * @since 3.0
 */
@Named
@Singleton
public class LdapConfigurationEntityAdapter
    extends ComponentSupport
{
  private static final String DB_CLASS = new OClassNameBuilder()
      .prefix("ldap")
      .type(LdapConfiguration.class)
      .build();

  private static final String DB_CONNECTION_CLASS = new OClassNameBuilder()
      .prefix("ldap")
      .type(Connection.class)
      .build();

  private static final String DB_MAPPING_CLASS = new OClassNameBuilder()
      .prefix("ldap")
      .type(Mapping.class)
      .build();

  private static final String P_ID = "id";

  private static final String P_NAME = "name";

  private static final String P_ORDER = "order";

  private static final String P_CONNECTION = "connection";

  private static final String P_MAPPING = "mapping";

  public OClass register(final ODatabaseDocumentTx db) {
    checkNotNull(db);
    OSchema schema = db.getMetadata().getSchema();
    OClass type = schema.getClass(DB_CLASS);
    if (type == null) {
      // connection
      OClass connectionType = schema.createClass(DB_CONNECTION_CLASS);
      // TODO

      // mapping
      OClass mappingType = schema.createClass(DB_MAPPING_CLASS);
      // TODO

      // ldapConfiguration
      type = schema.createClass(DB_CLASS);
      type.createProperty(P_ID, OType.STRING).setNotNull(true);
      type.createProperty(P_NAME, OType.STRING).setNotNull(true);
      type.createProperty(P_ORDER, OType.INTEGER).setNotNull(true);
      type.createProperty(P_CONNECTION, OType.EMBEDDED, connectionType).setNotNull(true);
      type.createProperty(P_MAPPING, OType.EMBEDDED, mappingType).setNotNull(true);

      type.createIndex(DB_CLASS + "_" + P_ID + "idx", INDEX_TYPE.UNIQUE, P_ID);
      type.createIndex(DB_CLASS + "_" + P_NAME + "idx", INDEX_TYPE.UNIQUE, P_NAME);

      log.info("Created schema: {}, properties: {}", type, type.properties());
    }
    return type;
  }

  public ODocument selectById(final ODatabaseDocumentTx db, final String id) {
    checkNotNull(db);
    checkNotNull(id);
    final OSQLSynchQuery<ODocument> query = new OSQLSynchQuery<>("SELECT FROM " + DB_CLASS + " WHERE " + P_ID + " = ?");
    final List<ODocument> results = db.command(query).execute(id);
    if (!results.isEmpty()) {
      return results.get(0);
    }
    return null;
  }

  public Iterable<ODocument> browse(final ODatabaseDocumentTx db) {
    checkNotNull(db);
    return db.browseClass(DB_CLASS);
  }

  public LdapConfiguration read(final ODocument document) {
    checkNotNull(document);
    LdapConfiguration entity = new LdapConfiguration();
    entity.setId(document.<String>field(P_ID, OType.STRING));
    entity.setName(document.<String>field(P_NAME, OType.STRING));
    entity.setOrder(document.<Integer>field(P_ORDER, OType.INTEGER));
    // TODO:
    return entity;
  }

  public ODocument edit(final ODocument document, final LdapConfiguration entity) {
    checkNotNull(document);
    checkNotNull(entity);

    document.field(P_ID, entity.getId());
    document.field(P_NAME, entity.getName());
    document.field(P_ORDER, entity.getOrder());

    return document.save();
  }

  public ODocument add(final ODatabaseDocumentTx db, final LdapConfiguration entity) {
    checkNotNull(db);
    ODocument doc = db.newInstance(DB_CLASS);
    return edit(doc, entity);
  }
}