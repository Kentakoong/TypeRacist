package dev.typeracist.typeracist.logic.characters;

import java.util.Random;

public class Assassin extends Character {
    private final Random random;

    public Assassin() {
        super(5, 3); // ATK: 5, DEF: 3
        this.random = new Random();
    }

    @Override
    public void useAbility() {
        // 20% chance to dodge attack
        // Dodge effect will be checked when receiving damage
    }

    public boolean canDodgeAttack() {
        return random.nextDouble() < 0.2; // 20% chance to dodge
    }
}