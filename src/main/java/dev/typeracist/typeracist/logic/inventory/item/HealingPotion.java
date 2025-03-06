package dev.typeracist.typeracist.logic.inventory.item;

import dev.typeracist.typeracist.logic.global.GameLogic;
import dev.typeracist.typeracist.logic.inventory.ActivateNow;
import dev.typeracist.typeracist.logic.inventory.Item;
import dev.typeracist.typeracist.utils.ResourceName;

public class HealingPotion extends Item implements ActivateNow {
    private static final int HEAL_AMOUNT = 15;

    public HealingPotion() {
        super("Healing Potion",
                "Heal your character by 15 HP.",
                15,
                ResourceName.IMAGE_SHOP_HEALING_POTION);
    }

    public int getHealAmount() {
        return HEAL_AMOUNT;
    }

    @Override
    public void activate() {
        GameLogic.getInstance().getSelectedCharacter().getHp().heal(HEAL_AMOUNT);
        GameLogic.getInstance().getSceneManager().showBreadcrumb(
                "HealingPotion is activated",
                GameLogic.getInstance().getPlayerName() + " is healed by " + HEAL_AMOUNT,
                3000
        );
    }

    @Override
    public Item copy() {
        return new HealingPotion();
    }
}