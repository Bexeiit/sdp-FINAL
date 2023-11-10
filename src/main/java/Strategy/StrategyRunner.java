package Strategy;

import java.util.Scanner;

public class StrategyRunner {

    public void PayWith() {
        Scanner cin = new Scanner(System.in);
        Payment payment = new Payment();

        System.out.println("""
                Choose your payment Method:
                1. Kaspi
                2. Visa
                3. Master Card""");
        int paymentMethod = cin.nextInt();
        boolean isPayment = true;
        while(isPayment) {
            switch (paymentMethod) {
                case 1:
                    payment.setPaymentStrategy(new Kaspi());
                    payment.executePayment();
                    isPayment = false;
                    break;
                case 2:
                    payment.setPaymentStrategy(new Visa());
                    payment.executePayment();
                    isPayment = false;
                    break;
                case 3:
                    payment.setPaymentStrategy(new MasterCard());
                    payment.executePayment();
                    isPayment = false;
                    break;
                default:
                    System.out.println("Incorrect payment method. Retry.");
                    paymentMethod = cin.nextInt();
            }
        }
    }
}
