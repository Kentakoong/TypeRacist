package dev.typeracist.typeracist.logic.characters.enemies;

import dev.typeracist.typeracist.logic.characters.Enemy;
import dev.typeracist.typeracist.logic.characters.HP;
import dev.typeracist.typeracist.logic.characters.skills.Evasion;
import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.utils.RandomRange;
import dev.typeracist.typeracist.utils.ResourceName;

public class BatSwarm extends Enemy {
    private static final String[] DESCRIPTIONS = {
            "Oh! a bat.",
            "It's flapping its wing.",
            "Screaming Echolocation."
    };
    private static final RandomRange COIN_RANGE = new RandomRange(25, 60);
    private static final RandomRange XP_RANGE = new RandomRange(100, 150);

    public BatSwarm() {
        super(new HP(75), 50, 20,
                ResourceManager.getImage(ResourceName.IMAGE_ENEMY_BAT),
                DESCRIPTIONS,
                new Evasion(),
                COIN_RANGE,
                XP_RANGE);
    }
}