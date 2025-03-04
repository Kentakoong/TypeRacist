package dev.typeracist.typeracist.logic.characters.skills;

import dev.typeracist.typeracist.gui.gameScene.BattlePane.BattlePane;
import dev.typeracist.typeracist.logic.characters.Skill;

public class PhantomStrike extends SkillWithProbability {
    public PhantomStrike(double probability) {
        super("Phantom Strike",
                "A deadly assassin technique that allows for a 50% chance to unleash a devastating double-damage attack, striking with supernatural precision.",
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
