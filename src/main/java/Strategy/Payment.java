package Strategy;

import java.util.Random;

public class Payment {
    PaymentStrategy paymentStrategy;

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void executePayment() {
        paymentStrategy.payTheBill();
    }
}
