
# Chapter 15: TLS and SSL

Companion source code for *Practical Application Security: A Software Engineer's Guide to Secure Development, Authentication, Cryptography, and AI-Assisted Security Engineering* by Sanjay Ghosh.

This project contains practical Java 17 examples demonstrating TLS configuration, HTTPS connections, TLS socket connections, and handshake information.

## Learning Objectives

The examples demonstrate how to:

- Inspect supported TLS protocols and cipher suites.
- Make HTTPS requests using Java's standard APIs.
- Establish a TLS socket connection.
- Inspect negotiated TLS settings and peer certificate information.
- Understand the role of certificate validation and hostname verification.

## Technologies

- Java 17
- Maven
- Java Secure Socket Extension (JSSE)
- Standard Java networking and security APIs

No external Java dependencies are required.

## Project Structure

```text
chapter-15/
├── pom.xml
├── README.md
└── src/
    └── main/
        └── java/
            └── com/
                └── clockwiseknowledge/
                    └── security/
                        ├── TLSProtocolInfo.java
                        ├── HTTPSConnection.java
                        ├── TLSClient.java
                        └── TLSHandshakeInfo.java
```

## Examples

### 1. TLSProtocolInfo

**Class:** `TLSProtocolInfo.java`

Displays TLS protocols and cipher suites supported by the default SSL/TLS context, along with its default-enabled settings.

Run:

```bash
java -cp target/classes com.clockwiseknowledge.security.TLSProtocolInfo
```

### 2. HTTPSConnection

**Class:** `HTTPSConnection.java`

Sends an HTTP GET request to `https://example.com` using `HttpsURLConnection`. Displays the HTTP response status and, for successful responses, the response body.

The example sets connection and read timeouts and uses Java's standard HTTPS security behavior.

Run:

```bash
java -cp target/classes com.clockwiseknowledge.security.HTTPSConnection
```

### 3. TLSClient

**Class:** `TLSClient.java`

Establishes a TLS socket connection to `example.com` on port 443. Explicitly enables HTTPS hostname verification, initiates the TLS handshake, and displays the negotiated TLS version and cipher suite.

Run:

```bash
java -cp target/classes com.clockwiseknowledge.security.TLSClient
```

### 4. TLSHandshakeInfo

**Class:** `TLSHandshakeInfo.java`

Establishes a TLS connection to `example.com` and displays session information, including the negotiated protocol, cipher suite, session creation time, and details of the peer certificates exposed by the TLS session.

Run:

```bash
java -cp target/classes com.clockwiseknowledge.security.TLSHandshakeInfo
```

## Build Requirements

Ensure that Java 17 or a compatible JDK and Maven are installed.

Verify the Java version:

```bash
java -version
```

Verify Maven:

```bash
mvn -version
```

Both commands should report an environment using Java 17.

## Build the Project

From the project root directory, run:

```bash
mvn clean package
```

Maven compiles the source files and packages the project into a JAR file under the `target` directory.

The compiled classes are available under:

```text
target/classes/
```

Run the examples using the commands listed above.

## Security Notes

- Use supported TLS versions, preferably TLS 1.3 where available, or securely configured TLS 1.2 when compatibility requires it.
- Supported protocols and cipher suites are not necessarily enabled by default.
- Preserve certificate validation and hostname verification.
- Never disable certificate or hostname verification merely to resolve connection failures.
- The examples use the default Java trust configuration and are intended for educational purposes.
- Certificate information displayed by `TLSHandshakeInfo` does not replace a complete explanation of certificate-chain validation.
- The examples connect to a public HTTPS endpoint and require network access. Actual protocol versions, cipher suites, certificates, and responses may vary.

## Related Chapters

- Chapter 14: Cipher Suites
- Chapter 16: Certificate Chain
- Chapter 17: Keystore and Truststore

## Author

Sanjay Ghosh

## License

This project is provided for educational purposes as companion source code for the book. Add the applicable license before redistributing the code.

