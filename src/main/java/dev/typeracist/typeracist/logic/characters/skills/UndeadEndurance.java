package dev.typeracist.typeracist.logic.characters.skills;

import dev.typeracist.typeracist.gui.gameScene.BattlePane.BattlePane;
import dev.typeracist.typeracist.logic.characters.Skill;

public class UndeadEndurance extends SkillWithProbability {
    private static final double DAMAGE_REDUCTION = 0.2; // 20% damage reduction

    public UndeadEndurance() {
        super("Undead Endurance",
                "Takes 20% less damage from normal attacks.",
                1.0); // Always active
    }

    @Override
    public Skill copy() {
        return new UndeadEndurance();
    }

    @Override
    public void useSkill(BattlePane battlePane) {
        // Passive skill, implementation handled in damage calculation
        super.useSkill(battlePane);
    }

    public double getDamageReduction() {
        return DAMAGE_REDUCTION;
    }
}