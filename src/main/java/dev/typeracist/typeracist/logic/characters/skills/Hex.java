package dev.typeracist.typeracist.logic.characters.skills;

import dev.typeracist.typeracist.logic.characters.Skill;
import dev.typeracist.typeracist.logic.characters.SkillActivationOnState;
import dev.typeracist.typeracist.logic.characters.SkillOnEnvironment;
import dev.typeracist.typeracist.logic.gameScene.BattlePaneStateManager;

public class Hex extends SkillWithProbability implements SkillOnEnvironment {
    public Hex() {
        super("Hex",
                "50% chance to cast every turn, if inflict player can't use their items",
                SkillActivationOnState.ACTIVATION_BEFORE_ITEM,
                0.50);
    }

    @Override
    public Skill copy() {
        return new Hex();
    }

    @Override
    public void useSkill(BattlePaneStateManager manager) {
        manager.getContext().getCurrentTurnContext().setItemSelectDisable(true);
    }
}