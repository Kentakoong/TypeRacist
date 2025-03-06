package dev.typeracist.typeracist.scene;

import dev.typeracist.typeracist.logic.global.GameLogic;
import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.logic.global.SaveManager;
import dev.typeracist.typeracist.logic.inventory.item.*;
import dev.typeracist.typeracist.utils.ResourceName;
import dev.typeracist.typeracist.utils.SceneName;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class ChestScene extends BaseScene {

    public ChestScene(double width, double height, String sceneName) {
        super(new AnchorPane(), width, height);

        AnchorPane root = (AnchorPane) getRoot();
        root.setStyle("-fx-background-color: #484848;");

        VBox chestContainer = new VBox();
        chestContainer.setAlignment(Pos.CENTER);
        chestContainer.setSpacing(20);
        chestContainer.setPrefWidth(width);

        Text title = new Text("CHEST LOOT");
        title.setFill(Color.WHITE);
        title.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 48));

        VBox rewardPane = new VBox();
        rewardPane.setAlignment(Pos.CENTER);
        rewardPane.setSpacing(20);
        rewardPane.setPadding(new Insets(10));
        rewardPane.setStyle("-fx-background-color: #949292;");
        rewardPane.setBorder(
                new Border(new BorderStroke(Color.gray(0.75), BorderStrokeStyle.SOLID, null, new BorderWidths(10))));
        rewardPane.setVisible(false);

        Label rewardHeader = new Label("- You got -");
        rewardHeader.setStyle("-fx-text-fill: black;");
        rewardHeader.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 36));

        Text rewardText = new Text(
                sceneName.equals(SceneName.REWARD1) ? "100 golds\n2 Healing Potion\n1 Time Potion\n1 Wooden Shield"
                        : "250 golds\n5 Healing Potion\n3 Potion of Typeswift\n2 Whirlwind Dagger\n2 Wooden Shield");
        rewardText.setFill(Color.WHITE);
        rewardText.setTextAlignment(TextAlignment.CENTER);
        rewardText.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 24));

        Button confirmLootButton = new Button("Back to Map");
        confirmLootButton.setPrefSize(150, 40);
        confirmLootButton.setMaxSize(150, 40);
        confirmLootButton.setAlignment(Pos.CENTER);
        confirmLootButton
                .setFont(Font.font(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 18).getName(), 18));
        confirmLootButton.setOnAction(event -> {
            if (sceneName.equals(SceneName.REWARD1)) {
                GameLogic.getInstance().getSelectedCharacter().gainCoin(100);
                GameLogic.getInstance().getSelectedCharacter().getInventory().addItem(new HealingPotion(), 2);
                GameLogic.getInstance().getSelectedCharacter().getInventory().addItem(new TimePotion(), 1);
                GameLogic.getInstance().getSelectedCharacter().getInventory().addItem(new WoodenShield(), 1);
            } else {
                GameLogic.getInstance().getSelectedCharacter().gainCoin(250);
                GameLogic.getInstance().getSelectedCharacter().getInventory().addItem(new HealingPotion(), 5);
                GameLogic.getInstance().getSelectedCharacter().getInventory().addItem(new PotionOfTypeswift(), 3);
                GameLogic.getInstance().getSelectedCharacter().getInventory().addItem(new WhirlwindDagger(), 2);
                GameLogic.getInstance().getSelectedCharacter().getInventory().addItem(new WoodenShield(), 2);
            }

            GameLogic.getInstance().getSelectedCharacter().clearBattle(sceneName);

            SaveManager.saveCharacter();

            GameLogic.getInstance().getSceneManager().setScene(SceneName.MAP);
        });

        rewardPane.getChildren().addAll(rewardHeader, rewardText, confirmLootButton);

        ImageView chestIcon = new ImageView(ResourceManager.getImage(ResourceName.IMAGE_CHEST_CLOSE));
        chestIcon.setFitWidth(450);
        chestIcon.setFitHeight(450);

        Button openChestButton = new Button("Open Chest");
        openChestButton.setPrefWidth(150);
        openChestButton.setPrefHeight(40);
        openChestButton.setFont(Font.font(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 18).getName(), 18));
        openChestButton.setOnAction(event -> {
            chestIcon.setImage(ResourceManager.getImage(ResourceName.IMAGE_CHEST_OPENED));
        });

        openChestButton.setOnAction(event -> rewardPane.setVisible(true));

        chestContainer.getChildren().addAll(title, chestIcon, openChestButton);

        root.getChildren().addAll(chestContainer, rewardPane);

        AnchorPane.setTopAnchor(chestContainer, 0.0);
        AnchorPane.setBottomAnchor(chestContainer, 0.0);
        AnchorPane.setLeftAnchor(chestContainer, 0.0);
        AnchorPane.setRightAnchor(chestContainer, 0.0);

        AnchorPane.setTopAnchor(rewardPane, height * 0.1);
        AnchorPane.setBottomAnchor(rewardPane, height * 0.1);
        AnchorPane.setLeftAnchor(rewardPane, width * 0.2);
        AnchorPane.setRightAnchor(rewardPane, width * 0.2);
    }

    @Override
    public void onSceneEnter() {

    }

    @Override
    public void onSceneLeave() {

    }
}
