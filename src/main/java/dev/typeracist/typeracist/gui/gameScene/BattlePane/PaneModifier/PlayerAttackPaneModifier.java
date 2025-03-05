package dev.typeracist.typeracist.gui.gameScene.BattlePane.PaneModifier;

import dev.typeracist.typeracist.gui.gameScene.BattlePane.BattlePane;
import dev.typeracist.typeracist.gui.gameScene.BattlePane.InformationPane.InfoPaneModifierType;
import dev.typeracist.typeracist.gui.gameScene.BattlePane.InformationPane.TypingInfoPaneModifier;
import dev.typeracist.typeracist.logic.characters.SkillActivationOnState;
import dev.typeracist.typeracist.logic.gameScene.BattlePaneStateContext;
import dev.typeracist.typeracist.logic.gameScene.BattlePaneStateManager;
import dev.typeracist.typeracist.utils.TurnOwnership;

public class PlayerAttackPaneModifier extends BasePaneModifier {
    public PlayerAttackPaneModifier(BattlePane battlePane, BattlePaneStateContext context) {
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
                            .setRawAttackScore((int) rawTypingScore);
                    returnControl();

                    manager.activateSkill(SkillActivationOnState.ACTIVATION_ON_ATTACK, TurnOwnership.PLAYER);
                    manager.activateSkill(SkillActivationOnState.ACTIVATION_ON_DEFENSE, TurnOwnership.ENEMY);
                }
        );
    }
}
