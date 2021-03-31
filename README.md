# Mule 4 Custom Properties Provider for AWS Secrets Manager

This mule custom properties extension allows Mule applications to retrieve sensitive configuration properties such as keystore passwords, database password etc directly from AWS secrets manager. The key benifit of this approach is that you don't have to put these sensitive property value in clear text in the configuration files, which is a major security issue. Some of the other benefit of this approach are:
-  You can make use of life cycle lambda functions or any other custom process to rotate the secrets in an automated way
- No need to manage encryption keys as we have to if we use secure configuration properties

The down side is ofcourse this solution requires access to AWS secrets manager

![](https://github.com/sandiindia/mule-awssm-extension/blob/v1.0.0/Arch.jpg)

## How it works?
Below example illustrates the usage. 
The **keyPassword** ```"${aws-sm-getSecretValue::keystoreCred:keyPassword}```** and keystore **pasword** ```"${aws-sm-getSecretValue::keystoreCred:keystorePassword}"``` refer to the secret manager defined prefix to fetch the secret from the Secrets manager. 

```xml
	<http:listener-config name="HTTP_Listener_config" doc:name="HTTP Listener config" doc:id="cdd1c593-0d92-4d51-a5cc-1ac3acd60496" >
		<http:listener-connection  protocol="HTTPS" host="0.0.0.0" port="8081" >
			<tls:context >
				<tls:key-store type="jks" keyPassword="${aws-sm-getSecretValue::keystoreCred:keyPassword}" password="${aws-sm-getSecretValue::keystoreCred:keystorePassword}" path="keystore.jks"/>
			</tls:context>
		</http:listener-connection>
	</http:listener-config>
```
Use the following format to get the secret manager specific secret key value

```"${aws-sm-getSecretValue::secretName:secretKey}"```

## Customizing the Module
Follow these steps to customize the extension package name:
1.  Import the  [Secrets Manager Properties Provider project](https://github.com/sandiindia/mule-awssm-extension)  into your favorite IDE. 
2.  Open the  `pom.xml`  file:
    
    1.  Define the GAV (`groupId`,  `artifactId`, and  `version`) of your module.
        
    2.  Define the  `name`  of your module.

3.  Change the package name (`com.sandiindia.mule.provider`) of your code.
    
Install the module locally using  `mvn clean install`  to make the module accessible from Studio.

## Using the Custom Properties Provider in a Mule Application

To use the custom properties provider:

1.  Create an application in Studio.
    
2.  Add the dependency to you new module:
    
    1.  Open the  `pom.xml`  file.
        
    2.  Within the  `<dependencies>`  tag, add a new dependency using the GAV that you put in your module.
        
    3.  Remember to add  `<classifier>mule-plugin</classifier>`  because it is a Mule module.
        
    4.  Save your changes.      

Now, open the application XML file and in the  **Global Elements**  tab and click  **Create**. Under  **Connector Configuration**, you should see an option for selecting the configuration from your custom module, for example
![](https://github.com/sandiindia/mule-awssm-extension/blob/v1.0.0/images/globalelement.PNG)

You can now configure your new component and start using properties with the prefix defined in your module.

![](https://github.com/sandiindia/mule-awssm-extension/blob/v1.0.0/images/config.PNG)
