package dev.typeracist.typeracist.logic.characters;

public class Warrior extends Character {
    public Warrior() {
        super(4, 5); // ATK: 4, DEF: 5
    }

    @Override
    public void useAbility() {
        // Warrior has no special ability
    }
}