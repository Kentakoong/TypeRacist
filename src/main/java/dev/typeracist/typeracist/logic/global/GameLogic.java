package dev.typeracist.typeracist.logic.global;

import dev.typeracist.typeracist.logic.gameScene.Dataset;
import dev.typeracist.typeracist.logic.gameScene.DatasetManager;
import dev.typeracist.typeracist.utils.DatasetName;
import javafx.stage.Stage;

public class GameLogic {
    private static GameLogic instance;
    private final SceneManager sceneManager;
    private final DatasetManager datasetManager;

    private GameLogic(Stage primaryStage) {
        sceneManager = new SceneManager(primaryStage);
        datasetManager = new DatasetManager();
    }

    public static void init(Stage primaryStage) {
        instance = new GameLogic(primaryStage);
    }

    public static void initializeDatasets() {
        Dataset popularBooks = new Dataset("/dev/typeracist/typeracist/datasets/popularBooks.json");
        popularBooks.initializeWithRanking();

        Dataset commonWords25k = new Dataset("/dev/typeracist/typeracist/datasets/commonWords25k.json");
        commonWords25k.initializeWithRanking();

        Dataset commonWords1k = new Dataset("/dev/typeracist/typeracist/datasets/commonWords1k.json");
        commonWords1k.initializeWithRanking();

        getInstance().datasetManager.addDataset(DatasetName.POPULAR_BOOKS, popularBooks);
        getInstance().datasetManager.addDataset(DatasetName.COMMON_WORDS_1K, commonWords1k);
        getInstance().datasetManager.addDataset(DatasetName.COMMON_WORDS_25K, commonWords25k);
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

    public DatasetManager getDatasetManager() {
        return datasetManager;
    }
}
