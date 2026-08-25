# Chapter 4 – SQL Injection

This directory contains the complete runnable source code associated with
Chapter 4 – SQL Injection of the book.

The examples demonstrate how SQL Injection vulnerabilities can occur in
Java applications and how they can be prevented using secure coding
techniques.

## Projects

### 4.6.1 – Vulnerable JDBC Example

Demonstrates a SQL Injection vulnerability caused by constructing a SQL
statement using user-supplied input.

### 4.6.2 – Secure PreparedStatement Example

Demonstrates how `PreparedStatement` separates SQL structure from user
input and helps prevent SQL Injection.

### 4.6.3 – Spring Boot Example

Demonstrates SQL Injection considerations in a Spring Boot application
and shows the secure approach.

### 4.6.4 – JPA/Hibernate Example

Demonstrates how JPA/Hibernate can be used to access data securely and
what developers should understand about SQL Injection when using ORM
frameworks.

## Important

The vulnerable examples in this chapter are intentionally insecure and
are provided only for educational purposes.

Do not deploy the vulnerable examples in a production environment or
connect them to databases containing real or sensitive information.

## Related Book Chapter

**Chapter 4 – SQL Injection**

The projects in this directory correspond to the Java examples discussed
in Section 4.6 of the book.
