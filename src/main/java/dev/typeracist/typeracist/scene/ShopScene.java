package dev.typeracist.typeracist.scene;

import dev.typeracist.typeracist.logic.global.GameLogic;
import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.logic.global.SceneManager;
import dev.typeracist.typeracist.utils.ResourceName;
import dev.typeracist.typeracist.utils.SceneName;
import javafx.geometry.Pos;
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
        titleLabel.setLayoutX(500);
        titleLabel.setLayoutY(20);
        titleLabel.setFont(Font.font(baseFont.getName(), 48));
        root.getChildren().add(titleLabel);

        // Placeholder for shopmaster image
//        ImageView shopMasterImage = new ImageView(ResourceManager.getImage(ResourceName.IMAGE_SHOPMASTER));
//        shopMasterImage.setFitWidth(150);
//        shopMasterImage.setFitHeight(150);
//        shopMasterImage.setLayoutX(600);
//        shopMasterImage.setLayoutY(100);
//        root.getChildren().add(shopMasterImage);

        Label shopMasterText = new Label("OHH, I'd buy the same \nif I were you.");
        shopMasterText.setStyle("-fx-text-fill: white;");
        shopMasterText.setLayoutX(630);
        shopMasterText.setLayoutY(270);
        shopMasterText.setFont(Font.font(baseFont.getName(), 20));
        root.getChildren().add(shopMasterText);

        // Creating shop items
        createShopItem("Healing Potion", "Heal your character by 20 HP.", 15, 100, ResourceName.IMAGE_SHOP_HEALING_POTION);
        createShopItem("Time Potion", "Increase the timer by 2.5 seconds. 1 turn.", 25, 170, ResourceName.IMAGE_SHOP_TIME_POTION);
        createShopItem("Potion of Typeswift", "Increase your CPS by x1.25. 1 turn.", 30, 240, ResourceName.IMAGE_SHOP_POTION_OF_TYPESWIFT);
        createShopItem("Fried Chicken", "Heal your character by 10 HP, lasts for 2 turns.", 20, 310, ResourceName.IMAGE_SHOP_FRIED_CHICKEN);
        createShopItem("Whirlwind Dagger", "ATK +6. Stun enemy every 3 turns.", 34, 380, ResourceName.IMAGE_SHOP_WHIRLWIND_DAGGER);
        createShopItem("Wooden Shield", "Reliable wooden shield. DEF +2.", 7, 450, ResourceName.IMAGE_SHOP_WOODEN_SHIELD);
        createShopItem("Typewriter", "Nothing, just a typewriter.", 696969, 520, ResourceName.IMAGE_SHOP_TYPEWRITER);

        Button returnButton = new Button("Return to Map");
        returnButton.setLayoutX(900);
        returnButton.setLayoutY(650);
//        returnButton.setOnAction(event -> SceneManager.setScene(SceneName.MAP));
        root.getChildren().add(returnButton);
    }

    private void createShopItem(String name, String description, int cost, double y, String imageResource) {
        ImageView itemImage = new ImageView(ResourceManager.getImage(imageResource));
        itemImage.setFitWidth(50);
        itemImage.setFitHeight(50);
        itemImage.setLayoutX(10);
        itemImage.setLayoutY(y);
        root.getChildren().add(itemImage);

        Font baseFont = ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 36);

        Label itemLabel = new Label(name + "\nDESC : " + description + " \nCOST : " + cost + " Golds");
        itemLabel.setStyle("-fx-text-fill: white;");
        itemLabel.setLayoutX(70);
        itemLabel.setLayoutY(y);
        itemLabel.setFont(Font.font(baseFont.getName(), 12));
        root.getChildren().add(itemLabel);

        Button buyButton = new Button("BUY");
        buyButton.setLayoutX(550);
        buyButton.setLayoutY(y+12.5);
//        buyButton.setOnAction(event -> purchaseItem(name));
        root.getChildren().add(buyButton);
    }


//    private void purchaseItem(String item) {
//        GameLogic.getInstance().purchaseItem(item);
//        infoLabel.setText(item + " purchased!");
//    }

    @Override
    public void onSceneEnter() {

    }

    @Override
    public void onSceneLeave() {
    }
}