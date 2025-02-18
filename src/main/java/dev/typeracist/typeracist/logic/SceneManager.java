package dev.typeracist.typeracist.logic;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;

public class SceneManager {
    final private Stage primaryStage;
    final private HashMap<String, Scene> scenes;
    final private SceneHistoryManager sceneHistoryManager;

    public SceneManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.scenes = new HashMap<>();
        this.sceneHistoryManager = new SceneHistoryManager();
    }

    public void addScene(String name, Scene scene) {
        scenes.put(name, scene);
    }

    public void closeStage() {
        primaryStage.close();
    }

    public boolean sceneExists(String name) {
        return scenes.containsKey(name);
    }

    public String getPreviousScene() {
        return sceneHistoryManager.getPreviousScene();
    }

    public void setToPreviousScene() {
        setScene(getPreviousScene());
    }

    public void setScene(String name) {
        if (!sceneExists(name))
            throw new IllegalArgumentException("Scene " + name + " not found");

        sceneHistoryManager.addSceneToHistory(name);
        primaryStage.setScene(scenes.get(name));
    }
}