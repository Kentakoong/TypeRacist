package dev.typeracist.typeracist.logic.characters.skills;

import dev.typeracist.typeracist.gui.gameScene.BattlePane.BattlePane;
import dev.typeracist.typeracist.logic.characters.Skill;

public class Hex extends SkillWithProbability {
    public Hex() {
        super("Hex",
                "25% chance to cast every turn, if inflict player can't use their items",
                0.25);
    }

    @Override
    public Skill copy() {
        return new Hex();
    }

    @Override
    public void useSkill(BattlePane battlePane) {
        // Hex effect will be handled in battle logic
        super.useSkill(battlePane);
    }
}