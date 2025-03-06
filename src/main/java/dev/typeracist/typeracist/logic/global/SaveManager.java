package dev.typeracist.typeracist.logic.global;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import dev.typeracist.typeracist.logic.characters.entities.Character;
import dev.typeracist.typeracist.logic.inventory.Item;
import dev.typeracist.typeracist.utils.Difficulty;

public class SaveManager {
    private static final String SAVE_DIRECTORY = System.getProperty("user.home") + File.separator + ".typeracist";
    private static final String SAVE_FILE = SAVE_DIRECTORY + File.separator;

    public static final String SAVE_FILE_CHARACTER = SAVE_FILE + "character.json";
    public static final String SAVE_FILE_SETTINGS = SAVE_FILE + "settings.json";

    public static Character getCharacter() {
        System.out.println("current difficulty: " + GameLogic.getInstance().getCurrentDifficulty());
        Character currentCharacter = GameLogic.getInstance().getSelectedCharacter();
        Difficulty currentDifficulty = GameLogic.getInstance().getCurrentDifficulty();
        String characterType = currentCharacter.getClass().getSimpleName();

        JSONObject saveData = loadExistingSave(SAVE_FILE_CHARACTER);
        JSONObject characterData = saveData.optJSONObject(characterType);

        currentCharacter.getClearedBattles().clear();

        if (characterData != null) {
            JSONObject difficultyData = characterData.optJSONObject(currentDifficulty.name());
            if (difficultyData != null) {
                // Load cleared battles
                JSONArray clearedBattles = difficultyData.optJSONArray("clearedBattles");
                if (clearedBattles != null) {
                    for (int i = 0; i < clearedBattles.length(); i++) {
                        currentCharacter.clearBattle(clearedBattles.getString(i));
                    }
                }

                // Load inventory
                currentCharacter.getInventory().loadItems(difficultyData.optJSONObject("inventory"));

                // Load coin and XP
                if (difficultyData.optInt("coin") != currentCharacter.getCoin()) {
                    currentCharacter.gainCoin(difficultyData.optInt("coin", 0) - currentCharacter.getCoin());
                }

                if (difficultyData.optInt("xp") != currentCharacter.getXp().getXp()) {
                    currentCharacter.getXp().setXp(difficultyData.optInt("xp"));
                }

                if (difficultyData.optInt("atk") != currentCharacter.getBaseAtk()) {
                    currentCharacter.setBaseAtk(difficultyData.optInt("atk"));
                }

                if (difficultyData.optInt("def") != currentCharacter.getBaseDef()) {
                    currentCharacter.setBaseDef(difficultyData.optInt("def"));
                }

                if (difficultyData.optInt("hp") != currentCharacter.getHp().getCurrentHP()) {
                    currentCharacter.getHp().setCurrentHP(difficultyData.optInt("hp"));
                }

                if (difficultyData.optInt("maxHP") != currentCharacter.getHp().getMaxHP()) {
                    currentCharacter.getHp().setMaxHP(difficultyData.optInt("maxHP"));
                }

                System.out.println("Character loaded successfully from " + SAVE_FILE_CHARACTER);
            }
        }
        return currentCharacter;
    }

    public static void saveCharacter() {
        Character currentCharacter = GameLogic.getInstance().getSelectedCharacter();
        Difficulty currentDifficulty = GameLogic.getInstance().getCurrentDifficulty();
        String characterType = currentCharacter.getClass().getSimpleName();

        JSONObject saveData = loadExistingSave(SAVE_FILE_CHARACTER); // Load existing save file
        JSONObject characterData = saveData.optJSONObject(characterType);
        if (characterData == null) {
            characterData = new JSONObject();
        }

        // Prepare new difficulty data
        JSONObject difficultyData = new JSONObject();
        difficultyData.put("clearedBattles", new JSONArray(currentCharacter.getClearedBattles()));

        // Convert inventory to name:amount format
        JSONObject inventoryData = new JSONObject();
        for (Map.Entry<Item, Integer> entry : currentCharacter.getInventory().getItems().entrySet()) {
            Item item = entry.getKey();
            int amount = entry.getValue();
            inventoryData.put(item.getName(), amount);
        }
        difficultyData.put("inventory", inventoryData);

        difficultyData.put("coin", currentCharacter.getCoin());
        difficultyData.put("xp", currentCharacter.getXp().getXp());
        difficultyData.put("atk", currentCharacter.getBaseAtk());
        difficultyData.put("def", currentCharacter.getBaseDef());
        difficultyData.put("hp", currentCharacter.getHp().getCurrentHP());
        difficultyData.put("maxHP", currentCharacter.getHp().getMaxHP());

        // Update or add difficulty-specific data
        characterData.put(currentDifficulty.name(), difficultyData);
        saveData.put(characterType, characterData);

        // Ensure the directory exists before saving
        File saveFile = new File(SAVE_FILE_CHARACTER);
        File parentDir = saveFile.getParentFile(); // Get the parent directory

        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs(); // Create directories if they don't exist
        }

        try (FileWriter file = new FileWriter(saveFile)) {
            file.write(saveData.toString(2)); // Pretty print
            System.out.println("Character saved successfully to " + SAVE_FILE_CHARACTER);
        } catch (IOException e) {
            System.err.println("Error saving character: " + e.getMessage());
        }

    }

    public static void loadSettings() {
        JSONObject saveData = loadExistingSave(SAVE_FILE_SETTINGS);
        GameLogic.getInstance().getMusicPlayer().setVolumeLevel(saveData.optInt("volume", 3));
    }

    public static void saveSettings() {
        JSONObject saveData = loadExistingSave(SAVE_FILE_SETTINGS);
        saveData.put("volume", GameLogic.getInstance().getMusicPlayer().getVolumeLevel());

        File saveFile = new File(SAVE_FILE_SETTINGS);
        File parentDir = saveFile.getParentFile(); // Get the parent directory

        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs(); // Create directories if they don't exist
        }

        try (FileWriter file = new FileWriter(saveFile)) {
            file.write(saveData.toString(2));
            System.out.println("Settings saved successfully to " + SAVE_FILE_SETTINGS);
        } catch (IOException e) {
            System.err.println("Error saving settings: " + e.getMessage());
        }
    }

    private static JSONObject loadExistingSave(String fileName) {
        try {
            if (saveFileExists(fileName)) {
                String content = new String(Files.readAllBytes(Paths.get(fileName)));
                return new JSONObject(content);
            }
        } catch (IOException e) {
            System.err.println("Error loading existing save: " + e.getMessage());
        }
        return new JSONObject();
    }

    public static boolean saveFileExists(String fileName) {
        return Files.exists(Paths.get(fileName));
    }

    public static void deleteSaveFile(String fileName) {
        File saveFile = new File(fileName);
        if (saveFile.exists()) {
            saveFile.delete();
            System.out.println("Save file deleted: " + saveFile);
        }
    }
}