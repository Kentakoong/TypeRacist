package dev.typeracist.typeracist.logic.characters;

public abstract class Character extends Entity {
    protected static final HP BASE_HP = new HP(35);
    protected static final int BASE_ATK = 4;
    protected static final int BASE_DEF = 5;
    protected static final int XP_TO_LEVEL_UP = 25;
    protected int xp;
    protected int coin;

    public Character(HP hp, int atk, int def) {
        super(hp, atk, def);
        this.xp = 0;
        this.coin = 0;
    }

    public Character(int atk, int def) {
        this(BASE_HP, atk, def);
    }

    public Character() {
        this(BASE_HP, BASE_ATK, BASE_DEF);
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
}