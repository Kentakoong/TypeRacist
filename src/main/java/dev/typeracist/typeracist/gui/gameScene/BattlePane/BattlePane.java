package dev.typeracist.typeracist.gui.gameScene.BattlePane;

import dev.typeracist.typeracist.gui.gameScene.BattlePane.InformationPane.InformationPane;
import dev.typeracist.typeracist.gui.global.ThemedButton;
import dev.typeracist.typeracist.logic.gameScene.BattlePaneStateContext;
import dev.typeracist.typeracist.logic.global.GameLogic;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class BattlePane extends VBox {
    private final BattlePaneStateContext battlePaneStateContext;
    private InformationPane informationPane;
    private ImageView enemyImageView;
    private HealthBar playerHealthBar;
    private HealthBar enemyHealthBar;
    private ThemedButton statsButton;

    public BattlePane(BattlePaneStateContext battlePaneStateContext) {
        this.battlePaneStateContext = battlePaneStateContext;
        setupLayout();
        initializeComponents();
    }

    private void setupLayout() {
        setSpacing(10);
        setPadding(new Insets(20, 10, 10, 10));
        setAlignment(Pos.TOP_CENTER);
        setBackground(new Background(new BackgroundFill(Color.web("#484848"), CornerRadii.EMPTY, Insets.EMPTY)));
        setFocusTraversable(true);
    }

    private void initializeComponents() {
        // Create header with stats button
        HBox headerContainer = createHeaderWithStatsButton();

        // Create enemy image view
        enemyImageView = createEnemyImageView();

        // Create information pane
        informationPane = createInformationPane();

        // Create health bars
        HBox healthBarsContainer = createHealthBars();

        // Add all components to the main VBox
        getChildren().addAll(headerContainer, enemyImageView, informationPane, healthBarsContainer);
    }

    private HBox createHeaderWithStatsButton() {
        statsButton = new ThemedButton("Show stats");
        statsButton.setFocusTraversable(false);
        statsButton.setOnMouseClicked(e -> showStatsPopup());

        HBox headerContainer = new HBox(statsButton);
        headerContainer.setAlignment(Pos.TOP_RIGHT);
        headerContainer.prefWidthProperty().bind(this.widthProperty());
        headerContainer.setPadding(new Insets(5));

        return headerContainer;
    }

    private void showStatsPopup() {
        // Create a VBox for each panel we want to display
        VBox playerStatsBox = StatsDisplayManager.createCharacterStatsBox();
        VBox enemyStatsBox = StatsDisplayManager.createEnemyStatsBox(battlePaneStateContext.getEnemy());
        VBox itemsUsedBox = StatsDisplayManager
                .createItemsUsedBox(battlePaneStateContext.getCurrentTurnContext().getItemsUsed());
        VBox skillsBox = StatsDisplayManager.createSkillsBox(GameLogic.getInstance().getSelectedCharacter(),
                battlePaneStateContext.getEnemy());

        // Add both skill and items panels to a VBox for the right side
        VBox rightPanels = new VBox(10, skillsBox, itemsUsedBox);
        rightPanels.setAlignment(Pos.CENTER);

        // Wrap the close button in a VBox for alignment
        VBox closeButtonBox = new VBox(20);
        closeButtonBox.setAlignment(Pos.CENTER);

        // Create a close button
        ThemedButton closeButton = new ThemedButton("Close");
        closeButton.setOnMouseClicked(e -> {
            GameLogic.getInstance().getSceneManager().closePopUp();
        });

        // Create the main container with player stats, enemy stats, and the right
        // panels
        HBox container = new HBox(20, playerStatsBox, enemyStatsBox, rightPanels);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(10));

        closeButtonBox.getChildren().addAll(container, closeButton);
        GameLogic.getInstance().getSceneManager().showPopUp(closeButtonBox, getWidth() - 50, getHeight() - 50);
    }

    private ImageView createEnemyImageView() {
        ImageView imageView = new ImageView(battlePaneStateContext.getEnemy().getImage());
        imageView.setFitWidth(225);
        imageView.setFitHeight(225);
        return imageView;
    }

    private InformationPane createInformationPane() {
        InformationPane pane = new InformationPane(this);
        pane.setPrefHeight(240);
        pane.prefWidthProperty().bind(this.widthProperty());
        pane.maxWidthProperty().bind(this.widthProperty());
        return pane;
    }

    private HBox createHealthBars() {
        playerHealthBar = new HealthBar(Color.BLUE);
        enemyHealthBar = new HealthBar(Color.RED);

        // Bind width to half of scene width
        playerHealthBar.prefWidthProperty().bind(widthProperty().divide(2));
        enemyHealthBar.prefWidthProperty().bind(widthProperty().divide(2));

        playerHealthBar.updateHealthBar(GameLogic.getInstance().getSelectedCharacter().getHp());
        enemyHealthBar.updateHealthBar(battlePaneStateContext.getEnemy().getHp());

        HBox healthBarsContainer = new HBox(10, playerHealthBar, enemyHealthBar);
        healthBarsContainer.setAlignment(Pos.CENTER);

        return healthBarsContainer;
    }

    public void updateHealthBars() {
        playerHealthBar.updateHealthBar(GameLogic.getInstance().getSelectedCharacter().getHp());
        enemyHealthBar.updateHealthBar(battlePaneStateContext.getEnemy().getHp());
    }

    // Getters
    public BattlePaneStateContext getStateContext() {
        return battlePaneStateContext;
    }

    public InformationPane getInformationPane() {
        return informationPane;
    }

    public ImageView getCharacterImage() {
        return enemyImageView;
    }

    public HealthBar getPlayerHpBar() {
        return playerHealthBar;
    }

    public HealthBar getEnemyHpBar() {
        return enemyHealthBar;
    }
}
