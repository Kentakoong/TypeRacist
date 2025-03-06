package dev.typeracist.typeracist.logic.inventory;

import dev.typeracist.typeracist.logic.game.battle.BattlePaneStateManager;

public interface ActivateOnTurn {
    void activate(BattlePaneStateManager manager);

    ActivateOnTurnState getActivateOnTurnState();
}
