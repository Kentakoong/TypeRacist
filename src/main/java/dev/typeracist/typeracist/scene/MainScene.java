package dev.typeracist.typeracist.scene;

import dev.typeracist.typeracist.logic.GameLogic;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MainScene extends Scene {
    public MainScene(double width, double height) {
        super(new VBox(), width, height);

        VBox root = (VBox) getRoot();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);

        Label title = new Label("TypeRacist");
        title.setFont(Font.font("JetBrainsMono NF", FontWeight.BOLD, 36));

        assert GameLogic.getInstance().getSceneManager().sceneExists("character-choosing");
        assert GameLogic.getInstance().getSceneManager().sceneExists("settings");

        VBox buttonBar = new VBox();
        buttonBar.setAlignment(Pos.CENTER);
        buttonBar.setSpacing(10);

        Button startButton = new Button("Start Game");
        startButton.setPrefWidth(200);
        startButton.setOnAction(event -> GameLogic.getInstance().getSceneManager().setScene("characters"));

        Button settingsButton = new Button("Settings");
        settingsButton.setPrefWidth(200);
        settingsButton.setOnAction(event -> GameLogic.getInstance().getSceneManager().setScene("settings"));

        Button exitButton = new Button("Exit");
        exitButton.setPrefWidth(200);
        exitButton.setOnAction(event -> GameLogic.getInstance().getSceneManager().closeStage());

        buttonBar.getChildren().addAll(startButton, settingsButton, exitButton);
        root.getChildren().addAll(title, buttonBar);
    }
}
