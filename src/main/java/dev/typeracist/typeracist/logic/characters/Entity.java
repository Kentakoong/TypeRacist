package dev.typeracist.typeracist.logic.characters;

import javafx.scene.image.Image;

public abstract class Entity {
    protected static final HP BASE_HP = new HP(50);
    protected static final int BASE_ATK = 2;
    protected static final int BASE_DEF = 5;
    protected final String name;
    protected final HP hp;
    protected final int atk;
    protected final int def;
    protected final Image image;

    public Entity(String name, HP hp, int atk, int def, Image image) {
        this.name = name;
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.image = image;
    }

    public Entity(HP hp, int atk, int def, Image image) {
        this.name = getClass().getSimpleName();
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.image = image;
    }

    public String getName() {
        return name;
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

    public Image getImage() {
        return image;
    }

    public int attack(Entity target) {
        int realDamage = Math.max(atk - target.def, 0);
        target.hp.damage(realDamage);

        return realDamage;
    }

    // Abstract method for character-specific ability
    public abstract void useAbility();
}
