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
 * Help controller.
 *
 * @since 3.0
 */
Ext.define('NX.controller.Help', {
  extend: 'Ext.app.Controller',
  requires: [
    'NX.Icons',
    'NX.Windows'
  ],
  mixins: {
    logAware: 'NX.LogAware'
  },

  views: [
    'header.Help',
    'AboutWindow'
  ],

  refs: [
    {
      ref: 'featureHelp',
      selector: 'nx-header-help menuitem[action=feature]'
    }
  ],

  /**
   * @private
   */
  baseUrl: 'http://links.sonatype.com/products/nexus',

  /**
   * @private
   * @type {NX.model.Feature}
   */
  selectedFeature: undefined,

  /**
   * @override
   */
  init: function () {
    var me = this;

    me.getApplication().getIconController().addIcons({
      'help-support': {
        file: 'support.png',
        variants: ['x16', 'x32']
      },
      'help-issues': {
        file: 'bug.png',
        variants: ['x16', 'x32']
      },
      'help-manual': {
        file: 'book_picture.png',
        variants: ['x16', 'x32']
      },
      'help-community': {
        file: 'users_4.png',
        variants: ['x16', 'x32']
      },
      'help-kb': {
        file: 'brain_trainer.png',
        variants: ['x16', 'x32']
      }
    });

    me.listen({
      controller: {
        '#Menu': {
          featureselected: me.onFeatureSelected
        }
      },
      component: {
        'nx-header-help menuitem[action=feature]': {
          click: me.onFeatureHelp
        },
        'nx-header-help menuitem[action=about]': {
          click: me.onAbout
        },
        'nx-header-help menuitem[action=docs]': {
          click: me.onDocs
        },
        'nx-header-help menuitem[action=support]': {
          click: me.onSupport
        },
        'nx-header-help menuitem[action=issues]': {
          click: me.onIssues
        },
        'nx-header-help menuitem[action=community]': {
          click: me.onCommunity
        },
        'nx-header-help menuitem[action=kb]': {
          click: me.onKnowledgeBase
        }
      }
    });
  },

  /**
   * @private
   * Update help menu content.
   * @param {NX.model.Feature} feature selected feature
   */
  onFeatureSelected: function (feature) {
    var me = this,
        text = feature.get('text'),
        iconName = feature.get('iconName'),
        featureHelp = me.getFeatureHelp();

    me.selectedFeature = feature;

    featureHelp.setText(NX.app.PluginStrings.GLOBAL_HEADER_HELP_FEATURE + text);
    featureHelp.setIconCls(NX.Icons.cls(iconName, 'x16'));
  },

  /**
   * @private
   * @param {String} section
   */
  openUrl: function(section) {
    NX.Windows.open(this.baseUrl + '/' + section);
  },

  /**
   * @private
   */
  onFeatureHelp: function() {
    var me = this,
        keyword = me.selectedFeature.get('helpKeyword'),
        url = me.baseUrl + '/docs-search/' + NX.State.getVersionMajorMinor() + '/' + keyword;

    NX.Windows.open(url);
  },

  /**
   * @private
   */
  onAbout: function() {
    Ext.widget('nx-aboutwindow');
  },

  /**
   * @private
   */
  onDocs: function() {
    NX.Windows.open(this.baseUrl + '/docs/' + NX.State.getVersionMajorMinor());
  },

  /**
   * @private
   */
  onSupport: function() {
    this.openUrl('support');
  },

  /**
   * @private
   */
  onIssues: function() {
    this.openUrl('issues');
  },

  /**
   * @private
   */
  onCommunity: function() {
    this.openUrl('community');
  },

  /**
   * @private
   */
  onKnowledgeBase: function() {
    this.openUrl('kb');
  }
});