package dev.typeracist.typeracist.logic.inventory.item;

import dev.typeracist.typeracist.logic.game.battle.BattlePaneStateManager;
import dev.typeracist.typeracist.logic.global.GameLogic;
import dev.typeracist.typeracist.logic.inventory.ActivateOnTurn;
import dev.typeracist.typeracist.logic.inventory.ActivateOnTurnState;
import dev.typeracist.typeracist.logic.inventory.Item;

public class FireDragonCurse extends Item implements ActivateOnTurn {
    private static final int BURN_DAMAGE = 10;

    public FireDragonCurse() {
        super("Fire Dragon Curse",
                "Blazing fragments left behind by a ferocious Fire Dragon.",
                0,
                null);
    }

    @Override
    public void activate(BattlePaneStateManager manager) {
        GameLogic.getInstance().getSelectedCharacter().getHp().damage(BURN_DAMAGE);
        GameLogic.getInstance().getSceneManager().showBreadcrumb(
                "The debris crackle as fiery embers envelop the air.",
                "You feel scorching pain as the fire dragon's curse takes hold.",
                3000
        );
    }

    @Override
    public ActivateOnTurnState getActivateOnTurnState() {
        return ActivateOnTurnState.BOTH;
    }

    @Override
    public Item copy() {
        return new FireDragonCurse();
    }
}