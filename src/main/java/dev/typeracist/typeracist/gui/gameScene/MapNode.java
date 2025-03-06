package dev.typeracist.typeracist.gui.gameScene;

import dev.typeracist.typeracist.logic.global.GameLogic;
import dev.typeracist.typeracist.utils.SceneName;
import dev.typeracist.typeracist.logic.global.BattleInfo;
import dev.typeracist.typeracist.logic.global.BattleNavigation;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class MapNode extends Button {
    private final Circle statusCircle;
    private final BattleInfo action;
    private final List<MapNode> neighbors = new ArrayList<>(); // Store connected nodes

    public MapNode(double x, double y, Image image, BattleInfo action) {
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

    public void addNeighbor(MapNode neighbor) {
        neighbors.add(neighbor);
    }

    public List<MapNode> getNeighbors() {
        return neighbors;
    }

    // Method to update the status color dynamically
    public void updateStatusColor() {
        statusCircle.setFill(getNodeColor(action));
    }

    // Getters for easy access
    public Circle getStatusCircle() {
        return statusCircle;
    }

    public BattleInfo getAction() {
        return action;
    }

    // Helper method to determine the correct color
    private Color getNodeColor(BattleInfo action) {
        // Check if the action is a battle using BattleNavigation

        if (action.getBattleName().startsWith(SceneName.REWARD)
                && GameLogic.getInstance().isBattleCleared(action.getBattleName())) {
            return Color.RED;
        }

        if (!action.isBattle()) {
            return Color.GRAY;
        }

        if (GameLogic.getInstance().isBattleCleared(action.getBattleName())) {
            return Color.GREEN; // Cleared
        } else if (GameLogic.getInstance().isPreviousBattleCleared(action.getBattleName())
                || action.getPrerequisiteBattle() == null) {
            return Color.YELLOW;
        } else if (action.getBattleName().equals("BOSS")) {
            return Color.RED; // Boss battle - locked
        }

        return Color.GRAY; // Locked
    }
}