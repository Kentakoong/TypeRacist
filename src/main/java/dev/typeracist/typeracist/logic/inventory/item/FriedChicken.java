package dev.typeracist.typeracist.logic.inventory.item;

import dev.typeracist.typeracist.logic.gameScene.BattlePaneStateManager;
import dev.typeracist.typeracist.logic.global.GameLogic;
import dev.typeracist.typeracist.logic.inventory.ActivateNow;
import dev.typeracist.typeracist.logic.inventory.ActivateOnTurn;
import dev.typeracist.typeracist.logic.inventory.ActivateOnTurnState;
import dev.typeracist.typeracist.logic.inventory.Item;
import dev.typeracist.typeracist.utils.ResourceName;

public class FriedChicken extends Item implements ActivateNow, ActivateOnTurn {
    private static final int HEAL_AMOUNT = 15;
    private static final int DURATION = 3;
    private boolean firstActive = true;

    public FriedChicken() {
        super("Fried Chicken",
                "Heal your character by 15 HP, lasts for 3 turns.",
                40,
                ResourceName.IMAGE_SHOP_FRIED_CHICKEN);
    }

    public int getHealAmount() {
        return HEAL_AMOUNT;
    }

    public int getDuration() {
        return DURATION;
    }

    @Override
    public void activate() {
        GameLogic.getInstance().getSelectedCharacter().getHp().heal(HEAL_AMOUNT);
        GameLogic.getInstance().getSceneManager().showBreadcrumb(
                "FriedChicken is activated",
                GameLogic.getInstance().getPlayerName() + " is healed by " + HEAL_AMOUNT,
                3000
        );
    }

    @Override
    public void activate(BattlePaneStateManager manager) {
        if (firstActive) {
            manager.getContext().ensureExistsGetTurnContext(manager.getContext().getCurrentTurn() + 1).addItemUsed(this);
            firstActive = false;
        } else {
            GameLogic.getInstance().getSelectedCharacter().getHp().heal(HEAL_AMOUNT);
            GameLogic.getInstance().getSceneManager().showBreadcrumb(
                    "FriedChicken passive is activated",
                    GameLogic.getInstance().getPlayerName() + " is healed by " + HEAL_AMOUNT,
                    3000
            );
        }
    }

    @Override
    public ActivateOnTurnState getActivateOnTurnState() {
        return ActivateOnTurnState.BOTH;
    }

    @Override
    public Item copy() {
        return new FriedChicken();
    }
}