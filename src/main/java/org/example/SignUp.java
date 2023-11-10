package org.example;


import Database.DatabaseConnection;

import java.sql.*;
import java.util.Scanner;

public class SignUp {
    Scanner cin = new Scanner(System.in);

    public void SignAs() {
        System.out.println("""
                I'm a:
                1. Customer
                2. Administrator""");

        switch (cin.nextInt()) {
            case 1:
                signAsCustomer();
                break;
            case 2:
                signAsAdministrator();
                break;
            default:
                System.out.println("wrong option");
                cin.nextInt();
        }
    }


    public void signAsCustomer() {
        System.out.print("Enter Your First Name: ");
        String firstName = cin.next();
        System.out.print("Enter Your Last Name: ");
        String lastName = cin.next();
        System.out.println("Enter Your E-Mail");
        String email = cin.next();

        while (true) {
            try {
                Connection connection = DatabaseConnection.ConnectionDB();
                String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, email);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next() && resultSet.getInt(1) > 0) {
                        System.out.println("Given E-Mail is already exists. Please enter other E-Mail.");
                        email = cin.next();
                }
                else {
                    break;
                }
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Create a Password");
        String password = cin.next();

        try (Connection connection = DatabaseConnection.ConnectionDB();) {
            String sql = "INSERT INTO users (first_name, last_name, email, password, role) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, password);
            preparedStatement.setString(5, "user");

            if (preparedStatement.executeUpdate() <= 0) {
                System.out.println("User registration failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void signAsAdministrator() {
        System.out.print("Enter Your First Name: ");
        String firstName = cin.next();
        System.out.print("Enter Your Last Name: ");
        String lastName = cin.next();
        System.out.println("Enter Your E-Mail");
        String email = cin.next();

        while (true) {
            try {
                Connection connection = DatabaseConnection.ConnectionDB();
                String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, email);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next() && resultSet.getInt(1) > 0) {
                   System.out.println("Given E-Mail is already exists. Please enter other E-Mail.");
                   email = cin.next();
                }
                else {
                    break;
                }
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Create a Password");
        String password = cin.next();

        try (Connection connection = DatabaseConnection.ConnectionDB()) {
            String sql = "INSERT INTO users (first_name, last_name, email, password, role) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, password);
            preparedStatement.setString(5, "admin");

            if (preparedStatement.executeUpdate() <= 0) {
                System.out.println("User registration failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
