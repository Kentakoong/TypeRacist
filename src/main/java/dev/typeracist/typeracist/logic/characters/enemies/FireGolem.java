package dev.typeracist.typeracist.logic.characters.enemies;

import dev.typeracist.typeracist.logic.characters.Enemy;
import dev.typeracist.typeracist.logic.characters.HP;
import dev.typeracist.typeracist.logic.characters.skills.FlamePunch;
import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.utils.RandomRange;
import dev.typeracist.typeracist.utils.ResourceName;

public class FireGolem extends Enemy {
    private static final String[] DESCRIPTIONS = {
            "Fire Golem on your way.",
            "Bro stands there, menacingly...",
            "It's hot in here."
    };
    private static final RandomRange COIN_RANGE = new RandomRange(50, 150);
    private static final RandomRange XP_RANGE = new RandomRange(250, 500);

    public FireGolem() {
        super(new HP(300), 55, 30,
                ResourceManager.getImage(ResourceName.IMAGE_ENEMY_FIRE_GOLEM),
                DESCRIPTIONS,
                new FlamePunch(),
                COIN_RANGE,
                XP_RANGE);
    }
}