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
/*global Ext, NX*/

/**
 * Add repository window.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.view.repository.RepositoryAdd', {
  extend: 'NX.view.AddWindow',
  alias: 'widget.nx-coreui-repository-add',
  requires: [
    'NX.Conditions'
  ],

  editableMarker: 'You do not have permission to create repositories',

  defaultFocus: 'id',

  initComponent: function () {
    var me = this;

    me.editableCondition = NX.Conditions.isPermitted('nexus:repositories', 'create');

    me.callParent(arguments);

    me.down('#id').setReadOnly(false);

    me.down('form').add({
      xtype: 'hiddenfield',
      name: 'template',
      value: me.template.id
    });
  }
});
