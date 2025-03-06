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

        // Get the full screen width and height
        double screenWidth = javafx.stage.Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = javafx.stage.Screen.getPrimary().getVisualBounds().getHeight();

        // Add scenes dynamically with the full screen size
        sceneManager.addScene(SceneName.MAIN, new MainScene(screenWidth, screenHeight));
        sceneManager.addScene(SceneName.SETTINGS, new SettingScene(screenWidth, screenHeight));
        sceneManager.addScene(SceneName.CHARACTERS, new CharacterScene(screenWidth, screenHeight));
        sceneManager.addScene(SceneName.DYNAMIC_PANE_TEST, new DynamicSceneDemo(screenWidth, screenHeight));
        sceneManager.addScene(SceneName.MAP, new MapScene(screenWidth, screenHeight));
        sceneManager.addScene(SceneName.SHOP, new ShopScene(screenWidth, screenHeight));
        sceneManager.addScene(SceneName.ENCHANT, new EnchantScene(screenWidth, screenHeight));
        sceneManager.addScene(SceneName.CHEST, new ChestScene(screenWidth, screenHeight));
        sceneManager.addScene(SceneName.FORGE, new ForgeScene(screenWidth, screenHeight));

        // Load dataset and keyboard pane in another thread
        new Thread(() -> {
            GameLogic.initializeDatasets();
            sceneManager.addScene(SceneName.BATTLE_SCENE, new BattleScene(screenWidth, screenHeight));
            sceneManager.addScene(SceneName.BATTLE_TEST, new BattleScene(screenWidth, screenHeight));
            sceneManager.addScene(SceneName.KEYBOARD_TEST, new KeyboardPaneSceneDemo(screenWidth, screenHeight));
        }).start();

        // Set the stage to full screen or maximized
        primaryStage.setMaximized(true); // Maximized window mode
        // primaryStage.setFullScreen(true); // Uncomment for full-screen mode

        sceneManager.setScene(SceneName.MAIN);
        primaryStage.show();
    }
}