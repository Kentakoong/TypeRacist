package dev.typeracist.typeracist.gui.gameScene;

import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.utils.ResourceName;
import javafx.animation.FadeTransition;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class ShopPane extends Pane {
    private final Label messageLabel;
    private final FadeTransition fadeIn;
    private final FadeTransition fadeOut;

    public ShopPane(double sceneWidth, double sceneHeight) {
        setStyle("-fx-background-color: #484848; " +
                "-fx-border-color: white; " +
                "-fx-border-width: 2; " +
                "-fx-background-radius: 20; " +
                "-fx-border-radius: 20;");
        setPrefSize(500, 300);
        setOpacity(0); // Start fully transparent
        setVisible(false);

        // Center the pane on the screen dynamically
        setLayoutX((sceneWidth - getPrefWidth()) / 2);
        setLayoutY((sceneHeight - getPrefHeight()) / 2);

        // Load custom font
        Font baseFont = ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 36);

        // Message label (centered)
        messageLabel = new Label();
        messageLabel.setFont(Font.font(baseFont.getName(), 20));
        messageLabel.setStyle("-fx-text-fill: white;");
        messageLabel.setWrapText(true);
        messageLabel.setMaxWidth(460);
        messageLabel.setLayoutX(20);
        messageLabel.setLayoutY(100);

        // Close button (centered)
        Button closeButton = new Button("Close");
        closeButton.setFont(Font.font(baseFont.getName(), 18));
        closeButton.setLayoutX(200);
        closeButton.setLayoutY(220);
        closeButton.setOnAction(e -> hidePopup());

        getChildren().addAll(messageLabel, closeButton);

        // Faster fade-in transition (300ms)
        fadeIn = new FadeTransition(Duration.millis(300), this);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        // Faster fade-out transition (300ms)
        fadeOut = new FadeTransition(Duration.millis(300), this);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(e -> setVisible(false));
    }

    // Show pop-up with fade-in effect
    public void showPopup(String message) {
        messageLabel.setText(message);
        setVisible(true);
        fadeIn.playFromStart();
    }

    // Hide pop-up with fade-out effect
    private void hidePopup() {
        fadeOut.playFromStart();
    }
}
