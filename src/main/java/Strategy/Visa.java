package Strategy;

public class Visa implements  PaymentStrategy{
    @Override
    public void payTheBill() {
        System.out.println("Your Payment was made with Visa card...");
    }
}
