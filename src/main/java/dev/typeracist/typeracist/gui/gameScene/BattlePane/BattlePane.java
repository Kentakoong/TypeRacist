package dev.typeracist.typeracist.gui.gameScene.BattlePane;

import dev.typeracist.typeracist.gui.gameScene.BattlePane.InformationPane.InformationPane;
import dev.typeracist.typeracist.gui.global.ThemedButton;
import dev.typeracist.typeracist.logic.characters.Enemy;
import dev.typeracist.typeracist.logic.characters.Entity;
import dev.typeracist.typeracist.logic.characters.Skill;
import dev.typeracist.typeracist.logic.characters.entities.Character;
import dev.typeracist.typeracist.logic.gameScene.BattlePaneStateContext;
import dev.typeracist.typeracist.logic.global.GameLogic;
import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.logic.inventory.Item;
import dev.typeracist.typeracist.utils.ResourceName;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.List;

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
        GameLogic.getInstance().getSceneManager().showPopUp(
                createStatsPane(), getWidth() - 50, getHeight() - 50);
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

    public VBox createStatsPane() {
        // Create a VBox for each panel we want to display
        VBox playerStatsBox = createCharacterStatsBox();
        VBox enemyStatsBox = createEnemyStatsBox();
        VBox itemsUsedBox = createItemsUsedBox();
        VBox skillsBox = createSkillsBox();

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
        return closeButtonBox;
    }

    private VBox createItemsUsedBox() {
        VBox container = new VBox(10);
        container.setAlignment(Pos.CENTER);
        container.setBackground(new Background(new BackgroundFill(
                Color.web("#E3E3E3"), new CornerRadii(10), Insets.EMPTY)));
        container.setPadding(new Insets(20));
        container.setBorder(new Border(new BorderStroke(
                Color.DARKGRAY, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(2))));

        Label titleLabel = new Label("Items Used This Turn");
        titleLabel.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 18));
        container.getChildren().add(titleLabel);

        // Get items used in current turn
        List<Item> itemsUsed = getStateContext().getCurrentTurnContext().getItemsUsed();

        if (itemsUsed == null || itemsUsed.isEmpty()) {
            Label noItemsLabel = new Label("No items used this turn");
            noItemsLabel.setTextFill(Color.DARKGRAY);
            noItemsLabel.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 14));
            container.getChildren().add(noItemsLabel);
        } else {
            // Create a box for each item used
            for (Item item : itemsUsed) {
                HBox itemBox = createItemBox(item);
                container.getChildren().add(itemBox);
            }
        }

        return container;
    }

    private HBox createItemBox(Item item) {
        // Item image
        ImageView itemImage = new ImageView(item.getImage());
        itemImage.setFitWidth(50);
        itemImage.setFitHeight(50);

        // Item details
        VBox itemDetails = new VBox(5);

        Label nameLabel = new Label(item.getName());
        nameLabel.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 16));

        Label descLabel = new Label(item.getDescription());
        descLabel.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 12));
        descLabel.setWrapText(true);

        itemDetails.getChildren().addAll(nameLabel, descLabel);

        // Combine into HBox
        HBox itemBox = new HBox(10, itemImage, itemDetails);
        itemBox.setAlignment(Pos.CENTER_LEFT);
        itemBox.setPadding(new Insets(5));
        itemBox.setBorder(new Border(new BorderStroke(
                Color.LIGHTGRAY, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(1))));

        return itemBox;
    }

    private VBox createSkillsBox() {
        VBox container = new VBox(10);
        container.setAlignment(Pos.CENTER);
        container.setBackground(new Background(new BackgroundFill(
                Color.web("#E3E3E3"), new CornerRadii(10), Insets.EMPTY)));
        container.setPadding(new Insets(20));
        container.setBorder(new Border(new BorderStroke(
                Color.DARKGRAY, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(2))));

        Label titleLabel = new Label("Skills");
        titleLabel.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 18));
        container.getChildren().add(titleLabel);

        // Player skill
        Entity player = GameLogic.getInstance().getSelectedCharacter();
        Skill playerSkill = player.getSkill();

        if (playerSkill != null) {
            HBox playerSkillBox = createSkillBox(playerSkill, player, "Player");
            container.getChildren().add(playerSkillBox);
        } else {
            Label noPlayerSkillLabel = new Label("Player has no skill");
            noPlayerSkillLabel.setTextFill(Color.DARKGRAY);
            noPlayerSkillLabel.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 14));
            container.getChildren().add(noPlayerSkillLabel);
        }

        // Enemy skill
        Enemy enemy = getStateContext().getEnemy();
        Skill enemySkill = enemy.getSkill();

        if (enemySkill != null) {
            HBox enemySkillBox = createSkillBox(enemySkill, enemy, "Enemy");
            container.getChildren().add(enemySkillBox);
        } else {
            Label noEnemySkillLabel = new Label("Enemy has no skill");
            noEnemySkillLabel.setTextFill(Color.DARKGRAY);
            noEnemySkillLabel.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 14));
            container.getChildren().add(noEnemySkillLabel);
        }

        return container;
    }

    private HBox createSkillBox(Skill skill, Entity entity, String entityType) {
        // Entity small image
        ImageView entityImage = new ImageView(entity.getImage());
        entityImage.setFitWidth(50);
        entityImage.setFitHeight(50);

        // Skill details
        VBox skillDetails = new VBox(5);

        Label nameLabel = new Label(skill.getName());
        nameLabel.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 16));

        Label cooldownLabel = new Label("Cooldown: " +
                skill.getCurrentCooldownTurns() + "/" + skill.getCooldownTurns());
        cooldownLabel.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 14));

        Label descLabel = new Label(skill.getDescription());
        descLabel.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 12));
        descLabel.setWrapText(true);

        skillDetails.getChildren().addAll(nameLabel, cooldownLabel, descLabel);

        // Combine into HBox
        HBox skillBox = new HBox(10, entityImage, skillDetails);
        skillBox.setAlignment(Pos.CENTER_LEFT);
        skillBox.setPadding(new Insets(5));

        // Set different background colors for player and enemy skills
        Color backgroundColor = entityType.equals("Player") ? Color.LIGHTBLUE : Color.LIGHTPINK;
        skillBox.setBackground(new Background(new BackgroundFill(
                backgroundColor, new CornerRadii(5), Insets.EMPTY)));

        skillBox.setBorder(new Border(new BorderStroke(
                Color.GRAY, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(1))));

        return skillBox;
    }

    private VBox createCharacterStatsBox() {
        Character player = GameLogic.getInstance().getSelectedCharacter();

        VBox statsBox = createStatsBoxBase();

        // Character image
        ImageView characterImage = new ImageView(player.getImage());
        characterImage.setFitWidth(225);
        characterImage.setFitHeight(225);

        // Character name label
        Label characterNameLabel = createNameLabel(GameLogic.getInstance().getPlayerName());

        // Player's level
        int playerLevel = player.getXp().getLevel();
        Label playerLevelLabel = new Label("Level " + playerLevel);
        playerLevelLabel.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 16));
        playerLevelLabel.setTextFill(Color.ORANGERED); // Set level color to orange

        // Player's current XP and XP to level up
        int currentXP = player.getXp().getXp();
        int expToLevelUp = player.getXp().getExpToLvlUp();
        Label xpLabel = new Label("XP: " + currentXP + " / " + expToLevelUp);
        xpLabel.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 10));

        HBox characterNameLabelContainer = new HBox(characterNameLabel, playerLevelLabel, xpLabel);
        characterNameLabelContainer.setAlignment(Pos.BASELINE_CENTER);
        characterNameLabelContainer.setSpacing(10);

        // Stat labels
        TextFlow attackLabel = createStatLabel("ATK", player.getTotalAtk(),
                player.getBaseAtk(), player.getExtraAtk());
        TextFlow healthLabel = createStatLabel("HP", player.getHp().getCurrentHP(), 0, 0);
        TextFlow defenseLabel = createStatLabel("DEF", player.getTotalDef(),
                player.getBaseDef(), player.getExtraDef());

        // Add the new labels to the stats box
        statsBox.getChildren().addAll(characterImage, characterNameLabelContainer, attackLabel, healthLabel,
                defenseLabel);

        return statsBox;
    }

    private VBox createEnemyStatsBox() {
        Enemy enemy = battlePaneStateContext.getEnemy();

        VBox statsBox = createStatsBoxBase();

        ImageView enemyImage = new ImageView(enemy.getImage());
        enemyImage.setFitWidth(225);
        enemyImage.setFitHeight(225);

        Label enemyNameLabel = createNameLabel(enemy.getName());

        TextFlow attackLabel = createStatLabel("ATK", enemy.getTotalAtk(),
                enemy.getBaseAtk(), enemy.getExtraAtk());
        TextFlow healthLabel = createStatLabel("HP", enemy.getHp().getCurrentHP(), 0, 0);
        TextFlow defenseLabel = createStatLabel("DEF", enemy.getTotalDef(),
                enemy.getBaseDef(), enemy.getExtraDef());

        statsBox.getChildren().addAll(enemyImage, enemyNameLabel,
                attackLabel, healthLabel, defenseLabel);

        return statsBox;
    }

    private VBox createStatsBoxBase() {
        VBox statsBox = new VBox(10);
        statsBox.setAlignment(Pos.CENTER);
        statsBox.setBackground(new Background(new BackgroundFill(
                Color.web("#E3E3E3"), new CornerRadii(10), Insets.EMPTY)));
        statsBox.setPadding(new Insets(20));
        statsBox.setBorder(new Border(new BorderStroke(
                Color.DARKGRAY, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(2))));

        return statsBox;
    }

    private Label createNameLabel(String name) {
        Label nameLabel = new Label(name);
        nameLabel.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 18));
        return nameLabel;
    }

    private TextFlow createStatLabel(String statName, int total, int base, int extra) {
        Text statText = new Text(statName + ": " + total + " ");
        statText.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 16));

        Text detailsText;
        if (extra != 0) {
            detailsText = new Text("(" + base + " + " + extra + ")");
        } else {
            detailsText = new Text("");
        }
        detailsText.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 16));
        detailsText.setFill(Color.GREEN);

        return new TextFlow(statText, detailsText);
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
