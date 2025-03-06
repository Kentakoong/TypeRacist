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
        for (String className : jsonItems.keySet()) {
            int amount = jsonItems.getInt(className);

            Item item = createItemByClassName(className);
            if (item != null) {
                items.put(item, amount);
            }
        }
    }

    private Item createItemByClassName(String className) {
        return switch (className) {
            case "WoodenShield" -> new WoodenShield();
            case "HealingPotion" -> new HealingPotion();
            case "FriedChicken" -> new FriedChicken();
            case "Typewriter" -> new Typewriter();
            case "TimePotion" -> new TimePotion();
            case "PotionOfTypeswift" -> new PotionOfTypeswift();
            case "WhirlwindDagger" -> new WhirlwindDagger();
            default -> null;
        };
    }
}
