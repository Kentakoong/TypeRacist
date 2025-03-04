package dev.typeracist.typeracist.logic.characters.entities.character;

import dev.typeracist.typeracist.logic.characters.entities.Character;
import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.utils.ResourceName;

public class Warrior extends Character {
    public Warrior() {
        super(4, 5, ResourceManager.getImage(ResourceName.IMAGE_CHARACTER_WARRIOR), null);
    }
}