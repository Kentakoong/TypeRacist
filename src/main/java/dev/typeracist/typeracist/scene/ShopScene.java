package dev.typeracist.typeracist.scene;

import dev.typeracist.typeracist.logic.characters.entities.Character;
import dev.typeracist.typeracist.logic.global.GameLogic;
import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.logic.inventory.Inventory;
import dev.typeracist.typeracist.logic.inventory.Item;
import dev.typeracist.typeracist.logic.inventory.item.*;
import dev.typeracist.typeracist.utils.ResourceName;
import dev.typeracist.typeracist.utils.SceneName;
import dev.typeracist.typeracist.gui.gameScene.ShopPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class ShopScene extends BaseScene {
        private final Pane root;
        private final Character playerCharacter;
        private final ShopPane shopPane;
        private final Label coinLabel;

        public ShopScene(double width, double height, Character playerCharacter) {
                super(new Pane(), width, height);
                this.root = (Pane) getRoot();
                this.playerCharacter = playerCharacter;
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

                Pane backgroundPane = new Pane();
                backgroundPane.setStyle("-fx-background-color: #000000;");
                backgroundPane.setPrefSize(700, 525);
                backgroundPane.setLayoutX(50);
                backgroundPane.setLayoutY(100);
                root.getChildren().add(backgroundPane);

                Pane anotherBackgroundPane = new Pane();
                anotherBackgroundPane.setStyle("-fx-background-color: #6B6869;");
                anotherBackgroundPane.setPrefSize(690, 515);
                anotherBackgroundPane.setLayoutX(55);
                anotherBackgroundPane.setLayoutY(105);
                root.getChildren().add(anotherBackgroundPane);

                ImageView shopMasterImage = new ImageView(ResourceManager.getImage(ResourceName.IMAGE_SHOP_SHOPMASTER));
                shopMasterImage.setFitWidth(250);
                shopMasterImage.setFitHeight(200);
                shopMasterImage.setLayoutX(760);
                shopMasterImage.setLayoutY(100);
                root.getChildren().add(shopMasterImage);

                Label shopMasterText = new Label("Buy something,\nwould ya?");
                shopMasterText.setStyle("-fx-text-fill: white;");
                shopMasterText.setLayoutX(775);
                shopMasterText.setLayoutY(310);
                shopMasterText.setFont(Font.font(baseFont.getName(), 20));
                root.getChildren().add(shopMasterText);

                // Show player coin balance at top-right
                ImageView coinIcon = new ImageView(ResourceManager.getImage(ResourceName.IMAGE_SHOP_COIN));
                coinIcon.setFitWidth(30);
                coinIcon.setFitHeight(30);
                coinIcon.setLayoutX(870);
                coinIcon.setLayoutY(30);
                root.getChildren().add(coinIcon);

                coinLabel = new Label(getPlayerCoinsText());
                coinLabel.setFont(Font.font(baseFont.getName(), 20));
                coinLabel.setStyle("-fx-text-fill: gold;");
                coinLabel.setLayoutX(910);
                coinLabel.setLayoutY(30);
                root.getChildren().add(coinLabel);

                // Creating shop items
                createShopItem(new HealingPotion(), 100);
                createShopItem(new TimePotion(), 170);
                createShopItem(new PotionOfTypeswift(), 240);
                createShopItem(new FriedChicken(), 310);
                createShopItem(new WhirlwindDagger(), 380);
                createShopItem(new WoodenShield(), 450);
                createShopItem(new Typewriter(), 520);

                // Return button (smaller size)
                Button returnButton = createStyledButton("Return to Map");
                returnButton.setLayoutX(810);
                returnButton.setLayoutY(675);
                returnButton.setOnAction(event -> GameLogic.getInstance().getSceneManager().setScene(SceneName.MAP));
                root.getChildren().add(returnButton);

                // Initialize and add the pop-up pane
                shopPane = new ShopPane(width, height);
                root.getChildren().add(shopPane);
        }

        private void createShopItem(Item item, double y) {
                Pane imageBackground = new Pane();
                imageBackground.setStyle("-fx-background-color: white;");
                imageBackground.setPrefSize(60, 60);
                imageBackground.setLayoutX(75);
                imageBackground.setLayoutY(y + 20);
                root.getChildren().add(imageBackground);

                ImageView itemImage = new ImageView(item.getImage());
                itemImage.setFitWidth(50);
                itemImage.setFitHeight(50);
                itemImage.setLayoutX(80);
                itemImage.setLayoutY(y + 25);
                root.getChildren().add(itemImage);

                Font baseFont = ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 36);

                Label itemLabel = new Label();
                itemLabel.setText("DESC : " + item.getDescription() + " \nCOST : " + item.getPrice() + " Golds");
                itemLabel.setStyle("-fx-text-fill: white; -fx-font-weight: normal;");

                Label nameLabel = new Label(item.getName());
                nameLabel.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");

                nameLabel.setLayoutX(150);
                nameLabel.setLayoutY(y + 20);
                itemLabel.setLayoutX(150);
                itemLabel.setLayoutY(y + 40);

                nameLabel.setFont(Font.font(baseFont.getName(), 12));
                itemLabel.setFont(Font.font(baseFont.getName(), 12));

                root.getChildren().addAll(nameLabel, itemLabel);

                // Buy button (shifted a little to the left)
                Button buyButton = createStyledButton("BUY");
                buyButton.setLayoutX(600); // Moved to the left
                buyButton.setLayoutY(y + 37.5);
                buyButton.setOnAction(event -> purchaseItem(item));
                root.getChildren().add(buyButton);
        }


        private void purchaseItem(Item item) {
                int playerCoins = playerCharacter.getCoin();
                int itemPrice = item.getPrice();
                Inventory inventory = playerCharacter.getInventory();

                if (playerCoins >= itemPrice) {
                        playerCharacter.spendCoin(itemPrice);
                        inventory.addItem(item);
                        shopPane.showPopup("You bought " + item.getName() + "!");
                } else {
                        shopPane.showPopup("Not enough coins to buy " + item.getName() + "!");
                }

                // Update coin display
                coinLabel.setText(getPlayerCoinsText());
        }

        private Button createStyledButton(String text) {
                Font baseFont = ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 36);
                Button button = new Button(text);
                button.setFont(Font.font(baseFont.getName(), 16));
                button.setStyle("-fx-background-color: #484848; -fx-text-fill: white; -fx-border-color: white; -fx-border-width: 2;");
                button.setPrefWidth(120);
                button.setPrefHeight(35);

                button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #606060; -fx-text-fill: white; -fx-border-color: white; -fx-border-width: 2;"));
                button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #484848; -fx-text-fill: white; -fx-border-color: white; -fx-border-width: 2;"));

                return button;
        }

        private String getPlayerCoinsText() {
                return playerCharacter.getCoin() + " Gold";
        }

        @Override
        public void onSceneEnter() {
        }

        @Override
        public void onSceneLeave() {
        }
}
