# Chapter 12 — RSA: Public-Key Cryptography

This project contains the Java examples accompanying Chapter 12 of **_Practical Application Security: A Software Engineer's Guide to Secure Development, Authentication, Cryptography, and AI-Assisted Security Engineering_** by Sanjay Ghosh.

The examples demonstrate RSA key-pair generation, encryption, decryption, and digital signatures using the Java Cryptography Architecture (JCA).

## Learning Objectives

By working through these examples, you will learn how to:

- Generate an RSA public/private key pair.
- Encrypt data using RSA-OAEP with SHA-256.
- Decrypt ciphertext using the corresponding private key.
- Create and verify RSA digital signatures using SHA256withRSA.
- Use Java's standard cryptographic APIs rather than implementing cryptographic algorithms manually.

## Prerequisites

- Java Development Kit (JDK) 17 or later.
- Apache Maven 3.8 or later.
- A terminal or command prompt.

Verify your Java and Maven installations:

```bash
java -version
mvn -version
```

## Project Structure

```text
chapter-12/
├── pom.xml
├── .gitignore
├── rsa-common/
│   └── RSAKeyStore.java
├── rsa-key-generation/
│   └── RSAKeyGeneration.java
├── rsa-encryption/
│   └── RSAEncryption.java
├── rsa-decryption/
│   └── RSADecryption.java
└── rsa-digital-signature/
    └── RSADigitalSignature.java
```

The project uses one Maven `pom.xml` to compile the Java examples from their individual source directories.

## Build the Project

Open a terminal in the `chapter-12` directory and run:

```bash
mvn clean package
```

A successful build should end with:

```text
BUILD SUCCESS
```

## Run the Examples

Run the following commands from the project root directory.

### 1. RSA Key Generation

```bash
mvn exec:java -Dexec.mainClass=RSAKeyGeneration
```

Generates a 2048-bit RSA key pair using Java's `KeyPairGenerator` API.

### 2. RSA Encryption

```bash
mvn exec:java -Dexec.mainClass=RSAEncryption
```

Encrypts a sample message using RSA-OAEP with SHA-256 and saves the Base64-encoded ciphertext to `demo-keys/ciphertext.txt`.

The shared key utility creates the key pair if it does not already exist.

### 3. RSA Decryption

```bash
mvn exec:java -Dexec.mainClass=RSADecryption
```

Reads the saved ciphertext, decrypts it using the corresponding private key, and displays the recovered message.

Run the encryption example before running the decryption example.

### 4. RSA Digital Signatures

```bash
mvn exec:java -Dexec.mainClass=RSADigitalSignature
```

Creates a digital signature using `SHA256withRSA` and verifies it using the corresponding public key.

The example verifies the original message and then attempts verification after modifying the message. Verification should succeed for the original content and fail for the modified content.

## Generated Files

The examples create a local `demo-keys/` directory containing:

| File | Purpose |
|---|---|
| `public.key` | Encoded RSA public key |
| `private.key` | Encoded RSA private key |
| `ciphertext.txt` | Base64-encoded ciphertext |

These files are generated at runtime and are excluded from version control through `.gitignore`.

**Important:** The private key is stored unencrypted for demonstration purposes. Do not use this storage approach in production applications.

To reset the demonstration, stop running the examples and remove the `demo-keys/` directory. The next run will generate a new key pair. Previously generated ciphertext will no longer be decryptable with that new key pair.

## Security Considerations

These examples are intended for education and experimentation. They are not a complete production-ready key-management solution.

- **Key protection:** Never commit private keys to source control, expose them in logs, or store them unprotected in production.
- **RSA key size:** The examples use a 2048-bit RSA key for demonstration. Follow current security guidance and organizational requirements when selecting key sizes.
- **Encryption padding:** Use RSA-OAEP with SHA-256 rather than textbook RSA or obsolete padding configurations.
- **Large data:** RSA is not intended for encrypting large files or arbitrary-length messages. In practical applications, use hybrid encryption: encrypt the data with a symmetric algorithm such as AES, then encrypt the symmetric key with the recipient's public key.
- **Digital signatures:** Signing and verification are different from encryption and decryption. A digital signature does not conceal the message, and trust in the signer's identity requires a trusted public-key association.
- **Production applications:** Prefer established cryptographic libraries and secure key-management facilities. Do not implement RSA mathematics yourself for production use.

## Related Learning Resources

- [Understanding RSA: A Simple Guide to Public-Key Math](https://dev.to/sanjayghosh/understanding-rsa-a-simple-guide-to-public-key-math-6g7) — an educational explanation of RSA mathematics.
- [RSA — Software Engineering Knowledge Base](https://clockwiseknowledge.com/se/RSA.html) — additional learning material.

## Author

**Sanjay Ghosh**

Author of _Practical Application Security_.

## License

A license has not yet been specified for this project. Add an appropriate `LICENSE` file before redistributing the code.

