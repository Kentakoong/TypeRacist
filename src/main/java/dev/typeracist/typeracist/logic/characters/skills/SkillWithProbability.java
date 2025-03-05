package dev.typeracist.typeracist.logic.characters.skills;

import dev.typeracist.typeracist.logic.characters.Skill;
import dev.typeracist.typeracist.logic.characters.SkillActivationOnState;

import java.util.Random;

public abstract class SkillWithProbability extends Skill {
    private final Random random;
    private final double probability;

    public SkillWithProbability(String name, String description, SkillActivationOnState activationOnState, double probability) {
        super(name, description, activationOnState);
        this.random = new Random();
        this.probability = probability;
    }

    public boolean isProbability() {
        return random.nextDouble() < probability;
    }
}
