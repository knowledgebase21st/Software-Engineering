# 5.6 – Cross-Site Scripting in Java

## Overview

This project demonstrates Cross-Site Scripting (XSS) in a Java web
application using Spring Boot and Thymeleaf.

The example shows the difference between rendering user-supplied content
as raw HTML and rendering it as escaped text.

The project contains both an intentionally vulnerable implementation and
a secure implementation so that the behavior can be compared directly.

> **Security Warning:** The vulnerable page is intentionally insecure and
> is provided only for educational purposes. Do not deploy the vulnerable
> implementation in a production environment.

## Learning Objectives

This example demonstrates:

- How XSS can occur when user-supplied content is rendered as HTML
- How Thymeleaf handles user-supplied content
- The difference between `th:utext` and `th:text`
- Why output encoding is important when rendering untrusted data
- How a secure rendering approach prevents user input from being
  interpreted as HTML
- How Spring Boot and Thymeleaf can help developers build safer web
  applications

## Technologies

- Java 17
- Spring Boot 4.1.0
- Spring Web
- Thymeleaf
- Apache Maven 3.9.11

## Prerequisites

Before running this example, make sure the following are installed:

- Java 17 or later
- Apache Maven 3.9.11 or later

Verify Java:

```bash
java -version

Verify Maven:

mvn -version

This example does not require a database.

Spring Boot Version

This example uses Spring Boot 4.1.0.

The project intentionally uses a fixed Spring Boot version so that the
example can be built and tested consistently.

Spring Boot manages the versions of its supported dependencies through
its dependency management, so individual Spring dependency versions are
not specified in pom.xml.

Project Structure
5.6/
├── README.md
├── pom.xml
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── securitybook/
        │           └── xss/
        │               └── XssApplication.java
        │
        └── resources/
            ├── application.properties
            └── templates/
                ├── vulnerable.html
                └── secure.html
How XSS Occurs

Cross-Site Scripting can occur when an application takes untrusted
user input and renders it as HTML without appropriate output encoding.

The general flow is:

User Input
    |
    v
Spring Boot Application
    |
    v
Thymeleaf Template
    |
    v
Browser
    |
    v
HTML / JavaScript

If the application treats user input as HTML, the browser may interpret
the supplied content as markup or script rather than ordinary text

Vulnerable Rendering

The vulnerable page uses Thymeleaf's th:utext attribute:

<p th:utext="${userInput}"></p>

th:utext renders the supplied value as unescaped HTML.

This means that HTML supplied by the user can be interpreted by the
browser as HTML markup.

For example, in a controlled local environment, input containing HTML
can be used to observe the difference between escaped and unescaped
rendering.

Warning: Use the vulnerable page only on your local development
environment.

Secure Rendering

The secure page uses Thymeleaf's th:text attribute:

<p th:text="${userInput}"></p>

th:text treats the supplied value as text rather than allowing it to
be interpreted as HTML.

For example, if the user supplies:

<script>alert('XSS')</script>

the secure page displays the content as text rather than interpreting
it as a script.

th:utext vs. th:text
Attribute	Behavior	Security Consideration
th:utext	Renders unescaped HTML	Can introduce XSS when used with untrusted input
th:text	Renders escaped text	Safer for untrusted user input

The key principle is:

Untrusted Input
      |
      v
Output Encoding / Escaping
      |
      v
HTML

User-supplied content should normally be rendered using mechanisms that
encode or escape it appropriately for the output context.

Running the Example
1. Build the Project

From the 5.6 directory:

mvn clean package
2. Start the Application
mvn spring-boot:run
3. Application Port

This example uses port 9292 instead of the default Spring Boot
port 8080.

The port is configured in:

src/main/resources/application.properties

The configuration contains:

server.port=9292

After starting the application, open:

http://localhost:9292

The two examples are available at:

http://localhost:9292/vulnerable
http://localhost:9292/secure
Testing the Example

Open the application in a web browser.

Use the vulnerable page to observe how user-supplied content is rendered
without HTML escaping.

Then use the secure page to observe how the same content is rendered as
text.

A simple test value is:

Hello from the user

Then, in the controlled local environment, try:

<script>alert('XSS')</script>
Vulnerable Page

The vulnerable page uses:

<p th:utext="${userInput}"></p>

The browser may interpret the supplied HTML as markup.

Secure Page

The secure page uses:

<p th:text="${userInput}"></p>

The browser displays the supplied content as text rather than interpreting
it as HTML.

What to Observe

Compare the behavior of the two pages using the same input.

The important difference is not the input itself. The difference is how
the application renders that input.

Vulnerable:

User Input
    |
    v
th:utext
    |
    v
Raw HTML
    |
    v
Browser interprets HTML


Secure:

User Input
    |
    v
th:text
    |
    v
Escaped Text
    |
    v
Browser displays text
Why Output Encoding Matters

Output encoding converts characters with special meaning in HTML into
representations that the browser displays as text.

For example, HTML characters such as:

<
>
"
'
&

may need to be encoded depending on the output context.

The goal is to ensure that untrusted data remains data rather than
becoming executable markup or script.

Important Security Principle

Treat all user-supplied data as untrusted.

Do not assume that user input is safe simply because it came from:

A form
A search field
A profile page
A URL parameter
A database
Another internal application

Data that originated from an untrusted source should be safely handled
before it is rendered in an HTML response.

Spring Boot and Thymeleaf

Spring Boot provides the web application infrastructure, while
Thymeleaf provides the server-side HTML templating.

Thymeleaf provides escaping behavior through attributes such as
th:text.

However, developers can bypass safe rendering mechanisms by explicitly
using unescaped rendering such as th:utext.

Framework protection therefore does not eliminate the need for secure
coding practices.

Vulnerable vs. Secure
Vulnerable	Secure
th:utext	th:text
Unescaped HTML	Escaped text
User input can be interpreted as HTML	User input is displayed as text
Unsafe for untrusted content	Appropriate for ordinary untrusted text
Running in a Controlled Environment

This project contains an intentionally vulnerable page for security
education.

Use it only on a local development machine.

Do not:

Deploy the vulnerable page to a public server
Use real user information
Use production credentials
Connect it to a production system
Test it against systems you do not own or have permission to test
Related Book Section

Chapter 5 – Cross-Site Scripting

Section 5.6 – Cross-Site Scripting in Java

This project provides the runnable Java/Spring Boot examples discussed
in the chapter.

Summary

This example demonstrates that XSS prevention is not simply a matter of
validating input.

The application must also render untrusted data safely in its output
context.

With Thymeleaf, developers should understand the difference between
escaped rendering such as th:text and unescaped rendering such as
th:utext.

The secure approach is to treat user-supplied content as untrusted and
use appropriate output encoding or escaping when rendering it.
