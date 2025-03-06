package dev.typeracist.typeracist.gui.game.battle.modifiers;

import dev.typeracist.typeracist.gui.game.battle.BattlePane;
import dev.typeracist.typeracist.logic.game.battle.BattlePaneStateContext;
import dev.typeracist.typeracist.logic.game.battle.BattlePaneStateManager;

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
