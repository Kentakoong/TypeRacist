package dev.typeracist.typeracist.scene;

import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.utils.ResourceName;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class EnchantScene extends BaseScene{
    private final Pane root;

    public EnchantScene(double width, double height) {
        super(new Pane(), width, height);
        root = (Pane) getRoot();
        root.setStyle("-fx-background-color: #484848;");

        Text title = new Text("ENCHANT");
        title.setLayoutX(350);
        title.setLayoutY(60);
        title.setFill(Color.WHITE);
        title.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO,48));

        ImageView enchantIcon = new ImageView(ResourceManager.getImage(ResourceName.IMAGE_MAP_BOOK));
        enchantIcon.setFitWidth(75);
        enchantIcon.setFitHeight(75);
        enchantIcon.setLayoutX(575);
        enchantIcon.setLayoutY(0);
        root.getChildren().add(enchantIcon);

        Button returnButton = new Button("Return to map");
        returnButton.setLayoutX(800);
        returnButton.setLayoutY(675);
        returnButton.setPrefWidth(150);
        returnButton.setPrefHeight(40);
//      returnButton.setOnAction(event -> SceneManager.setScene(SceneName.MAP));

        Pane weaponPane = createItemPane(5, 5, "CHOOSE\nWEAPON", ResourceName.IMAGE_ENCHANT_WEAPON);
        Pane scrollPane = createItemPane1(170, 5, "CHOOSE\nSCROLL", ResourceName.IMAGE_ENCHANT_SCROLL);
        Pane emptyPane = new Pane();
        emptyPane.setLayoutX(655);
        emptyPane.setLayoutY(5);
        emptyPane.setPrefSize(140, 140);
        emptyPane.setStyle("-fx-background-color: #000000;");

        ImageView enchantedWeapon = new ImageView(ResourceManager.getImage(ResourceName.IMAGE_ENCHANT_ENCHANTED_WEAPON));
        enchantedWeapon.setFitHeight(100);
        enchantedWeapon.setFitWidth(100);
        enchantedWeapon.setLayoutX(20);
        enchantedWeapon.setLayoutY(20);
        emptyPane.getChildren().add(enchantedWeapon);

        ImageView image1 = createImageView(ResourceName.IMAGE_ENCHANT_PLUS, 170, 24);
        ImageView image2 = createImageView(ResourceName.IMAGE_ENCHANT_TO, 490, 24);

        Pane itemSelectionPane = new Pane();
        itemSelectionPane.setLayoutX(95);
        itemSelectionPane.setLayoutY(90);
        itemSelectionPane.setPrefSize(800, 150);
        itemSelectionPane.setStyle("-fx-background-color: #ABABAB;");
        itemSelectionPane.getChildren().addAll(weaponPane, scrollPane, image1, image2, emptyPane);

        Pane weaponAndScrollSelectionPane = new Pane();
        weaponAndScrollSelectionPane.setLayoutX(125);
        weaponAndScrollSelectionPane.setLayoutY(275);
        weaponAndScrollSelectionPane.setPrefSize(735, 300);
        weaponAndScrollSelectionPane.setStyle("-fx-background-color: #D9D9D9;");
        root.getChildren().add(weaponAndScrollSelectionPane);

        Pane requirementsPane = new Pane();
        requirementsPane.setLayoutX(145);
        requirementsPane.setLayoutY(400);
        requirementsPane.setPrefSize(500, 50);
        requirementsPane.setStyle("-fx-background-color: #949494;");
        Text reqText = new Text("Required : 100 Golds, LVL 5");
        reqText.setLayoutX(10);
        reqText.setLayoutY(32);
        reqText.setFill(Color.WHITE);
        reqText.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO,24));
        requirementsPane.getChildren().add(reqText);

        Button enchantButton = new Button("Enchant");
        enchantButton.setLayoutX(660);
        enchantButton.setLayoutY(405);
        enchantButton.setPrefWidth(150);
        enchantButton.setPrefHeight(40);

        Text fromText = createLabel("FROM", 285, 300);
        Text toText = createLabel("TO", 675, 300);
        Text atkFrom = createLabel("ATK : 4   ABIL : NONE", 150, 350);
        Text atkTo = createLabel("ATK : 6   ABIL : LIGHT", 525, 350);

        root.getChildren().addAll(title, returnButton, itemSelectionPane, requirementsPane, enchantButton, fromText, toText, atkFrom, atkTo);

        Button testImageButton = new Button("Test Complete");
        testImageButton.setLayoutX(200);
        testImageButton.setLayoutY(675);
        testImageButton.setPrefWidth(150);
        testImageButton.setPrefHeight(40);
        testImageButton.setOnAction(event -> {
            updateItemPaneText(weaponPane, "");
            updateItemPaneText(scrollPane, "");
            updateItemPaneImage(weaponPane,ResourceName.IMAGE_ENCHANT_WEAPON);
            updateItemPaneImage(scrollPane,ResourceName.IMAGE_ENCHANT_SCROLL);
            weaponAndScrollSelectionPane.setVisible(false);
            fromText.setVisible(true);
            toText.setVisible(true);
            atkFrom.setVisible(true);
            atkTo.setVisible(true);
            enchantedWeapon.setVisible(true);
            requirementsPane.setVisible(true);
            enchantButton.setVisible(true);
        });
        root.getChildren().add(testImageButton);

        Button testTextButton = new Button("Test Incomplete");
        testTextButton.setLayoutX(400);
        testTextButton.setLayoutY(675);
        testTextButton.setPrefWidth(150);
        testTextButton.setPrefHeight(40);
        testTextButton.setOnAction(event -> {
            updateItemPaneImage(weaponPane,ResourceName.IMAGE_ENCHANT_SPACE);
            updateItemPaneImage(scrollPane,ResourceName.IMAGE_ENCHANT_SPACE);
            updateItemPaneText(weaponPane, "CHOOSE\nWEAPON");
            updateItemPaneText(scrollPane, "CHOOSE\nSCROLL");
            weaponAndScrollSelectionPane.setVisible(true);
            fromText.setVisible(false);
            toText.setVisible(false);
            atkFrom.setVisible(false);
            atkTo.setVisible(false);
            enchantedWeapon.setVisible(false);
            requirementsPane.setVisible(false);
            enchantButton.setVisible(false);
        });
        root.getChildren().add(testTextButton);

        fromText.setVisible(false);
        toText.setVisible(false);
        atkFrom.setVisible(false);
        atkTo.setVisible(false);
        requirementsPane.setVisible(false);
        enchantButton.setVisible(false);
        enchantedWeapon.setVisible(false);
        updateItemPaneImage(weaponPane,ResourceName.IMAGE_ENCHANT_SPACE);
        updateItemPaneImage(scrollPane,ResourceName.IMAGE_ENCHANT_SPACE);
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
        label.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO,28));
        label.setStyle("-fx-text-fill: white;");

        pane.getProperties().put("label", label);

        ImageView imageView = createImageView(imagePath, 20, 20);

        pane.getProperties().put("imageView", imageView);

        pane.getChildren().addAll(label, imageView);
        return pane;
    }

    private Pane createItemPane1(double x, double y, String text, String imagePath) {
        Pane pane = new Pane();
        pane.setLayoutX(x+125);
        pane.setLayoutY(y);
        pane.setPrefSize(140, 140);
        pane.setStyle("-fx-background-color: #000000;");

        Text label = new Text(text);
        label.setLayoutX(15);
        label.setLayoutY(60);
        label.setFill(Color.WHITE);
        label.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO,28));
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
        label.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO,24));
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
