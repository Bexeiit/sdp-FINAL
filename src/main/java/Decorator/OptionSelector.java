package Decorator;

import java.util.Scanner;

public class OptionSelector extends OptionsDecorator implements AdditionalOptions{

    public OptionSelector(AdditionalOptions additionalOptions) {
        super(additionalOptions);
    }

    public void body(Scanner cin) {
        System.out.println("Choose your ticket Class:\n" +
                "1. Economy\n" +
                "2. Business");
        int selectClass = cin.nextInt();
        boolean isTrue = true;
        while (isTrue) {
            switch (selectClass) {
                case 1 -> {
                    economyClass();
                    isTrue = false;
                }
                case 2 -> {
                    businessClass();
                    isTrue = false;
                }
                default -> {
                    System.out.println("Incorrect option. Retry.");
                    selectClass = cin.nextInt();
                }
            }
        }

        System.out.println("""
                Do you need some extra Baggage?
                1. Yes
                2. No""");
        int isBaggage = cin.nextInt();
        boolean isFalse = true;
        while (isFalse) {
            switch (isBaggage) {
                case 1:
                    isFalse = false;
                    addBaggage();
                    break;
                case 2:
                    isFalse = false;
                    break;
                default:
                    System.out.println("Incorrect Option. Retry.");
                    isBaggage = cin.nextInt();
            }
        }
    }

    @Override
    public String addBaggage() {
        System.out.println("You are using extra baggage.");
        return null;
    }

    @Override
    public String economyClass() {
        System.out.println("Your ticket class - Economy.");
        return null;
    }

    @Override
    public String businessClass() {
        System.out.println("Your ticket class - Business.");
        return null;
    }
}
