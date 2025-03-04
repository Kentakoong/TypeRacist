package dev.typeracist.typeracist.scene;

import dev.typeracist.typeracist.gui.gameScene.BattlePane.BattlePane;
import dev.typeracist.typeracist.logic.characters.Enemy;
import dev.typeracist.typeracist.logic.characters.HP;
import dev.typeracist.typeracist.logic.characters.skills.Evasion;
import dev.typeracist.typeracist.logic.gameScene.BattlePaneState;
import dev.typeracist.typeracist.logic.gameScene.BattlePaneStateContext;
import dev.typeracist.typeracist.logic.gameScene.BattlePaneStateManager;
import dev.typeracist.typeracist.logic.global.GameLogic;
import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.utils.DatasetName;
import dev.typeracist.typeracist.utils.RandomRange;
import dev.typeracist.typeracist.utils.ResourceName;

public class BattleScene extends BaseDynamicScene<BattlePane> {
    public BattleScene(double width, double height) {
        super(
                new BattlePane(
                        new BattlePaneStateContext(
                                new Enemy(
                                        new HP(60),
                                        30,
                                        15,
                                        ResourceManager.getImage(ResourceName.IMAGE_CHARACTER_ASSASSIN),
                                        new String[]{"Beware, An evil assassin have appear!."},
                                        new Evasion(),
                                        new RandomRange(10, 20),
                                        new RandomRange(10, 20)),
                                10 * 1000,
                                GameLogic.getInstance().getDatasetManager().getDataSet(DatasetName.COMMON_WORDS_1K),
                                dataset -> dataset.getRandomWordsByScoreRange(3, 7, 50)
                        )
                ),
                width,
                height);

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
