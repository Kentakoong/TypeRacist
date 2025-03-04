package dev.typeracist.typeracist.logic.inventory.item;

import dev.typeracist.typeracist.logic.inventory.Item;
import dev.typeracist.typeracist.utils.ResourceName;

public class WhirlwindDagger extends Item {
    private static final int ATTACK_BONUS = 6;
    private static final int STUN_INTERVAL = 3;

    public WhirlwindDagger() {
        super("Whirlwind Dagger",
                "ATK +6. Stun enemy every 3 turns.",
                34,
                ResourceName.IMAGE_SHOP_WHIRLWIND_DAGGER);
    }

    public int getAttackBonus() {
        return ATTACK_BONUS;
    }

    public int getStunInterval() {
        return STUN_INTERVAL;
    }
}