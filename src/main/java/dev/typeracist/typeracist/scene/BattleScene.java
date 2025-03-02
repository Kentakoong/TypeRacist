package dev.typeracist.typeracist.scene;

import dev.typeracist.typeracist.gui.gameScene.BattlePane;

public class BattleScene extends BaseDynamicScene<BattlePane> {
    public BattleScene(double width, double height) {
        super(new BattlePane(), width, height);

        addPane(0, new BattlePane());
        loadPane(0);
    }

    @Override
    public void onSceneEnter() {

    }

    @Override
    public void onSceneLeave() {

    }
}
