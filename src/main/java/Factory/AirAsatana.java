package Factory;

public class AirAsatana implements Pilot{
    @Override
    public void chooseFlight() {
        System.out.println("I have choosen Pilot from AirAsatana company");
    }

    @Override
    public String fly() {
        return "Pilot from AirAstana company have made a flight.";
    }
}
