package dev.typeracist.typeracist.logic.characters.entities;

import dev.typeracist.typeracist.logic.characters.Entity;
import dev.typeracist.typeracist.logic.characters.HP;
import dev.typeracist.typeracist.logic.characters.Skill;
import dev.typeracist.typeracist.logic.characters.XP;
import dev.typeracist.typeracist.logic.inventory.Inventory;
import javafx.scene.image.Image;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Character extends Entity {
    protected static final HP BASE_HP = new HP(35);
    protected static final int BASE_ATK = 4;
    protected static final int BASE_DEF = 5;
    protected static final int XP_TO_LEVEL_UP = 25;
    protected final Inventory inventory;
    protected XP xp;
    protected int coin;
    protected final Set<String> clearedBattles = new HashSet<>();
    // Map of battle prerequisites - each battle and what needs to be cleared before
    // it
    private static final Map<String, String> BATTLE_PREREQUISITES = new HashMap<>();

    // Initialize the battle prerequisites map
    static {
        BATTLE_PREREQUISITES.put("BATTLE1", null); // First battle has no prerequisites
        BATTLE_PREREQUISITES.put("BATTLE2", "BATTLE1");
        BATTLE_PREREQUISITES.put("BATTLE3", "BATTLE2");
        BATTLE_PREREQUISITES.put("BATTLE4", "BATTLE3");
        BATTLE_PREREQUISITES.put("BATTLE5", "BATTLE4");
        BATTLE_PREREQUISITES.put("BATTLE6", "BATTLE5");
        BATTLE_PREREQUISITES.put("BATTLE7", "BATTLE6");
        BATTLE_PREREQUISITES.put("BATTLE8", "BATTLE7");
        BATTLE_PREREQUISITES.put("BATTLE9", "BATTLE8");
        BATTLE_PREREQUISITES.put("BOSS", "BATTLE9");
        // Add other battle prerequisites as needed
    }

    public Character(HP hp, int atk, int def, Image image, Skill skill) {
        super(hp, atk, def, image, skill);
        this.xp = new XP();
        this.coin = 0;
        this.inventory = new Inventory();
    }

    public Character(int atk, int def, Image image, Skill skill) {
        this(BASE_HP, atk, def, image, skill);
    }

    public int attack(Entity target, int rawDamage) {
        int realDamage = Math.max(rawDamage * getTotalAtk() - target.getTotalDef(), 0);
        target.getHp().damage(realDamage);

        return realDamage;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public int getCoin() {
        return coin;
    }

    public void gainCoin(int amount) {
        this.coin += amount;
    }

    public XP getXp() {
        return xp;
    }

    public void spendCoin(int amount) {
        if (amount > coin) {
            throw new IllegalStateException("Not enough coins");
        }
        this.coin -= amount;
    }

    public void clearBattle(String battleName) {
        clearedBattles.add(battleName);
    }

    public boolean isBattleCleared(String battleName) {
        return clearedBattles.contains(battleName);
    }

    public Set<String> getClearedBattles() {
        return clearedBattles;
    }

    /**
     * Checks if a battle is unlocked based on prerequisites
     * 
     * @param battleName The name of the battle to check
     * @return true if the battle is unlocked, false otherwise
     */
    public boolean isBattleUnlocked(String battleName) {
        // If the battle doesn't exist in our prerequisites map, it's not a valid battle
        if (!BATTLE_PREREQUISITES.containsKey(battleName)) {
            return false;
        }

        // If the battle has no prerequisites, it's always unlocked
        String prerequisite = BATTLE_PREREQUISITES.get(battleName);
        if (prerequisite == null) {
            return true;
        }

        // Check if the immediate prerequisite is cleared
        return isBattleCleared(prerequisite);
    }

    /**
     * Checks if all battles up to and including the specified battle are cleared
     * 
     * @param battleName The name of the battle to check
     * @return true if all previous battles are cleared, false otherwise
     */
    public boolean areAllPreviousBattlesCleared(String battleName) {
        // If the battle doesn't exist in our prerequisites map, it's not a valid battle
        if (!BATTLE_PREREQUISITES.containsKey(battleName)) {
            return false;
        }

        // Start from the current battle's prerequisite and work backwards
        String currentBattle = BATTLE_PREREQUISITES.get(battleName);

        // If there's no prerequisite, then there are no previous battles to check
        if (currentBattle == null) {
            return true;
        }

        // Check each prerequisite in the chain
        while (currentBattle != null) {
            if (!isBattleCleared(currentBattle)) {
                return false;
            }
            currentBattle = BATTLE_PREREQUISITES.get(currentBattle);
        }

        return true;
    }

    public void saveToJson(String filePath) {
        JSONObject characterData = new JSONObject();
        characterData.put("clearedBattles", new JSONArray(this.clearedBattles));
        characterData.put("inventory", this.inventory.getItems());
        characterData.put("coin", this.coin);
        characterData.put("xp", this.xp.getXp());

        try (FileWriter file = new FileWriter(filePath)) {
            file.write(characterData.toString(2)); // Pretty print JSON
            System.out.println("Character saved successfully to " + filePath);
        } catch (IOException e) {
            System.err.println("Error saving character: " + e.getMessage());
        }
    }

    public void loadFromJson(String filePath) {
        try {
            if (Files.exists(Paths.get(filePath))) {
                String content = new String(Files.readAllBytes(Paths.get(filePath)));
                JSONObject characterData = new JSONObject(content);

                this.clearedBattles.clear();
                JSONArray battlesArray = characterData.optJSONArray("clearedBattles");
                if (battlesArray != null) {
                    for (int i = 0; i < battlesArray.length(); i++) {
                        this.clearedBattles.add(battlesArray.getString(i));
                    }
                }

                this.inventory.loadItems(characterData.optJSONObject("inventory"));
                this.coin = characterData.optInt("coin", 0);
                this.xp.setXp(characterData.optInt("xp", 0));

                System.out.println("Character loaded successfully from " + filePath);
            }
        } catch (IOException e) {
            System.err.println("Error loading character: " + e.getMessage());
        }
    }
}