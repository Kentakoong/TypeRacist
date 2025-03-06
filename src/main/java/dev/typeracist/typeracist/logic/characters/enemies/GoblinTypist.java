package dev.typeracist.typeracist.logic.characters.enemies;

import dev.typeracist.typeracist.logic.characters.Enemy;
import dev.typeracist.typeracist.logic.characters.HP;
import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.utils.RandomRange;
import dev.typeracist.typeracist.utils.ResourceName;

public class GoblinTypist extends Enemy {
    private static final String[] DESCRIPTIONS = {
            "Goblin typist is in your way.",
            "It swings its mace, scary.",
            "Bro tryna steal your coins from your pocket."
    };
    private static final RandomRange COIN_RANGE = new RandomRange(5, 15);
    private static final RandomRange XP_RANGE = new RandomRange(15, 30);

    public GoblinTypist() {
        super(new HP(50), 25, 5,
                ResourceManager.getImage(ResourceName.IMAGE_ENEMY_GOBLIN),
                DESCRIPTIONS,
                null,
                COIN_RANGE,
                XP_RANGE);
    }
}