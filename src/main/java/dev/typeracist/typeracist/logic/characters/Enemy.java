package dev.typeracist.typeracist.logic.characters;

import javafx.scene.image.Image;
import java.util.Random;

import dev.typeracist.typeracist.utils.RandomRange;

public class Enemy extends Entity {
    protected int dropXP;
    protected int dropCoin;
    protected String description;
    private static final Random random = new Random();

    public Enemy(HP hp, int atk, int def, Image image, String[] descriptions, Skill skill, RandomRange coinRange,
            RandomRange xpRange) {
        super(hp, atk, def, image, skill);
        this.description = getRandomDescription(descriptions);
        this.dropCoin = coinRange.getRandomValue();
        this.dropXP = xpRange.getRandomValue();
    }

    public Enemy(int atk, int def, Image image, String[] descriptions, Skill skill, RandomRange coinRange,
            RandomRange xpRange) {
        super(BASE_HP, atk, def, image, skill);
        this.description = getRandomDescription(descriptions);
        this.dropCoin = coinRange.getRandomValue();
        this.dropXP = xpRange.getRandomValue();
    }

    protected static String getRandomDescription(String[] descriptions) {
        return descriptions[random.nextInt(descriptions.length)];
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
