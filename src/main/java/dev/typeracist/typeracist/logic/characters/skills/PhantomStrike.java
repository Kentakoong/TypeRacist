package dev.typeracist.typeracist.logic.characters.skills;

import dev.typeracist.typeracist.logic.characters.Skill;
import dev.typeracist.typeracist.logic.characters.SkillActivationOnState;
import dev.typeracist.typeracist.logic.characters.SkillOnEnvironment;
import dev.typeracist.typeracist.logic.gameScene.BattlePaneStateManager;
import dev.typeracist.typeracist.logic.global.GameLogic;

public class PhantomStrike extends SkillWithProbability implements SkillOnEnvironment {
    public PhantomStrike(double probability) {
        super("Phantom Strike",
                "A deadly assassin technique that allows for a 50% chance to unleash a devastating double-damage attack, striking with supernatural precision.",
                SkillActivationOnState.ACTIVATION_ON_ATTACK,
                probability);
    }

    @Override
    public Skill copy() {
        return new MagicWand();
    }

    @Override
    public void useSkill(BattlePaneStateManager manager) {
        int playerAttack = manager.getContext().getCurrentTurnContext().getRawAttackScore()
                * GameLogic.getInstance().getSelectedCharacter().getTotalAtk();

        manager.getContext().getCurrentTurnContext().addPlayerAttackModifier(
                playerAttack
        );
    }

}
