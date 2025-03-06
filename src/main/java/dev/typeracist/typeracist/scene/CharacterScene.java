package dev.typeracist.typeracist.scene;

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
    private ThemedButton.RadioButtonGroup difficultyGroup;
    private ThemedButton.RadioButtonGroup characterGroup;

    public CharacterScene(double width, double height) {
        super(new VBox(), width, height);
        VBox root = (VBox) getRoot();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #484848;");

        HBox topContainer = createTopContainer();

        VBox centerContainer = createCenterContainer();

        warningLabel = new Label("");
        warningLabel.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 18));
        warningLabel.setTextFill(Color.RED);

        Region topSpacer = new Region();
        Region bottomSpacer = new Region();
        VBox.setVgrow(topSpacer, Priority.ALWAYS);
        VBox.setVgrow(bottomSpacer, Priority.ALWAYS);

        root.getChildren().addAll(topContainer, topSpacer, centerContainer, bottomSpacer, warningLabel);
    }

    private HBox createTopContainer() {
        HBox topContainer = new HBox();
        topContainer.setAlignment(Pos.TOP_RIGHT);
        topContainer.setPadding(new Insets(10));

        Region topSpacer = new Region();
        HBox.setHgrow(topSpacer, Priority.ALWAYS);

        ThemedButton homeButton = new ThemedButton("Home");
        homeButton.setOnAction(event -> GameLogic.getInstance().getSceneManager().setScene(SceneName.MAIN));

        topContainer.getChildren().add(homeButton);

        return topContainer;

    }

    private VBox createCenterContainer() {
        VBox centerContainer = new VBox();
        centerContainer.setAlignment(Pos.CENTER);
        centerContainer.setSpacing(20);

        Label titleLabel = new Label("Choose Your Character");
        titleLabel.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 36));
        titleLabel.setTextFill(Color.WHITE);

        // Character selection area
        HBox characterSelection = new HBox(10);
        characterSelection.setAlignment(Pos.CENTER);

        // Create a radio button group for characters
        characterGroup = new ThemedButton.RadioButtonGroup();

        for (Character character : new Character[] { new Warrior(), new Archer(), new Wizard(), new Assassin(),
                new Wretch() }) {
            ThemedButton characterButton = createCharacterButton(character);
            characterSelection.getChildren().add(characterButton);
        }

        characterInfoLabel = new Label("");
        characterInfoLabel.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 18));
        characterInfoLabel.setTextFill(Color.WHITE);

        // Name input field
        HBox nameBox = new HBox(10);
        nameBox.setAlignment(Pos.CENTER_LEFT);
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
        difficultyGroup = new ThemedButton.RadioButtonGroup();

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

        return centerContainer;
    }

    private ThemedButton createCharacterButton(Character character) {
        VBox characterContent = new VBox(5);
        characterContent.setAlignment(Pos.CENTER);
        characterContent.setPadding(new Insets(10));

        // Character Image
        ImageView characterView = new ImageView(character.getImage());
        characterView.setFitWidth(200);
        characterView.setFitHeight(200);

        // Character Name Label
        Label characterLabel = new Label(character.getClass().getSimpleName());
        characterLabel.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 16));
        characterLabel.setTextFill(Color.WHITE);

        characterContent.getChildren().addAll(characterView, characterLabel);

        // Create ThemedButton with the character content
        ThemedButton characterButton = new ThemedButton("");
        characterButton.setGraphic(characterContent);
        characterButton.setMinWidth(220); // Account for padding
        characterButton.setMinHeight(260); // Account for image and label
        characterButton.setBaseColor(Color.DARKGRAY)
                .setHoverColor(Color.GRAY)
                .setPressedColor(Color.LIGHTGRAY)
                .setBorderColor(Color.WHITE);

        // Add to radio button group
        characterGroup.addButton(characterButton);

        // Selection event
        characterButton.setOnAction(event -> {
            selectedCharacter = character;
            warningLabel.setText(""); // Clear warning when character is selected

            // Update character info label
            String characterName = character.getClass().getSimpleName();
            String characterDescription = character.getDescription();
            characterInfoLabel.setText(characterName + " - " + characterDescription);
        });

        return characterButton;
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