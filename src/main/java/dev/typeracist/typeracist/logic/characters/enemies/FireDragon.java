package dev.typeracist.typeracist.logic.characters.enemies;

import dev.typeracist.typeracist.logic.characters.Enemy;
import dev.typeracist.typeracist.logic.characters.HP;
import dev.typeracist.typeracist.logic.characters.skills.FireWhirlwind;
import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.utils.RandomRange;
import dev.typeracist.typeracist.utils.ResourceName;

public class FireDragon extends Enemy {
    private static final String[] DESCRIPTIONS = {
            "The sky burns as it descends.",
            "Bro's wingspan is bigger than your future…",
            "Flames roar. Nowhere to run."
    };
    private static final RandomRange COIN_RANGE = new RandomRange(40, 50);
    private static final RandomRange XP_RANGE = new RandomRange(30, 45);

    public FireDragon() {
        super(new HP(750), 55, 20,
                ResourceManager.getImage(ResourceName.IMAGE_ENEMY_DRAGON),
                DESCRIPTIONS,
                new FireWhirlwind(),
                COIN_RANGE,
                XP_RANGE);
    }
}