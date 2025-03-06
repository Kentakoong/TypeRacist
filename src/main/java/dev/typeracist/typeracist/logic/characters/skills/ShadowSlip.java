package dev.typeracist.typeracist.logic.characters.skills;

import dev.typeracist.typeracist.logic.characters.Skill;
import dev.typeracist.typeracist.logic.characters.SkillActivationOnState;
import dev.typeracist.typeracist.logic.characters.SkillOnEnvironment;
import dev.typeracist.typeracist.logic.game.battle.BattlePaneStateManager;

public class ShadowSlip extends SkillWithProbability implements SkillOnEnvironment {
    public ShadowSlip(double probability) {
        super("Shadow Slip",
                "A technique that gives a 20% chance to evade damage.",
                SkillActivationOnState.ACTIVATION_ON_DEFENSE,
                probability);
    }

    @Override
    public Skill copy() {
        return new MagicWand();
    }

    @Override
    public void useSkill(BattlePaneStateManager manager) {
        manager.getContext().getCurrentTurnContext().addEnemyAttackModifier((int) -1e9);
    }

}
