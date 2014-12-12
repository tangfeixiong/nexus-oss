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
 * Capability feature panel.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.view.capability.CapabilityFeature', {
  extend: 'NX.view.drilldown.Drilldown',
  alias: 'widget.nx-coreui-capability-feature',

  iconName: 'capability-default',

  masters: { xtype: 'nx-coreui-capability-list' },

  tabs: [
    { xtype: 'nx-coreui-capability-summary', weight: 10 },
    { xtype: 'nx-coreui-capability-settings', title: 'Settings', weight: 20 },
    { xtype: 'nx-coreui-capability-status', title: 'Status', weight: 30 },
    { xtype: 'nx-coreui-capability-about', title: 'About', weight: 40 }
  ],

  actions: [
    { xtype: 'button', text: 'Delete', action: 'delete', disabled: true, glyph: 'xf056@FontAwesome' /* fa-minus-circle */ },
    '-',
    { xtype: 'button', text: 'Enable', action: 'enable', disabled: true, glyph: 'xf04b@FontAwesome' /* fa-play */ },
    { xtype: 'button', text: 'Disable', action: 'disable', disabled: true, glyph: 'xf04d@FontAwesome' /* fa-stop */ }
  ]
});
