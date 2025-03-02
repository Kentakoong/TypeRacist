package dev.typeracist.typeracist.scene;

import dev.typeracist.typeracist.logic.global.GameLogic;
import dev.typeracist.typeracist.utils.SceneName;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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
        Font baseFont = Font.loadFont(KeyboardPaneSceneDemo.class.getResourceAsStream("/dev/typeracist/typeracist/fonts/DepartureMono-Regular.otf"), 36);
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

        Button startButton = new Button("Start Game");
        startButton.setPrefWidth(200);
        startButton.setOnAction(event -> GameLogic.getInstance().getSceneManager().setScene(SceneName.CHARACTERS));

        Button settingsButton = new Button("Settings");
        settingsButton.setPrefWidth(200);
        settingsButton.setOnAction(event -> GameLogic.getInstance().getSceneManager().setScene(SceneName.SETTINGS));

        Button exitButton = new Button("Exit");
        exitButton.setPrefWidth(200);
        exitButton.setOnAction(event -> GameLogic.getInstance().getSceneManager().closeStage());

        Button testSceneButton = new Button("Test Scene");
        testSceneButton.setPrefWidth(200);
        testSceneButton.setOnAction(event -> GameLogic.getInstance().getSceneManager().setScene(SceneName.DYNAMIC_PANE_TEST));


        Button testKeyBoardPaneSceneButton = new Button("Keyboard Test Scene");
        testKeyBoardPaneSceneButton.setPrefWidth(200);
        testKeyBoardPaneSceneButton.setOnAction(event -> GameLogic.getInstance().getSceneManager().setScene(SceneName.KEYBOARD_TEST));

        buttonBar.getChildren().addAll(startButton, settingsButton, exitButton, testSceneButton, testKeyBoardPaneSceneButton);
        root.getChildren().addAll(titleLabel, buttonBar);
    }

    @Override
    public void onSceneEnter() {

    }

    @Override
    public void onSceneLeave() {

    }
}
