# Chapter 10 – SAML

This directory contains the Java companion examples for **Chapter 10 – SAML** of the book:

**Practical Application Security**  
*A Software Engineer's Guide to Secure Development, Authentication, Cryptography, and AI-Assisted Security Engineering*

The examples demonstrate fundamental SAML concepts including:

- SAML assertion structure
- Parsing SAML XML
- Base64 encoding and decoding
- XML digital signatures
- SAML Single Sign-On (SSO) flow
- Assertion validation
- Access granted and access denied decisions

The examples are intentionally simplified so that the security concepts can be understood without introducing the complexity of a full production SAML implementation.

---

## Project Structure

```text
chapter-10/
├── pom.xml
├── README.md
│
├── create-saml-assertion/
│   └── CreateSAMLAssertion.java
│
├── parse-saml-assertion/
│   └── ParseSAMLAssertion.java
│
├── saml-base64-encoding/
│   └── SAMLBase64Encoding.java
│
├── xml-digital-signature/
│   └── XMLDigitalSignatureExample.java
│
└── saml-flow-simulation/
    └── SAMLFlowSimulation.java
```

The project uses a **single root `pom.xml`**. The five example directories are added as source directories by the Maven build.

---

# Requirements

The examples require:

- Java 17 or later
- Maven 3.x

The project uses standard Java XML, cryptography, and digital-signature APIs available in the JDK.

No external SAML framework is required for these examples.

---

# Build the Project

Run the commands from the `chapter-10` directory.

Build all examples:

```bash
mvn clean package
```

A successful build produces:

```text
target/
├── classes/
└── chapter-10-saml-1.0.0.jar
```

The compiled JAR contains the five example classes.

---

# Example 1 – Create a SAML Assertion

Directory:

```text
create-saml-assertion/
```

Source:

```text
CreateSAMLAssertion.java
```

Run:

```bash
mvn exec:java -Dexec.mainClass=CreateSAMLAssertion
```

This example creates a simplified SAML assertion containing elements such as:

- Issuer
- Subject
- NameID
- Conditions
- AudienceRestriction
- Audience
- AuthnStatement
- AttributeStatement

The assertion includes dynamically generated timestamps.

Example:

```text
=== Simplified SAML Assertion ===
<saml:Assertion ...>
    <saml:Issuer>https://idp.example.com</saml:Issuer>

    <saml:Subject>
        <saml:NameID>john.doe@example.com</saml:NameID>
    </saml:Subject>

    ...
</saml:Assertion>
```

This example focuses on understanding the basic structure of a SAML assertion.

---

# Example 2 – Parse a SAML Assertion

Directory:

```text
parse-saml-assertion/
```

Source:

```text
ParseSAMLAssertion.java
```

Run:

```bash
mvn exec:java -Dexec.mainClass=ParseSAMLAssertion
```

The example parses a simplified SAML assertion and extracts values such as:

- Issuer
- Subject
- Attributes

Example output:

```text
=== Parsed SAML Assertion ===
Issuer: https://idp.example.com
Subject: john.doe@example.com
email: john.doe@example.com
department: Engineering
```

The XML parser is configured with basic security hardening, including protection against external entity processing.

This demonstrates an important principle when processing security-sensitive XML:

> XML received from an external source should not be parsed using an unsafe default configuration.

---

# Example 3 – SAML Base64 Encoding

Directory:

```text
saml-base64-encoding/
```

Source:

```text
SAMLBase64Encoding.java
```

Run:

```bash
mvn exec:java -Dexec.mainClass=SAMLBase64Encoding
```

The example demonstrates:

```text
SAML XML
   ↓
Base64 Encoding
   ↓
Base64 Value
   ↓
Base64 Decoding
   ↓
Original SAML XML
```

The program prints the original XML, encoded value, and decoded XML.

It also demonstrates an important distinction:

```text
Base64 = Encoding
Base64 ≠ Encryption
```

Base64 does not provide confidentiality or cryptographic protection.

---

# Example 4 – XML Digital Signature

Directory:

```text
xml-digital-signature/
```

Source:

```text
XMLDigitalSignatureExample.java
```

Run:

```bash
mvn exec:java -Dexec.mainClass=XMLDigitalSignatureExample
```

This example demonstrates XML digital signing and verification using the Java XML Digital Signature API.

The example:

1. Generates an RSA key pair.
2. Creates a simplified XML assertion.
3. Calculates a SHA-256 digest.
4. Signs the XML using RSA-SHA256.
5. Adds the XML digital signature.
6. Validates the signature using the public key.
7. Prints the signed XML.

The expected result includes:

```text
Signature valid: true
```

The example uses:

```text
RSA
SHA-256
RSA-SHA256
Exclusive XML Canonicalization
Enveloped Signature Transform
```

The RSA key pair is generated dynamically for demonstration purposes.

In a production SAML deployment, the Service Provider would establish trust in the Identity Provider's configured signing key or certificate rather than simply generating a key pair for each execution.

---

# Example 5 – SAML SSO Flow Simulation

Directory:

```text
saml-flow-simulation/
```

Source:

```text
SAMLFlowSimulation.java
```

Run:

```bash
mvn exec:java -Dexec.mainClass=SAMLFlowSimulation
```

This is the end-to-end example.

Rather than simply printing a predefined SAML flow, the program dynamically:

1. Creates a SAML assertion.
2. Generates an RSA key pair.
3. Signs the assertion.
4. Validates the digital signature.
5. Validates the issuer.
6. Validates the audience.
7. Validates the assertion validity period.
8. Makes an access-control decision.

The example runs three scenarios automatically.

---

## Scenario 1 – Valid Assertion

The assertion contains the expected:

- Issuer
- Audience
- Validity period
- Digital signature

The expected result is:

```text
Signature: VALID
Issuer: VALID
Audience: VALID
Validity period: VALID

Access granted to: john.doe@example.com
```

---

## Scenario 2 – Invalid Audience

The assertion is correctly signed, but it is intended for a different Service Provider.

The expected result is:

```text
Signature: VALID
Issuer: VALID
Audience: INVALID
Validity period: VALID

Access denied.
```

This demonstrates an important security concept:

> A valid digital signature does not automatically make a SAML assertion acceptable.

The Service Provider must also validate security conditions such as the intended audience.

---

## Scenario 3 – Expired Assertion

The assertion is correctly signed, but its validity period has expired.

The expected result is:

```text
Signature: VALID
Issuer: VALID
Audience: VALID
Validity period: INVALID

Access denied.
```

This demonstrates why validating the assertion's time conditions is important.

---

# SAML Validation Demonstrated by the Example

The final example can be viewed conceptually as:

```text
SAML Assertion
      |
      v
Verify Digital Signature
      |
      v
Validate Issuer
      |
      v
Validate Audience
      |
      v
Validate Time Conditions
      |
      v
All Checks Pass?
   /        \
 Yes        No
  |          |
  v          v
Access      Access
Granted     Denied
```

This is a simplified educational model of the validation process.

A real SAML implementation performs additional protocol and security checks appropriate to the SAML profile and deployment.

---

# Running Individual Examples

All examples are executed from the `chapter-10` directory because the project uses one root Maven `pom.xml`.

For example:

```bash
mvn exec:java -Dexec.mainClass=CreateSAMLAssertion
```

```bash
mvn exec:java -Dexec.mainClass=ParseSAMLAssertion
```

```bash
mvn exec:java -Dexec.mainClass=SAMLBase64Encoding
```

```bash
mvn exec:java -Dexec.mainClass=XMLDigitalSignatureExample
```

```bash
mvn exec:java -Dexec.mainClass=SAMLFlowSimulation
```

---

# Important Security and Production Note

These examples are designed for **learning and demonstration**.

They are not intended to be used as a production SAML Identity Provider or Service Provider.

A production implementation should use an established and actively maintained SAML library and should follow the relevant SAML specifications and security guidance.

Production implementations need to address additional concerns including, where applicable:

- Trusted Identity Provider configuration
- Signing certificates and key management
- Assertion signature validation
- Issuer validation
- Audience validation
- Recipient and destination validation
- Assertion time conditions
- Replay protection
- Secure XML processing
- SAML profile and binding requirements
- Metadata validation and management
- Secure session management
- Key rotation
- Certificate expiration and renewal
- Logging and monitoring
- Library and dependency updates

The examples intentionally implement only a small subset of the concepts so that the underlying security mechanisms remain easy to understand.

---

# Relationship to Chapter 10

The examples correspond to the concepts discussed in:

**Chapter 10 – SAML**

The progression is intentional:

```text
Create SAML Assertion
        ↓
Parse SAML Assertion
        ↓
Understand Base64 Encoding
        ↓
Understand XML Digital Signatures
        ↓
Combine the Concepts
        ↓
Validate the Assertion
        ↓
Grant or Deny Access
```

This allows the reader to study each concept independently and then see how the concepts work together in a simplified SAML SSO flow.

---

# Source Code

The source code accompanies the book:

**Practical Application Security**  
*A Software Engineer's Guide to Secure Development, Authentication, Cryptography, and AI-Assisted Security Engineering*

Author:

**Sanjay Ghosh**

Website:

https://clockwiseknowledge.com/se/

---

# License

The source code is provided as companion material for the book.

See the repository license for the applicable terms of use.
