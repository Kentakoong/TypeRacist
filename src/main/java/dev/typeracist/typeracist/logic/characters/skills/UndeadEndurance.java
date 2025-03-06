package dev.typeracist.typeracist.logic.characters.skills;

import dev.typeracist.typeracist.logic.characters.Skill;
import dev.typeracist.typeracist.logic.characters.SkillActivationOnState;
import dev.typeracist.typeracist.logic.characters.SkillOnEnvironment;
import dev.typeracist.typeracist.logic.gameScene.BattlePaneStateManager;
import dev.typeracist.typeracist.logic.global.GameLogic;

public class UndeadEndurance extends Skill implements SkillOnEnvironment {
    private static final double DAMAGE_REDUCTION = 0.2; // 20% damage reduction

    public UndeadEndurance() {
        super("Undead Endurance",
                "Takes 20% less damage from normal attacks.",
                SkillActivationOnState.ACTIVATION_ON_DEFENSE
        );
    }

    public Skill copy() {
        return new UndeadEndurance();
    }

    public double getDamageReduction() {
        return DAMAGE_REDUCTION;
    }

    @Override
    public void useSkill(BattlePaneStateManager manager) {
        int playerAttack = manager.getContext().getCurrentTurnContext().getRawAttackScore()
                * GameLogic.getInstance().getSelectedCharacter().getTotalAtk();

        manager.getContext().getCurrentTurnContext().addPlayerAttackModifier(
                (int) Math.floor(-(playerAttack * DAMAGE_REDUCTION))
        );
    }
}