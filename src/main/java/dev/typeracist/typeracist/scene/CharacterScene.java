package dev.typeracist.typeracist.scene;

import dev.typeracist.typeracist.logic.characters.Character;
import dev.typeracist.typeracist.logic.characters.*;
import dev.typeracist.typeracist.logic.global.GameLogic;
import dev.typeracist.typeracist.utils.SceneName;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.HashMap;
import java.util.Map;

public class CharacterScene extends BaseScene {
    private Character selectedCharacter; // Store selected character ID
    private Label warningLabel; // Label to display warning message
    private Label characterInfoLabel; // Label to display character name & description

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
        Character warrior = new Warrior();
        Character archer = new Archer();
        Character wizard = new Wizard();
        Character assassin = new Assassin();
        Character wretch = new Wretch();

        // Map character images to names and descriptions
        Map<Character, String[]> characterData = new HashMap<>();
        characterData.put(warrior, new String[]{"Warrior", "A brave fighter with strong melee attacks." +
                "\n" + "ATK : 4\n" +
                "DEF : 5\nABIL : NONE"});
        characterData.put(archer, new String[]{"Archer", "A skilled marksman with excellent range." +
                "\nATK : 4\n" +
                "DEF : 3\nABIL : 50% to do double damage"});
        characterData.put(wizard, new String[]{"Wizard", "A master of elemental magic and spells." +
                "\nATK : 3\n" +
                "DEF : 4\nABIL : Magic wand(item) stun enemy for 1 turn (usable every 3 turns)"});
        characterData.put(assassin, new String[]{"Assassin", "A stealthy character with high critical damage." +
                "\nATK : 5\n" +
                "DEF : 3\nABIL : 20% to dodge attack"});
        characterData.put(wretch, new String[]{"Wretch", "A mysterious wanderer with unknown abilities." +
                "who wants challenge)\n" +
                "ATK : 3\n" +
                "DEF : 3\nABIL : NONE"});

        // Label to display selected character info
        characterInfoLabel = new Label("");
        characterInfoLabel.setFont(Font.font(baseFont.getName(), 18));
        characterInfoLabel.setStyle("-fx-text-fill: black;");

        for (Character character : characterData.keySet()) {
            ImageView characterView = new ImageView(character.getImage());
            characterView.setFitWidth(80);
            characterView.setFitHeight(80);

            characterView.setOnMouseClicked(event -> {
                selectedCharacter = character;
                System.out.println("Selected character: " + selectedCharacter);
                warningLabel.setText(""); // Clear warning when character is selected

                // Update character info label
                String characterName = characterData.get(character)[0];
                String characterDescription = characterData.get(character)[1];
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
        difficultyBox.getChildren().addAll(difficultyLabel, easyButton, normalButton, hardButton, hellButton);

        // Confirm button
        Button confirmButton = new Button("Confirm");
        confirmButton.setOnAction(event -> {

            if (selectedCharacter != null) {
                System.out.println("Character confirmed: " + selectedCharacter);
                //set select character
                GameLogic.getInstance().setSelectedCharacter(selectedCharacter);
                System.out.println("Select THIS!! :" + GameLogic.getInstance().getSelectedCharacter());
                GameLogic.getInstance().getSceneManager().setScene(SceneName.MAP);

            } else {
                // Show warning message in red
                warningLabel.setText("Please select a character first!");
                warningLabel.setStyle("-fx-text-fill: red;");
            }
        });

        // Warning Label (Initially empty)
        warningLabel = new Label("");
        warningLabel.setFont(Font.font(baseFont.getName(), 24));
        warningLabel.setStyle("-fx-text-fill: red;");

        root.getChildren().addAll(titleLabel, characterSelection, characterInfoLabel, nameBox, difficultyBox, confirmButton, warningLabel);
    }

    @Override
    public void onSceneEnter() {

    }

    @Override
    public void onSceneLeave() {

    }
}
