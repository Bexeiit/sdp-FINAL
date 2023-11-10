package Strategy;

public class MasterCard implements PaymentStrategy{
    @Override
    public void payTheBill() {
        System.out.println("Your payment was made with Master Card...");
    }
}
