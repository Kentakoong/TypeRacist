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

        sceneManager.addScene(SceneName.MAIN, new MainScene(800, 600));
        sceneManager.addScene(SceneName.SETTINGS, new SettingScene(800, 600));
        sceneManager.addScene(SceneName.CHARACTERS, new CharacterScene(800, 600));
        sceneManager.addScene(SceneName.DYNAMIC_PANE_TEST, new DynamicSceneDemo(800, 600));
        sceneManager.addScene(SceneName.KEYBOARD_TEST, new KeyboardPaneSceneDemo(800, 600));

        sceneManager.setScene(SceneName.MAIN);
        primaryStage.show();
    }
}