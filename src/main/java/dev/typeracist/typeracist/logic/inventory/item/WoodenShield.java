package dev.typeracist.typeracist.logic.inventory.item;

import dev.typeracist.typeracist.logic.global.GameLogic;
import dev.typeracist.typeracist.logic.inventory.ActivateNow;
import dev.typeracist.typeracist.logic.inventory.Item;
import dev.typeracist.typeracist.utils.ResourceName;

public class WoodenShield extends Item implements ActivateNow {
    private static final int DEFENSE_BONUS = 2;

    public WoodenShield() {
        super("Wooden Shield",
                "Reliable wooden shield. DEF +2.",
                30,
                ResourceName.IMAGE_SHOP_WOODEN_SHIELD);
    }

    public int getDefenseBonus() {
        return DEFENSE_BONUS;
    }

    @Override
    public void activate() {
        GameLogic.getInstance().getSelectedCharacter().addExtraDef(DEFENSE_BONUS);
        GameLogic.getInstance().getSceneManager().showBreadcrumb(
                "WoodenShield is activated",
                GameLogic.getInstance().getPlayerName() + " def got boost by " + DEFENSE_BONUS,
                3000
        );
    }

    @Override
    public Item copy() {
        return new WoodenShield();
    }
}