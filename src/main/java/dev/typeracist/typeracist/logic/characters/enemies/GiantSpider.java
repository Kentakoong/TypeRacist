package dev.typeracist.typeracist.logic.characters.enemies;

import dev.typeracist.typeracist.logic.characters.Enemy;
import dev.typeracist.typeracist.logic.characters.HP;
import dev.typeracist.typeracist.logic.characters.skills.WebTrap;
import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.utils.RandomRange;
import dev.typeracist.typeracist.utils.ResourceName;

public class GiantSpider extends Enemy {
    private static final String[] DESCRIPTIONS = {
            "A monstrous arachnid lurks in the shadows.",
            "It's crawling to the center of its web.",
            "Webs everywhere. You're trapped."
    };
    private static final RandomRange COIN_RANGE = new RandomRange(200, 350);
    private static final RandomRange XP_RANGE = new RandomRange(1000, 2000);

    public GiantSpider() {
        super(new HP(500), 65, 40,
                ResourceManager.getImage(ResourceName.IMAGE_ENEMY_SPIDER),
                DESCRIPTIONS,
                new WebTrap(),
                COIN_RANGE,
                XP_RANGE);
    }
}