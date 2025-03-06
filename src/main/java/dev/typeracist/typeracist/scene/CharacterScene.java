package dev.typeracist.typeracist.scene;

import java.util.HashMap;
import java.util.Map;

import dev.typeracist.typeracist.gui.gameScene.ConfirmationPane;
import dev.typeracist.typeracist.gui.global.ThemedButton;
import dev.typeracist.typeracist.logic.characters.entities.Character;
import dev.typeracist.typeracist.logic.characters.entities.character.Archer;
import dev.typeracist.typeracist.logic.characters.entities.character.Assassin;
import dev.typeracist.typeracist.logic.characters.entities.character.Warrior;
import dev.typeracist.typeracist.logic.characters.entities.character.Wizard;
import dev.typeracist.typeracist.logic.characters.entities.character.Wretch;
import dev.typeracist.typeracist.logic.global.GameLogic;
import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.utils.Difficulty;
import dev.typeracist.typeracist.utils.ResourceName;
import dev.typeracist.typeracist.utils.SceneName;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
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
        root.setStyle("-fx-background-color: #484848;");

        HBox topBar = new HBox();
        topBar.setAlignment(Pos.TOP_RIGHT);
        topBar.setPadding(new Insets(10));
        topBar.setPrefWidth(width);

        ThemedButton homeButton = new ThemedButton("Home");
        homeButton.setOnAction(event -> GameLogic.getInstance().getSceneManager().setScene(SceneName.MAIN));

        topBar.getChildren().add(homeButton);

        VBox centerContainer = new VBox();
        centerContainer.setAlignment(Pos.CENTER);
        centerContainer.setSpacing(20);

        Label titleLabel = new Label("Choose Your Character");
        titleLabel.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 36));
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
        characterInfoLabel.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 18));
        characterInfoLabel.setTextFill(Color.WHITE);

        for (Character character : characterData.keySet()) {
            // Character Image
            ImageView characterView = new ImageView(character.getImage());
            characterView.setFitWidth(200);
            characterView.setFitHeight(200);

            // Character Name Label
            Label characterLabel = new Label(characterData.get(character)[0]);
            characterLabel.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 16));
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
        nameLabel.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 18));
        nameLabel.setTextFill(Color.WHITE);

        TextField nameField = new TextField();
        nameBox.getChildren().addAll(nameLabel, nameField);

        // Difficulty selection buttons
        HBox difficultyBox = new HBox(10);
        difficultyBox.setAlignment(Pos.CENTER);

        Label difficultyLabel = new Label("Difficulty:");
        difficultyLabel.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 18));
        difficultyLabel.setTextFill(Color.WHITE);

        // Create a radio button group for difficulties
        ThemedButton.RadioButtonGroup difficultyGroup = new ThemedButton.RadioButtonGroup();

        for (Difficulty difficulty : Difficulty.values()) {
            ThemedButton difficultyButton = createStyledDifficultyButton(difficulty,
                    ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 18));

            // Add to radio button group
            difficultyGroup.addButton(difficultyButton);

            difficultyButton.setOnAction(e -> {
                selectedDifficulty = difficulty;
            });

            difficultyBox.getChildren().add(difficultyButton);
        }

        // Optionally, set a default selection (e.g., NORMAL)
        difficultyGroup.getButtons().stream()
                .filter(btn -> btn.getText().equals(Difficulty.NORMAL.getDisplayName()))
                .findFirst()
                .ifPresent(btn -> btn.setSelected(true));

        // Confirm Button
        ThemedButton confirmButton = new ThemedButton("Confirm");
        confirmButton.setOnAction(event -> {
            String playerName = nameField.getText().trim();

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

            ConfirmationPane confirmationPane = new ConfirmationPane(playerName, selectedCharacter, selectedDifficulty,
                    () -> {
                        GameLogic.getInstance().setPlayerName(playerName);
                        GameLogic.getInstance().setCurrentDifficulty(selectedDifficulty);
                        GameLogic.getInstance().setSelectedCharacter(selectedCharacter);

                        // Update map and navigate to it
                        ((MapScene) GameLogic.getInstance().getSceneManager().getScene(SceneName.MAP))
                                .updateNodeColors();
                        GameLogic.getInstance().getSceneManager().setScene(SceneName.MAP);
                    });

            GameLogic.getInstance().getSceneManager().showPopUp(confirmationPane, 750, 450);
        });

        centerContainer.getChildren().addAll(titleLabel, characterSelection, characterInfoLabel, nameBox, difficultyBox,
                confirmButton);

        warningLabel = new Label("");
        warningLabel.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 18));
        warningLabel.setTextFill(Color.RED);

        Region topSpacer = new Region();
        Region bottomSpacer = new Region();
        VBox.setVgrow(topSpacer, Priority.ALWAYS);
        VBox.setVgrow(bottomSpacer, Priority.ALWAYS);

        root.getChildren().addAll(topBar, topSpacer, centerContainer, bottomSpacer, warningLabel);
    }

    private ThemedButton createStyledDifficultyButton(Difficulty difficulty, Font font) {
        ThemedButton button = new ThemedButton(difficulty.getDisplayName());
        button.setFont(Font.font(font.getFamily(), 16)); // Set font size to 16

        // Set base colors based on difficulty
        switch (difficulty) {
            case EASY:
                button.setBorderColor(Color.LIGHTGREEN)
                        .setHoverColor(Color.GREEN)
                        .setPressedColor(Color.DARKGREEN);
                break;
            case NORMAL:
                button.setBorderColor(Color.YELLOW)
                        .setHoverColor(Color.GOLDENROD)
                        .setPressedColor(Color.DARKGOLDENROD);
                break;
            case HARD:
                button.setBorderColor(Color.RED)
                        .setHoverColor(Color.DARKRED)
                        .setPressedColor(Color.MAROON);
                break;
            case HELL:
                button.setBorderColor(Color.DARKRED)
                        .setHoverColor(Color.FIREBRICK)
                        .setPressedColor(Color.ORANGERED);
                break;
        }

        return button;
    }

    @Override
    public void onSceneEnter() {
    }

    @Override
    public void onSceneLeave() {

    }
}