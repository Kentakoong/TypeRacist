package dev.typeracist.typeracist.logic.characters.entities.character;

import dev.typeracist.typeracist.logic.characters.entities.Character;
import dev.typeracist.typeracist.logic.characters.skills.MagicWand;
import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.utils.ResourceName;

public class Wizard extends Character {

    public Wizard() {
        super(3,
                2,
                ResourceManager.getImage(ResourceName.IMAGE_CHARACTER_WIZARD),
                new MagicWand());
    }

}