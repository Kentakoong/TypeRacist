package dev.typeracist.typeracist.logic.characters.enemies;

import dev.typeracist.typeracist.logic.characters.Enemy;
import dev.typeracist.typeracist.logic.characters.HP;
import dev.typeracist.typeracist.logic.characters.RandomRange;
import dev.typeracist.typeracist.logic.characters.skills.WebTrap;
import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.utils.ResourceName;

public class GiantSpider extends Enemy {
    private static final String[] DESCRIPTIONS = {
            "A monstrous arachnid lurks in the shadows.",
            "It's crawling to the center of its web.",
            "Webs everywhere. You're trapped."
    };
    private static final RandomRange COIN_RANGE = new RandomRange(40, 50);
    private static final RandomRange XP_RANGE = new RandomRange(30, 45);

    public GiantSpider() {
        super(new HP(50), 10, 12,
                ResourceManager.getImage(ResourceName.IMAGE_ENEMY_SPIDER),
                DESCRIPTIONS,
                new WebTrap(),
                COIN_RANGE,
                XP_RANGE);
    }
}