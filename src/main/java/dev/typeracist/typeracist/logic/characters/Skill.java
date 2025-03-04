package dev.typeracist.typeracist.logic.characters;

import dev.typeracist.typeracist.gui.gameScene.BattlePane.BattlePane;

public abstract class Skill {
    protected final String name;
    protected final String description;

    protected final int cooldown;
    protected int currentCooldownTurns;

    public Skill(String name, String description, int cooldown) {
        this.name = name;
        this.description = description;
        this.cooldown = cooldown;
        this.currentCooldownTurns = 0;
    }

    public Skill(String name, String description) {
        this(name, description, 0);
    }

    public abstract Skill copy();

    public abstract void useSkill(BattlePane battlePane);

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getCooldownTurns() {
        return cooldown;
    }

    public boolean isOnCooldown() {
        return currentCooldownTurns > 0;
    }

    public void tickCooldown() {
        currentCooldownTurns--;
    }

    public void resetCooldown() {
        currentCooldownTurns = cooldown;
    }
}