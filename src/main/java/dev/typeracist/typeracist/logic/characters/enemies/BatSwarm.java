package dev.typeracist.typeracist.logic.characters.enemies;

import dev.typeracist.typeracist.logic.characters.Enemy;
import dev.typeracist.typeracist.logic.characters.HP;
import dev.typeracist.typeracist.logic.characters.RandomRange;
import dev.typeracist.typeracist.logic.characters.skills.Evasion;
import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.utils.ResourceName;

public class BatSwarm extends Enemy {
    private static final String[] DESCRIPTIONS = {
            "Oh! a bat.",
            "It's flapping its wing.",
            "Screaming Echolocation."
    };
    private static final RandomRange COIN_RANGE = new RandomRange(5, 8);
    private static final RandomRange XP_RANGE = new RandomRange(7, 12);

    public BatSwarm() {
        super(new HP(12), 3, 2,
                ResourceManager.getImage(ResourceName.IMAGE_ENEMY_BAT),
                DESCRIPTIONS,
                new Evasion(),
                COIN_RANGE,
                XP_RANGE);
    }
}