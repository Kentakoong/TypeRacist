package dev.typeracist.typeracist.logic.characters.entities;

import dev.typeracist.typeracist.logic.characters.Character;
import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.utils.ResourceName;

public class Wretch extends Character {
    public Wretch() {
        super(6, 3, ResourceManager.getImage(ResourceName.IMAGE_CHARACTER_WRETCH), null,
                "A mysterious wanderer with unknown abilities (who wants challenge)\nABIL : NONE");
    }
}