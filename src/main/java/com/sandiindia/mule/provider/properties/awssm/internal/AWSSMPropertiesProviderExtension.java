package com.sandiindia.mule.provider.properties.awssm.internal;

import org.mule.runtime.extension.api.annotation.Extension;
import org.mule.runtime.api.component.ComponentIdentifier;
import org.mule.runtime.extension.api.annotation.Configurations;
import org.mule.runtime.extension.api.annotation.Export;
import org.mule.runtime.extension.api.annotation.dsl.xml.Xml;
import static org.mule.runtime.api.component.ComponentIdentifier.builder;

/**
 * This is the main class of an extension, is the entry point from which configurations, connection providers, operations
 * and sources are going to be declared.
 */
@Xml(prefix = "aws-sm-properties-provider")
@Extension(name = "aws-sm-properties-provider")
@Configurations(AWSSMPropertiesProviderConfiguration.class)
@Export(classes = CustomConfigurationPropertiesProviderFactory.class,
resources = "META-INF/services/org.mule.runtime.config.api.dsl.model.properties.ConfigurationPropertiesProviderFactory")

public class AWSSMPropertiesProviderExtension {

	public static final String EXTENSION_NAMESPACE = "aws-sm-properties-provider";
    public static final ComponentIdentifier AWSSM_PROPERTIES_PROVIDER = builder().namespace(EXTENSION_NAMESPACE).name("config").build();
}
