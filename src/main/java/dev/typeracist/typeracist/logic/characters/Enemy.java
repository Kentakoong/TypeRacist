package dev.typeracist.typeracist.logic.characters;

import javafx.scene.image.Image;

public class Enemy extends Entity {
    protected int dropXP;
    protected int dropCoin;
    protected String description;

    public Enemy(HP hp, int atk, int def, Image image, String description) {
        super(hp, atk, def, image);
        this.description = description;
        this.dropXP = 0;
        this.dropCoin = 0;
    }

    public Enemy(int atk, int def, Image image, String description) {
        super(BASE_HP, atk, def, image);
        this.description = description;
        this.dropXP = 0;
        this.dropCoin = 0;
    }

    @Override
    public void useAbility() {

    }
}
