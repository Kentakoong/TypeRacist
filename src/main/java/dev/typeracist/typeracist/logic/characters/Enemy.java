package dev.typeracist.typeracist.logic.characters;

import java.util.Random;

import dev.typeracist.typeracist.logic.global.GameLogic;
import dev.typeracist.typeracist.utils.RandomRange;
import javafx.scene.image.Image;

public class Enemy extends Entity {
    private static final Random random = new Random();
    protected int dropXP;
    protected int dropCoin;
    protected String description;

    public Enemy(HP hp, int atk, int def, Image image, String[] descriptions, Skill skill, RandomRange coinRange,
            RandomRange xpRange) {
        super(hp, atk, def, image, skill);

        switch (GameLogic.getInstance().getCurrentDifficulty()) {
            case EASY -> {
                this.getHp().setMaxHP((int) Math.floor(hp.getMaxHP() * 0.8));
                this.baseAtk = (int) Math.floor(atk * 0.8);
                this.baseDef = (int) Math.floor(def * 0.8);
            }
            case NORMAL -> {
                this.getHp().setMaxHP(hp.getMaxHP());
                this.baseAtk = atk;
                this.baseDef = def;
            }
            case HARD -> {
                this.getHp().setMaxHP((int) Math.floor(hp.getMaxHP() * 1.5));
                this.baseAtk = (int) Math.floor(atk * 1.5);
                this.baseDef = (int) Math.floor(def * 1.5);
            }
            case HELL -> {
                this.getHp().setMaxHP(hp.getMaxHP() * 2);
                this.baseAtk *= 2;
                this.baseDef *= 2;
            }
        }

        this.hp.setCurrentHP(this.hp.getMaxHP());
        this.description = getRandomDescription(descriptions);
        this.dropCoin = coinRange.getRandomValue();
        this.dropXP = xpRange.getRandomValue();
    }

    public Enemy(int atk, int def, Image image, String[] descriptions, Skill skill, RandomRange coinRange,
            RandomRange xpRange) {
        this(BASE_HP, atk, def, image, descriptions, skill, coinRange, xpRange);
    }

    protected static String getRandomDescription(String[] descriptions) {
        return descriptions[random.nextInt(descriptions.length)];
    }

    public int attack(Entity target, int extraDefense) {
        int realDamage = Math.max(getTotalAtk() - extraDefense * target.getTotalDef(), 0);
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
