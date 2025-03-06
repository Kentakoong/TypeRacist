package dev.typeracist.typeracist.gui.gameScene.BattlePane;

import dev.typeracist.typeracist.logic.characters.Enemy;
import dev.typeracist.typeracist.logic.characters.Entity;
import dev.typeracist.typeracist.logic.characters.Skill;
import dev.typeracist.typeracist.logic.characters.entities.Character;
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

public class StatsDisplayManager {
    public static VBox createCharacterStatsBox() {
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
        characterNameLabelContainer.setAlignment(Pos.CENTER);
        characterNameLabelContainer.setSpacing(10);

        // Stat labels
        TextFlow attackLabel = createStatLabel("ATK", player.getTotalAtk(),
                player.getBaseAtk(), player.getExtraAtk());
        TextFlow healthLabel = createStatLabel("HP", player.getHp().getCurrentHP(), 0, 0);
        TextFlow defenseLabel = createStatLabel("DEF", player.getTotalDef(),
                player.getBaseDef(), player.getExtraDef());

        // Center all stat labels
        attackLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        healthLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        defenseLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        // Add the new labels to the stats box
        statsBox.getChildren().addAll(characterImage, characterNameLabelContainer, attackLabel, healthLabel,
                defenseLabel);

        return statsBox;
    }

    public static VBox createEnemyStatsBox(Enemy enemy) {
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

    public static VBox createItemsUsedBox(List<Item> itemsUsed) {
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

    public static VBox createSkillsBox(Character player, Enemy enemy) {
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

    private static VBox createStatsBoxBase() {
        VBox statsBox = new VBox(10);
        statsBox.setAlignment(Pos.CENTER);
        statsBox.setBackground(new Background(new BackgroundFill(
                Color.web("#E3E3E3"), new CornerRadii(10), Insets.EMPTY)));
        statsBox.setPadding(new Insets(20));
        statsBox.setBorder(new Border(new BorderStroke(
                Color.DARKGRAY, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(2))));

        return statsBox;
    }

    private static Label createNameLabel(String name) {
        Label nameLabel = new Label(name);
        nameLabel.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 18));
        return nameLabel;
    }

    private static TextFlow createStatLabel(String statName, int total, int base, int extra) {
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

        TextFlow textFlow = new TextFlow(statText, detailsText);
        textFlow.setMaxWidth(Double.MAX_VALUE); // Allow the TextFlow to expand to full width
        return textFlow;
    }

    private static HBox createItemBox(Item item) {
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

    private static HBox createSkillBox(Skill skill, Entity entity, String entityType) {
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
}