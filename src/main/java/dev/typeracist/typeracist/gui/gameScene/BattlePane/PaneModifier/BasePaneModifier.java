package dev.typeracist.typeracist.gui.gameScene.BattlePane.PaneModifier;

import dev.typeracist.typeracist.gui.gameScene.BattlePane.BattlePane;
import dev.typeracist.typeracist.logic.gameScene.BattlePaneStateContext;
import dev.typeracist.typeracist.logic.gameScene.BattlePaneStateManager;

abstract public class BasePaneModifier {
    protected BattlePane battlePane;
    protected BattlePaneStateContext context;
    private BattlePaneStateManager manager;

    public BasePaneModifier(BattlePane battlePane, BattlePaneStateContext context) {
        this.battlePane = battlePane;
        this.context = context;
    }

    public void setManager(BattlePaneStateManager manager) {
        this.manager = manager;
    }

    public abstract void initialize(BattlePaneStateManager manager);

    public void returnControl() {
        if (manager != null) {
            manager.notifyModifierComplete(this);
        }
    }
}
