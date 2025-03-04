package dev.typeracist.typeracist.logic.inventory.item;

import dev.typeracist.typeracist.logic.inventory.Item;
import dev.typeracist.typeracist.utils.ResourceName;

public class TimePotion extends Item {
    private static final double TIME_INCREASE = 2.5;
    private static final int DURATION = 1;

    public TimePotion() {
        super("Time Potion",
                "Increase the timer by 2.5 seconds. 1 turn.",
                25,
                ResourceName.IMAGE_SHOP_TIME_POTION);
    }

    public double getTimeIncrease() {
        return TIME_INCREASE;
    }

    public int getDuration() {
        return DURATION;
    }
}