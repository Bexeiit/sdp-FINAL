package Singleton.Flights;

import Database.DatabaseConnection;
import Observer.Observer;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Flights implements Observer {
    private static Flights flights;
    public static synchronized Flights getFlights() {
        if(flights == null) {
            flights = new Flights();
        }
        return flights;
    }

    private Flights() {}

    static Scanner scanner = new Scanner(System.in);

    private int idDB = 0;
    // nextval('flights_id_seq'::regclass)
    public void createFlight() throws SQLException {

        System.out.println("Enter flight number: ");
        String flight_number = scanner.next();
        System.out.println("Enter departure city: ");
        String from_city = scanner.next();
        System.out.println("Enter destination city: ");
        String to_city = scanner.next();
        System.out.println("Enter departure date: ");
        String departure_date = scanner.next();
        System.out.println("Enter departure time:");
        String departure_time = scanner.next();
        LocalDateTime depLDT = inputDateAndTime(departure_date, departure_time);
        System.out.println("Enter arrival date: ");
        String arrival_date = scanner.next();
        System.out.println("Enter arrival time: ");
        String arrival_time = scanner.next();
        LocalDateTime arriveLDT = inputDateAndTime(arrival_date, arrival_time);

        try {
            Connection connection = DatabaseConnection.ConnectionDB();
            String sql = "INSERT INTO flights (flight_number, from_city, to_city, departure_date, departure_time, arrival_date, arrival_time) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, flight_number);
            preparedStatement.setString(2, from_city);
            preparedStatement.setString(3, to_city);
            preparedStatement.setDate(4, Date.valueOf(depLDT.toLocalDate()));
            preparedStatement.setTime(5, Time.valueOf(depLDT.toLocalTime()));
            preparedStatement.setDate(6, Date.valueOf(arriveLDT.toLocalDate()));
            preparedStatement.setTime(7, Time.valueOf(arriveLDT.toLocalTime()));
            int rowsInserted = preparedStatement.executeUpdate(); // = 1

            if (rowsInserted > 0) {
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT currval('flights_id_seq'::regclass)");
                if (rs.next()) {
                    idDB = rs.getInt(1);
                }

                System.out.println("A new flight has been created successfully.");
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleEvent() {
        System.out.println("\n\nDear consumers, we have some new flights" +
                "\n");
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.ConnectionDB();
            String sql = "SELECT * FROM flights WHERE id = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, idDB);

            resultSet = ps.executeQuery();

            while (resultSet.next()) {
                System.out.println("Flight number -> " + resultSet.getString("flight_number") + "\n" +
                        "The flight from " + resultSet.getString("from_city") + " in " + resultSet.getString("departure_date") + " at " + resultSet.getString("departure_time") + "\n" +
                        "To " + resultSet.getString("to_city") + " in " + resultSet.getString("arrival_date") + " at " + resultSet.getString("arrival_time") );
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static LocalDateTime inputDateAndTime(String fullDate, String fullTime){
        String[] dates = fullDate.split("-");
        Integer day = Integer.parseInt(dates[0]);
        Integer month = Integer.parseInt(dates[1]);
        Integer year = Integer.parseInt(dates[2]);

        String[] times = fullTime.split(":");
        Integer hour = Integer.parseInt(times[0]);
        Integer minute = Integer.parseInt(times[1]);

        return LocalDateTime.of(year, month, day, hour, minute);

    }

    public void showFlights() {
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
            }


        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
