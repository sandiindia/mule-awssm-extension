package com.sandiindia.mule.provider.properties.awssm;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import javax.inject.Inject;

import org.mule.functional.junit4.MuleArtifactFunctionalTestCase;

import org.junit.Test;

public class AWSSMPropertiesProviderOperationsTestCase extends MuleArtifactFunctionalTestCase {

  /**
   * Specifies the mule config xml with the flows that are going to be executed in the tests, this file lives in the test resources.
   */
  @Override
  protected String getConfigFile() {
    return "test-mule-config.xml";
  }

 
  @Inject
  private SMTestObject testObject;

	/*
	 * @Test public void customPropertyProviderSuccessfullyConfigured() {
	 * assertThat(testObject.getValueFromProperty(), is("myCustomParameter")); }
	 */
  
  @Test
  public void customPropertyProviderAWSSecretsManagerTest() {
    assertThat(testObject.getValueFromProperty(), is("keystorepass"));
  }


}
