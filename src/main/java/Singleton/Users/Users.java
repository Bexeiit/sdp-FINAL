package Singleton.Users;

import Database.DatabaseConnection;
import Observer.Observer;

import java.sql.*;
import java.util.Scanner;

public class Users {
    private static Users users;
    private static String usersInfo = "\n";

    public static synchronized Users getUsers() {
        if(users == null) {
            users = new Users();
        }
        return users;
    }


    private Users(){}

    public void showUsers() {
        try{
            Connection connection = DatabaseConnection.ConnectionDB();

            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM users";
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()) {
                System.out.println("First Name\t|| Last Name\t|| E-Mail\t\t\t\t\t|| Password\t\t|| Flight Number\t|| Role");

                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                String password= resultSet.getString("password");
                String flightNumber = resultSet.getString("flight_number");
                String role = resultSet.getString("role");

                System.out.printf("%s\t\t|| %s\t\t|| %s\t\t|| %s\t\t|| %s\t\t|| %s%n",
                        firstName, lastName, email, password, flightNumber, role);
            }


        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUsers(Scanner cin) {
        System.out.println("Input Users' E-Mail to Remove user.");
        String email = cin.next();

        boolean isClearEmail = true;
        while (isClearEmail) {

            try {
                Connection connection = DatabaseConnection.ConnectionDB();
                String sql = "DELETE FROM users WHERE email = ?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, email);

                int delete = ps.executeUpdate();
                if(delete > 0) {
                    System.out.println("User Has been Successfully Banned.");
                    isClearEmail = false;
                } else {
                    System.out.println("Incorrect E-Mail or User doesn't exist");
                    email = cin.next();
                }

                ps.close();
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
