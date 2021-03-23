# Mule 4 Custom Properties Provider for AWS Secrets Manager

This mule custom properties extension allows Mule applications to retrive sensitive configuration properties such as keystore passwords, database password etc directly from AWS secrets manager. The key benifit of this approach is that you don't have to put these sensitive property value in clear text in the configuration files, which is a major security issue. Some of the other benefit of this approach are:
-  You can make use of life cycle lambda functions or any other custom process to automatically rotate the secrets in an automated way
- No need to manage encryption keys as we have to if we use secure configuration properties

The down side is ofcourse this solution requires access to AWS secrets manager
