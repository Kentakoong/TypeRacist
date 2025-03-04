package dev.typeracist.typeracist.logic.global;

import dev.typeracist.typeracist.gui.global.ThemedButton;
import dev.typeracist.typeracist.scene.BaseScene;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.HashMap;

public class SceneManager {
    private final Stage primaryStage;
    private final HashMap<String, BaseScene> scenes;
    private final SceneHistoryManager sceneHistoryManager;
    private Popup currentPopup;
    private boolean popUpOpen = false;

    public SceneManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.scenes = new HashMap<>();
        this.sceneHistoryManager = new SceneHistoryManager();
    }

    public void showPopUp(Pane content, double width, double height) {
        if (popUpOpen) {
            return;
        }

        popUpOpen = true;

        Popup popup = new Popup();

        // Center popup
        double centerX = primaryStage.getX() + (primaryStage.getWidth() - width) / 2;
        double centerY = primaryStage.getY() + (primaryStage.getHeight() - height) / 2;
        popup.setX(centerX);
        popup.setY(centerY);

        // Create close button
        Button closeButton = new ThemedButton("Close", Color.web("#C3C3C3"));
        closeButton.setOnAction(e -> {
            closePopUp();
        });

        // Create a StackPane for the background and content
        StackPane contentContainer = new StackPane();
        contentContainer.getChildren().addAll(content);

        // Create layout to contain content and close button
        VBox popupLayout = new VBox(10);
        popupLayout.setAlignment(Pos.CENTER);
        popupLayout.setPadding(new Insets(10));
        popupLayout.setMinSize(width, height);
        popupLayout.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, new CornerRadii(10), Insets.EMPTY)));
        popupLayout.setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(2))));

        popupLayout.getChildren().addAll(contentContainer, closeButton);

        popup.getContent().add(popupLayout);
        currentPopup = popup;
        popup.show(primaryStage);
    }

    public void addScene(String name, BaseScene scene) {
        scenes.put(name, scene);
    }

    public void removeScene(String name) {
        scenes.remove(name);
    }

    public BaseScene getScene(String name) {
        return scenes.get(name);
    }

    public BaseScene getCurrentScene() {
        return (BaseScene) primaryStage.getScene();
    }

    public boolean sceneExists(String name) {
        return scenes.containsKey(name);
    }

    public String getPreviousScene() {
        return sceneHistoryManager.getPreviousScene();
    }

    public void setToPreviousScene() {
        if (sceneHistoryManager.hasPreviousScene())
            setScene(getPreviousScene());
    }

    public void setScene(String name) {
        if (!sceneExists(name))
            throw new IllegalArgumentException("Scene " + name + " not found");

        if (getCurrentScene() != null)
            getCurrentScene().onSceneLeave();

        sceneHistoryManager.addSceneToHistory(name);
        BaseScene newScene = scenes.get(name);
        applyFadeTransition(primaryStage.getScene(), newScene);

        newScene.onSceneEnter();
    }

    public boolean isPopUpOpen() {
        return popUpOpen;
    }

    public Popup getCurrentPopup() {
        return currentPopup;
    }

    public void closePopUp() {
        if (currentPopup != null) {
            currentPopup.hide();
            popUpOpen = false;
            currentPopup = null;
        }
    }

    private void applyFadeTransition(Scene oldScene, Scene newScene) {
        if (oldScene == null) {
            primaryStage.setScene(newScene);
            return;
        }

        FadeTransition fadeOut = new FadeTransition(Duration.millis(100), oldScene.getRoot());
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(event -> {
            primaryStage.setScene(newScene);
            FadeTransition fadeIn = new FadeTransition(Duration.millis(100), newScene.getRoot());
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });
        fadeOut.play();
    }

    public void closeStage() {
        primaryStage.close();
    }
}
