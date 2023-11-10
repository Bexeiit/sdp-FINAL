package Factory;

public class ScatFactory implements PilotFactory{
    @Override
    public Pilot trainPilot() {
        return new Scat();
    }
}
