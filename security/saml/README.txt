+---------------------------------+------------------------------------------------------------+

| Java Example                    | Description                                                |
+---------------------------------+------------------------------------------------------------+

| CreateSAMLAssertion.java        | Demonstrates how a simplified SAML Assertion can be        |
|                                 | created as an XML document. Introduces the core            |
|                                 | components of a SAML Assertion including the Issuer,       |
|                                 | Subject, Authentication Statement, and Attribute           |
|                                 | Statement.                                                 |
+---------------------------------+------------------------------------------------------------+

| ParseSAMLAssertion.java         | Shows how Java's DOM Parser can parse a SAML Assertion     |
|                                 | and extract important information such as the              |
|                                 | authenticated user, issuer, authentication method, and     |
|                                 | user attributes.                                           |
+---------------------------------+------------------------------------------------------------+

| SAMLBase64Encoding.java         | Demonstrates how SAML Assertions are Base64 encoded before |
|                                 | transmission and decoded by the Service Provider. Also     |
|                                 | explains why Base64 is an encoding mechanism rather than   |
|                                 | an encryption technique.                                   |
+---------------------------------+------------------------------------------------------------+

| XMLDigitalSignatureExample.java | Illustrates the cryptographic principle behind SAML        |
|                                 | Digital Signatures. Demonstrates RSA key generation,       |
|                                 | digital signing, signature verification, and explains how  |
|                                 | Identity Providers prove the authenticity of a SAML        |
|                                 | Assertion.                                                 |
+---------------------------------+------------------------------------------------------------+

| SAMLFlowSimulation.java         | Provides a step-by-step simulation of a complete SAML      |
|                                 | Single Sign-On (SSO) authentication process, beginning     |
|                                 | with the user's login request and ending with              |
|                                 | successful access to the protected application.            |
+---------------------------------+------------------------------------------------------------+

