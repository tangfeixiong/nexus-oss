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
 * Feed grid.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.view.feed.FeedList', {
  extend: 'NX.view.drilldown.Master',
  alias: 'widget.nx-coreui-feed-list',

  store: 'Feed',

  /*
   * @override
   */

  initComponent: function() {
    var me = this;

    me.columns = [
      {
        xtype: 'nx-iconcolumn',
        width: 36,
        iconVariant: 'x16',
        iconName: function () {
          return 'feed-default';
        }
      },
      { header: 'Feed', dataIndex: 'name', flex: 1 },
      { header: 'URL', dataIndex: 'url', flex: 2, xtype: 'nx-linkcolumn' }
    ];

    me.callParent(arguments);
  },

  viewConfig: {
    emptyText: 'No feeds defined',
    deferEmptyText: false
  },

  tbar: [
    {
      xtype: 'button',
      text: 'Subscribe',
      glyph: 'xf09e@FontAwesome' /* fa-rss */,
      action: 'subscribe',
      disabled: true
    }
  ],

  plugins: [
    { ptype: 'gridfilterbox', emptyText: 'No feed matched criteria "$filter"' }
  ]

});
