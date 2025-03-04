package dev.typeracist.typeracist.gui.gameScene.BattlePane;

import dev.typeracist.typeracist.gui.gameScene.BattlePane.InformationPane.InformationPane;
import dev.typeracist.typeracist.logic.gameScene.BattlePaneState;
import dev.typeracist.typeracist.logic.gameScene.BattlePaneStateContext;
import dev.typeracist.typeracist.logic.gameScene.BattlePaneStateManager;
import dev.typeracist.typeracist.logic.global.GameLogic;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class BattlePane extends VBox {
    private BattlePaneStateContext battlePaneStateContext;
    private InformationPane informationPane;
    private ImageView characterImage;
    private HealthBar playerHpBar;
    private HealthBar enemyHpBar;
    private HBox hpBox;
    private HBox buttonBox;
    private Button attackButton;
    private Button statsButton;
    private Button itemsButton;

    public BattlePane(BattlePaneStateContext battleSceneStateContext) {
        this.battlePaneStateContext = battleSceneStateContext;

        setSpacing(10);
        setPadding(new Insets(10));
        setAlignment(Pos.CENTER);
        setBackground(new Background(new BackgroundFill(Color.web("#484848"), CornerRadii.EMPTY, Insets.EMPTY)));

        setFocusTraversable(true);

        initializeComponents();
        layoutComponents();
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

    public HBox getHpBox() {
        return hpBox;
    }

    public HBox getButtonBox() {
        return buttonBox;
    }

    public Button getAttackButton() {
        return attackButton;
    }

    public Button getStatsButton() {
        return statsButton;
    }

    public Button getItemsButton() {
        return itemsButton;
    }

    private void initializeComponents() {
        characterImage = new ImageView(battlePaneStateContext.getEnemy().getImage());
        informationPane = new InformationPane();
        informationPane.setPrefHeight(240);

        // Replace Label HP bars with HealthBars
        playerHpBar = new HealthBar(GameLogic.getInstance().getSelectedCharacter().getHp(), Color.BLUE);
        enemyHpBar = new HealthBar(battlePaneStateContext.getEnemy().getHp(), Color.RED);

        hpBox = new HBox(10, playerHpBar, enemyHpBar);
        hpBox.setAlignment(Pos.CENTER);

        attackButton = createButton("Attack!");
        statsButton = createButton("Stats");
        itemsButton = createButton("Items");
        buttonBox = new HBox(10, attackButton, statsButton, itemsButton);
        buttonBox.setAlignment(Pos.CENTER);
    }

    private void layoutComponents() {
        getChildren().addAll(characterImage, informationPane, hpBox, buttonBox);
    }

    public void setupEventHandlers(BattlePaneStateManager stateManager) {
        attackButton.setOnAction(event -> {
            stateManager.transitionToState(BattlePaneState.PLAYER_ATTACK);
            updateHealthBars();
        });

        statsButton.setOnAction(event -> {
            System.out.println("pop up stats");
        });

        itemsButton.setOnAction(event -> {
            System.out.println("pop up items");
        });
    }

    private void updateHealthBars() {
        playerHpBar.updateHealthBar();
        enemyHpBar.updateHealthBar();
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setFont(javafx.scene.text.Font.font("Arial", javafx.scene.text.FontWeight.BOLD, 14));
        button.setPrefWidth(80);
        return button;
    }
}