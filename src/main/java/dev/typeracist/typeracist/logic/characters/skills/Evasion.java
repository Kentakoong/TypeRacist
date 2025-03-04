package dev.typeracist.typeracist.logic.characters.skills;

import dev.typeracist.typeracist.gui.gameScene.BattlePane.BattlePane;
import dev.typeracist.typeracist.logic.characters.Skill;

public class Evasion extends SkillWithProbability {
    public Evasion() {
        super("Evasion",
                "15% chance to dodge the player's attack.",
                0.15);
    }

    @Override
    public Skill copy() {
        return new Evasion();
    }

    @Override
    public void useSkill(BattlePane battlePane) {
        // Dodge logic will be handled in damage calculation
        super.useSkill(battlePane);
    }
}