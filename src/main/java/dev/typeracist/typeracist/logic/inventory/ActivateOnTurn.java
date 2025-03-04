package dev.typeracist.typeracist.logic.inventory;

import dev.typeracist.typeracist.gui.gameScene.BattlePane.BattlePane;

public interface ActivateOnTurn {
    void activate(BattlePane battlePane);

    ActivateOnTurnState getActivateOnTurnState();
}
