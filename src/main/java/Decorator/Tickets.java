package Decorator;

import Adapter.EuroAdapter;
import Adapter.RubleAdapter;
import Adapter.Transfer;
import Adapter.TransferAdapter;
import Database.DatabaseConnection;
import Strategy.StrategyRunner;

import java.sql.*;
import java.util.Scanner;

public class Tickets {

    public void selectFlight(Scanner cin, String email){
        try{
            Connection connection = DatabaseConnection.ConnectionDB();

            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM flights";
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()) {
                System.out.println("Flight Nummber|| From City || To City || Departure Date || Departure Time || Arrival Date || Arrival Time||");

                String flightNumber = resultSet.getString("flight_number");
                String fromCity = resultSet.getString("from_city");
                String toCity = resultSet.getString("to_city");
                java.sql.Date departureDate = resultSet.getDate("departure_date");
                java.sql.Time departureTime = resultSet.getTime("departure_time");
                java.sql.Date arrivalDate = resultSet.getDate("arrival_date");
                java.sql.Time arrivalTime = resultSet.getTime("arrival_time");

                System.out.printf("%s\t\t\t%s\t\t%s \t%s\t%s\t\t%s\t\t%s%n",
                        flightNumber, fromCity, toCity, departureDate, departureTime, arrivalDate, arrivalTime);
                connection.close();
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.print("Choose flight number: ");
        String flightNum = cin.next();
        try {
            Connection connection = DatabaseConnection.ConnectionDB();
            Statement statement = connection.createStatement();
            String sql = "SELECT COUNT(*) FROM flights WHERE flight_number = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, flightNum);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int flightExists = rs.getInt(1);
                if (flightExists <= 0) {
                    System.err.println("Invalid flight number or flight number does not exist");
                    flightNum = cin.next();
                }
            }
            StrategyRunner strategyRunner = new StrategyRunner();
            strategyRunner.PayWith();
            buy(cin);
            initingFlightNumberToUser(flightNum, email);
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void initingFlightNumberToUser(String flightNumber, String email) throws SQLException {
        Connection connection = DatabaseConnection.ConnectionDB();
        String sql = "UPDATE users SET flight_number = ? WHERE email = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, flightNumber);
        ps.setString(2, email);

        ps.executeUpdate();

        connection.close();
    }

    public void buy(Scanner cin){
        System.out.println("To purchase tickets you must pay in Tenge.");
        System.out.println("""
                Choose your valute:
                1. Euro
                2. Dollar
                3. Ruble""");

        switch(cin.nextInt()) {
            case 1:
                Transfer transfer = new EuroAdapter();
                transfer.transferValute();
                break;
            case 2:
                Transfer transfer1 = new TransferAdapter();
                transfer1.transferValute();
                break;
            case 3:
                Transfer transfer2 = new RubleAdapter();
                transfer2.transferValute();
                break;
        }
    }
}
