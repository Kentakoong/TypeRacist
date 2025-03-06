package dev.typeracist.typeracist.logic.characters.enemies;

import dev.typeracist.typeracist.logic.characters.Enemy;
import dev.typeracist.typeracist.logic.characters.HP;
import dev.typeracist.typeracist.logic.characters.skills.SkillIssue;
import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.utils.RandomRange;
import dev.typeracist.typeracist.utils.ResourceName;

public class Nattee115 extends Enemy {
    private static final String[] DESCRIPTIONS = {
            "Hello Hello Hello",
            "It too easy!!",
            "Calm down, calm down, breath.."
    };
    private static final RandomRange COIN_RANGE = new RandomRange(115, 1150000);
    private static final RandomRange XP_RANGE = new RandomRange(115, 1150000);

    public Nattee115() {
        super(
                new HP(1150), 115, 115,
                ResourceManager.getImage(ResourceName.IMAGE_ENEMY_NATTEE_1),
                DESCRIPTIONS,
                new SkillIssue(),
                COIN_RANGE,
                XP_RANGE
        );
    }
}