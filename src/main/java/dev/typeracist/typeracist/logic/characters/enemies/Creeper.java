package dev.typeracist.typeracist.logic.characters.enemies;

import dev.typeracist.typeracist.logic.characters.Enemy;
import dev.typeracist.typeracist.logic.characters.HP;
import dev.typeracist.typeracist.logic.characters.RandomRange;
import dev.typeracist.typeracist.logic.characters.skills.CreeperExplosion;
import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.utils.ResourceName;

public class Creeper extends Enemy {
    private static final String[] DESCRIPTIONS = {
            "YOOO, a Creeper!",
            "HSSSS SHHSHSSH",
            "Is it blowing up?"
    };
    private static final RandomRange COIN_RANGE = new RandomRange(8, 15);
    private static final RandomRange XP_RANGE = new RandomRange(10, 12);

    public Creeper() {
        super(new HP(20), 4, 3,
                ResourceManager.getImage(ResourceName.IMAGE_ENEMY_CREEPER),
                DESCRIPTIONS,
                new CreeperExplosion(),
                COIN_RANGE,
                XP_RANGE);
    }
}