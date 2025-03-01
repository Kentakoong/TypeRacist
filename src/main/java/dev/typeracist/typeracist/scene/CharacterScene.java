package dev.typeracist.typeracist.scene;

import dev.typeracist.typeracist.logic.global.GameLogic;
import dev.typeracist.typeracist.utils.SceneName;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class CharacterScene extends Scene {
    private String selectedCharacter; // Store selected character ID

    public CharacterScene(double width, double height) {
        super(new VBox(), width, height);

        VBox root = (VBox) getRoot();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);
        root.setPadding(new Insets(20));

        // Add title label
        Font baseFont = Font.loadFont(getClass().getResourceAsStream("/dev/typeracist/typeracist/fonts/DepartureMono-Regular.otf"), 36);
        Label titleLabel = new Label("Choose Your Character");
        titleLabel.setStyle("-fx-text-fill: black;");
        titleLabel.setFont(Font.font(baseFont.getName(), 36));

        // Character selection area
        HBox characterSelection = new HBox(10);
        characterSelection.setAlignment(Pos.CENTER);

        String warrior =  "/dev/typeracist/typeracist/image/character/warrior.png";
        String archer = "/dev/typeracist/typeracist/image/character/archer.png";
        String wizard =  "/dev/typeracist/typeracist/image/character/wizard.png";
        String assassin =  "/dev/typeracist/typeracist/image/character/assassin.png";
        String wretch  = "/dev/typeracist/typeracist/image/character/wretch.png" ;

        // Array of character image paths
        String[] characterImages = {
                warrior,
                archer,
                wizard,
                assassin,
                wretch
        };

        // Loop to create character images
        for (int i = 0; i < characterImages.length; i++) {
            String characterPath = characterImages[i]; // Create a final local variable
            Image characterImage = new Image(getClass().getResourceAsStream(characterPath)); // Fix image loading
            ImageView characterView = new ImageView(characterImage);
            characterView.setFitWidth(80);
            characterView.setFitHeight(80);

            characterView.setOnMouseClicked(event -> {
                selectedCharacter = characterPath; // Use the final local variable
                System.out.println("Selected character: " + selectedCharacter);
            });

            characterSelection.getChildren().add(characterView);
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
        confirmButton.setOnAction(event -> {
            if (selectedCharacter != null) {
                System.out.println("Character confirmed: " + selectedCharacter);
                GameLogic.getInstance().getSceneManager().setScene(SceneName.MAP);
            } else {
                System.out.println("Please select a character first!");
            }
        });

        root.getChildren().addAll(titleLabel, characterSelection, nameBox, difficultyBox, confirmButton);
    }
}
