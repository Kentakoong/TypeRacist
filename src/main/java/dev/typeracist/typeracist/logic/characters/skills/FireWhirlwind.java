package dev.typeracist.typeracist.logic.characters.skills;

import dev.typeracist.typeracist.logic.characters.Skill;
import dev.typeracist.typeracist.logic.characters.SkillActivationOnState;
import dev.typeracist.typeracist.logic.characters.SkillOnEnvironment;
import dev.typeracist.typeracist.logic.gameScene.BattlePaneStateManager;
import dev.typeracist.typeracist.logic.inventory.item.FireDragonCurse;

public class FireWhirlwind extends SkillWithProbability implements SkillOnEnvironment {
    private static final int DURATION = 2;

    public FireWhirlwind() {
        super("Fire Whirlwind",
                "Every turn have a 25% chance to Deals massive 10 fire damage for 2 turns",
                SkillActivationOnState.ACTIVATION_ON_ATTACK,
                0.25);
    }

    @Override
    public Skill copy() {
        return new FireWhirlwind();
    }

    @Override
    public void useSkill(BattlePaneStateManager manager) {
        int currentTurn = manager.getContext().getCurrentTurn();

        for (int i = currentTurn; i <= currentTurn + DURATION; i++) {
            manager.getContext().ensureExistsGetTurnContext(i).addItemUsed(new FireDragonCurse());
        }
    }

    public int getDuration() {
        return DURATION;
    }
}