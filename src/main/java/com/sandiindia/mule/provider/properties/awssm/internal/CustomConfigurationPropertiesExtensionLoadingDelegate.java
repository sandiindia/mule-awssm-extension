/*
 * (c) 2003-2018 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package com.sandiindia.mule.provider.properties.awssm.internal;

import org.mule.runtime.api.meta.model.declaration.fluent.ExtensionDeclarer;
import org.mule.runtime.extension.api.loader.ExtensionLoadingContext;
import org.mule.runtime.extension.api.loader.ExtensionLoadingDelegate;

/**
 * Declares extension for Secure Properties Configuration module
 *
 * @since 1.0
 */

public class CustomConfigurationPropertiesExtensionLoadingDelegate implements ExtensionLoadingDelegate {

  // TODO replace with you extension name. This must be a meaningful name for this module.
  //  public static final String EXTENSION_NAME = "AWS SM Properties Provider";
  //  public static final String CONFIG_ELEMENT = "config";

  @Override
  public void accept(ExtensionDeclarer extensionDeclarer, ExtensionLoadingContext context) {		
		  
  }

}
