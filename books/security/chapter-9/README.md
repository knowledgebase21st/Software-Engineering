# Chapter 9 - OAuth 2.0

This project contains the Java Servlet implementation used in Chapter 9, **OAuth 2.0**, of *Practical Application Security* by Sanjay Ghosh.

The project is an educational simulation of the OAuth 2.0 Authorization Code flow. It intentionally keeps the authorization server, client, and resource server roles inside one WAR so the complete flow can be studied and run as a single application.

## Project Structure

```text
chapter-9/
├── pom.xml
├── README.md
└── src/
    └── main/
        ├── java/
        │   └── com/oauth/chapter9/
        │       ├── OAuthDemoStore.java
        │       ├── OAuthUtil.java
        │       ├── LoginServlet.java
        │       ├── AuthorizeServlet.java
        │       ├── CallbackServlet.java
        │       ├── TokenServlet.java
        │       └── DataServlet.java
        └── webapp/
            └── index.html
```

## OAuth Roles in This Application


| Servlet            | Endpoint     | Invoked by     | Role          | Responsibility       |
|--------------------|--------------|----------------|---------------|----------------------|
| `LoginServlet`     | `/login`     | User / Browser | Client        | Starts the OAuth     |
|                    |              |                |               | authorization flow   |
|                    |              |                |               |
| `AuthorizeServlet` | `/authorize` | Browser        | Authorization | Authenticates the    |
|                    |              |                | Server        | user and obtains     |
|                    |              |                |               | consent              |
|                    |              |                |               |
| `CallbackServlet`  | `/callback`  | Browser        | Client        | Validates `state`    |
|                    |              |                |               | and performs the     |
|                    |              |                |               | token exchange       |
| `TokenServlet`     | `/token`     | Client         | Authorization | Exchanges the auth   |
|                    |              |                | Server        | code for an access   |
|                    |              |                |               | token                |
|                    |              |                |               |
| `DataServlet`      | `/data`      | Client         | Resource      | Validates the access |
|                    |              |                | Server        | token and protects   |
|                    |              |                |               | the resource         |


**Invoked by** identifies who actually sends the HTTP request to the endpoint.
**Role** identifies which OAuth component the Servlet represents.

The revised implementation represents the token exchange directly as a client-to-token-endpoint request rather than using a separate helper page.

## Security Concepts Demonstrated

- Authorization Code flow
- `state` validation
- PKCE using `S256`
- Registered redirect URI validation
- Short-lived authorization codes
- Single-use authorization codes
- Opaque access tokens
- Bearer token presentation
- Scope enforcement
- Access-token expiration
- Separation of Authorization Server and Resource Server responsibilities

The demo client requests:

```text
calendar.read calendar.write
```

The authorization server intentionally grants only:

```text
calendar.read
```

Therefore:

- `GET /data` requires `calendar.read` and succeeds after a valid flow.
- `POST /data` requires `calendar.write` and is rejected because the issued token has only `calendar.read`.

## Technologies

- Java 17
- Java Servlet API 4.0.1
- Maven
- WAR deployment
- Servlet annotations (`@WebServlet`)

The application can be deployed to a Servlet 4 compatible container such as Apache Tomcat 9.x.

## Build

From the `chapter-9` directory:

```bash
mvn clean package
```

The generated WAR is:

```text
target/chapter-9-oauth2-servlet-demo.war
```

Do not commit the `target/` directory to GitHub.

## Requirements

This example uses:

- Java 17 or later
- Apache Tomcat 10.x
- Maven 3.x

### Jakarta Servlet API

This project uses the Jakarta Servlet API because it targets Tomcat 10.x.

Tomcat 10 migrated from the older Java EE `javax.servlet.*` namespace to the Jakarta EE `jakarta.servlet.*` namespace. Therefore, the source code in this project uses imports such as:

```java
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

If you run the application on an older Tomcat version such as Tomcat 9, the Servlet API uses the older javax.servlet.* namespace instead. The imports and corresponding Maven dependency would therefore need to be changed to the javax.servlet API.

For this book's example, use Tomcat 10.x and the jakarta.servlet namespace as shown in the source code.

## Run

Copy the generated WAR into the Tomcat `webapps` directory and start Tomcat.

Open:

```text
http://localhost:8080/chapter-9-oauth2-servlet-demo/
```

Select **Start OAuth 2.0 Flow**.

The demo uses:

```text
Username: admin
Password: password
```

## Flow

```text
Browser
  ↓
/login
  ↓
/authorize
  ↓
User Authentication / Consent
  ↓
/callback?code=...&state=...
  ↓
POST /token
  ↓
Access Token
  ↓
GET /data
  ↓
Protected Resource
```

## PKCE

The client generates a `code_verifier` and derives the `code_challenge` with SHA-256 followed by Base64url encoding when `S256` is used.

The authorization server stores the challenge with the authorization code and verifies the `code_verifier` during the token exchange.

## Scope

The client requests:

```text
calendar.read calendar.write
```

The authorization server grants only:

```text
calendar.read
```

The resource server checks the required scope for the requested operation.

## Important Educational Limitation

This project is designed for learning. It is **not a production OAuth 2.0 server**.

It uses in-memory storage, a demo username/password, simplified client registration, and an educational token model. Production systems should use established OAuth/OIDC libraries and appropriate persistent storage, key management, TLS configuration, client authentication, monitoring, and security controls.

## Source Code

This project accompanies Chapter 9 of *Practical Application Security* by Sanjay Ghosh.
