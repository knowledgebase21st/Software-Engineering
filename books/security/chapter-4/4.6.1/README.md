# 4.6.1 – Vulnerable JDBC Example

## Overview

This project demonstrates a SQL Injection vulnerability in a Java
application that uses JDBC.

The application intentionally constructs a SQL query by concatenating
user-supplied input with the SQL statement. This allows user input to
influence the structure of the SQL query.

This example is intentionally vulnerable and is provided for educational
purposes only.

> **Security Warning:** Do not deploy this application in a production
> environment or connect it to a database containing real or sensitive
> information.

## Learning Objectives

This example demonstrates:

- How SQL Injection can occur in a JDBC application
- Why constructing SQL statements using string concatenation is unsafe
- How untrusted input can become part of a SQL statement
- Why parameterized queries are necessary
- How JDBC interacts with a relational database

## Technologies

- Java 17
- JDBC
- PostgreSQL
- Maven

## Prerequisites

Before running this example, make sure the following are installed:

- Java 17 or later
- Maven
- PostgreSQL
- Basic knowledge of SQL and PostgreSQL

If you are new to PostgreSQL, refer to the PostgreSQL tutorial in the
Software Engineering Knowledge Base:

https://clockwiseknowledge.com/se//postgresql.html

## Database Setup

This example uses PostgreSQL.

Create a PostgreSQL database for this project.

For example:

```bash
createdb sql_injection_demo

The project includes a schema.sql file that creates the users table
and inserts the sample users required by the application.

Execute the schema using:

psql -d sql_injection_demo -f schema.sql

Note: The database creation step is intentionally left to the
reader. The supplied schema.sql file creates the tables and sample
data required by the example.

Sample Users

The schema creates the following sample users:

Username	Password
alice	alice123
bob	bob123
charlie	charlie123

These credentials are dummy values intended only for this educational
example.

Security Note: The passwords are stored as plain text intentionally
because this example focuses on SQL Injection rather than password
storage. Never store passwords as plain text in a real application.

Database Configuration

The Java application reads the PostgreSQL connection information from
environment variables.

Set the following variables before running the application:

export DB_URL="jdbc:postgresql://localhost:5432/sql_injection_demo"
export DB_USER="sql_demo_user"
export DB_PASSWORD="sql_demo_password"

Do not place real database credentials directly in the source code or
commit them to GitHub.

Project Structure
4.6.1/
├── README.md
├── pom.xml
├── schema.sql
└── src/
    └── main/
        └── java/
            └── com/
                └── securitybook/
                    └── sqlinjection/
                        └── VulnerableLogin.java
How the Vulnerability Occurs

The application constructs the SQL statement using string concatenation.

Conceptually, the code follows this pattern:

String sql =
    "SELECT * FROM users WHERE username = '" +
    username +
    "' AND password = '" +
    password +
    "'";

The problem is that the values supplied by the user become part of the
SQL statement itself.

Instead of treating the username and password strictly as data, the
database receives a complete SQL statement containing the user-supplied
values.

An attacker can provide specially crafted input that changes the meaning
of the SQL statement.

Running the Example
1. Make Sure PostgreSQL Is Running

Make sure the PostgreSQL server is running.

2. Create the Database
createdb sql_injection_demo
3. Execute the Schema

From the 4.6.1 directory:

psql -d sql_injection_demo -f schema.sql
4. Set Database Environment Variables
export DB_URL="jdbc:postgresql://localhost:5432/sql_injection_demo"
export DB_USER="sql_demo_user"
export DB_PASSWORD="sql_demo_password"
5. Build the Project

From the 4.6.1 directory:

mvn clean package
6. Run the Application
mvn exec:java -Dexec.mainClass="com.securitybook.sqlinjection.VulnerableLogin"
What to Observe

First, run the application using normal input and observe the expected
behavior.

For example:

Username: alice
Password: alice123

The application should authenticate the user successfully.

The application also displays the generated SQL statement.

Then experiment with specially crafted input in the local test
environment.

Observe how the generated SQL statement changes when user input is
incorporated directly into the query.

The purpose of this exercise is to understand why SQL statements should
not be constructed by concatenating untrusted input.

Why This Code Is Vulnerable

The vulnerability occurs because the application combines:

SQL statement + untrusted user input

using string concatenation.

The resulting string is sent to the database as SQL.

The database therefore cannot reliably distinguish between SQL
instructions and user-supplied data when the input changes the structure
of the resulting SQL statement.

Secure Version

The secure approach is demonstrated in:

4.6.2

The next example uses PreparedStatement to separate SQL structure from
user-supplied values.

The secure implementation prevents user input from changing the intended
structure of the SQL statement.

Comparison
Vulnerable Approach	Secure Approach
String concatenation	Parameterized query
User input becomes part of SQL	User input is treated as data
Uses Statement	Uses PreparedStatement
Vulnerable to SQL Injection	Helps prevent SQL Injection
Related Book Section

Chapter 4 – SQL Injection

Section 4.6.1 – Vulnerable JDBC Example

Educational Use

This project is intentionally vulnerable and should only be used in a
controlled local development environment.

Never use:

Real user credentials
Production databases
Sensitive information
Production systems

when experimenting with this example.

The purpose of this project is to help developers understand how SQL
Injection vulnerabilities occur and why parameterized queries are
necessary.
