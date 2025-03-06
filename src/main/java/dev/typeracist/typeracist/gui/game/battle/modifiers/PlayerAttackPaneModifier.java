package dev.typeracist.typeracist.gui.game.battle.modifiers;

import dev.typeracist.typeracist.gui.game.battle.BattlePane;
import dev.typeracist.typeracist.gui.game.information.InfoPaneModifierType;
import dev.typeracist.typeracist.gui.game.information.modifiers.TypingInfoPaneModifier;
import dev.typeracist.typeracist.logic.characters.SkillActivationOnState;
import dev.typeracist.typeracist.logic.game.battle.BattlePaneStateContext;
import dev.typeracist.typeracist.logic.game.battle.BattlePaneStateManager;
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
