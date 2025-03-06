package dev.typeracist.typeracist.logic.characters.skills;

import dev.typeracist.typeracist.logic.characters.Skill;
import dev.typeracist.typeracist.logic.characters.SkillActivationOnState;
import dev.typeracist.typeracist.logic.characters.SkillOnEnvironment;
import dev.typeracist.typeracist.logic.gameScene.BattlePaneState;
import dev.typeracist.typeracist.logic.gameScene.BattlePaneStateManager;

public class MagicWand extends Skill implements SkillOnEnvironment {
    public MagicWand() {
        super("Mystic Paralysis Wand",
                "A magical wand that temporarily immobilizes enemies, rendering them stunned for one turn.",
                SkillActivationOnState.ACTIVATION_BEFORE_DEFENSE,
                3);
    }

    @Override
    public Skill copy() {
        return new MagicWand();
    }

    @Override
    public void useSkill(BattlePaneStateManager manager) {
        System.out.println("activated here");

        manager.getContext().getCurrentTurnContext().addEnemyAttackModifier(-(int) 1e9);
        manager.transitionToState(BattlePaneState.PLAYER_DEFENSE_RESULT);
    }

}
