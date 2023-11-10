package Decorator;

import java.util.Scanner;

public class UserRemoteSystem {
    public static void userRemote(String flightNumber, String email) {
        Scanner cin = new Scanner(System.in);
        boolean isOff = true;

        while(isOff) {
            System.out.println("""
                    Choose option:
                    1. My Flight List
                    2. Purchase Tickets
                    0. Exit""");
            int option = cin.nextInt();
            boolean isOptionTrue = true;

            while (isOptionTrue) {
                switch (option) {
                    case 1:
                        new GetFlightList(flightNumber);
                        isOptionTrue = false;
                        break;
                    case 2:
                        Tickets tickets = new Tickets();
                        tickets.selectFlight(cin, email);
                        isOptionTrue = false;
                        break;
                    case 0:
                        isOptionTrue = false;
                        isOff = false;
                        break;
                    default:
                        System.out.println("Incorrect Option. Retry.");
                        option = cin.nextInt();
                }
            }
        }
    }
}
