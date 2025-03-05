package dev.typeracist.typeracist.scene;

import dev.typeracist.typeracist.logic.global.GameLogic;
import dev.typeracist.typeracist.logic.global.MusicPlayer;
import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.utils.ResourceName;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SettingScene extends BaseScene {
    private final VBox volumeBarContainer;
    private final Rectangle[] volumeBars;
    private final Label volumeLabel;

    public SettingScene(double width, double height) {
        super(new VBox(), width, height);

        VBox root = (VBox) getRoot();
        root.setAlignment(Pos.TOP_CENTER);
        root.setSpacing(20);

        // Navigation Bar (HBox for proper left-right alignment)
        HBox navigationBar = new HBox();
        navigationBar.setAlignment(Pos.CENTER);
        navigationBar.setPadding(new Insets(40));
        navigationBar.setPrefWidth(width);

        // Buttons for navigation
        Button previousButton = new Button("Previous");
        previousButton.setOnAction(event -> GameLogic.getInstance().getSceneManager().setToPreviousScene());

        Button nextButton = new Button("Next"); // Example for Next Button
        nextButton.setDisable(true);

        // Make sure the buttons align at the left and right edges
        HBox leftBox = new HBox(previousButton);
        leftBox.setAlignment(Pos.CENTER_LEFT);
        HBox rightBox = new HBox(nextButton);
        rightBox.setAlignment(Pos.CENTER_RIGHT);

        // Push buttons to the edges by setting horizontal grow
        HBox.setHgrow(leftBox, javafx.scene.layout.Priority.ALWAYS);
        HBox.setHgrow(rightBox, javafx.scene.layout.Priority.ALWAYS);

        navigationBar.getChildren().addAll(leftBox, new Label(), rightBox);

        // Title
        Label title = new Label("Settings");
        title.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 36));

        // Settings Table
        VBox settingsTable = new VBox();
        settingsTable.setAlignment(Pos.CENTER);
        settingsTable.setSpacing(20);

        // Volume Control Section
        VBox volumeSection = new VBox(10);
        volumeSection.setAlignment(Pos.CENTER);

        // Volume Label
        volumeLabel = new Label("Volume: 3");
        volumeLabel.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 20));

        // Volume Bar Container
        volumeBarContainer = new VBox(5);
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

        volumeBars = new Rectangle[6]; // 0-5 levels

        for (int i = 0; i < 6; i++) {
            Rectangle bar = new Rectangle(20, 10 + (i * 5));
            bar.setFill(Color.GRAY);

            StackPane barContainer = new StackPane(bar);
            barContainer.setAlignment(Pos.BOTTOM_CENTER);
            barContainer.setMinHeight(40);

            volumeBarsBox.getChildren().add(barContainer);
            volumeBars[i] = bar;
        }

        volumeSection.getChildren().addAll(volumeLabel, volumeBarsBox, volumeControlButtons);

        // Other settings (example checkboxes)
        for (int i = 0; i < 3; ++i) {
            CheckBox checkBox = new CheckBox("Setting " + i);
            settingsTable.getChildren().add(checkBox);
        }

        // Add volume section to settings table
        settingsTable.getChildren().add(0, volumeSection);

        // Add everything to the root
        root.getChildren().addAll(navigationBar, title, settingsTable);

        // Initialize volume bars based on current volume
        updateVolumeDisplay();
    }

    private void decreaseVolume() {
        MusicPlayer musicPlayer = GameLogic.getInstance().getMusicPlayer();
        int currentVolume = musicPlayer.getVolumeLevel();

        if (currentVolume > 0) {
            musicPlayer.setVolumeLevel(currentVolume - 1);
            updateVolumeDisplay();
        }
    }

    private void increaseVolume() {
        MusicPlayer musicPlayer = GameLogic.getInstance().getMusicPlayer();
        int currentVolume = musicPlayer.getVolumeLevel();

        if (currentVolume < 5) {
            musicPlayer.setVolumeLevel(currentVolume + 1);
            updateVolumeDisplay();
        }
    }

    private void updateVolumeDisplay() {
        int currentVolume = GameLogic.getInstance().getMusicPlayer().getVolumeLevel();
        volumeLabel.setText("Volume: " + currentVolume);

        // Update volume bars
        for (int i = 0; i < volumeBars.length; i++) {
            if (i <= currentVolume) {
                volumeBars[i].setFill(Color.BLUE);
            } else {
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
