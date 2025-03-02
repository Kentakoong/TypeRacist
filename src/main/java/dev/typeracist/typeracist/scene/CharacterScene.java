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

import java.util.HashMap;
import java.util.Map;

public class CharacterScene extends Scene {
    private String selectedCharacter; // Store selected character ID
    private Label warningLabel; // Label to display warning message
    private Label characterInfoLabel; // Label to display character name & description
    private String selectedDifficulty = null;
    public CharacterScene(double width, double height) {
        super(new VBox(), width, height);

        VBox root = (VBox) getRoot();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);
        root.setPadding(new Insets(20));

        // Load font
        Font baseFont = Font.loadFont(getClass().getResourceAsStream("/dev/typeracist/typeracist/fonts/DepartureMono-Regular.otf"), 36);

        // Title Label
        Label titleLabel = new Label("Choose Your Character");
        titleLabel.setStyle("-fx-text-fill: black;");
        titleLabel.setFont(Font.font(baseFont.getName(), 36));

        // Character selection area
        HBox characterSelection = new HBox(10);
        characterSelection.setAlignment(Pos.CENTER);

        // Character Image Paths
        String warrior = "/dev/typeracist/typeracist/image/character/warrior.png";
        String archer = "/dev/typeracist/typeracist/image/character/archer.png";
        String wizard = "/dev/typeracist/typeracist/image/character/wizard.png";
        String assassin = "/dev/typeracist/typeracist/image/character/assassin.png";
        String wretch = "/dev/typeracist/typeracist/image/character/wretch.png";

        // Map character images to names and descriptions
        Map<String, String[]> characterData = new HashMap<>();
        characterData.put(warrior, new String[]{"Warrior", "A brave fighter with strong melee attacks.\nATK : 4\nDEF : 5\nABIL : NONE"});
        characterData.put(archer, new String[]{"Archer", "A skilled marksman with excellent range.\nATK : 4\nDEF : 3\nABIL : 50% to do double damage"});
        characterData.put(wizard, new String[]{"Wizard", "A master of elemental magic and spells.\nATK : 3\nDEF : 4\nABIL : Magic wand(item) stun enemy for 1 turn (usable every 3 turns)"});
        characterData.put(assassin, new String[]{"Assassin", "A stealthy character with high critical damage.\nATK : 5\nDEF : 3\nABIL : 20% to dodge attack"});
        characterData.put(wretch, new String[]{"Wretch", "A mysterious wanderer with unknown abilities.\nATK : 3\nDEF : 3\nABIL : NONE"});

        // Label to display selected character info
        characterInfoLabel = new Label("");
        characterInfoLabel.setFont(Font.font(baseFont.getName(), 18));
        characterInfoLabel.setStyle("-fx-text-fill: black;");

        for (String characterPath : characterData.keySet()) {
            Image characterImage = new Image(getClass().getResourceAsStream(characterPath));
            ImageView characterView = new ImageView(characterImage);
            characterView.setFitWidth(80);
            characterView.setFitHeight(80);

            characterView.setOnMouseClicked(event -> {
                selectedCharacter = characterPath;
                System.out.println("Selected character: " + selectedCharacter);
                warningLabel.setText(""); // Clear warning when character is selected

                // Update character info label
                String characterName = characterData.get(characterPath)[0];
                String characterDescription = characterData.get(characterPath)[1];
                characterInfoLabel.setText(characterName + " - " + characterDescription);
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

        // Add event listeners for difficulty buttons
        easyButton.setOnAction(e -> selectDifficulty("Easy", easyButton, normalButton, hardButton, hellButton));
        normalButton.setOnAction(e -> selectDifficulty("Normal", easyButton, normalButton, hardButton, hellButton));
        hardButton.setOnAction(e -> selectDifficulty("Hard", easyButton, normalButton, hardButton, hellButton));
        hellButton.setOnAction(e -> selectDifficulty("Hell", easyButton, normalButton, hardButton, hellButton));

        difficultyBox.getChildren().addAll(difficultyLabel, easyButton, normalButton, hardButton, hellButton);

        // Confirm button
        Button confirmButton = new Button("Confirm");
        confirmButton.setOnAction(event -> {
            String playerName = nameField.getText().trim();

            // Validate inputs
            if (selectedCharacter == null) {
                warningLabel.setText("Please select a character!");
                warningLabel.setStyle("-fx-text-fill: red;");
                return;
            }
            if (playerName.isEmpty()) {
                warningLabel.setText("Please enter a name!");
                warningLabel.setStyle("-fx-text-fill: red;");
                return;
            }
            if (selectedDifficulty == null) {
                warningLabel.setText("Please select a difficulty!");
                warningLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            // If all inputs are valid, proceed to MapScene
            System.out.println("Character confirmed: " + selectedCharacter);
            GameLogic.getInstance().setSelectedCharacter(selectedCharacter);
            ((MapScene) GameLogic.getInstance().getSceneManager().getScene(SceneName.MAP)).updateNodeColors();
            GameLogic.getInstance().getSceneManager().setScene(SceneName.MAP);
            System.out.println("Selected Character: " + GameLogic.getInstance().getSelectedCharacter());
        });

        // Warning Label (Initially empty)
        warningLabel = new Label("");
        warningLabel.setFont(Font.font(baseFont.getName(), 24));
        warningLabel.setStyle("-fx-text-fill: red;");

        root.getChildren().addAll(titleLabel, characterSelection, characterInfoLabel, nameBox, difficultyBox, confirmButton, warningLabel);
    }

    // Method to set selected difficulty and update button styles
    private void selectDifficulty(String difficulty, Button... buttons) {
        selectedDifficulty = difficulty;
        for (Button button : buttons) {
            if (button.getText().equals(difficulty)) {
                button.setStyle("-fx-background-color: #FFD700;"); // Highlight selected
            } else {
                button.setStyle("-fx-background-color: transparent;"); // Reset others
            }
        }
    }
}
