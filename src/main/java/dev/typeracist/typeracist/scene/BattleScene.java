package dev.typeracist.typeracist.scene;

import dev.typeracist.typeracist.gui.gameScene.BattlePane.BattlePane;
import dev.typeracist.typeracist.gui.gameScene.level.BattlePaneLevel1;
import dev.typeracist.typeracist.gui.gameScene.level.BattlePaneLevel2;
import dev.typeracist.typeracist.logic.gameScene.BattlePaneState;
import dev.typeracist.typeracist.logic.gameScene.BattlePaneStateManager;

public class BattleScene extends BaseDynamicScene<BattlePane> {
    public BattleScene(double width, double height) {
        super(new BattlePaneLevel1(), width, height);
        initializeBattlePanes((BattlePane) getRoot());
    }

    private void initializeBattlePanes(BattlePane rootPane) {
        addPane(0, rootPane);
        addPane(1, new BattlePaneLevel2());
    }

    @Override
    public void onSceneEnter() {
        BattlePane battlePane = (BattlePane) getRoot();
        BattlePaneStateManager sceneStateManager = new BattlePaneStateManager(battlePane, battlePane.getStateContext());
        sceneStateManager.transitionToState(BattlePaneState.ENEMY_DESCRIPTION);
    }

    @Override
    public void onSceneLeave() {

    }
}
