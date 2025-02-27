package dev.typeracist.typeracist.logic.characters;

public class Wizard extends Character {
    private int turnsSinceLastStun;
    private static final int STUN_COOLDOWN = 3;

    public Wizard() {
        super(3, 4); // ATK: 3, DEF: 4
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