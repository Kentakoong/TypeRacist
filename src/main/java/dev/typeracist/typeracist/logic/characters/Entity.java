package dev.typeracist.typeracist.logic.characters;

import javafx.scene.image.Image;

public class Entity {
    protected static final HP BASE_HP = new HP(50);
    protected static final int BASE_ATK = 2;
    protected static final int BASE_DEF = 5;
    protected final String name;
    protected final HP hp;
    protected int atk;
    protected int def;
    protected final Image image;
    protected final Skill skill;

    public Entity(String name, HP hp, int atk, int def, Image image, Skill skill) {
        this.name = name;
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.image = image;
        this.skill = skill;
    }

    public Entity(HP hp, int atk, int def, Image image, Skill skill) {
        this.name = getClass().getSimpleName();
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.image = image;
        this.skill = skill;
    }

    /**
     * Copy constructor to create a deep copy of an Entity
     * 
     * @param other The Entity to copy from
     */
    public Entity(Entity other) {
        this.name = new String(other.name);
        this.hp = new HP(other.hp.getCurrentHP());
        this.atk = other.atk;
        this.def = other.def;
        this.image = other.image;
        this.skill = other.skill.copy();
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

    public void setHp(HP hp) {
        this.hp.setCurrentHP(hp.getCurrentHP());
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public void addAtk(int atk) {
        this.atk += atk;
    }

    public void addDef(int def) {
        this.def += def;
    }

    public int attack(Entity target) {
        int realDamage = Math.max(atk - target.def, 0);
        target.hp.damage(realDamage);

        return realDamage;
    }
}
