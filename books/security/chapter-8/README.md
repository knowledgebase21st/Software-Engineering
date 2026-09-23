# Chapter 8 – JSON Web Tokens (JWT)

This directory contains the source code examples for Chapter 8 of:

**Practical Application Security**  
*A Software Engineer's Guide to Secure Development, Authentication, Cryptography, and AI-Assisted Security Engineering*

**Author:** Sanjay Ghosh

---

## Overview

This chapter introduces JSON Web Tokens (JWT) and demonstrates the basic lifecycle of a signed JWT in a Java application.

The examples focus on three fundamental operations:

1. Creating a signed JWT
2. Decoding a JWT
3. Validating a JWT

The examples use the **JJWT** library and Maven.

These examples are intentionally small so that each JWT operation can be studied independently.

---

## Project Structure

```text
chapter-8/
├── pom.xml
├── README.md
│
├── jwt-generation/
│   ├── pom.xml
│   └── src/
│       └── main/
│           └── java/
│               └── jwt/
│                   └── JwtTokenGenerator.java
│
├── jwt-decoding/
│   ├── pom.xml
│   └── src/
│       └── main/
│           └── java/
│               └── jwt/
│                   └── JWTTokenDecoder.java
│
├── jwt-validation/
│   ├── pom.xml
│   └── src/
│       └── main/
│           └── java/
│               └── jwt/
│                   └── JWTValidation.java
│
└── jwt-flow.png
Technologies
Java 17
Maven
JJWT 0.13.0
JWT Examples
1. JWT Generation

The jwt-generation module demonstrates how to create a signed JWT.

The generated token contains claims such as:

iss – issuer
sub – subject
aud – audience
iat – issued-at time
exp – expiration time
jti – JWT ID

The token is signed using an HMAC signing key.

2. JWT Decoding

The jwt-decoding module demonstrates how the header and payload of a JWT can be decoded.

This example intentionally performs only decoding.

Decoding a JWT does not validate the token.

A JWT payload can normally be read without possessing the signing key. Therefore, applications must not treat decoded claims as trusted information until the token has been properly validated.

3. JWT Validation

The jwt-validation module demonstrates how to validate a signed JWT.

The example verifies the token's signature and validates important claims such as:

Issuer
Audience
Expiration

A token that fails validation must not be trusted.

Building All Examples

From the chapter-8 directory, run:

mvn clean package

The root pom.xml is the Maven parent and aggregator. It builds all three modules.

Building an Individual Example

Each example is also an independent Maven module.

For example:

cd jwt-generation
mvn clean package

Similarly:

cd ../jwt-decoding
mvn clean package

and:

cd ../jwt-validation
mvn clean package
Running the Examples

The examples are designed to be run in sequence.

Step 1 – Generate a JWT

Navigate to the jwt-generation directory:

cd jwt-generation

Build the example:

mvn clean package

Run the example:

mvn exec:java

The program generates and displays a signed JWT:

Generated JWT Token:
eyJhbGciOiJIUzI1NiJ9...

Copy the generated JWT because it will be used by the decoding and validation examples.

Step 2 – Decode the JWT

Navigate to the jwt-decoding directory:

cd ../jwt-decoding

Build the example:

mvn clean package

Run the decoder by passing the JWT as a command-line argument:

mvn exec:java -Dexec.args="YOUR_JWT_TOKEN"

For example:

mvn exec:java -Dexec.args="eyJhbGciOiJIUzI1NiJ9..."

The program displays the JWT header and payload.

This example intentionally performs only decoding.

Decoding a JWT does not validate the token.

On Windows PowerShell, the following form can also be used:

mvn exec:java '-Dexec.args=YOUR_JWT_TOKEN'
Step 3 – Validate the JWT

Navigate to the jwt-validation directory:

cd ../jwt-validation

Build the example:

mvn clean package

Run the validator by passing the JWT as a command-line argument:

mvn exec:java -Dexec.args="YOUR_JWT_TOKEN"

For example:

mvn exec:java -Dexec.args="eyJhbGciOiJIUzI1NiJ9..."

The validation example verifies the JWT signature and validates the configured claims.

A valid token is accepted and its claims are displayed.

An invalid, modified, or expired token is rejected.

Decoding vs. Validation

The examples demonstrate an important distinction:

JWT
  ↓
Decode
  ↓
Read Contents

versus:

JWT
  ↓
Validate Signature
  ↓
Validate Claims
  ↓
Trust Token

Decoding allows an application to read the token contents.

Validation determines whether the token can be trusted.

Important Security Note

The signing key used in these examples is intended only for demonstration and learning.

Do not use the example key in a production application.

Production signing keys should be:

Strong and randomly generated
Protected from unauthorized access
Stored using an appropriate secrets or key-management mechanism
Rotated according to the application's security requirements
Never committed to source control

Do not place passwords, private keys, API keys, or other sensitive information in a JWT payload merely because the token is signed.

A signed JWT provides integrity and authenticity of the signed content. It does not automatically provide confidentiality.

Source Code and Book

These examples accompany Chapter 8 – JSON Web Tokens (JWT) of:

Practical Application Security

The chapter explains:

JWT structure
Header, payload, and signature
JWT authentication flow
JWT creation
JWT decoding
JWT validation
Standard claims
Token expiration
Common JWT security mistakes
Secure JWT implementation practices
AI-assisted security engineering
License

The source code is provided for educational purposes as part of the book.

See the repository and book distribution for applicable licensing information.
