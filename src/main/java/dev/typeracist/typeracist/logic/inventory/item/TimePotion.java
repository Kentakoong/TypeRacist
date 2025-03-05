package dev.typeracist.typeracist.logic.inventory.item;

import dev.typeracist.typeracist.logic.gameScene.BattlePaneStateManager;
import dev.typeracist.typeracist.logic.global.GameLogic;
import dev.typeracist.typeracist.logic.inventory.ActivateOnTurn;
import dev.typeracist.typeracist.logic.inventory.ActivateOnTurnState;
import dev.typeracist.typeracist.logic.inventory.Item;
import dev.typeracist.typeracist.utils.ResourceName;

public class TimePotion extends Item implements ActivateOnTurn {
    private static final long TIME_INCREASE = (long) 5.0 * 1000;
    private static final int DURATION = 1;
    private boolean firstActive = true;

    public TimePotion() {
        super("Time Potion",
                "Increase the timer by 5.0 seconds. 1 turn.",
                25,
                ResourceName.IMAGE_SHOP_TIME_POTION);
    }

    public double getTimeIncrease() {
        return TIME_INCREASE;
    }

    public int getDuration() {
        return DURATION;
    }

    @Override
    public void activate(BattlePaneStateManager manager) {
        if (firstActive) {
            manager.getContext().setTypingMaxTime(manager.getContext().getTypingMaxTime() + TIME_INCREASE);
            manager.getContext().ensureExistsGetTurnContext(manager.getContext().getCurrentTurn() + DURATION).addItemUsed(this);
            GameLogic.getInstance().getSceneManager().showBreadcrumb(
                    "Time Potion is activated",
                    "typing time got extended to " + manager.getContext().getTypingMaxTime() / 1000.0 + " seconds",
                    3000
            );
        } else {
            manager.getContext().setTypingMaxTime(manager.getContext().getTypingMaxTime() - TIME_INCREASE);
        }
    }

    @Override
    public ActivateOnTurnState getActivateOnTurnState() {
        return ActivateOnTurnState.BOTH;
    }

    @Override
    public Item copy() {
        return new TimePotion();
    }
}