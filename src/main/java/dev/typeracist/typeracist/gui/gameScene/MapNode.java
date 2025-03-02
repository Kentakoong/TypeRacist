package dev.typeracist.typeracist.gui.gameScene;

import dev.typeracist.typeracist.logic.global.GameLogic;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class MapNode extends Button {
    private final Circle statusCircle;
    private final String action;

    public MapNode(double x, double y, Image image, String action) {
        super(); // Initialize the Button

        this.action = action;

        // Create status circle
        statusCircle = new Circle(30); // Adjust radius as needed
        statusCircle.setLayoutX(x + 35); // Centered with the icon
        statusCircle.setLayoutY(y + 25);
        statusCircle.setFill(getNodeColor(action)); // Set initial color

        // Create image icon
        ImageView icon = new ImageView(image);
        icon.setFitWidth(50);
        icon.setFitHeight(50);

        // Set button properties
        this.setGraphic(icon);
        this.setStyle("-fx-background-color: transparent;");
        this.setLayoutX(x);
        this.setLayoutY(y);
    }

    // Method to update the status color dynamically
    public void updateStatusColor() {
        statusCircle.setFill(getNodeColor(action));
    }

    // Getters for easy access
    public Circle getStatusCircle() {
        return statusCircle;
    }

    public String getAction() {
        return action;
    }

    // Helper method to determine the correct color
    private Color getNodeColor(String action) {
        if (GameLogic.getInstance().isBattleCleared(action)) {
            return Color.GREEN; // Won
        } else if (GameLogic.getInstance().isBattleUnlocked(action)) {
            return Color.YELLOW; // Playable
        } else if (action.equals("BOSS")) {
            return Color.RED; // Boss
        } else {
            return Color.WHITE; // Locked
        }
    }
}