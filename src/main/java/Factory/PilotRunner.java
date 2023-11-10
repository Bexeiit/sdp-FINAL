package Factory;

import java.util.Scanner;

public class PilotRunner {
    public void runner() {
        Scanner cin = new Scanner(System.in);

        System.out.println("""
                Choose a Pilot:
                1. Scat
                2. Air Asatana""");
        int Pilot = cin.nextInt();

        switch (Pilot) {
            case 1:
                AirAsatana airAsatana = new AirAsatana();
                airAsatana.chooseFlight();
                airAsatana.fly();

                break;
            case 2:
                Scat scat = new Scat();
                scat.chooseFlight();
                scat.fly();
                break;
        }
    }
}
