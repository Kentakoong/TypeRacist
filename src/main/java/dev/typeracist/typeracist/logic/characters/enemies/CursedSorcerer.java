package dev.typeracist.typeracist.logic.characters.enemies;

import dev.typeracist.typeracist.logic.characters.Enemy;
import dev.typeracist.typeracist.logic.characters.HP;
import dev.typeracist.typeracist.logic.characters.skills.Hex;
import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.utils.RandomRange;
import dev.typeracist.typeracist.utils.ResourceName;

public class CursedSorcerer extends Enemy {
    private static final String[] DESCRIPTIONS = {
            "You get crossed by a sorcerer",
            "Don't let him use Avada Kedavra.",
            "He's mumbling something."
    };
    private static final RandomRange COIN_RANGE = new RandomRange(125, 200);
    private static final RandomRange XP_RANGE = new RandomRange(500, 1000);

    public CursedSorcerer() {
        super(new HP(350), 55, 20,
                ResourceManager.getImage(ResourceName.IMAGE_ENEMY_SORCERER),
                DESCRIPTIONS,
                new Hex(),
                COIN_RANGE,
                XP_RANGE);
    }
}