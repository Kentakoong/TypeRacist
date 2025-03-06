package dev.typeracist.typeracist.scene;

import dev.typeracist.typeracist.gui.global.ThemedButton;
import dev.typeracist.typeracist.logic.global.GameLogic;
import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.utils.ResourceName;
import dev.typeracist.typeracist.utils.SceneName;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class MainScene extends BaseScene {
        public MainScene(double width, double height) {
                super(new VBox(), width, height);

                VBox root = (VBox) getRoot();
                root.setAlignment(Pos.CENTER);
                root.setSpacing(20);

                // Add title label
                Font baseFont = ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 36);
                Label titleLabel = new Label("TypeRacist");
                titleLabel.setStyle("-fx-text-fill: black;");
                titleLabel.setLayoutX(50);
                titleLabel.setLayoutY(10);
                titleLabel.setFont(Font.font(baseFont.getName(), 36));

                assert GameLogic.getInstance().getSceneManager().sceneExists(SceneName.CHARACTERS);
                assert GameLogic.getInstance().getSceneManager().sceneExists(SceneName.SETTINGS);

                VBox buttonBar = new VBox();
                buttonBar.setAlignment(Pos.CENTER);
                buttonBar.setSpacing(10);

                ThemedButton startButton = new ThemedButton("Start Game");
                startButton.setOnAction(
                                event -> GameLogic.getInstance().getSceneManager().setScene(SceneName.CHARACTERS));

                ThemedButton settingsButton = new ThemedButton("Settings");
                settingsButton.setOnAction(
                                event -> GameLogic.getInstance().getSceneManager().setScene(SceneName.SETTINGS));

                ThemedButton exitButton = new ThemedButton("Exit");
                exitButton.setOnAction(event -> GameLogic.getInstance().getSceneManager().closeStage());

                ThemedButton testSceneButton = new ThemedButton("Test Scene");
                testSceneButton
                                .setOnAction(event -> GameLogic.getInstance().getSceneManager()
                                                .setScene(SceneName.DYNAMIC_PANE_TEST));

                ThemedButton testKeyBoardPaneSceneButton = new ThemedButton("Keyboard Test Scene");
                testKeyBoardPaneSceneButton
                                .setOnAction(event -> GameLogic.getInstance().getSceneManager()
                                                .setScene(SceneName.KEYBOARD_TEST));

                ThemedButton testShopPaneSceneButton = new ThemedButton("Shop Test Scene");
                testShopPaneSceneButton
                                .setOnAction(event -> GameLogic.getInstance().getSceneManager()
                                                .setScene(SceneName.SHOP));

                ThemedButton testChestPaneSceneButton = new ThemedButton("Chest Test Scene");
                testChestPaneSceneButton
                                .setOnAction(event -> GameLogic.getInstance().getSceneManager()
                                                .setScene(SceneName.CHEST));

                ThemedButton testBattleScene = new ThemedButton("Battle Test Scene");
                testBattleScene.setOnAction(event -> {
                        try {
                                GameLogic.getInstance().getSceneManager().setScene(SceneName.BATTLE_TEST);
                        } catch (IllegalArgumentException e) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Error");
                                alert.setHeaderText("Scene is not loaded.");
                                alert.setContentText("Please wait for scene to load, try again in a few seconds.");
                                alert.showAndWait();
                        }
                });

                buttonBar.getChildren().addAll(startButton, settingsButton, exitButton, testSceneButton,
                                testKeyBoardPaneSceneButton, testBattleScene, testShopPaneSceneButton,
                                testChestPaneSceneButton);
                for (Node button : buttonBar.getChildren()) {
                        ((ThemedButton) button).setPrefWidth(300);
                }
                root.getChildren().addAll(titleLabel, buttonBar);
        }

        @Override
        public void onSceneEnter() {

        }

        @Override
        public void onSceneLeave() {

        }
}
