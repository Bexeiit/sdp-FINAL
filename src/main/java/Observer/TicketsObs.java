package Observer;

import Database.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TicketsObs implements Observed{
    Scanner scanner = new Scanner(System.in);
    List<String> stringList = new ArrayList<>();
    List<Observer> customersList = new ArrayList<>();
    private int idDB;

    public void addFlight(){
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
    public void addCustomer(String email) {
        System.out.println("Customer has added");
    }

    @Override
    public void removeCustomer(String email) {
        System.out.println("Customer has removed");
    }

    @Override
    public void notifyCustomer(String email) {
        System.out.println("Customer have been noticed");
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
}
