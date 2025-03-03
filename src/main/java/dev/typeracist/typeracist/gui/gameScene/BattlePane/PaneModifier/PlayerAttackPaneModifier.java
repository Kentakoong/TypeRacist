package dev.typeracist.typeracist.gui.gameScene.BattlePane.PaneModifier;

import dev.typeracist.typeracist.gui.gameScene.BattlePane.BattlePane;
import dev.typeracist.typeracist.gui.gameScene.BattlePane.InformationPane.InfoPaneModifierType;
import dev.typeracist.typeracist.gui.gameScene.BattlePane.InformationPane.TypingInfoPaneModifier;
import dev.typeracist.typeracist.logic.gameScene.BattlePaneStateContext;
import dev.typeracist.typeracist.logic.gameScene.BattlePaneStateManager;
import dev.typeracist.typeracist.logic.gameScene.TypedWordStatus;

public class PlayerAttackPaneModifier extends BasePaneModifier {
    public PlayerAttackPaneModifier(BattlePane battlePane, BattlePaneStateContext context) {
        super(battlePane, context);
    }

    @Override
    public void initialize(BattlePaneStateManager manager) {
        battlePane.getStatsButton().setDisable(false);
        battlePane.getAttackButton().setDisable(true);

        TypingInfoPaneModifier typingInfoPaneModifier =
                (TypingInfoPaneModifier) battlePane.getInformationPane().setToPane(InfoPaneModifierType.TYPING_PANE);

        typingInfoPaneModifier.getTypingPane().setOnStop(event -> {
                    long rawTypingScore = typingInfoPaneModifier
                            .getTypingPane()
                            .getTypingTracker()
                            .getTypedWordStatuses()
                            .entrySet()
                            .stream()
                            .filter(record -> record.getValue() == TypedWordStatus.CORRECTED)
                            .count();

                    battlePane
                            .getStateContext()
                            .getCurrentTurnContext()
                            .setRawAttackScore((int) rawTypingScore);

                    returnControl();
                }
        );
    }
}
