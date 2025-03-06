package dev.typeracist.typeracist.scene;

import dev.typeracist.typeracist.logic.global.GameLogic;
import dev.typeracist.typeracist.logic.global.MusicPlayer;
import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.logic.global.SaveManager;
import dev.typeracist.typeracist.utils.ResourceName;
import dev.typeracist.typeracist.utils.SceneName;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class SettingScene extends BaseScene {
    private static final int DEFAULT_VOLUME = 3;
    private final VBox volumeBarContainer;
    private final Rectangle[] volumeBars;
    private final Label volumeLabel;

    public SettingScene(double width, double height) {
        super(new AnchorPane(), width, height);

        // Initialize final fields
        volumeLabel = new Label("Volume: 3");
        volumeBarContainer = new VBox(5);
        volumeBars = new Rectangle[5];

        AnchorPane root = (AnchorPane) getRoot();

        // Create main content container
        VBox contentContainer = new VBox();
        contentContainer.setAlignment(Pos.CENTER);
        contentContainer.setSpacing(20);
        contentContainer.setPrefWidth(width);

        // Set the content container to fill the AnchorPane
        AnchorPane.setTopAnchor(contentContainer, 0.0);
        AnchorPane.setLeftAnchor(contentContainer, 0.0);
        AnchorPane.setRightAnchor(contentContainer, 0.0);
        AnchorPane.setBottomAnchor(contentContainer, 0.0);

        // Home Button - now can be positioned absolutely
        Button homeButton = new Button("Home");
        homeButton.setFont(Font.font(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 18).getName(), 18));
        homeButton.setOnAction(event -> {
            GameLogic.getInstance().getSceneManager().setScene(SceneName.MAIN);
        });

        // Position the home button at the top right
        AnchorPane.setTopAnchor(homeButton, 10.0);
        AnchorPane.setRightAnchor(homeButton, 10.0);

        // Title
        Label title = new Label("Settings");
        title.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 36));

        // Settings Table
        VBox settingsTable = new VBox();
        settingsTable.setAlignment(Pos.CENTER);
        settingsTable.setSpacing(20);

        Button expButton = new Button("Add 1000 EXP");
        expButton.setOnAction(e -> {
            GameLogic.getInstance().getSelectedCharacter().getXp().gainXP(1000);
            GameLogic.getInstance().getSceneManager().showBreadcrumb(
                    "Add 1000 EXP",
                    "",
                    1000
            );
        });

        Button coinButton = new Button("Add 1000 coins");
        coinButton.setOnAction(e -> {
            GameLogic.getInstance().getSelectedCharacter().gainCoin(1000);
            GameLogic.getInstance().getSceneManager().showBreadcrumb(
                    "Add 1000 Coins",
                    "",
                    1000
            );
        });


        settingsTable.getChildren().add(0, createVolumeSection());
        settingsTable.getChildren().add(createResetSettingsSection());
        settingsTable.getChildren().add(coinButton);
        settingsTable.getChildren().add(expButton);

        // Add everything to the content container
        contentContainer.getChildren().addAll(title, settingsTable);

        // Add both the home button and content container to the root AnchorPane
        root.getChildren().addAll(contentContainer, homeButton);

        // Initialize volume bars based on current volume
        updateVolumeDisplay();
    }

    private VBox createVolumeSection() {
        // Volume Control Section
        VBox volumeSection = new VBox(10);
        volumeSection.setAlignment(Pos.CENTER);

        // Configure Volume Label
        volumeLabel.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 20));

        // Configure Volume Bar Container
        volumeBarContainer.setAlignment(Pos.CENTER);

        // Create volume control buttons
        HBox volumeControlButtons = new HBox(20);
        volumeControlButtons.setAlignment(Pos.CENTER);

        Button decreaseButton = new Button("-");
        decreaseButton.setOnAction(e -> decreaseVolume());

        Button increaseButton = new Button("+");
        increaseButton.setOnAction(e -> increaseVolume());

        volumeControlButtons.getChildren().addAll(decreaseButton, increaseButton);

        // Create volume bars
        HBox volumeBarsBox = new HBox(5);
        volumeBarsBox.setAlignment(Pos.CENTER);
        volumeBarsBox.setPadding(new Insets(10));

        for (int i = 0; i < 5; i++) {
            Rectangle bar = new Rectangle(20, 10 + (i * 5));
            bar.setFill(Color.GRAY);

            StackPane barContainer = new StackPane(bar);
            barContainer.setAlignment(Pos.BOTTOM_CENTER);
            barContainer.setMinHeight(40);

            volumeBarsBox.getChildren().add(barContainer);
            volumeBars[i] = bar;
        }

        volumeSection.getChildren().addAll(volumeLabel, volumeBarsBox, volumeControlButtons);

        return volumeSection;
    }

    private VBox createResetSettingsSection() {
        VBox resetSection = new VBox(10);
        resetSection.setAlignment(Pos.CENTER);
        resetSection.setPadding(new Insets(20, 0, 0, 0));

        Label resetLabel = new Label("Reset Settings");
        resetLabel.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 20));

        Button resetGameButton = new Button("Reset Game Progress");
        resetGameButton.setFont(Font.font(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 16).getName(), 16));
        resetGameButton.setStyle("-fx-background-color: #ff5252; -fx-text-fill: white;");
        resetGameButton.setOnAction(e -> showResetGameConfirmation());

        Button resetSettingsButton = new Button("Reset to Default Settings");
        resetSettingsButton.setFont(Font.font(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 16).getName(),
                16));
        resetSettingsButton.setStyle("-fx-background-color: #ff5252; -fx-text-fill: white;");
        resetSettingsButton.setOnAction(e -> showResetSettingsConfirmation());

        resetSection.getChildren().addAll(resetLabel, resetGameButton, resetSettingsButton);

        return resetSection;
    }

    private void showResetSettingsConfirmation() {
        Alert confirmDialog = new Alert(AlertType.WARNING);
        confirmDialog.setTitle("Reset Settings");
        confirmDialog.setHeaderText("Reset to Default Settings");
        confirmDialog.setContentText(
                "Are you sure you want to reset all settings to default values? This cannot be undone.");

        // Style the alert dialog
        DialogPane dialogPane = confirmDialog.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("/dev/typeracist/typeracist/css/dialog.css").toExternalForm());

        confirmDialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                resetToDefaultSettings();
            }
        });
    }

    private void showResetGameConfirmation() {
        Alert confirmDialog = new Alert(AlertType.WARNING);
        confirmDialog.setTitle("Reset Game Progress");
        confirmDialog.setHeaderText("Reset All Game Progress");
        confirmDialog.setContentText(
                "WARNING: This will delete ALL your game progress including character data, cleared battles, inventory, and coins. This action cannot be undone!");

        // Style the alert dialog
        DialogPane dialogPane = confirmDialog.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("/dev/typeracist/typeracist/css/dialog.css").toExternalForm());

        confirmDialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                resetGameProgress();
            }
        });
    }

    private void resetToDefaultSettings() {
        // Reset volume to default
        MusicPlayer musicPlayer = GameLogic.getInstance().getMusicPlayer();
        musicPlayer.setVolumeLevel(DEFAULT_VOLUME);

        // Update the UI
        updateVolumeDisplay();

        // Save the reset settings
        SaveManager.saveSettings();

        // Show confirmation message
        showSuccessAlert("Settings Reset", "Settings Reset Successfully",
                "All settings have been reset to their default values.");
    }

    private void resetGameProgress() {
        // Delete character save file
        SaveManager.deleteSaveFile(SaveManager.SAVE_FILE_CHARACTER);

        // Reload the character with default values
        GameLogic.getInstance().resetSelected();

        // Show confirmation message
        showSuccessAlert("Game Progress Reset", "Game Progress Reset Successfully",
                "All game progress has been reset to the beginning state.");
    }

    private void showSuccessAlert(String title, String header, String content) {
        Alert successAlert = new Alert(AlertType.INFORMATION);
        successAlert.setTitle(title);
        successAlert.setHeaderText(header);
        successAlert.setContentText(content);

        // Style the alert dialog
        DialogPane dialogPane = successAlert.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("/dev/typeracist/typeracist/css/dialog.css").toExternalForm());

        successAlert.showAndWait();
    }

    private void decreaseVolume() {
        MusicPlayer musicPlayer = GameLogic.getInstance().getMusicPlayer();
        int currentVolume = musicPlayer.getVolumeLevel();

        if (currentVolume > 0) {
            musicPlayer.setVolumeLevel(currentVolume - 1);
            updateVolumeDisplay();
            SaveManager.saveSettings();
        }
    }

    private void increaseVolume() {
        MusicPlayer musicPlayer = GameLogic.getInstance().getMusicPlayer();
        int currentVolume = musicPlayer.getVolumeLevel();

        if (currentVolume < 5) {
            musicPlayer.setVolumeLevel(currentVolume + 1);
            updateVolumeDisplay();
            SaveManager.saveSettings();
        }
    }

    private void updateVolumeDisplay() {
        int currentVolume = GameLogic.getInstance().getMusicPlayer().getVolumeLevel();
        volumeLabel.setText("Volume: " + currentVolume);

        // Update volume bars
        for (int i = 0; i < volumeBars.length; i++) {
            if (currentVolume == 0) {
                // All bars gray when volume is 0
                volumeBars[i].setFill(Color.GRAY);
            } else if (i < currentVolume) {
                // Blue for bars up to current volume level (1-5)
                volumeBars[i].setFill(Color.BLUE);
            } else {
                // Gray for bars above current volume level
                volumeBars[i].setFill(Color.GRAY);
            }
        }
    }

    @Override
    public void onSceneEnter() {
        // Update volume display when entering the scene
        updateVolumeDisplay();
    }

    @Override
    public void onSceneLeave() {
        // Nothing to do when leaving the scene
    }
}
