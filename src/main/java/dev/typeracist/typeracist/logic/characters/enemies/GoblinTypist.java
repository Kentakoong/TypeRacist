package dev.typeracist.typeracist.logic.characters.enemies;

import dev.typeracist.typeracist.logic.characters.Enemy;
import dev.typeracist.typeracist.logic.characters.HP;
import dev.typeracist.typeracist.logic.characters.RandomRange;
import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.utils.ResourceName;

public class GoblinTypist extends Enemy {
    private static final String[] DESCRIPTIONS = {
            "Goblin typist is in your way.",
            "It swings its mace, scary.",
            "Bro tryna steal your coins from your pocket."
    };
    private static final RandomRange COIN_RANGE = new RandomRange(2, 5);
    private static final RandomRange XP_RANGE = new RandomRange(5, 10);

    public GoblinTypist() {
        super(new HP(7), 2, 1,
                ResourceManager.getImage(ResourceName.IMAGE_ENEMY_GOBLIN),
                DESCRIPTIONS,
                null,
                COIN_RANGE,
                XP_RANGE);
    }
}