package dev.typeracist.typeracist.gui.gameScene.BattlePane.PaneModifier;

import dev.typeracist.typeracist.gui.gameScene.BattlePane.BattlePane;
import dev.typeracist.typeracist.logic.gameScene.BattlePaneStateContext;
import dev.typeracist.typeracist.logic.gameScene.BattlePaneStateManager;
import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.utils.ResourceName;
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

public class GameEnemyStatsOptionPaneModifier extends BasePaneModifier {
    public GameEnemyStatsOptionPaneModifier(BattlePane battlePane, BattlePaneStateContext context) {
        super(battlePane, context);
    }

    @Override
    public void initialize(BattlePaneStateManager manager) {
        battlePane.getInformationPane().getChildren().clear();

        Label enemyStats = new Label(context.getEnemy().getName() + "-LEVEL: 0");
        enemyStats.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 18));

        Region spacing = new Region();
        VBox.setVgrow(spacing, Priority.ALWAYS);

        Label pressAnyKeyToContinue = new Label("<< Press any key to continue.. >> ");
        pressAnyKeyToContinue.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 14));
        pressAnyKeyToContinue.setTextFill(Color.DARKGRAY);
        pressAnyKeyToContinue.setAlignment(Pos.CENTER);
        pressAnyKeyToContinue.setMaxWidth(Double.MAX_VALUE);
        pressAnyKeyToContinue.setVisible(false);

        battlePane.getInformationPane().getChildren().addAll(
                enemyStats,
                spacing,
                pressAnyKeyToContinue
        );

        battlePane.requestFocus();

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
                        }
                )
        ).play();
    }
}
