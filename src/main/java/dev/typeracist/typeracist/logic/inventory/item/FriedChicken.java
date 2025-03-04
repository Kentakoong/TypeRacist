package dev.typeracist.typeracist.logic.inventory.item;

import dev.typeracist.typeracist.logic.inventory.Item;
import dev.typeracist.typeracist.utils.ResourceName;

public class FriedChicken extends Item {
    private static final int HEAL_AMOUNT = 10;
    private static final int DURATION = 2;

    public FriedChicken() {
        super("Fried Chicken",
                "Heal your character by 10 HP, lasts for 2 turns.",
                20,
                ResourceName.IMAGE_SHOP_FRIED_CHICKEN);
    }

    public int getHealAmount() {
        return HEAL_AMOUNT;
    }

    public int getDuration() {
        return DURATION;
    }
}