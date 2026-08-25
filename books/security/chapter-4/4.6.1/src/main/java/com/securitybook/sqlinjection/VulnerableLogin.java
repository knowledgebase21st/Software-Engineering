/*
 * Copyright (c) 2026 Sanjay Ghosh
 *
 * Chapter 4 – SQL Injection
 * Section 4.6.1 – Vulnerable JDBC Example
 *
 * Author: Sanjay Ghosh
 *
 * Description:
 * This example intentionally demonstrates a SQL Injection vulnerability
 * in a Java application using JDBC and PostgreSQL.
 *
 * The application constructs a SQL statement by concatenating
 * user-supplied input directly into the SQL query.
 *
 * This code is intentionally vulnerable and is provided solely for
 * educational and security-learning purposes.
 *
 * Security Warning:
 * Do not use this implementation in a production environment.
 * Do not connect it to a database containing real or sensitive data.
 *
 * The secure implementation using PreparedStatement is demonstrated
 * in Section 4.6.2.
 */

package com.securitybook.sqlinjection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class VulnerableLogin {

    
    /*Make Sure that these 3 enviornment variables are set */
    private static final String dbURL =  System.getenv("DB_URL");
    private static final String dbUser =  System.getenv("DB_USER");
    private static final String dbPassword =  System.getenv("DB_PASSWORD");

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        authenticate(username, password);

        scanner.close();
    }

    private static void authenticate(String username, String password) {

        String sql =
                "SELECT id, username, full_name " +
                "FROM users " +
                "WHERE username = '" + username + "' " +
                "AND password = '" + password + "'";

        System.out.println("\nGenerated SQL:");
        System.out.println(sql);

        try (Connection connection =
                     DriverManager.getConnection(
                             dbURL,
                             dbUser,
                             dbPassword);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            if (resultSet.next()) {

                System.out.println("\nLogin successful!");
                System.out.println(
                        "Welcome, " + resultSet.getString("full_name"));

            } else {

                System.out.println("\nLogin failed.");

            }

        } catch (SQLException e) {

            System.err.println("Database error: " + e.getMessage());
        }
    }
}
