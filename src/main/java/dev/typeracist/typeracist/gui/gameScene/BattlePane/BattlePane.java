package dev.typeracist.typeracist.gui.gameScene.BattlePane;

import dev.typeracist.typeracist.gui.gameScene.BattlePane.InformationPane.InformationPane;
import dev.typeracist.typeracist.logic.gameScene.BattlePaneStateContext;
import dev.typeracist.typeracist.logic.global.GameLogic;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;


public class BattlePane extends VBox {
    private BattlePaneStateContext battlePaneStateContext;
    private InformationPane informationPane;
    private ImageView characterImage;
    private HealthBar playerHpBar;
    private HealthBar enemyHpBar;

    public BattlePane(BattlePaneStateContext battleSceneStateContext) {
        this.battlePaneStateContext = battleSceneStateContext;

        setSpacing(10);
        setPadding(new Insets(10));
        setAlignment(Pos.CENTER);
        setBackground(new Background(new BackgroundFill(Color.web("#484848"), CornerRadii.EMPTY, Insets.EMPTY)));

        setFocusTraversable(true);
        initialize();
    }

    public BattlePaneStateContext getStateContext() {
        return battlePaneStateContext;
    }

    public InformationPane getInformationPane() {
        return informationPane;
    }

    public ImageView getCharacterImage() {
        return characterImage;
    }

    public HealthBar getPlayerHpBar() {
        return playerHpBar;
    }

    public HealthBar getEnemyHpBar() {
        return enemyHpBar;
    }

    private void initialize() {
        characterImage = new ImageView(battlePaneStateContext.getEnemy().getImage());
        informationPane = new InformationPane(this);
        informationPane.setPrefHeight(240);
        informationPane.prefWidthProperty().bind(this.widthProperty());
        informationPane.maxWidthProperty().bind(this.widthProperty());

        // Replace Label HP bars with HealthBars
        playerHpBar = new HealthBar(GameLogic.getInstance().getSelectedCharacter().getHp(), Color.BLUE);
        enemyHpBar = new HealthBar(battlePaneStateContext.getEnemy().getHp(), Color.RED);

        HBox hpBox = new HBox(10, playerHpBar, enemyHpBar);
        hpBox.setAlignment(Pos.CENTER);

        getChildren().addAll(characterImage, informationPane, hpBox);
    }

    public void updateHealthBars() {
        playerHpBar.updateHealthBar();
        enemyHpBar.updateHealthBar();
    }
}