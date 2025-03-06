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
                new MagicWand(),
                "A master of elemental magic and spells.\nATK : 3\nDEF : 4\nABIL : Magic wand(item) stun enemy for 1 turn (usable every 3 turns)");
    }

}