package dev.typeracist.typeracist.logic.characters.entity.character;

import dev.typeracist.typeracist.logic.characters.entity.Character;
import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.utils.ResourceName;

import java.util.Random;

public class Assassin extends Character {
    private final Random random;

    public Assassin() {
        super(5, 3, ResourceManager.getImage(ResourceName.IMAGE_CHARACTER_ASSASSIN)); // ATK: 5, DEF: 3
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