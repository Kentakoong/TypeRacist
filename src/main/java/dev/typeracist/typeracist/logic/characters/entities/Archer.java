package dev.typeracist.typeracist.logic.characters.entities;

import dev.typeracist.typeracist.logic.characters.Character;
import dev.typeracist.typeracist.logic.characters.skills.PhantomStrike;
import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.utils.ResourceName;

public class Archer extends Character {
    public Archer() {
        super(4,
                3,
                ResourceManager.getImage(ResourceName.IMAGE_CHARACTER_ARCHER),
                new PhantomStrike(0.25),
                "A skilled marksman with excellent range.\nABIL : 25% to do double damage");
    }
}