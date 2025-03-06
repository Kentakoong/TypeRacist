package dev.typeracist.typeracist.gui.game;

import dev.typeracist.typeracist.gui.global.ThemedButton;
import dev.typeracist.typeracist.logic.characters.Character;
import dev.typeracist.typeracist.logic.global.GameLogic;
import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.utils.Difficulty;
import dev.typeracist.typeracist.utils.ResourceName;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ConfirmationPane extends StackPane {
    public ConfirmationPane(String playerName, Character selectedCharacter, Difficulty selectedDifficulty,
            Runnable onConfirm) {
        // Set Background
        this.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); -fx-padding: 20px; -fx-background-radius: 10px;");

        VBox contentBox = new VBox(10);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setPadding(new Insets(20));

        // Character Image
        ImageView characterImageView = new ImageView(selectedCharacter.getImage());
        characterImageView.setFitWidth(150);
        characterImageView.setFitHeight(150);

        // Title Label
        Label titleLabel = new Label("Confirm Your Character");
        titleLabel.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 36)); // Set to baseFont
        titleLabel.setTextFill(Color.WHITE);

        // Content Label
        Label contentLabel = new Label(
                "Name: " + playerName + "\n" +
                        "Character: " + selectedCharacter.getName() + "\n" +
                        "Difficulty: " + selectedDifficulty.getDisplayName());
        contentLabel.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 18)); // Set to baseFont
        contentLabel.setTextFill(Color.WHITE);
        contentLabel.setAlignment(Pos.CENTER);

        // Buttons
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);

        ThemedButton okButton = new ThemedButton("OK");
        okButton.setOnAction(e -> {
            onConfirm.run(); // Perform the confirmation action
            this.setVisible(false);
            GameLogic.getInstance().getSceneManager().closePopUp();
        });
        ThemedButton cancelButton = new ThemedButton("Cancel");
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
