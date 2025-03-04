package dev.typeracist.typeracist.logic.inventory.item;

import dev.typeracist.typeracist.logic.inventory.Item;
import dev.typeracist.typeracist.utils.ResourceName;

public class WoodenShield extends Item {
    private static final int DEFENSE_BONUS = 2;

    public WoodenShield() {
        super("Wooden Shield",
                "Reliable wooden shield. DEF +2.",
                7,
                ResourceName.IMAGE_SHOP_WOODEN_SHIELD);
    }

    public int getDefenseBonus() {
        return DEFENSE_BONUS;
    }
}