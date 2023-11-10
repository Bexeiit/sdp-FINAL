package Factory;

public class AirAsatanaFactory implements PilotFactory{

    @Override
    public Pilot trainPilot() {
        return new AirAsatana();
    }
}
