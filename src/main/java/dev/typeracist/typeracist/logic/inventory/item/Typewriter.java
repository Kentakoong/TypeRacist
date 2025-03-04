package dev.typeracist.typeracist.logic.inventory.item;

import dev.typeracist.typeracist.gui.gameScene.BattlePane.BattlePane;
import dev.typeracist.typeracist.logic.inventory.ActivateOnTurn;
import dev.typeracist.typeracist.logic.inventory.ActivateOnTurnState;
import dev.typeracist.typeracist.logic.inventory.Item;
import dev.typeracist.typeracist.utils.ResourceName;

public class Typewriter extends Item implements ActivateOnTurn {
    public Typewriter() {
        super("Typewriter",
                "Nothing, just a typewriter.",
                696969,
                ResourceName.IMAGE_SHOP_TYPEWRITER);
    }

    @Override
    public void activate(BattlePane battlePane) {

    }

    @Override
    public ActivateOnTurnState getActivateOnTurnState() {
        return ActivateOnTurnState.BOTH;
    }
}