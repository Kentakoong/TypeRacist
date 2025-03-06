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
    private static final RandomRange COIN_RANGE = new RandomRange(300, 500);
    private static final RandomRange XP_RANGE = new RandomRange(2500, 5000);

    public FireDragon() {
        super(new HP(750), 75, 50,
                ResourceManager.getImage(ResourceName.IMAGE_ENEMY_DRAGON),
                DESCRIPTIONS,
                new FireWhirlwind(),
                COIN_RANGE,
                XP_RANGE);
    }
}