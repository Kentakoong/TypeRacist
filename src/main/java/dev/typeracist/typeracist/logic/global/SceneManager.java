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
import java.util.LinkedList;
import java.util.Queue;

public class SceneManager {
    private final Stage primaryStage;
    private final HashMap<String, BaseScene> scenes;
    private final SceneHistoryManager sceneHistoryManager;
    private final Queue<BreadcrumbData> breadcrumbQueue = new LinkedList<>();
    private boolean isBreadcrumbPlaying = false;
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

        VBox popupLayout = new VBox(10);
        popupLayout.setAlignment(Pos.CENTER);
        popupLayout.setPadding(new Insets(10));
        popupLayout.setMinSize(width, height);
        popupLayout.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, new CornerRadii(10), Insets.EMPTY)));
        popupLayout.setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(2))));
        popupLayout.setOpacity(0);

        Button closeButton = new ThemedButton("Close", Color.web("#C3C3C3"));
        closeButton.setOnAction(e -> closePopUp());
        closeButton.setTextFill(Color.BLACK);

        popupLayout.getChildren().addAll(content, closeButton);
        popup.getContent().add(popupLayout);
        currentPopup = popup;
        popup.show(primaryStage);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(200), popupLayout);
        fadeIn.setInterpolator(Interpolator.EASE_BOTH);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }

    public void closePopUp() {
        if (currentPopup != null) {
            VBox popupLayout = (VBox) currentPopup.getContent().get(0);

            FadeTransition fadeOut = new FadeTransition(Duration.millis(200), popupLayout);
            fadeOut.setInterpolator(Interpolator.EASE_BOTH);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setOnFinished(e -> {
                if (currentPopup != null) {
                    currentPopup.hide();
                }

                currentPopup = null;
                popUpOpen = false;
            });
            fadeOut.play();
        }
    }

    public void showBreadcrumb(String title, String description, long durationInMilliseconds) {
        breadcrumbQueue.add(new BreadcrumbData(title, description, durationInMilliseconds));
        startBreadcrumbScheduler();
    }

    private void startBreadcrumbScheduler() {
        Timeline scheduler = new Timeline(new KeyFrame(Duration.millis(700), event -> {
            if (!breadcrumbQueue.isEmpty()) {
                playNextBreadcrumb();
            }
        }));

        scheduler.setCycleCount(Animation.INDEFINITE); // Keep running indefinitely
        scheduler.play();
    }

    private void playNextBreadcrumb() {
        if (breadcrumbQueue.isEmpty()) {
            return;
        }

        BreadcrumbData data = breadcrumbQueue.poll();
        Popup breadcrumb = new Popup();

        VBox content = new VBox(5);
        content.setAlignment(Pos.CENTER);

        Label titleLabel = new Label(data.title);
        titleLabel.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 20));
        titleLabel.setTextFill(Color.DARKBLUE);

        Label descriptionLabel = new Label(data.description);
        descriptionLabel.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 16));
        descriptionLabel.setTextFill(Color.BLACK);

        content.setPadding(new Insets(20));

        // Initial Background Color (Light Grey)
        Background defaultBackground = new Background(new BackgroundFill(Color.LIGHTGREY, new CornerRadii(10), Insets.EMPTY));
        Background fadeBackground = new Background(new BackgroundFill(Color.DARKGRAY, new CornerRadii(10), Insets.EMPTY));

        content.setBackground(defaultBackground);
        content.getChildren().addAll(titleLabel, descriptionLabel);

        StackPane container = new StackPane(content);
        container.setPadding(new Insets(10));

        breadcrumb.getContent().add(container);
        breadcrumb.show(primaryStage);

        // Position the popup at the center
        double initialX = primaryStage.getX();
        double initialY = primaryStage.getY();
        breadcrumb.setX(initialX + 10);
        breadcrumb.setY(initialY + 50);
        content.setOpacity(0);
        content.setTranslateY(0); // Start at normal position

        Timeline timeline = new Timeline(
                // Fade-in effect
                new KeyFrame(Duration.millis(200),
                        new KeyValue(content.opacityProperty(), 1, Interpolator.EASE_BOTH)),

                // Change background early before fading out
                new KeyFrame(Duration.millis(100 + data.durationInMilliseconds),
                        event -> content.setBackground(fadeBackground)),

                new KeyFrame(Duration.millis(data.durationInMilliseconds)), // Hold visibility

                // Fade-out + Move Up by (height * 2)
                new KeyFrame(Duration.millis(200 + data.durationInMilliseconds),
                        new KeyValue(content.opacityProperty(), 0, Interpolator.EASE_BOTH),
                        new KeyValue(content.scaleXProperty(), 0.9, Interpolator.EASE_BOTH),
                        new KeyValue(content.scaleYProperty(), 0.9, Interpolator.EASE_BOTH),
                        new KeyValue(content.translateYProperty(), -75, Interpolator.EASE_OUT)),

                // Hide popup after animation
                new KeyFrame(Duration.millis(250 + data.durationInMilliseconds),
                        event -> breadcrumb.hide())
        );
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

    private static class BreadcrumbData {
        String title;
        String description;
        long durationInMilliseconds;

        BreadcrumbData(String title, String description, long durationInMilliseconds) {
            this.title = title;
            this.description = description;
            this.durationInMilliseconds = durationInMilliseconds;
        }
    }
}
