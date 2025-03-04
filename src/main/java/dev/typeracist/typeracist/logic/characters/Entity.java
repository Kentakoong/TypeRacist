package dev.typeracist.typeracist.logic.characters;

import javafx.scene.image.Image;

public class Entity {
    protected static final HP BASE_HP = new HP(50);
    protected static final int BASE_ATK = 2;
    protected static final int BASE_DEF = 5;

    protected final String name;
    protected final HP hp;
    protected final Image image;
    protected final Skill skill;

    protected int baseAtk;
    protected int extraAtk;
    protected int baseDef;
    protected int extraDef;

    public Entity(String name, HP hp, int atk, int def, Image image, Skill skill) {
        this.name = name;
        this.hp = new HP(hp.getCurrentHP());
        this.baseAtk = atk;
        this.baseDef = def;
        this.image = image;
        this.skill = skill;
    }

    public Entity(HP hp, int atk, int def, Image image, Skill skill) {
        this.name = getClass().getSimpleName();
        this.hp = new HP(hp.getCurrentHP());
        this.baseAtk = atk;
        this.baseDef = def;
        this.image = image;
        this.skill = skill;
    }

    public Entity(Entity other) {
        this.name = other.name;
        this.hp = new HP(other.hp.getCurrentHP());
        this.baseAtk = other.baseAtk;
        this.baseDef = other.baseDef;
        this.extraAtk = other.extraAtk;
        this.extraDef = other.extraDef;
        this.image = other.image;
        this.skill = other.skill.copy();
    }

    public int getTotalAtk() {
        return baseAtk + extraAtk;
    }

    public int getTotalDef() {
        return baseDef + extraDef;
    }

    public void heal(int amount) {
        this.hp.heal(amount);
    }

    public void addAtk(int atk) {
        this.baseAtk += atk;
    }

    public void addDef(int def) {
        this.baseDef += def;
    }

    public void addExtraAtk(int extraAtk) {
        this.extraAtk += extraAtk;
    }

    public void addExtraDef(int extraDef) {
        this.extraDef += extraDef;
    }

    public void resetBonuses() {
        this.extraAtk = 0;
        this.extraDef = 0;
    }

    public int attack(Entity target) {
        int totalAtk = getTotalAtk();
        int targetDef = target.getTotalDef();
        int damageDealt = Math.max(totalAtk - targetDef, 0);
        target.hp.damage(damageDealt);
        return damageDealt;
    }

    public String getName() {
        return name;
    }

    public HP getHp() {
        return hp;
    }

    public int getBaseAtk() {
        return baseAtk;
    }

    public void setBaseAtk(int atk) {
        this.baseAtk = atk;
    }

    public int getBaseDef() {
        return baseDef;
    }

    public void setBaseDef(int def) {
        this.baseDef = def;
    }

    public Image getImage() {
        return image;
    }

    public Skill getSkill() {
        return skill;
    }

    public int getExtraAtk() {
        return extraAtk;
    }

    public void setExtraAtk(int extraAtk) {
        this.extraAtk = extraAtk;
    }

    public int getExtraDef() {
        return extraDef;
    }

    public void setExtraDef(int extraDef) {
        this.extraDef = extraDef;
    }
}
