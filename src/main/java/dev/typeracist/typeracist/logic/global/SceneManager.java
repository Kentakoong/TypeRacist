package dev.typeracist.typeracist.logic.global;

import dev.typeracist.typeracist.gui.global.ThemedButton;
import dev.typeracist.typeracist.scene.BaseScene;
import dev.typeracist.typeracist.utils.ResourceName;
import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

        // Create layout to contain content and close button
        VBox popupLayout = new VBox(10);
        popupLayout.setAlignment(Pos.CENTER);
        popupLayout.setPadding(new Insets(10));
        popupLayout.setMinSize(width, height);
        popupLayout.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, new CornerRadii(10), Insets.EMPTY)));
        popupLayout.setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(2))));
        popupLayout.setOpacity(0); // Start with opacity 0

        // Create close button
        Button closeButton = new ThemedButton("Close", Color.web("#C3C3C3"));
        closeButton.setOnAction(e -> closePopUp());
        closeButton.setTextFill(Color.BLACK);

        popupLayout.getChildren().addAll(content, closeButton);
        popup.getContent().add(popupLayout);
        currentPopup = popup;
        popup.show(primaryStage);

        // Apply fade-in transition
        FadeTransition fadeIn = new FadeTransition(Duration.millis(200), popupLayout);
        fadeIn.setInterpolator(Interpolator.EASE_BOTH);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }

    public void closePopUp() {
        if (currentPopup != null) {
            VBox popupLayout = (VBox) currentPopup.getContent().get(0);

            // Apply fade-out transition
            FadeTransition fadeOut = new FadeTransition(Duration.millis(200), popupLayout);
            fadeOut.setInterpolator(Interpolator.EASE_BOTH);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setOnFinished(e -> {
                currentPopup.hide();
                currentPopup = null;
                popUpOpen = false;
            });
            fadeOut.play();
        }
    }

    public void showBreadcrumb(String title, String description, long durationInMilliseconds) {
        Popup breadcrumb = new Popup();

        // Create vertical layout for title and description
        VBox content = new VBox(5); // 5 pixel spacing between elements
        content.setAlignment(Pos.CENTER);

        // Title Label
        Label titleLabel = new Label(title);
        titleLabel.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 20));
        titleLabel.setTextFill(Color.DARKBLUE);
        titleLabel.setStyle("-fx-font-weight: bold;");

        // Description Label
        Label descriptionLabel = new Label(description);
        descriptionLabel.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 16));
        descriptionLabel.setTextFill(Color.BLACK);

        // Container styling
        content.setPadding(new Insets(20));
        content.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, new CornerRadii(10), Insets.EMPTY)));

        // Add labels to container
        content.getChildren().addAll(titleLabel, descriptionLabel);

        // Wrap content in StackPane for better transition handling
        StackPane container = new StackPane(content);
        container.setPadding(new Insets(10));

        breadcrumb.getContent().add(container);
        breadcrumb.show(primaryStage);

        // Position the popup
        double initialX = primaryStage.getX() + primaryStage.getWidth() / 2 - container.getWidth() / 2;
        double initialY = primaryStage.getY() + primaryStage.getHeight() / 2;
        breadcrumb.setX(initialX);
        breadcrumb.setY(initialY);

        // Initial opacity and scale
        content.setOpacity(0);

        // Create timeline for animation
        Timeline timeline = new Timeline();

        // Fade-in and scale up
        KeyFrame fadeIn = new KeyFrame(Duration.millis(300),
                new KeyValue(content.opacityProperty(), 1, Interpolator.EASE_BOTH)
        );

        // Pause
        KeyFrame pause = new KeyFrame(Duration.millis(300 + durationInMilliseconds));

        // Fade-out and scale down
        KeyFrame fadeOutAndScaleDown = new KeyFrame(Duration.millis(600 + durationInMilliseconds),
                new KeyValue(content.opacityProperty(), 0, Interpolator.EASE_BOTH),
                new KeyValue(content.scaleXProperty(), 0.9, Interpolator.EASE_BOTH),
                new KeyValue(content.scaleYProperty(), 0.9, Interpolator.EASE_BOTH)
        );

        // Close popup after animation
        KeyFrame close = new KeyFrame(Duration.millis(600 + durationInMilliseconds),
                event -> breadcrumb.hide()
        );

        timeline.getKeyFrames().addAll(fadeIn, pause, fadeOutAndScaleDown, close);
        timeline.play();
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
            fadeIn.setInterpolator(Interpolator.EASE_BOTH);
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
