package dev.typeracist.typeracist.logic.characters;

import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.utils.ResourceName;

public class Warrior extends Character {
    public Warrior() {
        super(4, 5, ResourceManager.getImage(ResourceName.WARRIOR)); // ATK: 4, DEF: 5
    }

    @Override
    public void useAbility() {
        // Warrior has no special ability
    }
}