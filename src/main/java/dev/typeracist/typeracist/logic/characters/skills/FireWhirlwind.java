package dev.typeracist.typeracist.logic.characters.skills;

import dev.typeracist.typeracist.gui.gameScene.BattlePane.BattlePane;
import dev.typeracist.typeracist.logic.characters.Skill;

public class FireWhirlwind extends SkillWithProbability {
    private static final int FIRE_DAMAGE = 10;
    private static final int DURATION = 2;

    public FireWhirlwind() {
        super("Fire Whirlwind",
                "Every turn have a 15% chance to Deals massive 10 fire damage for 2 turns",
                0.15);
    }

    @Override
    public Skill copy() {
        return new FireWhirlwind();
    }

    @Override
    public void useSkill(BattlePane battlePane) {
        // Fire whirlwind effect will be handled in battle logic
        super.useSkill(battlePane);
    }

    public int getFireDamage() {
        return FIRE_DAMAGE;
    }

    public int getDuration() {
        return DURATION;
    }
}