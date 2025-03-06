package dev.typeracist.typeracist.logic.characters.entities.character;

import dev.typeracist.typeracist.logic.characters.entities.Character;
import dev.typeracist.typeracist.logic.characters.skills.ShadowSlip;
import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.utils.ResourceName;

public class Assassin extends Character {

    public Assassin() {
        super(5,
                3,
                ResourceManager.getImage(ResourceName.IMAGE_CHARACTER_ASSASSIN),
                new ShadowSlip(0.2),
                "A stealthy character with high critical damage.\nABIL : 20% to dodge attack");
    }
}