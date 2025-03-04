package dev.typeracist.typeracist.logic.characters.skills;

import dev.typeracist.typeracist.gui.gameScene.BattlePane.BattlePane;
import dev.typeracist.typeracist.logic.characters.Skill;

public class ShadowSlip extends SkillWithProbability {
    public ShadowSlip(double probability) {
        super("Shadow Slip",
                "A cunning technique that allows the assassin to momentarily phase out of an incoming attack, granting a 20% chance to completely avoid damage.",
                probability);
    }

    @Override
    public Skill copy() {
        return new MagicWand();
    }

    @Override
    public void useSkill(BattlePane battlePane) {
        // TODO: Implement the skill logic
        super.useSkill(battlePane);

    }

}
