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
 * Sign-in window.
 *
 * @since 3.0
 */
Ext.define('NX.view.SignIn', {
  extend: 'Ext.window.Window',
  alias: 'widget.nx-signin',

  title: NX.app.PluginStrings.GLOBAL_SIGN_IN_TITLE,

  layout: 'fit',
  autoShow: true,
  modal: true,
  constrain: true,
  width: 320,
  defaultFocus: 'username',
  resizable: false,

  /**
   * @protected
   */
  initComponent: function () {
    var me = this;

    Ext.apply(this, {
      items: {
        xtype: 'form',
        bodyPadding: 10,
        defaultType: 'textfield',
        defaults: {
          labelAlign: 'left',
          labelWidth: 100,
          anchor: '100%'
        },
        items: [
          {
            name: 'username',
            itemId: 'username',
            fieldLabel: NX.app.PluginStrings.GLOBAL_SIGN_IN_USERNAME,
            emptyText: NX.app.PluginStrings.GLOBAL_SIGN_IN_USERNAME_PLACEHOLDER,
            allowBlank: false,
            validateOnBlur: false // allow cancel to be clicked w/o validating this to be non-blank
          },
          {
            name: 'password',
            itemId: 'password',
            inputType: 'password',
            fieldLabel: NX.app.PluginStrings.GLOBAL_SIGN_IN_PASSWORD,
            emptyText: NX.app.PluginStrings.GLOBAL_SIGN_IN_PASSWORD_PLACEHOLDER,
            allowBlank: false,
            validateOnBlur: false // allow cancel to be clicked w/o validating this to be non-blank
          },
          {
            xtype: 'checkbox',
            fieldLabel: NX.app.PluginStrings.GLOBAL_SIGN_IN_REMEMBER_ME,
            name: 'remember'
          }
        ],

        buttonAlign: 'left',
        buttons: [
          { text: NX.app.PluginStrings.GLOBAL_SIGN_IN_SUBMIT, action: 'signin', formBind: true, bindToEnter: true, ui: 'primary' },
          { text: NX.app.PluginStrings.GLOBAL_SIGN_IN_CANCEL, handler: me.close, scope: me }
        ]
      }
    });

    me.callParent();
  }

});
