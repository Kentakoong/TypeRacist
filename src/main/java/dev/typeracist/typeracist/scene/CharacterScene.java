package dev.typeracist.typeracist.scene;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import dev.typeracist.typeracist.gui.gameScene.ConfirmationPane;
import dev.typeracist.typeracist.logic.characters.entities.Character;
import dev.typeracist.typeracist.logic.characters.entities.character.Archer;
import dev.typeracist.typeracist.logic.characters.entities.character.Assassin;
import dev.typeracist.typeracist.logic.characters.entities.character.Warrior;
import dev.typeracist.typeracist.logic.characters.entities.character.Wizard;
import dev.typeracist.typeracist.logic.characters.entities.character.Wretch;
import dev.typeracist.typeracist.logic.global.GameLogic;
import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.utils.ResourceName;
import dev.typeracist.typeracist.utils.SceneName;
import dev.typeracist.typeracist.utils.Difficulty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class CharacterScene extends BaseScene {
    private Character selectedCharacter; // Store selected character ID
    private Label warningLabel; // Label to display warning message
    private Label characterInfoLabel; // Label to display character name & description
    private Difficulty selectedDifficulty = null;

    public CharacterScene(double width, double height) {
        super(new VBox(), width, height);
        VBox root = (VBox) getRoot();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);
        root.setPadding(new Insets(20));

        // set background to grey
        root.setBackground(new Background(new BackgroundFill(Color.web("#484848"), CornerRadii.EMPTY, Insets.EMPTY)));

        // Load font
        Font baseFont = ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 36);

        // Title Label
        Label titleLabel = new Label("Choose Your Character");
        titleLabel.setFont(baseFont);
        titleLabel.setTextFill(Color.WHITE);

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
        characterData.put(warrior, new String[] { "Warrior", "A brave fighter with strong melee attacks." +
                "\n" + "ATK : 4\n" +
                "DEF : 5\nABIL : NONE" });
        characterData.put(archer, new String[] { "Archer", "A skilled marksman with excellent range." +
                "\nATK : 4\n" +
                "DEF : 3\nABIL : 50% to do double damage" });
        characterData.put(wizard, new String[] { "Wizard", "A master of elemental magic and spells." +
                "\nATK : 3\n" +
                "DEF : 4\nABIL : Magic wand(item) stun enemy for 1 turn (usable every 3 turns)" });
        characterData.put(assassin, new String[] { "Assassin", "A stealthy character with high critical damage." +
                "\nATK : 5\n" +
                "DEF : 3\nABIL : 20% to dodge attack" });
        characterData.put(wretch, new String[] { "Wretch", "A mysterious wanderer with unknown abilities." +
                "who wants challenge)\n" +
                "ATK : 3\n" +
                "DEF : 3\nABIL : NONE" });

        Map<Character, VBox> characterFrames = new HashMap<>(); // Store frames for updating selection effect

        characterInfoLabel = new Label("");
        characterInfoLabel.setFont(Font.font(baseFont.getName(), 18));
        characterInfoLabel.setTextFill(Color.WHITE);

        for (Character character : characterData.keySet()) {
            // Character Image
            ImageView characterView = new ImageView(character.getImage());
            characterView.setFitWidth(80);
            characterView.setFitHeight(80);

            // Character Name Label
            Label characterLabel = new Label(characterData.get(character)[0]);
            characterLabel.setFont(Font.font(baseFont.getName(), 16));
            characterLabel.setTextFill(Color.BLACK);

            // Character Frame (VBox with border)
            VBox characterBox = new VBox(5, characterView, characterLabel);
            characterBox.setAlignment(Pos.CENTER);
            characterBox.setPadding(new Insets(10));
            characterBox.setBackground(
                    new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(10), Insets.EMPTY)));
            characterBox.setBorder(new Border(
                    new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, new CornerRadii(10), BorderWidths.DEFAULT)));

            // Store frame in the map
            characterFrames.put(character, characterBox);

            // Selection event (also works when clicking the background)
            characterBox.setOnMouseClicked(event -> {
                selectedCharacter = character;
                warningLabel.setText(""); // Clear warning when character is selected

                // Update character info label
                String characterName = characterData.get(character)[0];
                String characterDescription = characterData.get(character)[1];
                characterInfoLabel.setText(characterName + " - " + characterDescription);

                // Highlight selected character frame
                for (VBox frame : characterFrames.values()) {
                    frame.setStyle(
                            "-fx-border-color: gray; -fx-border-width: 2px; -fx-border-radius: 10px; -fx-background-color: white; -fx-background-radius: 10px;"); // Reset
                                                                                                                                                                  // others
                }
                characterBox.setStyle(
                        "-fx-border-color: gold; -fx-border-width: 3px; -fx-border-radius: 10px; -fx-background-color: lightyellow; -fx-background-radius: 10px;"); // Highlight
                                                                                                                                                                    // selected
            });

            // Hover effect
            characterBox.setOnMouseEntered(event -> {
                if (selectedCharacter != character) { // Only apply if not selected
                    characterBox.setStyle(
                            "-fx-border-color: blue; -fx-border-width: 2px; -fx-border-radius: 10px; -fx-background-color: lightgray; -fx-background-radius: 10px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0.5, 0, 3);");
                }
            });

            characterBox.setOnMouseExited(event -> {
                if (selectedCharacter != character) { // Only reset if not selected
                    characterBox.setStyle(
                            "-fx-border-color: gray; -fx-border-width: 2px; -fx-border-radius: 10px; -fx-background-color: white; -fx-background-radius: 10px;");
                }
            });

            characterSelection.getChildren().add(characterBox);
        }

        // Name input field
        HBox nameBox = new HBox(10);
        nameBox.setAlignment(Pos.CENTER);
        Label nameLabel = new Label("Name:");
        nameLabel.setFont(Font.font(baseFont.getName(), 18));
        nameLabel.setTextFill(Color.WHITE);

        TextField nameField = new TextField();
        nameBox.getChildren().addAll(nameLabel, nameField);

        // Difficulty selection buttons
        HBox difficultyBox = new HBox(10);
        difficultyBox.setAlignment(Pos.CENTER);
        Label difficultyLabel = new Label("Difficulty:");
        difficultyLabel.setFont(Font.font(baseFont.getName(), 18));
        difficultyLabel.setTextFill(Color.WHITE);

        Button easyButton = createStyledDifficultyButton("Easy", baseFont);
        Button normalButton = createStyledDifficultyButton("Normal", baseFont);
        Button hardButton = createStyledDifficultyButton("Hard", baseFont);
        Button hellButton = createStyledDifficultyButton("Hell", baseFont);

        easyButton.setOnAction(e -> selectDifficulty("Easy", easyButton, normalButton, hardButton, hellButton));
        normalButton.setOnAction(e -> selectDifficulty("Normal", easyButton, normalButton, hardButton, hellButton));
        hardButton.setOnAction(e -> selectDifficulty("Hard", easyButton, normalButton, hardButton, hellButton));
        hellButton.setOnAction(e -> selectDifficulty("Hell", easyButton, normalButton, hardButton, hellButton));

        difficultyBox.getChildren().addAll(difficultyLabel, easyButton, normalButton, hardButton, hellButton);

        // Confirm button

        // Confirm Button
        Button confirmButton = createStyledButton("Confirm", baseFont);
        confirmButton.setOnAction(event -> {
            String playerName = nameField.getText().trim();

            // Validate inputs
            if (selectedCharacter == null) {
                warningLabel.setText("Please select a character!");
                return;
            }
            if (playerName.isEmpty()) {
                warningLabel.setText("Please enter a name!");
                return;
            }
            if (selectedDifficulty == null) {
                warningLabel.setText("Please select a difficulty!");
                return;
            }


            ConfirmationPane confirmationPane = new ConfirmationPane(playerName, selectedCharacter, selectedDifficulty, () -> {
                GameLogic.getInstance().setPlayerName(playerName);
                GameLogic.getInstance().setCurrentDifficulty(selectedDifficulty);
                GameLogic.getInstance().setSelectedCharacter(selectedCharacter);

                // Update map and navigate to it
                ((MapScene) GameLogic.getInstance().getSceneManager().getScene(SceneName.MAP)).updateNodeColors();
                GameLogic.getInstance().getSceneManager().setScene(SceneName.MAP);
            });

            GameLogic.getInstance().getSceneManager().showPopUp(confirmationPane,500,300);
        });

        // Warning Label (Initially empty)
        warningLabel = new Label("");
        warningLabel.setFont(baseFont);
        warningLabel.setTextFill(Color.RED);

        root.getChildren().addAll(titleLabel, characterSelection, characterInfoLabel, nameBox, difficultyBox,
                confirmButton, warningLabel);

    }

    private Button createStyledButton(String text, Font font) {
        Button button = new Button(text);
        button.setFont(font);
        button.setTextFill(Color.WHITE);
        button.setBackground(new Background(new BackgroundFill(Color.DIMGRAY, new CornerRadii(5), Insets.EMPTY)));

        // Hover effect: Change background color when hovered
        button.setOnMouseEntered(event -> {
            button.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(5), Insets.EMPTY)));
            button.setTextFill(Color.BLACK);
        });

        button.setOnMouseExited(event -> {
            button.setBackground(new Background(new BackgroundFill(Color.DIMGRAY, new CornerRadii(5), Insets.EMPTY)));
            button.setTextFill(Color.WHITE);
        });

        return button;
    }

    private Button createStyledDifficultyButton(String text, Font font) {
        Button button = new Button(text);
        button.setFont(font);
        button.setTextFill(Color.WHITE);
        button.setBackground(new Background(new BackgroundFill(Color.DIMGRAY, new CornerRadii(5), Insets.EMPTY)));

        // Default shadow effect (matches confirm button)
        DropShadow defaultShadow = new DropShadow();
        defaultShadow.setColor(Color.BLACK);
        defaultShadow.setRadius(5);
        button.setEffect(defaultShadow);

        // Store original style
        final String defaultStyle = "-fx-background-color: dimgray; -fx-border-color: gold; -fx-border-width: 3px; -fx-border-radius: 5px; -fx-text-fill: white;";
        button.setStyle(defaultStyle);

        // Hover effect (only if not selected)
        button.setOnMouseEntered(event -> {
            if (!button.getStyle().contains("#FFD700")) { // Don't change color if it's selected
                if (text.equals("Easy")) {
                    button.setStyle(
                            "-fx-background-color: lightgreen; -fx-border-color: gold; -fx-border-width: 3px; -fx-border-radius: 5px;");
                } else if (text.equals("Normal")) {
                    button.setStyle(
                            "-fx-background-color: yellow; -fx-border-color: gold; -fx-border-width: 3px; -fx-border-radius: 5px;");
                } else if (text.equals("Hard")) {
                    button.setStyle(
                            "-fx-background-color: red; -fx-border-color: gold; -fx-border-width: 3px; -fx-border-radius: 5px;");
                } else if (text.equals("Hell")) {
                    button.setStyle(
                            "-fx-background-color: red; -fx-border-color: gold; -fx-border-width: 3px; -fx-border-radius: 5px;");

                    // Fire effect for Hell difficulty
                    DropShadow fireEffect = new DropShadow();
                    fireEffect.setColor(Color.ORANGERED);
                    fireEffect.setRadius(15);
                    fireEffect.setSpread(0.7);
                    button.setEffect(fireEffect);
                }
                button.setTextFill(Color.BLACK);
            }
        });

        // Reset to original if not selected
        button.setOnMouseExited(event -> {
            if (!button.getStyle().contains("#FFD700")) { // Keep color if selected
                button.setStyle(defaultStyle);
                button.setEffect(defaultShadow);
            }
            button.setTextFill(Color.WHITE);
        });

        return button;
    }

    // Method to set selected difficulty and update button styles
    private void selectDifficulty(String difficultyName, Button... buttons) {
        selectedDifficulty = Difficulty.fromDisplayName(difficultyName);
        for (Button button : buttons) {
            if (button.getText().equals(difficultyName)) {
                button.setStyle(
                        "-fx-background-color: #FFD700; -fx-border-color: gold; -fx-border-width: 3px; -fx-border-radius: 5px; -fx-text-fill: black;");
            } else {
                button.setStyle(
                        "-fx-background-color: dimgray; -fx-border-color: gold; -fx-border-width: 3px; -fx-border-radius: 5px; -fx-text-fill: white;");
            }
        }
    }

    @Override
    public void onSceneEnter() {
    }

    @Override
    public void onSceneLeave() {

    }
}