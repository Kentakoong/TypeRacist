package dev.typeracist.typeracist.logic.characters;

import javafx.scene.image.Image;

public abstract class Entity {
    protected static final HP BASE_HP = new HP(10);
    protected static final int BASE_ATK = 2;
    protected static final int BASE_DEF = 5;

    protected final HP hp;
    protected final int atk;
    protected final int def;
    protected final Image image;

    public Entity(HP hp, int atk, int def, Image image) {
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.image = image;
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

    // Abstract method for character-specific ability
    public abstract void useAbility();
}
