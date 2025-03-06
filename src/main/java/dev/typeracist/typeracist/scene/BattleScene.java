package dev.typeracist.typeracist.scene;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import dev.typeracist.typeracist.gui.gameScene.BattlePane.BattlePane;
import dev.typeracist.typeracist.gui.gameScene.level.BattlePaneLevel1;
import dev.typeracist.typeracist.gui.gameScene.level.BattlePaneLevel2;
import dev.typeracist.typeracist.gui.gameScene.level.BattlePaneLevel3;
import dev.typeracist.typeracist.gui.gameScene.level.BattlePaneLevel4;
import dev.typeracist.typeracist.gui.gameScene.level.BattlePaneLevel5;
import dev.typeracist.typeracist.gui.gameScene.level.BattlePaneLevel6;
import dev.typeracist.typeracist.gui.gameScene.level.BattlePaneLevel7;
import dev.typeracist.typeracist.gui.gameScene.level.BattlePaneLevel8;
import dev.typeracist.typeracist.gui.gameScene.level.BattlePaneLevel9;
import dev.typeracist.typeracist.gui.gameScene.level.BattlePaneLevelBoss;
import dev.typeracist.typeracist.logic.gameScene.BattlePaneState;
import dev.typeracist.typeracist.logic.gameScene.BattlePaneStateContext;
import dev.typeracist.typeracist.logic.gameScene.BattlePaneStateManager;
import dev.typeracist.typeracist.logic.global.GameLogic;

public class BattleScene extends BaseDynamicScene<BattlePane> {
    private final Map<Integer, BattlePaneStateManager> sceneStateManagers;
    private final Map<Integer, Supplier<BattlePaneStateContext>> paneStateContexts;
    private int currentPaneId;

    public BattleScene(double width, double height) {
        super(new BattlePaneLevel1(), width, height);
        this.paneStateContexts = new HashMap<>();
        this.sceneStateManagers = new HashMap<>();
        this.currentPaneId = 0;
        initializeBattlePanes();
    }

    private void initializeBattlePanes() {
        addPane(0, BattlePaneLevel1::initializeContext);
        addPane(1, BattlePaneLevel2::initializeContext);
        addPane(2, BattlePaneLevel3::initializeContext);
        addPane(3, BattlePaneLevel4::initializeContext);
        addPane(4, BattlePaneLevel5::initializeContext);
        addPane(5, BattlePaneLevel6::initializeContext);
        addPane(6, BattlePaneLevel7::initializeContext);
        addPane(7, BattlePaneLevel8::initializeContext);
        addPane(8, BattlePaneLevel9::initializeContext);
        addPane(9, BattlePaneLevelBoss::initializeContext);
    }

    public void addPane(int id, Supplier<BattlePaneStateContext> paneState) {
        paneStateContexts.put(id, paneState);
    }

    public void loadPane(int id) {
        BattlePaneStateContext battlePaneStateContext = paneStateContexts.get(id).get();

        BattlePane newPane = new BattlePane(battlePaneStateContext);
        BattlePaneStateManager manager = new BattlePaneStateManager(
                newPane,
                battlePaneStateContext,
                id);

        manager.transitionToState(BattlePaneState.ENEMY_DESCRIPTION);
        sceneStateManagers.put(id, manager);
        currentPaneId = id;
        setRoot(newPane);
    }

    @Override
    public void onSceneEnter() {
        loadPane(currentPaneId);
        if (sceneStateManagers.containsKey(currentPaneId)) {
            sceneStateManagers.get(currentPaneId).transitionToState(BattlePaneState.ENEMY_DESCRIPTION);
        }
    }

    @Override
    public void onSceneLeave() {
        // Handle scene leave logic if necessary
    }
}
