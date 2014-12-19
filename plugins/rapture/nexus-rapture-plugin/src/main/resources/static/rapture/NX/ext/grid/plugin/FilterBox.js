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
/*global Ext*/

/**
 * Filter plugin for grids.
 *
 * @since 3.0
 */
Ext.define('NX.ext.grid.plugin.FilterBox', {
  extend: 'NX.ext.grid.plugin.Filtering',
  alias: 'plugin.gridfilterbox',

  /**
   * @cfg {String} emptyText Text to be used as grid empty text when no records are matching the filter. If text
   * contains "${filter}" it will be replaced with filter value.
   */

  /**
   * @override
   */
  init: function (grid) {
    var me = this,
        tbar = grid.getDockedItems('toolbar[dock="top"]')[0],
        items = [
          '->',
          {
            xtype: 'nx-searchbox',
            emptyText: NX.I18n.get('GLOBAL_FILTER_PLACEHOLDER'),
            searchDelay: 200,
            width: 200,
            listeners: {
              search: me.onSearch,
              searchcleared: me.onSearchCleared,
              scope: me
            }
          }
        ];

    me.callParent(arguments);

    if (tbar) {
      tbar.add(items);
    }
    else {
      grid.addDocked([
        {
          xtype: 'toolbar',
          dock: 'top',
          items: items
        }
      ]);
    }

    me.grid.on('filteringautocleared', me.syncSearchBox, me);
  },

  /**
   * @private
   * Filter grid.
   */
  onSearch: function (searchbox, value) {
    var me = this;

    if (me.emptyText) {
      me.grid.getView().emptyText = '<div class="x-grid-empty">' + me.emptyText.replace(/\$filter/, value) + '</div>';
    }
    me.filter(value);
  },

  /**
   * @private
   * Clear filtering on grid.
   */
  onSearchCleared: function () {
    var me = this;

    if (me.grid.emptyText) {
      me.grid.getView().emptyText = '<div class="x-grid-empty">' + me.grid.emptyText + '</div>';
    }
    me.clearFilter();
  },

  /**
   * @private
   * Syncs filtering value with search box.
   */
  syncSearchBox: function () {
    var me = this;

    me.grid.down('nx-searchbox').setValue(me.filterValue);
  }

});