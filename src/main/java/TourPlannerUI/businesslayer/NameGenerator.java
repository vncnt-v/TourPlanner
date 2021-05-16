package TourPlannerUI.businesslayer;

import java.util.Random;

public class NameGenerator {
    public static String GenerateName(int nameLength) {
        String[] consonants = {"b","c","d","f","g","h","j","k","l","m","n","p","q","r","s","t","v","w","x","y","z"};
        String[] vocals = {"a","e","i","o","u"};
        String name = "";

        name += getRandom(consonants);
        name += getRandom(vocals);

        int currentLenght = 2;

        while (currentLenght < nameLength) {
            name += getRandom(consonants);
            name += getRandom(vocals);
            currentLenght += 2;
        }
        return name;
    }

    public static String getRandom(String[] arr) {
        int rnd = new Random().nextInt(arr.length);
        return arr[rnd];
    }
}
