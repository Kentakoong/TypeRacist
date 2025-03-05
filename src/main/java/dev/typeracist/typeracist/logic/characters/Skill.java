package dev.typeracist.typeracist.logic.characters;

public abstract class Skill {
    protected final String name;
    protected final String description;
    protected final SkillActivationOnState activationOnState;
    protected final int cooldown;
    protected int currentCooldownTurns;

    public Skill(String name, String description, SkillActivationOnState activationOnState, int cooldown) {
        this.name = name;
        this.description = description;
        this.cooldown = cooldown;
        this.currentCooldownTurns = 0;
        this.activationOnState = activationOnState;
    }

    public Skill(String name, String description, SkillActivationOnState activationOnState) {
        this(name, description, activationOnState, 0);
    }

    public abstract Skill copy();

    public SkillActivationOnState getActivationOnState() {
        return activationOnState;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getCooldownTurns() {
        return cooldown;
    }

    public int getCurrentCooldownTurns() {
        return currentCooldownTurns;
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