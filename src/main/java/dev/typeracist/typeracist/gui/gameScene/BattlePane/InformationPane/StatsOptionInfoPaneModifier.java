package dev.typeracist.typeracist.gui.gameScene.BattlePane.InformationPane;

import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.utils.ResourceName;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.util.function.Consumer;

public class StatsOptionInfoPaneModifier extends BaseInfoPaneModifier {
    final private Label playerOption;
    final private Label enemyOption;
    private StatsOptionState statsOptionState;
    private Consumer<StatsOptionState> onChooseHandler;

    public StatsOptionInfoPaneModifier(InformationPane subPaneNavigator) {
        super(subPaneNavigator);

        this.playerOption = new Label("* Player");
        this.playerOption.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 18));
        this.enemyOption = new Label("* Enemy");
        this.enemyOption.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 18));
        this.statsOptionState = StatsOptionState.PLAYER;
    }

    public Consumer<StatsOptionState> getOnChooseHandler() {
        return onChooseHandler;
    }

    public void setOnChooseHandler(Consumer<StatsOptionState> onChooseHandler) {
        this.onChooseHandler = onChooseHandler;
    }

    public StatsOptionState getStatsOptionState() {
        return statsOptionState;
    }


    @Override
    protected void initialize() {
        HBox hBox = new HBox();

        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(40);
        hBox.getChildren().addAll(playerOption, enemyOption);

        setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (onChooseHandler != null) {
                    onChooseHandler.accept(statsOptionState);
                }
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

        setFocusTraversable(true);
        getChildren().add(hBox);
        reRenderOptionsColor();
        Platform.runLater(this::requestFocus);
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