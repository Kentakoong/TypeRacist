package dev.typeracist.typeracist.scene;

import dev.typeracist.typeracist.logic.global.GameLogic;
import dev.typeracist.typeracist.utils.SceneName;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class CharacterScene extends Scene {
    public CharacterScene(double width, double height) {
        super(new VBox(), width, height);

        VBox root = (VBox) getRoot();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);
        root.setPadding(new Insets(20));

        Label title = new Label("Choose your Character");
        title.setFont(Font.font("JetBrainsMono NF", FontWeight.BOLD, 24));

        // Character selection area
        HBox characterSelection = new HBox(10);
        characterSelection.setAlignment(Pos.CENTER);
        for (int i = 0; i < 5; i++) {
            Rectangle characterSlot = new Rectangle(80, 80);
            characterSlot.setStyle("-fx-fill: lightgray; -fx-stroke: black;");
            characterSelection.getChildren().add(characterSlot);
        }

        // Name input field
        HBox nameBox = new HBox(10);
        nameBox.setAlignment(Pos.CENTER);
        Label nameLabel = new Label("Name :");
        TextField nameField = new TextField();
        nameBox.getChildren().addAll(nameLabel, nameField);

        // Difficulty selection buttons
        HBox difficultyBox = new HBox(10);
        difficultyBox.setAlignment(Pos.CENTER);
        Label difficultyLabel = new Label("Difficulty :");
        Button easyButton = new Button("Easy");
        Button normalButton = new Button("Normal");
        Button hardButton = new Button("Hard");
        Button hellButton = new Button("Hell");
        difficultyBox.getChildren().addAll(difficultyLabel, easyButton, normalButton, hardButton, hellButton);

        // Confirm button
        Button confirmButton = new Button("Confirm");
        confirmButton.setOnAction(event -> GameLogic.getInstance().getSceneManager().setScene(SceneName.MAP));

        root.getChildren().addAll(title, characterSelection, nameBox, difficultyBox, confirmButton);
    }
}
