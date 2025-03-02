package dev.typeracist.typeracist.logic.global;

import dev.typeracist.typeracist.scene.BaseScene;
import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.HashMap;

public class SceneManager {
    private final Stage primaryStage;
    private final HashMap<String, BaseScene> scenes;
    private final SceneHistoryManager sceneHistoryManager;

    public SceneManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.scenes = new HashMap<>();
        this.sceneHistoryManager = new SceneHistoryManager();
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
