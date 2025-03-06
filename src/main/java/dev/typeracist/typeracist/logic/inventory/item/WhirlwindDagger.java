package dev.typeracist.typeracist.logic.inventory.item;

import dev.typeracist.typeracist.logic.gameScene.BattlePaneState;
import dev.typeracist.typeracist.logic.gameScene.BattlePaneStateManager;
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
        GameLogic.getInstance().getSceneManager().showBreadcrumb(
                "WhirlwindDagger is activated",
                GameLogic.getInstance().getPlayerName() + " atk got boost by " + ATTACK_BONUS,
                3000
        );
    }

    @Override
    public void activate(BattlePaneStateManager manager) {
        if (firstActivate) {
            int currentTurn = manager.getContext().getCurrentTurn();
            for (int i = currentTurn; i <= currentTurn + 30; i++) {
                if ((i - currentTurn) % STUN_INTERVAL == 0) {
                    manager.getContext().ensureExistsGetTurnContext(i).addItemUsed(this);
                }
            }
        }

        manager.getContext().getCurrentTurnContext().setEnemyAttackModifier((int) -1e9);
        manager.transitionToState(BattlePaneState.PLAYER_DEFENSE_RESULT);
        GameLogic.getInstance().getSceneManager().showBreadcrumb(
                "WhirlwindDagger passive is activated",
                manager.getContext().getEnemy().getName() + " is stunt for 1 turn",
                3000
        );
    }

    @Override
    public ActivateOnTurnState getActivateOnTurnState() {
        return ActivateOnTurnState.BEFORE_DEFENSE;
    }

    @Override
    public Item copy() {
        return new WhirlwindDagger();
    }
}