package TourPlannerUI.businesslayer;

import java.util.Random;

public class TourNameGenerator {
    public static String GenerateName() {
        String[] destinations = {"Wien","St. Pölten","Innsbruck","Salzburg","Wörgel","Kufstein","Jenbach","Krems","Tulln","Wiener Berg","Stadtpark","Graz","DisneyLand","NintendoWorld","Mars","Mond","Zürich","Amsterdam","Hamburg","Berlin","München"};
        String name = "";

        int rand = new Random().nextInt(destinations.length);
        name += destinations[rand];
        name += " - ";
        int rand2 = new Random().nextInt(destinations.length);
        while (rand == rand2){
            rand2 = new Random().nextInt(destinations.length);
        }
        name += destinations[rand2];

        return name;
    }
}
