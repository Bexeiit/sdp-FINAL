package Factory;

public class Scat implements Pilot{
    @Override
    public void chooseFlight() {
        System.out.println("I have choosen Pilot from Scat company");
    }

    @Override
    public String fly() {
        return "Pilot from Scat company have made a flight.";
    }
}
