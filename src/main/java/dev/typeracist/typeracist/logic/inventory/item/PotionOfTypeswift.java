package dev.typeracist.typeracist.logic.inventory.item;

import dev.typeracist.typeracist.gui.gameScene.BattlePane.BattlePane;
import dev.typeracist.typeracist.logic.inventory.ActivateOnTurn;
import dev.typeracist.typeracist.logic.inventory.ActivateOnTurnState;
import dev.typeracist.typeracist.logic.inventory.Item;
import dev.typeracist.typeracist.utils.ResourceName;

public class PotionOfTypeswift extends Item implements ActivateOnTurn {
    private static final double CPS_MULTIPLIER = 1.25;
    private static final int DURATION = 1;

    public PotionOfTypeswift() {
        super("Potion of Typeswift",
                "Increase your CPS by x1.25. 1 turn.",
                30,
                ResourceName.IMAGE_SHOP_POTION_OF_TYPESWIFT);
    }

    public double getCpsMultiplier() {
        return CPS_MULTIPLIER;
    }

    public int getDuration() {
        return DURATION;
    }

    @Override
    public void activate(BattlePane battlePane) {
        battlePane.getStateContext().getCurrentTurnContext().setWordMultiplier(CPS_MULTIPLIER);
        System.out.println(battlePane.getStateContext().getCurrentTurnContext().getWordMultiplier());
    }

    @Override
    public ActivateOnTurnState getActivateOnTurnState() {
        return ActivateOnTurnState.BOTH;
    }
}