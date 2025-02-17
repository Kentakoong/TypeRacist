package dev.typeracist.typeracist.application;

import dev.typeracist.typeracist.logic.GameLogic;
import dev.typeracist.typeracist.logic.SceneManager;
import dev.typeracist.typeracist.scene.CharacterScene;
import dev.typeracist.typeracist.scene.MainScene;
import dev.typeracist.typeracist.scene.SettingScene;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        GameLogic.init(primaryStage);

        SceneManager sceneManager = GameLogic.getInstance().getSceneManager();

        sceneManager.addScene("main", new MainScene(800, 600));
        sceneManager.addScene("settings", new SettingScene(800, 600));
        sceneManager.addScene("characters", new CharacterScene(800, 600));

        sceneManager.setScene("main");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}