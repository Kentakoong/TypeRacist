package dev.typeracist.typeracist.logic.characters;

public abstract class Character {
    protected final HP hp;
    protected final int atk;
    protected final int def;
    protected int xp;
    protected int coin;
    protected static final int BASE_HP = 35;
    protected static final int XP_TO_LEVEL_UP = 25;

    public Character(int atk, int def) {
        this.hp = new HP(BASE_HP);
        this.atk = atk;
        this.def = def;
        this.xp = 0;
        this.coin = 0;
    }

    public HP getHp() {
        return hp;
    }

    public int getAtk() {
        return atk;
    }

    public int getDef() {
        return def;
    }

    public int getXp() {
        return xp;
    }

    public void gainXp(int amount) {
        this.xp += amount;
    }

    public boolean canLevelUp() {
        return xp >= XP_TO_LEVEL_UP;
    }

    public int getCoin() {
        return coin;
    }

    public void gainCoin(int amount) {
        this.coin += amount;
    }

    public void spendCoin(int amount) {
        if (amount > coin) {
            throw new IllegalStateException("Not enough coins");
        }
        this.coin -= amount;
    }

    // Abstract method for character-specific ability
    public abstract void useAbility();
}