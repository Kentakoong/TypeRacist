package dev.typeracist.typeracist.logic.global;

import dev.typeracist.typeracist.logic.gameScene.DataSet;
import dev.typeracist.typeracist.logic.gameScene.DataSetManager;
import dev.typeracist.typeracist.utils.DataSetName;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.Set;

public class GameLogic {
    private static GameLogic instance;
    private final SceneManager sceneManager;
    private final DataSetManager dataSetManager;
    private String selectedCharacter; // Store character image path or ID
    private final Set<String> clearedBattles = new HashSet<>();

    private GameLogic(Stage primaryStage) {
        sceneManager = new SceneManager(primaryStage);
        dataSetManager = new DataSetManager();
        selectedCharacter = "/dev/typeracist/typeracist/image/character/warrior.png";
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

    //intermediated pass variable  CharacterScene and Map
    public void setSelectedCharacter(String character) {
        this.selectedCharacter = character;
    }

    public String getSelectedCharacter() {
        return selectedCharacter;
    }

    public void clearBattle(String battleName) {
        clearedBattles.add(battleName);
    }

    public boolean isBattleCleared(String battleName) {
        return clearedBattles.contains(battleName);
    }

    // Checks if a battle is playable based on previous battle clear status
    public boolean isBattleUnlocked(String battleName) {
        switch (battleName) {
            case "BATTLE1": return true;
            case "BATTLE2": return isBattleCleared("BATTLE1");
            case "BATTLE3": return isBattleCleared("BATTLE2");
            case "BATTLE4": return isBattleCleared("BATTLE3");
            case "BATTLE5": return isBattleCleared("BATTLE4");
            case "BATTLE6": return isBattleCleared("BATTLE5");
            case "BATTLE7": return isBattleCleared("BATTLE6");
            case "BATTLE8": return isBattleCleared("BATTLE7");
            case "BATTLE9": return isBattleCleared("BATTLE8");
            case "BOSS": return isBattleCleared("BATTLE9");
            default: return false;
        }
    }
}
