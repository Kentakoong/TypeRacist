package dev.typeracist.typeracist.logic.characters.skills;

import java.util.Random;

import dev.typeracist.typeracist.gui.gameScene.BattlePane.BattlePane;
import dev.typeracist.typeracist.logic.characters.Skill;

public abstract class SkillWithProbability extends Skill {
    private final Random random;
    private final double probability;

    public SkillWithProbability(String name, String description, double probability) {
        super(name, description);
        this.random = new Random();
        this.probability = probability;
    }

    public boolean isProbability() {
        return random.nextDouble() < probability;
    }

    public void useSkill(BattlePane battlePane) {
        if (!isProbability()) {
            return;
        }
    }
}
