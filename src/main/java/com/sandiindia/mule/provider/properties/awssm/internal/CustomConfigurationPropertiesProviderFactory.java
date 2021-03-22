/*
 * (c) 2003-2018 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package com.sandiindia.mule.provider.properties.awssm.internal;

import static com.sandiindia.mule.provider.properties.awssm.internal.AWSSMPropertiesProviderExtension.AWSSM_PROPERTIES_PROVIDER;

import java.io.StringReader;
import java.util.Optional;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.mule.runtime.api.component.ComponentIdentifier;
import org.mule.runtime.config.api.dsl.model.ConfigurationParameters;
import org.mule.runtime.config.api.dsl.model.ResourceProvider;
import org.mule.runtime.config.api.dsl.model.properties.ConfigurationPropertiesProvider;
import org.mule.runtime.config.api.dsl.model.properties.ConfigurationPropertiesProviderFactory;
import org.mule.runtime.config.api.dsl.model.properties.ConfigurationProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.http.nio.netty.NettyNioAsyncHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerAsyncClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;
import software.amazon.awssdk.services.secretsmanager.model.SecretsManagerException;

/**
 * Builds the provider for a custom-properties-provider:config element.
 *
 * @since 1.0
 */
public class CustomConfigurationPropertiesProviderFactory implements ConfigurationPropertiesProviderFactory {

	public static final String EXTENSION_NAMESPACE = "aws-sm-properties-provider";
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomConfigurationPropertiesProviderFactory.class);
	private final static String CUSTOM_PROPERTIES_PREFIX = "aws-sm-getSecretValue::";

	@Override
	public ComponentIdentifier getSupportedComponentIdentifier() {
		return AWSSM_PROPERTIES_PROVIDER;
	}

	private AwsCredentialsProvider getSDKCredProvider(String credType) {
		return ProfileCredentialsProvider.create();
	}

	public String getAWSSecretValue(String region, String secretName, String key, String authType) {

		Region REG = Region.of(region);

		SecretsManagerAsyncClient secretsClient = SecretsManagerAsyncClient.builder().region(REG)
				.httpClient(NettyNioAsyncHttpClient.builder().build()).credentialsProvider(getSDKCredProvider(authType))
				.build();

		try {
			LOGGER.debug("Looking for secret " + secretName + " **** Key ***** " + key);
			GetSecretValueRequest valueRequest = GetSecretValueRequest.builder().secretId(secretName).build();

			GetSecretValueResponse valueResponse;
			try {
				valueResponse = secretsClient.getSecretValue(valueRequest).get();
				String secretData = valueResponse.secretString();
				JsonReader jsonReader = Json.createReader(new StringReader(secretData));
				JsonObject secretObject = jsonReader.readObject();
				String secret = secretObject.getString(key);
				return secret;
			} catch (Exception e) {
				LOGGER.error("****Error while loading secret*****", e);
			}

			return null;

		} catch (SecretsManagerException e) {
			LOGGER.error("Error while loading secret", e);
			return null;
		}
	}

	@Override
	public ConfigurationPropertiesProvider createProvider(ConfigurationParameters parameters,
			ResourceProvider externalResourceProvider) {

		// This is how you can access the configuration parameter of the
		// <custom-properties-provider:config> element.
//    String accessKeyValue = parameters.getStringParameter("accessKey");
//    String secretKeyValue = parameters.getStringParameter("accessKey");

		LOGGER.info("**********Creating AWS Secret Manager Properties Provider*********");
		String awsRegion = parameters.getStringParameter("region");
		String authType = parameters.getStringParameter("authType");
		LOGGER.debug("Region" + awsRegion);
		LOGGER.debug("Auth Type" + authType);

		return new ConfigurationPropertiesProvider() {

			@Override
			public Optional<ConfigurationProperty> getConfigurationProperty(String configurationAttributeKey) {
				if (configurationAttributeKey.startsWith(CUSTOM_PROPERTIES_PREFIX)) {
					String effectiveKey = configurationAttributeKey.substring(CUSTOM_PROPERTIES_PREFIX.length());
					String[] secretParts = effectiveKey.split(":");
					
					String propertyValue = getAWSSecretValue(awsRegion, secretParts[0], secretParts[1], authType);
					if (effectiveKey != null) {
						return Optional.of(new ConfigurationProperty() {

							@Override
							public Object getSource() {
								return "custom provider source";
							}

							@Override
							public Object getRawValue() {
								return propertyValue;
							}

							@Override
							public String getKey() {
								return effectiveKey;
							}
						});
					}
				}
				return Optional.empty();
			}

			@Override
			public String getDescription() {
				// TODO change to a meaningful name for error reporting.
				return "AWS Secret Manager Properties Provider";
			}
		};
	}

}
