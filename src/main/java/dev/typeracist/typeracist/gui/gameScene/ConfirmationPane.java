package dev.typeracist.typeracist.gui.gameScene;

import dev.typeracist.typeracist.logic.global.GameLogic;
import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.utils.Difficulty;
import dev.typeracist.typeracist.utils.ResourceName;
import dev.typeracist.typeracist.logic.characters.entities.Character;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;


public class ConfirmationPane extends StackPane {
    public ConfirmationPane(String playerName, Character selectedCharacter, Difficulty selectedDifficulty, Runnable onConfirm) {
        // Load font
        Font baseFont = ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 18);
        Font baseFontTitle = ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 36);

        // Set Background
        this.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); -fx-padding: 20px; -fx-background-radius: 10px;");

        VBox contentBox = new VBox(10);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setPadding(new Insets(20));
        contentBox.setStyle("-fx-background-color: #484848; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        // Character Image
        ImageView characterImageView = new ImageView(selectedCharacter.getImage());
        characterImageView.setFitWidth(100);
        characterImageView.setFitHeight(100);

        // Title Label
        Label titleLabel = new Label("Confirm Your Character Choice");
        titleLabel.setFont(baseFontTitle);  // Set to baseFont
        titleLabel.setTextFill(Color.WHITE);


        // Content Label
        Label contentLabel = new Label(
                "Name: " + playerName + "\n" +
                        "Character: " + selectedCharacter.getName() + "\n" +
                        "Difficulty: " + selectedDifficulty.getDisplayName() + "\n\n" +
                        "Click OK to go to Arena Map");
        contentLabel.setFont(baseFont);  // Set to baseFont
        contentLabel.setTextFill(Color.WHITE);
        contentLabel.setWrapText(true);
        contentLabel.setAlignment(Pos.CENTER);

        // Buttons
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);

        Button okButton = new Button("OK");
        Button cancelButton = new Button("Cancel");

        okButton.setFont(baseFont);  // Set to baseFont
        okButton.setStyle("-fx-background-color: dimgray; -fx-text-fill: white; -fx-border-radius: 5; -fx-background-radius: 5;");
        okButton.setOnMouseEntered(e -> okButton.setStyle("-fx-background-color: lightgray;"));
        okButton.setOnMouseExited(e -> okButton.setStyle("-fx-background-color: dimgray;"));
        okButton.setOnAction(e -> {
            onConfirm.run(); // Perform the confirmation action
            this.setVisible(false);
            GameLogic.getInstance().getSceneManager().closePopUp();
        });

        cancelButton.setFont(baseFont);  // Set to baseFont
        cancelButton.setStyle("-fx-background-color: dimgray; -fx-text-fill: white; -fx-border-radius: 5; -fx-background-radius: 5;");
        cancelButton.setOnMouseEntered(e -> cancelButton.setStyle("-fx-background-color: lightgray;"));
        cancelButton.setOnMouseExited(e -> cancelButton.setStyle("-fx-background-color: dimgray;"));
        cancelButton.setOnAction(e -> {
            this.setVisible(false);
            GameLogic.getInstance().getSceneManager().closePopUp();
        });

        buttonBox.getChildren().addAll(okButton, cancelButton);

        // Add Elements
        contentBox.getChildren().addAll(titleLabel, characterImageView, contentLabel, buttonBox);
        this.getChildren().add(contentBox);
        this.setAlignment(Pos.CENTER);
    }
}


