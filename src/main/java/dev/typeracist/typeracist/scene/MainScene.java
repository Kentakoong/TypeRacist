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

                buttonBar.getChildren().addAll(startButton, settingsButton, exitButton);
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
