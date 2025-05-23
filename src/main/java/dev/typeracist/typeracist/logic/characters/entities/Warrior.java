package dev.typeracist.typeracist.logic.characters.entities;

import dev.typeracist.typeracist.logic.characters.Character;
import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.utils.ResourceName;

public class Warrior extends Character {
    public Warrior() {
        super(4, 6, ResourceManager.getImage(ResourceName.IMAGE_CHARACTER_WARRIOR), null,
                "A brave fighter with strong melee attacks.\nABIL : NONE");
    }
}