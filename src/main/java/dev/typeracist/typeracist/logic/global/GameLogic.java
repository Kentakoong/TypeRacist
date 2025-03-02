package dev.typeracist.typeracist.logic.global;

import dev.typeracist.typeracist.logic.gameScene.Dataset;
import dev.typeracist.typeracist.logic.gameScene.DatasetManager;
import dev.typeracist.typeracist.utils.DatasetName;
import dev.typeracist.typeracist.logic.characters.Character;
import dev.typeracist.typeracist.logic.characters.Warrior;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.Set;

public class GameLogic {
    private static GameLogic instance;
    private final SceneManager sceneManager;
    private final DatasetManager datasetManager;
    private final Set<String> clearedBattles = new HashSet<>();
    private Character selectedCharacter; // Store character image path or ID

    private GameLogic(Stage primaryStage) {
        sceneManager = new SceneManager(primaryStage);
        datasetManager = new DatasetManager();
        selectedCharacter = new Warrior();
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

    public Character getSelectedCharacter() {
        return selectedCharacter;
    }

    //intermediated pass variable  CharacterScene and Map
    public void setSelectedCharacter(Character character) {
        this.selectedCharacter = character;
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
            case "BATTLE2":
                return isBattleCleared("BATTLE1");
            case "BATTLE3":
                return isBattleCleared("BATTLE2");
            case "BATTLE4":
                return isBattleCleared("BATTLE3");
            case "BATTLE5":
                return isBattleCleared("BATTLE4");
            case "BATTLE6":
                return isBattleCleared("BATTLE5");
            case "BATTLE7":
                return isBattleCleared("BATTLE6");
            case "BATTLE8":
                return isBattleCleared("BATTLE7");
            case "BATTLE9":
                return isBattleCleared("BATTLE8");
            case "BOSS":
                return isBattleCleared("BATTLE9");
            default:
                return false;
        }
    }
}
