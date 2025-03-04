package dev.typeracist.typeracist.scene;

import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.logic.inventory.Item;
import dev.typeracist.typeracist.logic.inventory.item.*;
import dev.typeracist.typeracist.utils.ResourceName;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class ShopScene extends BaseScene {
        private final Pane root;
        private String selectedItem = null;

        public ShopScene(double width, double height) {
                super(new Pane(), width, height);
                root = (Pane) getRoot();
                root.setStyle("-fx-background-color: #333;");

                Font baseFont = ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 36);

                Label titleLabel = new Label("SHOP");
                titleLabel.setStyle("-fx-text-fill: white;");
                titleLabel.setLayoutX(400);
                titleLabel.setLayoutY(25);
                titleLabel.setFont(Font.font(baseFont.getName(), 48));
                root.getChildren().add(titleLabel);

                ImageView shopIcon = new ImageView(ResourceManager.getImage(ResourceName.IMAGE_MAP_SHOP));
                shopIcon.setFitWidth(75);
                shopIcon.setFitHeight(75);
                shopIcon.setLayoutX(550);
                shopIcon.setLayoutY(20);
                root.getChildren().add(shopIcon);

                Pane BackgroundPane = new Pane();
                BackgroundPane.setStyle("-fx-background-color: #000000;");
                BackgroundPane.setPrefSize(700, 525);
                BackgroundPane.setLayoutX(50);
                BackgroundPane.setLayoutY(100);
                root.getChildren().add(BackgroundPane);

                Pane AnotherBackgroundPane = new Pane();
                AnotherBackgroundPane.setStyle("-fx-background-color: #ABABAB;");
                AnotherBackgroundPane.setPrefSize(690, 515);
                AnotherBackgroundPane.setLayoutX(55);
                AnotherBackgroundPane.setLayoutY(105);
                root.getChildren().add(AnotherBackgroundPane);
                // Placeholder for shopmaster image
                ImageView shopMasterImage = new ImageView(ResourceManager.getImage(ResourceName.IMAGE_SHOP_SHOPMASTER));
                shopMasterImage.setFitWidth(250);
                shopMasterImage.setFitHeight(200);
                shopMasterImage.setLayoutX(760);
                shopMasterImage.setLayoutY(100);
                root.getChildren().add(shopMasterImage);

                Label shopMasterText = new Label("Buy something,\nwould ya?.");
                shopMasterText.setStyle("-fx-text-fill: white;");
                shopMasterText.setLayoutX(775);
                shopMasterText.setLayoutY(310);
                shopMasterText.setFont(Font.font(baseFont.getName(), 20));
                root.getChildren().add(shopMasterText);

                // Creating shop items
                createShopItem(new HealingPotion(), 100);
                createShopItem(new TimePotion(), 170);
                createShopItem(new PotionOfTypeswift(), 240);
                createShopItem(new FriedChicken(), 310);
                createShopItem(new WhirlwindDagger(), 380);
                createShopItem(new WoodenShield(), 450);
                createShopItem(new Typewriter(), 520);

                Button returnButton = new Button("Return to Map");
                returnButton.setLayoutX(800);
                returnButton.setLayoutY(675);
                returnButton.setPrefWidth(150);
                returnButton.setPrefHeight(40);
                // returnButton.setOnAction(event -> SceneManager.setScene(SceneName.MAP));
                root.getChildren().add(returnButton);
        }

        private void createShopItem(Item item, double y) {
                // Create a white background pane for the item image
                Pane imageBackground = new Pane();
                imageBackground.setStyle("-fx-background-color: white;");
                imageBackground.setPrefSize(60, 60); // Slightly larger than the image
                imageBackground.setLayoutX(75);
                imageBackground.setLayoutY(y + 20);
                root.getChildren().add(imageBackground);

                // Item image
                ImageView itemImage = new ImageView(item.getImage());
                itemImage.setFitWidth(50);
                itemImage.setFitHeight(50);
                itemImage.setLayoutX(80);
                itemImage.setLayoutY(y + 25);
                root.getChildren().add(itemImage);

                Font baseFont = ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 36);

                // Item label with bold name
                Label itemLabel = new Label();
                itemLabel.setText("DESC : " + item.getDescription() + " \nCOST : " + item.getPrice() + " Golds");
                itemLabel.setStyle("-fx-text-fill: white; -fx-font-weight: normal;");

                // Bold and black name only
                Label nameLabel = new Label(item.getName());
                nameLabel.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");

                // Position labels
                nameLabel.setLayoutX(150);
                nameLabel.setLayoutY(y + 20);
                itemLabel.setLayoutX(150);
                itemLabel.setLayoutY(y + 40);

                nameLabel.setFont(Font.font(baseFont.getName(), 12));
                itemLabel.setFont(Font.font(baseFont.getName(), 12));

                root.getChildren().addAll(nameLabel, itemLabel);

                // Buy button
                Button buyButton = new Button("BUY");
                buyButton.setLayoutX(675);
                buyButton.setLayoutY(y + 37.5);
                root.getChildren().add(buyButton);
        }

        // private void purchaseItem(String item) {
        // GameLogic.getInstance().purchaseItem(item);
        // infoLabel.setText(item + " purchased!");
        // }

        @Override
        public void onSceneEnter() {

        }

        @Override
        public void onSceneLeave() {
        }
}