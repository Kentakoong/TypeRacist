package dev.typeracist.typeracist.logic.inventory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
}
