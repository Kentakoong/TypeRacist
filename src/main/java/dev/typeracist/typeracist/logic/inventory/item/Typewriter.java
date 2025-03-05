package dev.typeracist.typeracist.logic.inventory.item;

import dev.typeracist.typeracist.logic.global.GameLogic;
import dev.typeracist.typeracist.logic.inventory.ActivateNow;
import dev.typeracist.typeracist.logic.inventory.Item;
import dev.typeracist.typeracist.utils.ResourceName;

public class Typewriter extends Item implements ActivateNow {
    public Typewriter() {
        super("Typewriter",
                "Nothing, just a typewriter.",
                696969,
                ResourceName.IMAGE_SHOP_TYPEWRITER);
    }

    @Override
    public void activate() {
        GameLogic.getInstance().getSceneManager().showBreadcrumb(
                "Typewriter is activated",
                "Nothing happens",
                3000
        );
    }
}