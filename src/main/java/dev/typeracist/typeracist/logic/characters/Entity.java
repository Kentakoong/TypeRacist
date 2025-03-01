package dev.typeracist.typeracist.logic.characters;

public abstract class Entity {
    protected static final HP BASE_HP = new HP(10);
    protected static final int BASE_ATK = 2;
    protected static final int BASE_DEF = 5;

    protected final HP hp;
    protected final int atk;
    protected final int def;


    public Entity(HP hp, int atk, int def) {
        this.hp = hp;
        this.atk = atk;
        this.def = def;
    }

    public Entity() {
        this(BASE_HP, BASE_ATK, BASE_DEF);
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

    // Abstract method for character-specific ability
    public abstract void useAbility();
}
