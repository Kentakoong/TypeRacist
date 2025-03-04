package dev.typeracist.typeracist.logic.inventory.item;

import dev.typeracist.typeracist.gui.gameScene.BattlePane.BattlePane;
import dev.typeracist.typeracist.logic.global.GameLogic;
import dev.typeracist.typeracist.logic.inventory.ActivateNow;
import dev.typeracist.typeracist.logic.inventory.ActivateOnTurn;
import dev.typeracist.typeracist.logic.inventory.ActivateOnTurnState;
import dev.typeracist.typeracist.logic.inventory.Item;
import dev.typeracist.typeracist.utils.ResourceName;

public class WhirlwindDagger extends Item implements ActivateNow, ActivateOnTurn {
    private static final int ATTACK_BONUS = 6;
    private static final int STUN_INTERVAL = 3;
    private boolean firstActivate = true;

    public WhirlwindDagger() {
        super("Whirlwind Dagger",
                "ATK +6. Stun enemy every 3 turns, for 30 turns.",
                34,
                ResourceName.IMAGE_SHOP_WHIRLWIND_DAGGER);
    }

    public int getAttackBonus() {
        return ATTACK_BONUS;
    }

    public int getStunInterval() {
        return STUN_INTERVAL;
    }

    @Override
    public void activate() {
        GameLogic.getInstance().getSelectedCharacter().addExtraAtk(6);
    }

    @Override
    public void activate(BattlePane battlePane) {
        if (firstActivate) {
            int currentTurn = battlePane.getStateContext().getCurrentTurn();
            for (int i = currentTurn; i <= currentTurn + 30; i++) {
                if ((i - currentTurn) % 3 == 0) {
                    battlePane.getStateContext().ensureExistsGetTurnContext(i).addItemUsed(this);
                }
            }
        }
    }

    @Override
    public ActivateOnTurnState getActivateOnTurnState() {
        return ActivateOnTurnState.BOTH;
    }
}