package Decorator;

import Database.DatabaseConnection;

import java.sql.*;

public class GetFlightList {
    public GetFlightList(String flightNumber) {
        try {
            Connection connection = DatabaseConnection.ConnectionDB();

            String sql = "SELECT * FROM flights WHERE flight_number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, flightNumber);

            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("Flight Number\tFrom City\tTo City\tDeparture Date\tDeparture Time\tArrival Date\tArrival Time");

            while (resultSet.next()) {
                String flightNum = resultSet.getString("flight_number");
                String fromCity = resultSet.getString("from_city");
                String toCity = resultSet.getString("to_city");
                Date departureDate = resultSet.getDate("departure_date");
                Time departureTime = resultSet.getTime("departure_time");
                Date arrivalDate = resultSet.getDate("arrival_date");
                Time arrivalTime = resultSet.getTime("arrival_time");

                System.out.printf("%s\t%s\t%s\t%s\t%s\t%s\t%s%n",
                        flightNum, fromCity, toCity, departureDate, departureTime, arrivalDate, arrivalTime);
            }

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
