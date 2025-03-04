package dev.typeracist.typeracist.logic.characters;

import java.util.Random;

public class RandomRange {
    private final int min;
    private final int max;
    private final Random random;

    public RandomRange(int min, int max) {
        this.min = min;
        this.max = max;
        this.random = new Random();
    }

    public int getRandomValue() {
        return random.nextInt(max - min + 1) + min;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
}