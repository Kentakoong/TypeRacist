package dev.typeracist.typeracist.logic.characters.skills;

import dev.typeracist.typeracist.logic.characters.Skill;
import dev.typeracist.typeracist.logic.characters.SkillActivationOnState;
import dev.typeracist.typeracist.logic.characters.SkillOnEnvironment;
import dev.typeracist.typeracist.logic.game.battle.BattlePaneStateManager;
import dev.typeracist.typeracist.logic.inventory.item.FireGolemDebris;

import java.util.Random;

public class FlamePunch extends Skill implements SkillOnEnvironment {
    private static final int MIN_BURN_DAMAGE = 2;
    private static final int MAX_BURN_DAMAGE = 3;
    private final Random random;

    public FlamePunch() {
        super("Flame Punch",
                "Inflict player with fire effect, deal 2-3 damages to player for 500 turns.",
                SkillActivationOnState.ACTIVATION_ON_ATTACK
        );
        this.random = new Random();
    }

    @Override
    public Skill copy() {
        return new FlamePunch();
    }

    @Override
    public void useSkill(BattlePaneStateManager manager) {
        int currentTurn = manager.getContext().getCurrentTurn();
        for (int i = currentTurn; i <= currentTurn + 500; i++) {
            manager.getContext().ensureExistsGetTurnContext(i).addItemUsed(new FireGolemDebris());
        }
    }

    public int getBurnDamage() {
        return random.nextInt(MAX_BURN_DAMAGE - MIN_BURN_DAMAGE + 1) + MIN_BURN_DAMAGE;
    }
}