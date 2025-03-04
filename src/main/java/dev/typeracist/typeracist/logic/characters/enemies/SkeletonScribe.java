package dev.typeracist.typeracist.logic.characters.enemies;

import dev.typeracist.typeracist.logic.characters.Enemy;
import dev.typeracist.typeracist.logic.characters.HP;
import dev.typeracist.typeracist.logic.characters.RandomRange;
import dev.typeracist.typeracist.logic.characters.skills.UndeadEndurance;
import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.utils.ResourceName;

public class SkeletonScribe extends Enemy {
    private static final String[] DESCRIPTIONS = {
            "Skeleton approaches near you.",
            "SPOOKY SCARY SKELETON.",
            "It's using its bones to be a weapon."
    };
    private static final RandomRange COIN_RANGE = new RandomRange(5, 7);
    private static final RandomRange XP_RANGE = new RandomRange(7, 12);

    public SkeletonScribe() {
        super(new HP(10), 3, 1,
                ResourceManager.getImage(ResourceName.IMAGE_ENEMY_SKELETON),
                DESCRIPTIONS,
                new UndeadEndurance(),
                COIN_RANGE,
                XP_RANGE);
    }
}