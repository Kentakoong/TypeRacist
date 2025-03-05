package dev.typeracist.typeracist.logic.characters.skills;

import dev.typeracist.typeracist.logic.characters.Skill;
import dev.typeracist.typeracist.logic.characters.SkillActivationOnState;
import dev.typeracist.typeracist.logic.characters.SkillOnEnvironment;
import dev.typeracist.typeracist.logic.gameScene.BattlePaneStateManager;

public class ShadowSlip extends SkillWithProbability implements SkillOnEnvironment {
    public ShadowSlip(double probability) {
        super("Shadow Slip",
                "A cunning technique that allows the assassin to momentarily phase out of an incoming attack, granting a 20% chance to completely avoid damage.",
                SkillActivationOnState.ACTIVATION_ON_DEFENSE,
                probability);
    }

    @Override
    public Skill copy() {
        return new MagicWand();
    }

    @Override
    public void useSkill(BattlePaneStateManager manager) {
    }

}
