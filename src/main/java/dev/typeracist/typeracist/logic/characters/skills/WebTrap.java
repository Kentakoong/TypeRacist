package dev.typeracist.typeracist.logic.characters.skills;

import dev.typeracist.typeracist.logic.characters.Skill;
import dev.typeracist.typeracist.logic.characters.SkillActivationOnState;
import dev.typeracist.typeracist.logic.characters.SkillOnEnvironment;
import dev.typeracist.typeracist.logic.gameScene.BattlePaneStateManager;

public class WebTrap extends Skill implements SkillOnEnvironment {
    public WebTrap() {
        super("Web Trap",
                "Player cannot use items for one turn.",
                SkillActivationOnState.ACTIVATION_BEFORE_ITEM,
                4 // because of this skill is call 2 time for 1 turn
        );
    }

    @Override
    public Skill copy() {
        return new WebTrap();
    }

    @Override
    public void useSkill(BattlePaneStateManager manager) {
        manager.getContext().getCurrentTurnContext().setItemSelectDisable(true);
    }
}