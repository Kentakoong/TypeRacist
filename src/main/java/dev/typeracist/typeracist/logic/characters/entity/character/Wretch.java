package dev.typeracist.typeracist.logic.characters.entity.character;

import dev.typeracist.typeracist.logic.characters.entity.Character;
import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.utils.ResourceName;

public class Wretch extends Character {
    public Wretch() {
        super(3, 3, ResourceManager.getImage(ResourceName.IMAGE_CHARACTER_WRETCH), null);
    }
}