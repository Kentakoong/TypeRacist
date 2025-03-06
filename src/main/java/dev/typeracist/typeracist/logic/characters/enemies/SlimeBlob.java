package dev.typeracist.typeracist.logic.characters.enemies;

import dev.typeracist.typeracist.logic.characters.Enemy;
import dev.typeracist.typeracist.logic.characters.HP;
import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.utils.RandomRange;
import dev.typeracist.typeracist.utils.ResourceName;

public class SlimeBlob extends Enemy {
    private static final String[] DESCRIPTIONS = {
            "Slime bob approaches.",
            "It tries to reshape to something.",
            "Looks like a green blob."
    };
    private static final RandomRange COIN_RANGE = new RandomRange(1, 5);
    private static final RandomRange XP_RANGE = new RandomRange(5, 10);

    public SlimeBlob() {
        super(new HP(20), 5, 3,
                ResourceManager.getImage(ResourceName.IMAGE_ENEMY_SLIME),
                DESCRIPTIONS,
                null,
                COIN_RANGE,
                XP_RANGE);
    }
}