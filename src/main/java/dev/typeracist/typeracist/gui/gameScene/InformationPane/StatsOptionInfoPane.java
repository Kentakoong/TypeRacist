package dev.typeracist.typeracist.gui.gameScene.InformationPane;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

enum StatsOptionState {
    PLAYER,
    ENEMY
}

public class StatsOptionInfoPane extends BaseInfoPane {
    final private Label playerOption;
    final private Label enemyOption;
    final private InformationPane subPaneNavigator;

    private StatsOptionState statsOptionState;

    public StatsOptionInfoPane(InformationPane subPaneNavigator) {
        super(subPaneNavigator);

        this.subPaneNavigator = subPaneNavigator;
        this.playerOption = new Label("* Player");
        this.playerOption.setFont(Font.font(baseFont.getName(), 18));
        this.enemyOption = new Label("* Enemy");
        this.enemyOption.setFont(Font.font(baseFont.getName(), 18));
        this.statsOptionState = StatsOptionState.PLAYER;

        setFocusTraversable(true);
        initialize();
        Platform.runLater(this::requestFocus);
    }

    @Override
    protected void initializeContent() {
        HBox hBox = new HBox();

        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(40);
        hBox.getChildren().addAll(playerOption, enemyOption);

        setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                switch (statsOptionState) {
                    case PLAYER -> {
                        System.out.println("choose player stat");
                    }
                    case ENEMY -> {
                        System.out.println("choose enemy stat");
                    }
                }

                return;
            }

            boolean isValidKey = switch (event.getCode()) {
                case A, H, LEFT, D, L, RIGHT -> true;
                default -> false;
            };

            if (!isValidKey) {
                return;
            }

            if (statsOptionState == StatsOptionState.PLAYER) {
                statsOptionState = StatsOptionState.ENEMY;
            } else {
                statsOptionState = StatsOptionState.PLAYER;
            }

            reRenderOptionsColor();
        });

        getChildren().add(hBox);
        reRenderOptionsColor();
    }

    private void reRenderOptionsColor() {
        playerOption.setTextFill(Color.DARKGRAY);
        enemyOption.setTextFill(Color.DARKGRAY);

        switch (statsOptionState) {
            case PLAYER -> playerOption.setTextFill(Color.YELLOW);
            case ENEMY -> enemyOption.setTextFill(Color.YELLOW);
        }
    }


}