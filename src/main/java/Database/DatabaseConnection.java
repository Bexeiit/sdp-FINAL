package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection ConnectionDB() throws SQLException {
        String jdbcURL = "jdbc:postgresql://localhost:5432/Flight-Registration-System";
        String username = "postgres";
        String password = "1";

            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
        return connection;
    }

    public static void main(String[] args) {


        try {
            Connection connection = DatabaseConnection.ConnectionDB();
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM flights";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                System.out.println("Flight Number: \t" + resultSet.getString("flight_number"));
                System.out.println("From City: \t\t" + resultSet.getString("from_city"));
                System.out.println("To City: \t\t" + resultSet.getString("to_city"));
                System.out.println("Departure Date: " + resultSet.getDate("departure_date"));
                System.out.println("Departure Time: " + resultSet.getTime("departure_time"));
                System.out.println("Arrival Date: \t" + resultSet.getDate("arrival_date"));
                System.out.println("Arrival Time: \t" + resultSet.getTime("arrival_time"));
                System.out.println();
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
