package utils;

import java.util.Random;

public class RandomNumberGenerator {

    Random random = new Random();

    public double generateRandomNumber(double min, double max) {
        return min + (max - min) * random.nextDouble();
    }

}
