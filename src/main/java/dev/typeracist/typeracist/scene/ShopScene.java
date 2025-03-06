package dev.typeracist.typeracist.scene;

import dev.typeracist.typeracist.gui.global.ThemedButton;
import dev.typeracist.typeracist.logic.global.GameLogic;
import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.logic.global.SaveManager;
import dev.typeracist.typeracist.logic.inventory.Inventory;
import dev.typeracist.typeracist.logic.inventory.Item;
import dev.typeracist.typeracist.logic.inventory.item.FriedChicken;
import dev.typeracist.typeracist.logic.inventory.item.HealingPotion;
import dev.typeracist.typeracist.logic.inventory.item.PotionOfTypeswift;
import dev.typeracist.typeracist.logic.inventory.item.TimePotion;
import dev.typeracist.typeracist.logic.inventory.item.Typewriter;
import dev.typeracist.typeracist.logic.inventory.item.WhirlwindDagger;
import dev.typeracist.typeracist.logic.inventory.item.WoodenShield;
import dev.typeracist.typeracist.utils.ResourceName;
import dev.typeracist.typeracist.utils.SceneName;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class ShopScene extends BaseScene {
    private final VBox root;
    private Label coinLabel;

    public ShopScene(double width, double height) {
        super(new VBox(), width, height);
        this.root = (VBox) getRoot();
        root.setStyle("-fx-background-color: #333;");
        root.setPadding(new Insets(50));
        root.setAlignment(Pos.TOP_CENTER);
        root.setSpacing(20);
        root.setPrefWidth(width);

        if (GameLogic.getInstance().getSelectedCharacter() == null) {
            GameLogic.getInstance().getSceneManager().setScene(SceneName.CHARACTERS);
            return;
        }

        HBox header = createShopHeader();

        HBox shopContainer = new HBox();
        shopContainer.setAlignment(Pos.CENTER);
        shopContainer.setSpacing(10);
        shopContainer.setPadding(new Insets(10));

        VBox shopItemContainer = createShopContainer();
        VBox.setVgrow(shopItemContainer, Priority.ALWAYS);

        VBox shopMasterContainer = createShopMaster();

        shopContainer.getChildren().addAll(shopItemContainer, shopMasterContainer);

        ThemedButton returnButton = new ThemedButton("Return to Map");
        returnButton.setOnAction(event -> GameLogic.getInstance().getSceneManager().setScene(SceneName.MAP));

        root.getChildren().addAll(header, shopContainer, returnButton);
    }

    private HBox createShopHeader() {

        HBox header = new HBox();

        // placeholder for left side of header
        Pane leftSide = new Pane();

        // Create spacers
        Region leftSpacer = new Region();
        Region rightSpacer = new Region();
        HBox.setHgrow(leftSpacer, Priority.ALWAYS);
        HBox.setHgrow(rightSpacer, Priority.ALWAYS);

        Label titleLabel = new Label("SHOP");
        titleLabel.setStyle("-fx-text-fill: white;");
        titleLabel.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 48));

        ImageView shopIcon = new ImageView(ResourceManager.getImage(ResourceName.IMAGE_MAP_SHOP));
        shopIcon.setFitWidth(75);
        shopIcon.setFitHeight(75);

        HBox rightSide = new HBox();

        ImageView coinIcon = new ImageView(ResourceManager.getImage(ResourceName.IMAGE_SHOP_COIN));
        coinIcon.setFitWidth(30);
        coinIcon.setFitHeight(30);

        coinLabel = new Label(getPlayerCoinsText());
        coinLabel.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 20));
        coinLabel.setStyle("-fx-text-fill: gold;");

        rightSide.getChildren().addAll(coinIcon, coinLabel);

        header.getChildren().addAll(leftSide, leftSpacer, titleLabel, shopIcon, rightSpacer, rightSide);
        header.setAlignment(Pos.CENTER);
        header.setSpacing(20);

        return header;
    }

    private HBox createShopItem(Item item) {
        HBox itemContainer = new HBox();
        itemContainer.setAlignment(Pos.CENTER_LEFT);
        itemContainer.setSpacing(10);
        itemContainer.setPadding(new Insets(10));

        ImageView itemImage = new ImageView(item.getImage());
        itemImage.setFitWidth(50);
        itemImage.setFitHeight(50);

        VBox itemInfoContainer = new VBox();
        itemInfoContainer.setAlignment(Pos.CENTER_LEFT);
        itemInfoContainer.setSpacing(5);
        HBox.setHgrow(itemInfoContainer, Priority.ALWAYS);

        Label itemLabel = new Label();
        itemLabel.setText("DESC : " + item.getDescription());
        itemLabel.setStyle("-fx-text-fill: white;");
        itemLabel.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 12));

        Label nameLabel = new Label(item.getName());
        nameLabel.setStyle("-fx-text-fill: white");
        nameLabel.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 14));

        itemInfoContainer.getChildren().addAll(nameLabel, itemLabel);

        ImageView coinIcon = new ImageView(ResourceManager.getImage(ResourceName.IMAGE_SHOP_COIN));
        coinIcon.setFitWidth(15);
        coinIcon.setFitHeight(15);

        Label costLabel = new Label(String.valueOf(item.getPrice()));
        costLabel.setStyle("-fx-text-fill: gold;");
        costLabel.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 16));

        ThemedButton buyButton = new ThemedButton("BUY");
        buyButton.setOnAction(event -> purchaseItem(item));

        itemContainer.getChildren().addAll(itemImage, itemInfoContainer, coinIcon, costLabel, buyButton);

        return itemContainer;
    }

    private VBox createShopContainer() {
        VBox shopItemContainer = new VBox();
        shopItemContainer.setAlignment(Pos.CENTER);
        shopItemContainer.setSpacing(10);
        shopItemContainer.setPadding(new Insets(10));
        shopItemContainer.setStyle("-fx-background-color: #484848; -fx-border-color: black; -fx-border-width: 2;");

        HBox.setHgrow(shopItemContainer, Priority.ALWAYS);

        // Create items once and reuse them
        Item[] shopItems = {
                new HealingPotion(),
                new TimePotion(),
                new PotionOfTypeswift(),
                new FriedChicken(),
                new WhirlwindDagger(),
                new WoodenShield(),
                new Typewriter()
        };

        for (Item item : shopItems) {
            shopItemContainer.getChildren().add(createShopItem(item));
        }

        return shopItemContainer;
    }

    private VBox createShopMaster() {

        VBox shopMasterContainer = new VBox();
        shopMasterContainer.setAlignment(Pos.TOP_LEFT);
        shopMasterContainer.setSpacing(10);
        shopMasterContainer.setPadding(new Insets(10));

        ImageView shopMasterImage = new ImageView(ResourceManager.getImage(ResourceName.IMAGE_SHOP_SHOPMASTER));
        shopMasterImage.setFitWidth(250);
        shopMasterImage.setFitHeight(200);

        Label shopMasterText = new Label("Buy something,\nwould ya?");
        shopMasterText.setStyle("-fx-text-fill: white;");
        shopMasterText.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 20));

        shopMasterContainer.getChildren().addAll(shopMasterImage, shopMasterText);

        return shopMasterContainer;
    }

    private void purchaseItem(Item item) {
        int playerCoins = GameLogic.getInstance().getSelectedCharacter().getCoin();
        int itemPrice = item.getPrice();
        Inventory inventory = GameLogic.getInstance().getSelectedCharacter().getInventory();

        if (playerCoins >= itemPrice) {
            inventory.addItem(item);
            GameLogic.getInstance().getSelectedCharacter().spendCoin(itemPrice);
            GameLogic.getInstance().getSceneManager().showBreadcrumb("You bought " + item.getName() + "!", "", 1000);
            SaveManager.saveCharacter();
        } else {
            GameLogic.getInstance().getSceneManager().showBreadcrumb("Not enough coins to buy " + item.getName() + "!",
                    "", 1000);
        }

        // Update coin display
        coinLabel.setText(getPlayerCoinsText());
    }

    private String getPlayerCoinsText() {
        return GameLogic.getInstance().getSelectedCharacter().getCoin() + " Gold";
    }

    @Override
    public void onSceneEnter() {
        coinLabel.setText(getPlayerCoinsText());
    }

    @Override
    public void onSceneLeave() {
    }
}
