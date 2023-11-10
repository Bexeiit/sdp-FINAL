package Strategy;

public class Kaspi implements PaymentStrategy{
    @Override
    public void payTheBill() {
        System.out.println("Your payment was made with Kaspi application...");
    }

}
