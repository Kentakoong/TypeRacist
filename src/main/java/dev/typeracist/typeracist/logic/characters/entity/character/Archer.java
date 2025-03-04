package dev.typeracist.typeracist.logic.characters.entity.character;

import dev.typeracist.typeracist.logic.characters.entity.Character;
import dev.typeracist.typeracist.logic.characters.skills.PhantomStrike;
import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.utils.ResourceName;

public class Archer extends Character {
    public Archer() {
        super(4,
                3,
                ResourceManager.getImage(ResourceName.IMAGE_CHARACTER_ARCHER),
                new PhantomStrike(0.5));
    }
}