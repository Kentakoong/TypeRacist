package dev.typeracist.typeracist.logic.characters;

import javafx.scene.image.Image;

public class Enemy extends Entity {
    protected int dropXP;
    protected int dropCoin;
    protected String description;

    public Enemy(HP hp, int atk, int def, Image image, String description, Skill skill) {
        super(hp, atk, def, image, skill);
        this.description = description;
        this.dropXP = 0;
        this.dropCoin = 0;
    }

    public Enemy(int atk, int def, Image image, String description, Skill skill) {
        super(BASE_HP, atk, def, image, skill);
        this.description = description;
        this.dropXP = 0;
        this.dropCoin = 0;
    }

    public int attack(Entity target, int extraDefense) {
        int realDamage = Math.max(atk - extraDefense * target.getDef(), 0);
        target.getHp().damage(realDamage);

        return realDamage;
    }

    public int getDropXP() {
        return dropXP;
    }

    public int getDropCoin() {
        return dropCoin;
    }

    public String getDescription() {
        return description;
    }
}
