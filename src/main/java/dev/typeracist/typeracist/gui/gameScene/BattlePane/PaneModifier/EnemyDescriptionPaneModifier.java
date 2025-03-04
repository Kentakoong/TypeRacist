package dev.typeracist.typeracist.gui.gameScene.BattlePane.PaneModifier;

import dev.typeracist.typeracist.gui.gameScene.BattlePane.BattlePane;
import dev.typeracist.typeracist.logic.gameScene.BattlePaneState;
import dev.typeracist.typeracist.logic.gameScene.BattlePaneStateContext;
import dev.typeracist.typeracist.logic.gameScene.BattlePaneStateManager;
import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.utils.ResourceName;
import javafx.scene.control.Label;

public class EnemyDescriptionPaneModifier extends BasePaneModifier {
    public EnemyDescriptionPaneModifier(BattlePane battlePane, BattlePaneStateContext context) {
        super(battlePane, context);
    }

    @Override
    public void initialize(BattlePaneStateManager manager) {
        battlePane.getInformationPane().getChildren().clear();

        battlePane.getAttackButton().setDisable(false);
        battlePane.getStatsButton().setDisable(false);

        Label enemyDescriptionLabel = new Label("* " + context.getEnemy().getDescription());
        enemyDescriptionLabel.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 18));

        battlePane.getInformationPane().getChildren().addAll(
                enemyDescriptionLabel);

        manager.setReversibleState(BattlePaneState.ENEMY_DESCRIPTION);
        returnControl();
    }
}
