package dev.typeracist.typeracist.logic.global;

import dev.typeracist.typeracist.logic.gameScene.DataSet;
import dev.typeracist.typeracist.logic.gameScene.DataSetManager;
import dev.typeracist.typeracist.utils.DataSetName;
import javafx.stage.Stage;

public class GameLogic {
    private static GameLogic instance;
    private final SceneManager sceneManager;
    private final DataSetManager dataSetManager;

    private GameLogic(Stage primaryStage) {
        sceneManager = new SceneManager(primaryStage);
        dataSetManager = new DataSetManager();
    }

    public static void init(Stage primaryStage) {
        instance = new GameLogic(primaryStage);
    }

    public static void initializeDataSets() {
        DataSet popularBooks = new DataSet("/dev/typeracist/typeracist/datasets/popularBooks.json");
        popularBooks.initializeWithRanking();

        DataSet commonWords25k = new DataSet("/dev/typeracist/typeracist/datasets/commonWords25k.json");
        commonWords25k.initializeWithRanking();

        DataSet commonWords1k = new DataSet("/dev/typeracist/typeracist/datasets/commonWords1k.json");
        commonWords1k.initializeWithRanking();

        getInstance().dataSetManager.addDataset(DataSetName.POPULAR_BOOKS, popularBooks);
        getInstance().dataSetManager.addDataset(DataSetName.COMMON_WORDS_1K, commonWords1k);
        getInstance().dataSetManager.addDataset(DataSetName.COMMON_WORDS_25K, commonWords25k);
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

    public DataSetManager getDataSetManager() {
        return dataSetManager;
    }
}
