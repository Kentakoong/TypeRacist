package dev.typeracist.typeracist.logic.inventory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import dev.typeracist.typeracist.logic.inventory.item.*;

public class Inventory {
    private final Map<Item, Integer> items;

    public Inventory() {
        this.items = new HashMap<>();
    }

    public void addItem(Item item, int amount) {
        items.put(item, items.getOrDefault(item, 0) + amount);
    }

    public void addItem(Item item) {
        addItem(item, 1);
    }

    public void removeItem(Item item, int amount) {
        items.computeIfPresent(item, (key, value) -> (value > amount) ? value - amount : null);
    }

    public int getItemAmount(Item item) {
        return items.getOrDefault(item, 0);
    }

    public Map<Item, Integer> getItems() {
        return Collections.unmodifiableMap(items);
    }

    public void loadItems(JSONObject jsonItems) {
        if (jsonItems == null) {
            return;
        }

        items.clear();
        for (String itemName : jsonItems.keySet()) {
            int amount = jsonItems.getInt(itemName);

            Item item = createItemByName(itemName);
            if (item != null) {
                items.put(item, amount);
            }
        }
    }

    private Item createItemByName(String name) {
        return switch (name.toLowerCase()) {
            case "wooden shield" -> new WoodenShield();
            case "healing potion" -> new HealingPotion();
            case "fried chicken" -> new FriedChicken();
            case "typewriter" -> new Typewriter();
            case "time potion" -> new TimePotion();
            case "potion of typeswift" -> new PotionOfTypeswift();
            case "whirlwind dagger" -> new WhirlwindDagger();
            default -> null;
        };
    }
}
