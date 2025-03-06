package dev.typeracist.typeracist.logic.characters;

public class HP {
    private int maxHP;
    private int currentHP;

    public HP(int maxHP, int currentHP) {
        this.maxHP = maxHP;
        this.currentHP = currentHP;
    }

    public HP(int maxHP) {
        this(maxHP, maxHP);
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public void setCurrentHP(int hp) {
        this.currentHP = Math.min(Math.max(hp, 0), maxHP);
    }

    public void heal(int amount) {
        setCurrentHP(currentHP + amount);
    }

    public void damage(int amount) {
        setCurrentHP(currentHP - amount);
    }

    public boolean isDead() {
        return currentHP <= 0;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int amount) {
        this.maxHP = amount;
        this.currentHP = Math.min(currentHP, maxHP);
    }

    public void addMaxHp(int amount) {
        this.maxHP += amount;
        this.currentHP = Math.min(currentHP, maxHP);
    }
}