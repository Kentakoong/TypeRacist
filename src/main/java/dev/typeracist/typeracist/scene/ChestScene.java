package dev.typeracist.typeracist.scene;

import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.utils.ResourceName;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class ChestScene extends BaseScene {
    private final Pane root;

    public ChestScene(double width, double height) {
        super(new Pane(), width, height);
        root = (Pane) getRoot();
        root.setStyle("-fx-background-color: #484848;");

        Text title = new Text("CHEST LOOT");
        title.setLayoutX(350);
        title.setLayoutY(60);
        title.setFill(Color.WHITE);
        title.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 48));

        Pane rewardPane = new Pane();
        rewardPane.setLayoutX(265);
        rewardPane.setLayoutY(150);
        rewardPane.setPrefSize(465, 500);
        rewardPane.setStyle("-fx-background-color: #C4C4C4;");
        rewardPane.setVisible(false);

        Pane rewardBackgroundPane = new Pane();
        rewardBackgroundPane.setLayoutX(10);
        rewardBackgroundPane.setLayoutY(10);
        rewardBackgroundPane.setPrefSize(445, 480);
        rewardBackgroundPane.setStyle("-fx-background-color: #949292;");

        Label rewardHeader = new Label("- You got -");
        rewardHeader.setLayoutX(105);
        rewardHeader.setLayoutY(15);
        rewardHeader.setStyle("-fx-text-fill: black;");
        rewardHeader.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 36));

        Text rewardText = new Text("- Healing Potion\n- Netherite Sword\n- Pendant of Vitality");
        rewardText.setLayoutX(20);
        rewardText.setLayoutY(100);
        rewardText.setFill(Color.WHITE);
        rewardText.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 24));

        Button confirmLootButton = new Button("Confirm");
        confirmLootButton.setLayoutX(160);
        confirmLootButton.setLayoutY(425);
        confirmLootButton.setPrefWidth(150);
        confirmLootButton.setPrefHeight(40);


        rewardPane.getChildren().addAll(rewardBackgroundPane, rewardHeader, confirmLootButton, rewardText);

        ImageView closeChestIcon = new ImageView(ResourceManager.getImage(ResourceName.IMAGE_CHEST_CLOSE));
        closeChestIcon.setFitWidth(450);
        closeChestIcon.setFitHeight(450);
        closeChestIcon.setLayoutX(275);
        closeChestIcon.setLayoutY(100);

        ImageView openedChestIcon = new ImageView(ResourceManager.getImage(ResourceName.IMAGE_CHEST_OPENED));
        openedChestIcon.setFitWidth(450);
        openedChestIcon.setFitHeight(450);
        openedChestIcon.setLayoutX(275);
        openedChestIcon.setLayoutY(100);
        openedChestIcon.setVisible(false);

        Button openButton = new Button("OPEN");
        openButton.setLayoutX(425);
        openButton.setLayoutY(525);
        openButton.setPrefWidth(150);
        openButton.setPrefHeight(40);
        openButton.setOnAction(event -> {
            closeChestIcon.setVisible(false);
            openedChestIcon.setVisible(true);
            rewardPane.setVisible(true);
            openButton.setLayoutY(565);
            openButton.setText("OPENED");
            openButton.setDisable(true);
        });

        confirmLootButton.setOnAction(event -> rewardPane.setVisible(false));

        Button returnButton = new Button("Return to map");
        returnButton.setLayoutX(800);
        returnButton.setLayoutY(675);
        returnButton.setPrefWidth(150);
        returnButton.setPrefHeight(40);
//      returnButton.setOnAction(event -> SceneManager.setScene(SceneName.MAP));

        root.getChildren().addAll(title, closeChestIcon, openedChestIcon, openButton, returnButton, rewardPane);
    }

    @Override
    public void onSceneEnter() {

    }

    @Override
    public void onSceneLeave() {

    }
}
