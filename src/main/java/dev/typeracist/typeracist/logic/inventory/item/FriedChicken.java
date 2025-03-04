package dev.typeracist.typeracist.logic.inventory.item;

import dev.typeracist.typeracist.gui.gameScene.BattlePane.BattlePane;
import dev.typeracist.typeracist.logic.global.GameLogic;
import dev.typeracist.typeracist.logic.inventory.ActivateNow;
import dev.typeracist.typeracist.logic.inventory.ActivateOnTurn;
import dev.typeracist.typeracist.logic.inventory.ActivateOnTurnState;
import dev.typeracist.typeracist.logic.inventory.Item;
import dev.typeracist.typeracist.utils.ResourceName;

public class FriedChicken extends Item implements ActivateNow, ActivateOnTurn {
    private static final int HEAL_AMOUNT = 10;
    private static final int DURATION = 2;
    private boolean firstActive = true;

    public FriedChicken() {
        super("Fried Chicken",
                "Heal your character by 10 HP, lasts for 2 turns.",
                20,
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
    }

    @Override
    public void activate(BattlePane battlePane) {
        if (firstActive) {
            battlePane.getStateContext().ensureExistsGetTurnContext(battlePane.getStateContext().getCurrentTurn() + 1).addItemUsed(this);
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

}