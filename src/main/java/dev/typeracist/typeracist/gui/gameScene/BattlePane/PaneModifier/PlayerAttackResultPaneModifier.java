package dev.typeracist.typeracist.gui.gameScene.BattlePane.PaneModifier;

import dev.typeracist.typeracist.gui.gameScene.BattlePane.BattlePane;
import dev.typeracist.typeracist.gui.gameScene.BattlePane.InformationPane.InfoPaneModifierType;
import dev.typeracist.typeracist.logic.characters.SkillActivationOnState;
import dev.typeracist.typeracist.logic.gameScene.BattlePaneStateContext;
import dev.typeracist.typeracist.logic.gameScene.BattlePaneStateManager;
import dev.typeracist.typeracist.logic.global.GameLogic;
import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.utils.ResourceName;
import dev.typeracist.typeracist.utils.ShakeTransition;
import dev.typeracist.typeracist.utils.TurnOwnership;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class PlayerAttackResultPaneModifier extends BasePaneModifier {
    public PlayerAttackResultPaneModifier(BattlePane battlePane, BattlePaneStateContext context) {
        super(battlePane, context);
    }

    @Override
    public void initialize(BattlePaneStateManager manager) {
        battlePane.getInformationPane().getChildren().clear();
        battlePane.getInformationPane().setToPane(InfoPaneModifierType.TEXT);

        int rawAttackScore = battlePane.getStateContext().getCurrentTurnContext().getRawAttackScore();
        assert rawAttackScore != -1;

        manager.activateSkill(SkillActivationOnState.ACTIVATION_AFTER_ATTACK, TurnOwnership.PLAYER);
        manager.activateSkill(SkillActivationOnState.ACTIVATION_AFTER_DEFENSE, TurnOwnership.PLAYER);

        int attackScore = 0;
        if (context.getCurrentTurnContext().isHadAttack()) {
            attackScore = context.getCurrentTurnContext().getAttackDamage();
        } else {
            attackScore = GameLogic.getInstance().getSelectedCharacter().getTotalAtk() * rawAttackScore;
            attackScore += context.getCurrentTurnContext().getPlayerAttackModifier();
            attackScore = context.getEnemy().damage(attackScore);
            context.getCurrentTurnContext().setAttackDamage(attackScore);
        }

        Label rawAttackScoreLabel = new Label("Word typed: " + rawAttackScore);
        rawAttackScoreLabel.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 24));

        Label attackScoreLabel = new Label(
                "You attack " + context.getEnemy().getName() + " for " + attackScore + " damages");
        attackScoreLabel.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 18));

        Region spacing = new Region();
        VBox.setVgrow(spacing, Priority.ALWAYS);

        Label pressAnyKeyToContinue = new Label("<< Press any key to continue.. >> ");
        pressAnyKeyToContinue.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 14));
        pressAnyKeyToContinue.setTextFill(Color.DARKGRAY);
        pressAnyKeyToContinue.setAlignment(Pos.CENTER);
        pressAnyKeyToContinue.setMaxWidth(Double.MAX_VALUE);
        pressAnyKeyToContinue.setVisible(false);

        battlePane.getInformationPane().getChildren().addAll(
                rawAttackScoreLabel,
                attackScoreLabel,
                pressAnyKeyToContinue);

        battlePane.updateHealthBars();
        battlePane.requestFocus();

        new ShakeTransition(battlePane.getCharacterImage());

        // 0.5 seconds delay before
        new Timeline(
                new KeyFrame(
                        Duration.millis(500),
                        ae -> {
                            FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.2), pressAnyKeyToContinue);
                            fadeIn.setFromValue(0);
                            fadeIn.setToValue(1);
                            fadeIn.setOnFinished(event -> {
                                EventHandler<? super KeyEvent> keyPressEvent = battlePane.getOnKeyPressed();

                                battlePane.setOnKeyPressed(keyEvent -> {
                                    battlePane.setOnKeyPressed(keyPressEvent); // Restore previous state
                                    returnControl();
                                });
                            });
                            pressAnyKeyToContinue.setVisible(true); // Ensure the node is visible
                            fadeIn.play();
                        }))
                .play();
    }
}
