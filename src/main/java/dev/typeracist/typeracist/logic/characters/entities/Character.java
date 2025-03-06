package dev.typeracist.typeracist.logic.characters.entities;

import dev.typeracist.typeracist.logic.characters.Entity;
import dev.typeracist.typeracist.logic.characters.HP;
import dev.typeracist.typeracist.logic.characters.Skill;
import dev.typeracist.typeracist.logic.characters.XP;
import dev.typeracist.typeracist.logic.global.BattleInfo;
import dev.typeracist.typeracist.logic.inventory.Inventory;
import javafx.scene.image.Image;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public abstract class Character extends Entity {
    protected static final HP BASE_HP = new HP(35);
    protected static final int BASE_ATK = 4;
    protected static final int BASE_DEF = 5;
    protected static final int XP_TO_LEVEL_UP = 25;
    protected final Inventory inventory;
    protected final Set<String> clearedBattles = new HashSet<>();
    protected XP xp;
    protected int coin;
    protected String description;

    public Character(HP hp, int atk, int def, Image image, Skill skill, String description) {
        super(hp, atk, def, image, skill);
        this.xp = new XP();
        this.coin = 0100000;
        this.inventory = new Inventory();
        this.description = description;
    }

    public Character(int atk, int def, Image image, Skill skill, String description) {
        this(BASE_HP, atk, def, image, skill, description);
    }

    // public int attack(Entity target, int rawDamage) {
    // int realDamage = Math.max(rawDamage * getTotalAtk() - target.getTotalDef(),
    // 0);
    // target.getHp().damage(realDamage);
    //
    // return realDamage;
    // }

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
        BattleInfo battleInfo = dev.typeracist.typeracist.logic.global.BattleNavigation
                .getNavigationDetails(battleName);
        return battleInfo != null && battleInfo.getPrerequisiteBattle() == null;
    }

    public boolean isPreviousBattleCleared(String battleName) {
        BattleInfo battleInfo = dev.typeracist.typeracist.logic.global.BattleNavigation
                .getNavigationDetails(battleName);
        return battleInfo != null && isBattleCleared(battleInfo.getPrerequisiteBattle());
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

    public String getDescription() {
        return description;
    }
}