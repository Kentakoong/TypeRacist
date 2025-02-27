package dev.typeracist.typeracist.logic.characters;

public class HP {
    private int currentHP;
    private final int maxHP;
    private final int minHP;

    public HP(int maxHP) {
        this.maxHP = maxHP;
        this.minHP = 0;
        this.currentHP = maxHP;
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public void setCurrentHP(int hp) {
        this.currentHP = Math.min(Math.max(hp, minHP), maxHP);
    }

    public void heal(int amount) {
        setCurrentHP(currentHP + amount);
    }

    public void damage(int amount) {
        setCurrentHP(currentHP - amount);
    }

    public boolean isDead() {
        return currentHP <= minHP;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public int getMinHP() {
        return minHP;
    }
}