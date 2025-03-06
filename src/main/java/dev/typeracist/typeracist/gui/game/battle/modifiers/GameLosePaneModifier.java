package dev.typeracist.typeracist.gui.game.battle.modifiers;

import dev.typeracist.typeracist.gui.game.battle.BattlePane;
import dev.typeracist.typeracist.gui.game.information.InfoPaneModifierType;
import dev.typeracist.typeracist.logic.game.battle.BattlePaneStateContext;
import dev.typeracist.typeracist.logic.game.battle.BattlePaneStateManager;
import dev.typeracist.typeracist.logic.global.GameLogic;
import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.utils.ResourceName;
import dev.typeracist.typeracist.utils.SceneName;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class GameLosePaneModifier extends BasePaneModifier {
    public GameLosePaneModifier(BattlePane battlePane, BattlePaneStateContext context) {
        super(battlePane, context);
    }

    @Override
    public void initialize(BattlePaneStateManager manager) {
        battlePane.getInformationPane().setToPane(InfoPaneModifierType.TEXT);
        battlePane.getInformationPane().getChildren().clear();

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);

        Label GameOverLabel = new Label("You Lose!");
        GameOverLabel.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 24));

        Label GameOverDescriptionLabel = new Label("Try again next time!");
        GameOverDescriptionLabel.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 18));

        vBox.getChildren().addAll(GameOverLabel, GameOverDescriptionLabel);

        Label pressAnyKeyToContinue = new Label("<< Press any key to continue.. >> ");
        pressAnyKeyToContinue.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 14));
        pressAnyKeyToContinue.setTextFill(Color.DARKGRAY);
        pressAnyKeyToContinue.setAlignment(Pos.CENTER);
        pressAnyKeyToContinue.setMaxWidth(Double.MAX_VALUE);
        pressAnyKeyToContinue.setVisible(false);

        battlePane.getInformationPane().setSpacing(20);
        battlePane.getInformationPane().getChildren().addAll(vBox, pressAnyKeyToContinue);

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
                                    GameLogic.getInstance().getSceneManager().setScene(SceneName.MAP);
                                });

                            });

                            pressAnyKeyToContinue.setVisible(true);
                            fadeIn.play();
                        }))
                .play();
    }
}
