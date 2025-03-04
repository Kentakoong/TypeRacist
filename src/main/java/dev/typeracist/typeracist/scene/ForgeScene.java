package dev.typeracist.typeracist.scene;

import dev.typeracist.typeracist.logic.global.GameLogic;
import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.utils.ResourceName;
import dev.typeracist.typeracist.utils.SceneName;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class ForgeScene extends BaseScene {

    private final Pane root;

    public ForgeScene(double width, double height) {
        super(new Pane(), width, height);
        root = (Pane) getRoot();
        root.setStyle("-fx-background-color: #484848;");
        Text title = new Text("FORGE");
        title.setLayoutX(375);
        title.setLayoutY(60);
        title.setFill(Color.WHITE);
        title.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 48));

        Text plusState = new Text("+1");
        plusState.setLayoutX(90);
        plusState.setLayoutY(130);
        plusState.setFill(Color.WHITE);
        plusState.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 36));
        plusState.setVisible(false);

        ImageView forgeIcon = new ImageView(ResourceManager.getImage(ResourceName.IMAGE_MAP_ANVIL));
        forgeIcon.setFitWidth(75);
        forgeIcon.setFitHeight(75);
        forgeIcon.setLayoutX(545);
        forgeIcon.setLayoutY(0);
        root.getChildren().add(forgeIcon);

        Button returnButton = new Button("Return to map");
        returnButton.setLayoutX(800);
        returnButton.setLayoutY(675);
        returnButton.setPrefWidth(150);
        returnButton.setPrefHeight(40);
        returnButton.setOnAction(event -> GameLogic.getInstance().getSceneManager().setScene(SceneName.MAP));

        Pane weaponPane = createItemPane(5, 5, "CHOOSE\nWEAPON", ResourceName.IMAGE_ENCHANT_WEAPON);
        Pane emptyPane = new Pane();
        emptyPane.setLayoutX(305);
        emptyPane.setLayoutY(5);
        emptyPane.setPrefSize(140, 140);
        emptyPane.setStyle("-fx-background-color: #000000;");
        emptyPane.getChildren().add(plusState);

        ImageView upgradedWeapon = new ImageView(ResourceManager.getImage(ResourceName.IMAGE_ENCHANT_WEAPON));
        upgradedWeapon.setFitHeight(100);
        upgradedWeapon.setFitWidth(100);
        upgradedWeapon.setLayoutX(20);
        upgradedWeapon.setLayoutY(20);
        emptyPane.getChildren().add(upgradedWeapon);

        ImageView image1 = createImageView(ResourceName.IMAGE_ENCHANT_TO, 175, 24);

        Pane itemSelectionPane = new Pane();
        itemSelectionPane.setLayoutX(265);
        itemSelectionPane.setLayoutY(90);
        itemSelectionPane.setPrefSize(450, 150);
        itemSelectionPane.setStyle("-fx-background-color: #ABABAB;");
        itemSelectionPane.getChildren().addAll(weaponPane, image1, emptyPane);

        Pane weaponSelectionPane = new Pane();
        weaponSelectionPane.setLayoutX(125);
        weaponSelectionPane.setLayoutY(275);
        weaponSelectionPane.setPrefSize(735, 300);
        weaponSelectionPane.setStyle("-fx-background-color: #D9D9D9;");
        root.getChildren().add(weaponSelectionPane);

        Pane requirementsPane = new Pane();
        requirementsPane.setLayoutX(145);
        requirementsPane.setLayoutY(400);
        requirementsPane.setPrefSize(500, 50);
        requirementsPane.setStyle("-fx-background-color: #949494;");
        Text reqText = new Text("Required : 50 Golds, LVL 2");
        reqText.setLayoutX(10);
        reqText.setLayoutY(32);
        reqText.setFill(Color.WHITE);
        reqText.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 24));
        requirementsPane.getChildren().add(reqText);

        Button upgradeButton = new Button("Upgrade");
        upgradeButton.setLayoutX(660);
        upgradeButton.setLayoutY(405);
        upgradeButton.setPrefWidth(150);
        upgradeButton.setPrefHeight(40);

        Text fromText = createLabel("FROM", 310, 300);
        Text toText = createLabel("TO", 625, 300);
        Text atkFrom = createLabel("ATK : 4", 285, 350);
        Text atkTo = createLabel("ATK : 6", 590, 350);

        root.getChildren().addAll(title, returnButton, itemSelectionPane, requirementsPane, upgradeButton, fromText, toText, atkFrom, atkTo);

        Button testInsertButton = new Button("Test Complete");
        testInsertButton.setLayoutX(200);
        testInsertButton.setLayoutY(675);
        testInsertButton.setPrefWidth(150);
        testInsertButton.setPrefHeight(40);
        testInsertButton.setOnAction(event -> {
            updateItemPaneText(weaponPane, "");
            updateItemPaneImage(weaponPane, ResourceName.IMAGE_ENCHANT_WEAPON);
            fromText.setVisible(true);
            toText.setVisible(true);
            atkFrom.setVisible(true);
            atkTo.setVisible(true);
            plusState.setVisible(true);
            upgradedWeapon.setVisible(true);
            weaponSelectionPane.setVisible(false);
            requirementsPane.setVisible(true);
            upgradeButton.setVisible(true);
        });
        root.getChildren().add(testInsertButton);

        Button testNoInsertButton = new Button("Test Incomplete");
        testNoInsertButton.setLayoutX(400);
        testNoInsertButton.setLayoutY(675);
        testNoInsertButton.setPrefWidth(150);
        testNoInsertButton.setPrefHeight(40);
        testNoInsertButton.setOnAction(event -> {
            updateItemPaneImage(weaponPane, ResourceName.IMAGE_ENCHANT_SPACE);
            updateItemPaneText(weaponPane, "CHOOSE\nWEAPON");
            weaponSelectionPane.setVisible(true);
            fromText.setVisible(false);
            toText.setVisible(false);
            atkFrom.setVisible(false);
            atkTo.setVisible(false);
            plusState.setVisible(false);
            upgradedWeapon.setVisible(false);
            requirementsPane.setVisible(false);
            upgradeButton.setVisible(false);
        });
        root.getChildren().add(testNoInsertButton);

        fromText.setVisible(false);
        toText.setVisible(false);
        atkFrom.setVisible(false);
        atkTo.setVisible(false);
        requirementsPane.setVisible(false);
        upgradeButton.setVisible(false);
        upgradedWeapon.setVisible(false);
        updateItemPaneImage(weaponPane, ResourceName.IMAGE_ENCHANT_SPACE);
    }

    private Pane createItemPane(double x, double y, String text, String imagePath) {
        Pane pane = new Pane();
        pane.setLayoutX(x);
        pane.setLayoutY(y);
        pane.setPrefSize(140, 140);
        pane.setStyle("-fx-background-color: #000000;");

        Text label = new Text(text);
        label.setLayoutX(15);
        label.setLayoutY(60);
        label.setFill(Color.WHITE);
        label.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 28));
        label.setStyle("-fx-text-fill: white;");

        pane.getProperties().put("label", label);

        ImageView imageView = createImageView(imagePath, 20, 20);

        pane.getProperties().put("imageView", imageView);

        pane.getChildren().addAll(label, imageView);
        return pane;
    }

    private ImageView createImageView(String path, double x, double y) {
        ImageView imageView = new ImageView(ResourceManager.getImage(path));
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        return imageView;
    }

    private Text createLabel(String text, double x, double y) {
        Text label = new Text(text);
        label.setLayoutX(x);
        label.setLayoutY(y);
        label.setFill(Color.WHITE);
        label.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 24));
        return label;
    }

    public void updateItemPaneText(Pane pane, String newText) {
        if (pane.getProperties().containsKey("label")) {
            Text label = (Text) pane.getProperties().get("label");
            label.setText(newText);
        }
    }

    public void updateItemPaneImage(Pane pane, String newImagePath) {
        if (pane.getProperties().containsKey("imageView")) {
            ImageView imageView = (ImageView) pane.getProperties().get("imageView");

            // Ensure ResourceManager.getImage() returns an Image object, not a path
            Image newImage = ResourceManager.getImage(newImagePath);

            // Now set the image directly without wrapping it in 'new Image()'
            imageView.setImage(newImage);
        }
    }

    @Override
    public void onSceneEnter() {

    }

    @Override
    public void onSceneLeave() {

    }
}
