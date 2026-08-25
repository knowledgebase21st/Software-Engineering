### `4.6.2/README.md`

```markdown
# 4.6.2 – Secure PreparedStatement Example

## Overview

This project demonstrates how to prevent SQL Injection in a Java
application using JDBC `PreparedStatement` and PostgreSQL.

The example is the secure counterpart to the vulnerable JDBC
implementation presented in Section 4.6.1.

Instead of concatenating user-supplied values into a SQL statement, the
application uses parameterized SQL with `PreparedStatement`.

## Learning Objectives

This example demonstrates:

- How `PreparedStatement` works
- How parameterized SQL separates SQL structure from user input
- How user-supplied values are treated as data
- How `PreparedStatement` helps prevent SQL Injection
- The difference between `Statement` and `PreparedStatement`

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
- Basic knowledge of SQL and JDBC

This example uses the same PostgreSQL database and sample data
established for Section 4.6.1.

If you are new to PostgreSQL, refer to the PostgreSQL tutorial in the
Software Engineering Knowledge Base:

https://clockwiseknowledge.com/se//postgresql.html

## Database Setup

This example uses the PostgreSQL database created for Section 4.6.1.

```text
Database: sql_injection_demo
Schema:   public
Table:    users

No additional schema setup is required if 4.6.1 has already been
configured.

The schema.sql file in 4.6.1 creates the users table and sample
data used by this example.

If the database has not yet been created, follow the database setup
instructions in:

4.6.1/README.md
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
4.6.2/
├── README.md
├── pom.xml
└── src/
    └── main/
        └── java/
            └── com/
                └── securitybook/
                    └── sqlinjection/
                        └── SecureLogin.java
How PreparedStatement Prevents SQL Injection

The application uses parameterized SQL:

String sql =
    "SELECT id, username, full_name " +
    "FROM users " +
    "WHERE username = ? " +
    "AND password = ?";

The values are supplied separately:

statement.setString(1, username);
statement.setString(2, password);

The SQL structure is therefore defined independently of the
user-supplied values.

Vulnerable vs Secure
Vulnerable Implementation

The implementation in Section 4.6.1 uses string concatenation:

String sql =
    "SELECT * FROM users WHERE username = '" +
    username +
    "' AND password = '" +
    password +
    "'";

User input becomes part of the SQL statement.

Secure Implementation

This example uses PreparedStatement:

String sql =
    "SELECT id, username, full_name " +
    "FROM users " +
    "WHERE username = ? AND password = ?";

The user input is supplied separately:

statement.setString(1, username);
statement.setString(2, password);
Running the Example
1. Make Sure PostgreSQL Is Running

Make sure the PostgreSQL server is running and that the
sql_injection_demo database is available.

2. Set Database Environment Variables
export DB_URL="jdbc:postgresql://localhost:5432/sql_injection_demo"
export DB_USER="sql_demo_user"
export DB_PASSWORD="sql_demo_password"
3. Build the Project

From the 4.6.2 directory:

mvn clean package
4. Run the Application
mvn exec:java -Dexec.mainClass="com.securitybook.sqlinjection.SecureLogin"
5. Test Normal Credentials

For example:

Username: alice
Password: alice123

The application should display:

Login successful!
Welcome, Alice Johnson
Testing With Unexpected Input

The application can also be tested with specially crafted input to
verify that user input does not change the SQL statement's structure.

The same test input used against the vulnerable implementation can be
used against this application.

The expected result is that the input is treated as a value rather than
as part of the SQL command.

What to Observe

The application prints the SQL statement:

SELECT id, username, full_name
FROM users
WHERE username = ?
AND password = ?

Notice that the actual user-supplied values are not concatenated into
the SQL statement.

This is the important difference from Section 4.6.1.

Why This Code Is Secure Against SQL Injection

The application separates:

SQL Structure
     +
User-Supplied Data

The SQL statement is prepared first:

PreparedStatement statement =
    connection.prepareStatement(sql);

The user values are then supplied through parameters:

statement.setString(1, username);
statement.setString(2, password);

As a result, specially crafted input cannot change the intended
structure of the SQL query.

Comparison
Vulnerable JDBC	Secure JDBC
Statement	PreparedStatement
String concatenation	Parameterized SQL
User input becomes part of SQL	User input is supplied as data
SQL structure can be changed by input	SQL structure remains fixed
Vulnerable to SQL Injection	Helps prevent SQL Injection
Related Book Section

Chapter 4 – SQL Injection

Section 4.6.2 – Secure PreparedStatement Example

This project is the secure counterpart to:

4.6.1 – Vulnerable JDBC Example
Educational Use

This project is provided for educational purposes and demonstrates a
secure approach to database access using JDBC PreparedStatement.

Although this example demonstrates SQL Injection prevention, production
applications should also use additional security practices such as
secure password storage, least-privilege database accounts, input
validation, proper error handling, and secure configuration management.
