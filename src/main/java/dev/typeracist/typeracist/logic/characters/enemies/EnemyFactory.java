package dev.typeracist.typeracist.logic.characters.enemies;

import dev.typeracist.typeracist.logic.characters.Enemy;
import java.util.Random;

public class EnemyFactory {
    private static final Random random = new Random();

    public static Enemy createRandomEasyEnemy() {
        int choice = random.nextInt(5);
        return switch (choice) {
            case 0 -> new SlimeBlob();
            case 1 -> new GoblinTypist();
            case 2 -> new SkeletonScribe();
            case 3 -> new BatSwarm();
            case 4 -> new Creeper();
            default -> throw new IllegalStateException("Invalid enemy choice");
        };
    }

    public static Enemy createRandomMediumEnemy() {
        int choice = random.nextInt(4);
        return switch (choice) {
            case 0 -> new FireGolem();
            case 1 -> new CursedSorcerer();
            case 2 -> new GiantSpider();
            case 3 -> new FireDragon();
            default -> throw new IllegalStateException("Invalid enemy choice");
        };
    }

    public static Enemy createRandomEnemy() {
        return random.nextBoolean() ? createRandomEasyEnemy() : createRandomMediumEnemy();
    }
}