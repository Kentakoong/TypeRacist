package dev.typeracist.typeracist.logic.characters;

public class Enemy extends Entity {
    protected int dropXP;
    protected int dropCoin;
    protected String description;

    public Enemy(HP hp, int atk, int def, String description) {
        super(hp, atk, def);
        this.description = description;
        this.dropXP = 0;
        this.dropCoin = 0;
    }

    public Enemy(int atk, int def, String description) {
        super(BASE_HP, atk, def);
        this.description = description;
        this.dropXP = 0;
        this.dropCoin = 0;
    }

    public Enemy() {
        super();
        this.dropXP = 0;
        this.dropCoin = 0;
    }

    @Override
    public void useAbility() {

    }
}
