package org.example;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner cin = new Scanner(System.in);
        while (true) {
            System.out.println("""
                
                Welcome to BYE Airlines! Select one of the options:
                
                1. Sign up
                2. Log in
                0. Exit
                """);

            switch (cin.nextInt()) {
                case 1:
                    SignUp signUp = new SignUp();
                    signUp.SignAs();
                    break;
                case 2:
                    LogIn logIn = new LogIn();
                    logIn.logInAs();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Wrong option.");
                    cin.nextInt();
            }
        }
    }
}