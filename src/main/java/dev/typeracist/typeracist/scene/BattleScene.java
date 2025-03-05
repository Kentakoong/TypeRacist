package dev.typeracist.typeracist.scene;

import dev.typeracist.typeracist.gui.gameScene.BattlePane.BattlePane;
import dev.typeracist.typeracist.gui.gameScene.level.*;
import dev.typeracist.typeracist.logic.gameScene.BattlePaneState;
import dev.typeracist.typeracist.logic.gameScene.BattlePaneStateManager;

import java.util.HashMap;
import java.util.Map;

public class BattleScene extends BaseDynamicScene<BattlePane> {
    final private Map<Integer, BattlePaneStateManager> sceneStateManagers;

    public BattleScene(double width, double height) {
        super(new BattlePaneLevel1(), width, height);
        this.sceneStateManagers = new HashMap<>();

        initializeBattlePanes((BattlePane) getRoot());
    }

    private void initializeBattlePanes(BattlePane rootPane) {
        addPane(0, rootPane);
        addPane(1, new BattlePaneLevel2());
        addPane(2, new BattlePaneLevel3());
        addPane(3, new BattlePaneLevel4());
        addPane(4, new BattlePaneLevel5());
        addPane(5, new BattlePaneLevel6());
        addPane(6, new BattlePaneLevel7());
        addPane(7, new BattlePaneLevel8());
        addPane(8, new BattlePaneLevel9());
    }

    @Override
    public void addPane(int id, BattlePane pane) {
        BattlePaneStateManager sceneStateManager = new BattlePaneStateManager(pane, pane.getStateContext());
        sceneStateManagers.put(id, sceneStateManager);
        super.addPane(id, pane);
    }

    @Override
    public void loadPane(int id) {
        super.loadPane(id);
        sceneStateManagers.get(id).transitionToState(BattlePaneState.ENEMY_DESCRIPTION);
    }

    @Override
    public void onSceneEnter() {
        loadPane(8);
    }

    @Override
    public void onSceneLeave() {

    }
}
