package org.example;

import Database.DatabaseConnection;
import Decorator.UserRemoteSystem;
import Singleton.AdminRemoteSystem;

import java.sql.*;
import java.util.Scanner;

public class LogIn {
    Scanner cin = new Scanner(System.in);

    public void logInAs() throws SQLException {
        System.out.println("""
                I'm a:
                1. Customer
                2. Administrator""");

        switch (cin.nextInt()) {
            case 1:
                logInAsCustomer();
                break;

            case 2:
                logInAsAdministrator();
                break;
            default:
                System.out.println("wrong option");
                cin.nextInt();
        }
    }
    public void logInAsCustomer() {
        System.out.print("Enter your E-Mail: ");
        String email = cin.next();

        boolean isEmailExists = true;
        while (true) {
            try {
                Connection connection = DatabaseConnection.ConnectionDB();
                String emailSql = "SELECT COUNT(*) FROM users WHERE email = ?";
                PreparedStatement emailStatement = connection.prepareStatement(emailSql);
                emailStatement.setString(1, email);
                ResultSet emailResultSet = emailStatement.executeQuery();

                if (emailResultSet.next() && emailResultSet.getInt(1) > 0) {
                    isEmailExists = false;
//                    break;
                }
                else {
                    System.out.println("Invalid E-Mail or you're not registered yet. Try again.");
                    email = cin.next();
                }

                // После того как email подтвержден, мы можем получить flight_number
                String flightNumber = null;
                if (!isEmailExists) {
                    String flightSql = "SELECT flight_number FROM users WHERE email = ?";
                    PreparedStatement flightStatement = connection.prepareStatement(flightSql);
                    flightStatement.setString(1, email);
                    ResultSet flightResultSet = flightStatement.executeQuery();

                    if (flightResultSet.next()) {
                        flightNumber = flightResultSet.getString("flight_number");
                    }
                }

//                connection.close();

                System.out.print("Enter password: ");
                String password = cin.next();

                while (true) {
                    try {
                        connection = DatabaseConnection.ConnectionDB();
                        String passwordSql = "SELECT COUNT(*) FROM users WHERE password = ?";
                        PreparedStatement passwordStatement = connection.prepareStatement(passwordSql);
                        passwordStatement.setString(1, password);
                        ResultSet passwordResultSet = passwordStatement.executeQuery();

                        if (passwordResultSet.next() && passwordResultSet.getInt(1) > 0) {
                                // передал короче Flightnumber в RemoteSystem
                                UserRemoteSystem.userRemote(flightNumber, email);
                                break;
                        }
                        else {
                            System.out.println("Invalid Password. Try again.");
                            password = cin.next();
                        }
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public void logInAsAdministrator() throws SQLException {
        System.out.print("Enter your E-Mail: ");
        String email = cin.next();

        while (true) {
            try {
                Connection connection = DatabaseConnection.ConnectionDB();
                String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, email);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    if (count > 0) {
                        String checkRole = "SELECT COUNT(*) FROM users WHERE email = ? AND role = 'admin'";
                        PreparedStatement ps = connection.prepareStatement(checkRole);
                        ps.setString(1, email);
                        ResultSet rs = ps.executeQuery();

                        if (rs.next() && rs.getInt(1) > 0) {
                                break;
                        }
                        else {
                            System.out.println("Invalid E-Mail or you are not an Admin. Try again.");
                            email = cin.next();
                        }
                    } else {
                        System.out.println("Invalid E-Mail or you are not registered yet. Try again.");
                        email = cin.next();
                    }
                }

                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        System.out.print("Enter password: ");
        String password = cin.next();

        while (true) {
            try {
                Connection connection = DatabaseConnection.ConnectionDB();
                String sql = "SELECT COUNT(*) FROM users WHERE password = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, password);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next() && resultSet.getInt(1) > 0) {
                        break;
                }
                else {
                    System.out.println("Invalid Password. Try again.");
                    password = cin.next();
                }

                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        AdminRemoteSystem.adminRemote();
    }
}
