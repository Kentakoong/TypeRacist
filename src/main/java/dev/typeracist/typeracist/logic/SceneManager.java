package dev.typeracist.typeracist.logic;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;

public class SceneManager {
    final private Stage primaryStage;
    final private HashMap<String, Scene> scenes;

    public SceneManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
        scenes = new HashMap<String, Scene>();
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

    public void setScene(String name) {
        if (!sceneExists(name))
            throw new IllegalArgumentException("Scene " + name + " not found");

        primaryStage.setScene(scenes.get(name));
    }
}