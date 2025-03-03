package dev.typeracist.typeracist.gui.gameScene.BattlePane.PaneModifier;

import dev.typeracist.typeracist.gui.gameScene.BattlePane.BattlePane;
import dev.typeracist.typeracist.gui.gameScene.BattlePane.InformationPane.InfoPaneModifierType;
import dev.typeracist.typeracist.gui.gameScene.BattlePane.InformationPane.StatsOptionInfoPaneModifier;
import dev.typeracist.typeracist.logic.gameScene.BattlePaneState;
import dev.typeracist.typeracist.logic.gameScene.BattlePaneStateContext;
import dev.typeracist.typeracist.logic.gameScene.BattlePaneStateManager;

public class GameStatsOptionPaneModifier extends BasePaneModifier {
    public GameStatsOptionPaneModifier(BattlePane battlePane, BattlePaneStateContext context) {
        super(battlePane, context);
    }

    @Override
    public void initialize(BattlePaneStateManager manager) {
        battlePane.getStatsButton().setDisable(true);
        StatsOptionInfoPaneModifier statsOptionInfoPaneModifier =
                (StatsOptionInfoPaneModifier) battlePane.getInformationPane().setToPane(InfoPaneModifierType.STATS_OPTION_PANE);

        battlePane.getInformationPane().requestFocus();
        statsOptionInfoPaneModifier.setOnChooseHandler(state -> {
            switch (state) {
                case PLAYER -> {
                    returnControl();
                    manager.transitionToState(BattlePaneState.GAME_PLAYER_STATS_OPTION);
                }
                case ENEMY -> {
                    returnControl();
                    manager.transitionToState(BattlePaneState.GAME_ENEMY_STATS_OPTION);
                }
            }
        });
    }
}
