/*
 * Copyright (c) 2026 Sanjay Ghosh
 *
 * Chapter 4 – SQL Injection
 * Section 4.6.2 – Secure PreparedStatement Example
 *
 * Author: Sanjay Ghosh
 *
 * Description:
 * This example demonstrates how to prevent SQL Injection in a Java
 * application using JDBC PreparedStatement and PostgreSQL.
 *
 * Unlike the vulnerable example in Section 4.6.1, this implementation
 * does not concatenate user-supplied values into the SQL statement.
 * Instead, user input is supplied through PreparedStatement parameters.
 *
 * Security Principle:
 * SQL structure and user-supplied data are kept separate.
 *
 * This example is provided for educational and security-learning purposes.
 *
 * Related Example:
 * Section 4.6.1 – Vulnerable JDBC Example
 */

package com.securitybook.sqlinjection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class SecureLogin {

    public static void main(String[] args) {

        String dbUrl = System.getenv("DB_URL");
        String dbUser = System.getenv("DB_USER");
        String dbPassword = System.getenv("DB_PASSWORD");

        Scanner scanner = new Scanner(System.in);

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        authenticate(
                dbUrl,
                dbUser,
                dbPassword,
                username,
                password);

        scanner.close();
    }

    private static void authenticate(
            String dbUrl,
            String dbUser,
            String dbPassword,
            String username,
            String password) {

        String sql =
                "SELECT id, username, full_name " +
                "FROM users " +
                "WHERE username = ? " +
                "AND password = ?";

        System.out.println("\nSQL Statement:");
        System.out.println(sql);

        try (Connection connection =
                     DriverManager.getConnection(
                             dbUrl,
                             dbUser,
                             dbPassword);
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    System.out.println("\nLogin successful!");
                    System.out.println(
                            "Welcome, " +
                            resultSet.getString("full_name"));

                } else {

                    System.out.println("\nLogin failed.");
                }
            }

        } catch (SQLException e) {

            System.err.println(
                    "Database error: " + e.getMessage());
        }
    }
}
