package dev.typeracist.typeracist.logic.characters;

import java.util.Random;

public class Archer extends Character {
    private final Random random;

    public Archer() {
        super(4, 3); // ATK: 4, DEF: 3
        this.random = new Random();
    }

    @Override
    public void useAbility() {
        // 50% chance to do double damage
        if (random.nextDouble() < 0.5) {
            // Double damage will be applied when attacking
            // Implementation of attack mechanism will be handled separately
        }
    }

    public boolean isDoubleDamageActive() {
        return random.nextDouble() < 0.5;
    }
}