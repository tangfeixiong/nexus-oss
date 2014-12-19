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
 * Application strings
 *
 * @since 3.0
 */
Ext.define('NX.app.PluginStrings', {
  singleton: true,
  requires: [
    'NX.I18n'
  ],

  keys: {

    /*
     * Global strings
     */

    // Header
    GLOBAL_HEADER_TITLE: 'Sonatype Nexus>',
    GLOBAL_HEADER_BROWSE_TOOLTIP: 'Browse Server Contents>',
    GLOBAL_HEADER_ADMIN_TOOLTIP: 'Server administration and configuration>',
    GLOBAL_HEADER_SEARCH_PLACEHOLDER: 'Search…>',
    GLOBAL_HEADER_REFRESH_TOOLTIP: 'Refresh current view and data>',
    GLOBAL_HEADER_USER_TOOLTIP: 'User profile and options>',
    GLOBAL_HEADER_SIGN_IN: 'Sign In>',
    GLOBAL_HEADER_SIGN_IN_TOOLTIP: 'Have an account?>',
    GLOBAL_HEADER_SIGN_OUT_TOOLTIP: 'Sign out>',
    GLOBAL_HEADER_HELP_TOOLTIP: 'Help>',
    GLOBAL_HEADER_HELP_FEATURE: 'Help for:>',
    GLOBAL_HEADER_HELP_ABOUT: 'About>',
    GLOBAL_HEADER_HELP_DOCUMENTATION: 'Documentation>',
    GLOBAL_HEADER_HELP_KB: 'Knowledge Base>',
    GLOBAL_HEADER_HELP_COMMUNITY: 'Community>',
    GLOBAL_HEADER_HELP_ISSUES: 'Issue Tracker>',
    GLOBAL_HEADER_HELP_SUPPORT: 'Support>',

    // Footer
    GLOBAL_FOOTER_COPYRIGHT: 'Sonatype Nexus™ © Copyright Sonatype, Inc.>',

    // Sign in
    GLOBAL_SIGN_IN_TITLE: 'Sign In>',
    GLOBAL_SIGN_IN_USERNAME: 'Username>',
    GLOBAL_SIGN_IN_USERNAME_PLACEHOLDER: 'enter your username>',
    GLOBAL_SIGN_IN_PASSWORD: 'Password>',
    GLOBAL_SIGN_IN_PASSWORD_PLACEHOLDER: 'enter your password>',
    GLOBAL_SIGN_IN_REMEMBER_ME: 'Remember me>',
    GLOBAL_SIGN_IN_SUBMIT: 'Sign In>',
    GLOBAL_SIGN_IN_CANCEL: 'Cancel>',

    /*
     * Features
     */

    // Welcome
    //BROWSE_WELCOME_TITLE: 'Welcome>',
    BROWSE_WELCOME_SUBTITLE: 'Welcome to Sonatype Nexus!>',

    // Search
    //BROWSE_SEARCH_TITLE: 'Search>',
    BROWSE_SEARCH_SUBTITLE: 'Search for components>',
    BROWSE_SEARCH_COMPONENTS_CRITERIA_KEYWORD: 'Keyword>',
    BROWSE_SEARCH_COMPONENTS_CRITERIA_ARTIFACT_ID: 'Artifact ID>',
    BROWSE_SEARCH_COMPONENTS_CRITERIA_CLASSIFIER: 'Classifier>',
    BROWSE_SEARCH_COMPONENTS_CRITERIA_CLASS_NAME: 'Class name>',
    BROWSE_SEARCH_COMPONENTS_CRITERIA_FORMAT: 'Format>',
    BROWSE_SEARCH_COMPONENTS_CRITERIA_GROUP_ID: 'Group ID>',
    BROWSE_SEARCH_COMPONENTS_CRITERIA_PACKAGING: 'Packaging>',
    BROWSE_SEARCH_COMPONENTS_CRITERIA_SHA_1: 'SHA-1>',
    BROWSE_SEARCH_COMPONENTS_CRITERIA_VERSION: 'Version>',
    BROWSE_SEARCH_COMPONENTS_CRITERIA_FILTER_PLACEHOLDER: 'any>',
    BROWSE_SEARCH_COMPONENTS_COMPONENT_COLUMN: 'Component>',
    BROWSE_SEARCH_COMPONENTS_EMPTY_STATE: 'No results>',
    BROWSE_SEARCH_VERSIONS_GROUP: 'Group>',
    BROWSE_SEARCH_VERSIONS_NAME: 'Name>',
    BROWSE_SEARCH_VERSIONS_FORMAT: 'Format>',
    BROWSE_SEARCH_VERSIONS_POPULAR: 'Most popular version>',
    BROWSE_SEARCH_VERSIONS_VERSION_COLUMN: 'Version>',
    BROWSE_SEARCH_VERSIONS_FILE_COLUMN: 'File>',
    BROWSE_SEARCH_VERSIONS_REPOSITORY_COLUMN: 'Repository>',
    BROWSE_SEARCH_VERSIONS_AGE_COLUMN: 'Age>',
    BROWSE_SEARCH_VERSIONS_POPULARITY_COLUMN: 'Popularity>',
    BROWSE_SEARCH_VERSIONS_SECURITY_COLUMN: 'Security>',
    BROWSE_SEARCH_VERSIONS_LICENSE_COLUMN: 'License>',
    BROWSE_SEARCH_DETAILS_INFO_TAB: 'Info>',
    BROWSE_SEARCH_DETAILS_MAVEN_TAB: 'Maven>',
    BROWSE_SEARCH_INFO_PATH: 'Path>',
    BROWSE_SEARCH_MAVEN_GROUP: 'Group>',
    BROWSE_SEARCH_MAVEN_ARTIFACT: 'Artifact>',
    BROWSE_SEARCH_MAVEN_VERSION: 'Version>',
    BROWSE_SEARCH_MAVEN_CLASSIFIER: 'Classifier>',
    BROWSE_SEARCH_MAVEN_EXTENSION: 'Extension>',
    BROWSE_SEARCH_MAVEN_XML: 'XML>'
    //BROWSE_SEARCH_COMPONENT_INFO_LICENSE_SECTION: 'License Analysis>',
    //BROWSE_SEARCH_COMPONENT_INFO_THREAT_LEVEL_COLUMN: 'Threat Level>',
    //BROWSE_SEARCH_COMPONENT_INFO_DECLARED_LICENSES_COLUMN: 'Declared License(s)>',
    //BROWSE_SEARCH_COMPONENT_INFO_OBSERVED_LICENSES_COLUMN: 'Observed License(s) in Source>',
    //BROWSE_SEARCH_COMPONENT_INFO_SECURITY_SECTION: 'Security Issues>',
    //BROWSE_SEARCH_COMPONENT_INFO_THREAT_LEVEL_COLUMN: 'Threat Level>',
    //BROWSE_SEARCH_COMPONENT_INFO_PROBLEM_CODE_COLUMN: 'Problem Code>',
    //BROWSE_SEARCH_COMPONENT_INFO_SUMMARY_COLUMN: 'Summary>',

    // Feeds
  }
}, function(obj) {
  NX.I18n.register(obj.keys);
});

