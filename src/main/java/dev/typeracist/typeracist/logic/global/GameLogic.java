package dev.typeracist.typeracist.logic.global;

import java.util.HashMap;
import java.util.Map;

import dev.typeracist.typeracist.logic.characters.Character;
import dev.typeracist.typeracist.logic.characters.entities.Warrior;
import dev.typeracist.typeracist.logic.game.dataset.Dataset;
import dev.typeracist.typeracist.logic.game.dataset.DatasetManager;
import dev.typeracist.typeracist.utils.DatasetName;
import dev.typeracist.typeracist.utils.Difficulty;
import dev.typeracist.typeracist.utils.ResourceName;
import javafx.stage.Stage;

public class GameLogic {
    private static GameLogic instance;
    private final SceneManager sceneManager;
    private final DatasetManager datasetManager;
    private final Map<String, Character> characters = new HashMap<>();
    private final Map<Difficulty, Map<String, Character>> charactersByDifficulty = new HashMap<>();
    private final MusicPlayer musicPlayer;
    private Character selectedCharacter;
    private String playerName;
    private Difficulty currentDifficulty;

    private GameLogic(Stage primaryStage) {
        sceneManager = new SceneManager(primaryStage);
        datasetManager = new DatasetManager();
        musicPlayer = MusicPlayer.getInstance();

        charactersByDifficulty.put(Difficulty.EASY, new HashMap<>());
        charactersByDifficulty.put(Difficulty.NORMAL, new HashMap<>());
        charactersByDifficulty.put(Difficulty.HARD, new HashMap<>());
        charactersByDifficulty.put(Difficulty.HELL, new HashMap<>());

        selectedCharacter = new Warrior();
        characters.put(selectedCharacter.getClass().getSimpleName(), selectedCharacter);
        playerName = "You";
        currentDifficulty = Difficulty.NORMAL;
    }

    public static void init(Stage primaryStage) {
        instance = new GameLogic(primaryStage);
        SaveManager.loadSettings();

        // todo: remove this
        SaveManager.saveCharacter();
    }

    public static void initializeDatasets() {
        Dataset popularBooks = new Dataset(ResourceName.DATASET_POPULAR_BOOKS);
        popularBooks.initializeWithRanking();

        Dataset commonWords25k = new Dataset(ResourceName.DATASET_COMMON_WORDS_25K);
        commonWords25k.initializeWithRanking();

        Dataset commonWords1k = new Dataset(ResourceName.DATASET_COMMON_WORDS_1K);
        commonWords1k.initializeWithRanking();

        Dataset dataStructureAndAlgorithm = new Dataset(ResourceName.DATASET_DATA_STRUCTURE_AND_ALGORITHM);
        dataStructureAndAlgorithm.initializeWithRanking();

        getInstance().datasetManager.addDataset(DatasetName.POPULAR_BOOKS, popularBooks);
        getInstance().datasetManager.addDataset(DatasetName.COMMON_WORDS_1K, commonWords1k);
        getInstance().datasetManager.addDataset(DatasetName.COMMON_WORDS_25K, commonWords25k);
        getInstance().datasetManager.addDataset(DatasetName.DATA_STRUCTURE_AND_ALGORITHM, dataStructureAndAlgorithm);
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

    public void setSelectedCharacter(Character character) {
        this.selectedCharacter = character;
        // Add or update character in the map
        characters.put(character.getClass().getSimpleName(), character);

        // Also add to difficulty-specific map if difficulty is set
        if (currentDifficulty != null) {
            Map<String, Character> difficultyMap = charactersByDifficulty.get(currentDifficulty);
            if (difficultyMap != null) {
                difficultyMap.put(character.getClass().getSimpleName(), character);
            }
        }
    }

    public void resetSelected() {
        selectedCharacter = null;
        currentDifficulty = null;
    }

    public Map<String, Character> getCharacters() {
        return characters;
    }

    public Map<Difficulty, Map<String, Character>> getCharactersByDifficulty() {
        return charactersByDifficulty;
    }

    public Map<String, Character> getCharactersForDifficulty(Difficulty difficulty) {
        return charactersByDifficulty.getOrDefault(difficulty, new HashMap<>());
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Difficulty getCurrentDifficulty() {
        return currentDifficulty;
    }

    public void setCurrentDifficulty(Difficulty difficulty) {
        this.currentDifficulty = difficulty;
    }

    public void clearBattle(String battleName) {
        if (selectedCharacter != null) {
            selectedCharacter.clearBattle(battleName);
        }
    }

    public boolean isBattleCleared(String battleName) {
        return selectedCharacter != null && selectedCharacter.isBattleCleared(battleName);
    }

    public boolean isBattleUnlocked(String battleName) {
        return selectedCharacter != null && selectedCharacter.isBattleUnlocked(battleName);
    }

    /**
     * Checks if all battles up to and including the specified battle are cleared
     *
     * @param battleName The name of the battle to check
     * @return true if all previous battles are cleared, false otherwise
     */
    public boolean isPreviousBattleCleared(String battleName) {
        return selectedCharacter != null && selectedCharacter.isPreviousBattleCleared(battleName);
    }

    public MusicPlayer getMusicPlayer() {
        return musicPlayer;
    }
}
