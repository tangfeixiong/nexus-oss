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
 * A single panel in a drilldown series
 *
 * @since 3.0
 */

Ext.define('NX.view.drilldown.Item', {
  extend: 'Ext.panel.Panel',
  alias: 'widget.nx-drilldown-item',

  itemName: 'item',
  itemClass: null,
  itemBookmark: null,

  layout: 'fit',

  /**
   * Set the name of this drilldown item (appears in the breadcrumb)
   */
  setItemName: function(text) {
    this.itemName = text;
  },

  /**
   * Set the icon class of this drilldown item (appears in the breadcrumb)
   */
  setItemClass: function(cls) {
    this.itemClass = cls;
  },

  /**
   * Set the page to load when the breadcrumb segment associated with this drilldown item is clicked
   */
  setItemBookmark: function(bookmark, scope) {
    this.itemBookmark = {
      obj: bookmark,
      scope: scope
    };
  }
});