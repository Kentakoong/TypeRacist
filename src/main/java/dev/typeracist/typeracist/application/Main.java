package dev.typeracist.typeracist.application;

import dev.typeracist.typeracist.logic.global.GameLogic;
import dev.typeracist.typeracist.logic.global.SceneManager;
import dev.typeracist.typeracist.scene.*;
import dev.typeracist.typeracist.utils.SceneName;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        GameLogic.init(primaryStage);

        SceneManager sceneManager = GameLogic.getInstance().getSceneManager();

        sceneManager.addScene(SceneName.MAIN, new MainScene(1000, 750));
        sceneManager.addScene(SceneName.SETTINGS, new SettingScene(1000, 750));
        sceneManager.addScene(SceneName.CHARACTERS, new CharacterScene(1000, 750));
        sceneManager.addScene(SceneName.DYNAMIC_PANE_TEST, new DynamicSceneDemo(1000, 750));
        sceneManager.addScene(SceneName.MAP, new MapScene(1000, 750));

        // load dataset and keyboard pane, in other page.
        new Thread(() -> {
            GameLogic.initializeDatasets();
            sceneManager.addScene(SceneName.KEYBOARD_TEST, new KeyboardPaneSceneDemo(1000, 750));
        }).start();

        sceneManager.setScene(SceneName.MAIN);
        primaryStage.show();
    }
}