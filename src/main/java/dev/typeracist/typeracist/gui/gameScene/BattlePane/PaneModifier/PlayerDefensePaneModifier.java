package dev.typeracist.typeracist.gui.gameScene.BattlePane.PaneModifier;

import dev.typeracist.typeracist.gui.gameScene.BattlePane.BattlePane;
import dev.typeracist.typeracist.gui.gameScene.BattlePane.InformationPane.InfoPaneModifierType;
import dev.typeracist.typeracist.gui.gameScene.BattlePane.InformationPane.TypingInfoPaneModifier;
import dev.typeracist.typeracist.logic.gameScene.BattlePaneStateContext;
import dev.typeracist.typeracist.logic.gameScene.BattlePaneStateManager;

public class PlayerDefensePaneModifier extends BasePaneModifier {
    public PlayerDefensePaneModifier(BattlePane battlePane, BattlePaneStateContext context) {
        super(battlePane, context);
    }

    @Override
    public void initialize(BattlePaneStateManager manager) {
        TypingInfoPaneModifier typingInfoPaneModifier =
                (TypingInfoPaneModifier) battlePane.getInformationPane().setToPane(InfoPaneModifierType.TYPING_PANE);

        typingInfoPaneModifier.getTypingPane().setOnStop(event -> {
                    long rawTypingScore = typingInfoPaneModifier.countCompletedWords();

                    battlePane
                            .getStateContext()
                            .getCurrentTurnContext()
                            .setRawDefenseScore((int) rawTypingScore);

                    returnControl();
                }
        );
    }
}
