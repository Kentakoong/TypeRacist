package dev.typeracist.typeracist.logic.inventory.item;

import dev.typeracist.typeracist.logic.inventory.Item;
import dev.typeracist.typeracist.utils.ResourceName;

public class HealingPotion extends Item {
    private static final int HEAL_AMOUNT = 20;

    public HealingPotion() {
        super("Healing Potion",
                "Heal your character by 20 HP.",
                15,
                ResourceName.IMAGE_SHOP_HEALING_POTION);
    }

    public int getHealAmount() {
        return HEAL_AMOUNT;
    }
}