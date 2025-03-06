package dev.typeracist.typeracist.logic.inventory.item;

import dev.typeracist.typeracist.logic.gameScene.BattlePaneStateManager;
import dev.typeracist.typeracist.logic.global.GameLogic;
import dev.typeracist.typeracist.logic.inventory.ActivateOnTurn;
import dev.typeracist.typeracist.logic.inventory.ActivateOnTurnState;
import dev.typeracist.typeracist.logic.inventory.Item;
import dev.typeracist.typeracist.utils.ResourceName;

public class PotionOfTypeswift extends Item implements ActivateOnTurn {
    private static final double CPS_MULTIPLIER = 2;
    private static final int DURATION = 1;

    public PotionOfTypeswift() {
        super("Potion of Typeswift",
                "Increase your CPS by x2. 1 turn.",
                75,
                ResourceName.IMAGE_SHOP_POTION_OF_TYPESWIFT);
    }

    public double getCpsMultiplier() {
        return CPS_MULTIPLIER;
    }

    public int getDuration() {
        return DURATION;
    }

    @Override
    public void activate(BattlePaneStateManager manager) {
        manager.getContext().getCurrentTurnContext().setWordMultiplier(CPS_MULTIPLIER);
        GameLogic.getInstance().getSceneManager().showBreadcrumb(
                "Potion of type swift is activated",
                GameLogic.getInstance().getPlayerName() + " got boost by x" + CPS_MULTIPLIER,
                3000
        );
    }

    @Override
    public ActivateOnTurnState getActivateOnTurnState() {
        return ActivateOnTurnState.BOTH;
    }

    @Override
    public Item copy() {
        return new PotionOfTypeswift();
    }
}