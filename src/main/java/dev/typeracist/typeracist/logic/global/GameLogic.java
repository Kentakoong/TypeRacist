package dev.typeracist.typeracist.logic.global;

import javafx.stage.Stage;

public class GameLogic {
    private static GameLogic instance;
    private final SceneManager sceneManager;

    private GameLogic(Stage primaryStage) {
        sceneManager = new SceneManager(primaryStage);
    }

    public static void init(Stage primaryStage) {
        instance = new GameLogic(primaryStage);
    }

    public static GameLogic getInstance() {
        if (instance == null) {
            throw new IllegalStateException("GameLogic has not been initialized. Call init(Stage) first.");
        }
        return instance;
    }

    public SceneManager getSceneManager() {
        return sceneManager;
    }
}
