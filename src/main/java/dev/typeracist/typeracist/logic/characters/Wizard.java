package dev.typeracist.typeracist.logic.characters;

import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.utils.ResourceName;

public class Wizard extends Character {
    private static final int STUN_COOLDOWN = 3;
    private int turnsSinceLastStun;

    public Wizard() {
        super(3, 4, ResourceManager.getImage(ResourceName.IMAGE_CHARACTER_WIZARD)); // ATK: 3, DEF: 4
        this.turnsSinceLastStun = STUN_COOLDOWN; // Ready to use at start
    }

    @Override
    public void useAbility() {
        if (canUseStun()) {
            // Stun effect will be applied
            turnsSinceLastStun = 0;
        }
    }

    public boolean canUseStun() {
        return turnsSinceLastStun >= STUN_COOLDOWN;
    }

    public void incrementTurn() {
        turnsSinceLastStun++;
    }
}