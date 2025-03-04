package dev.typeracist.typeracist.logic.characters.skills;

import dev.typeracist.typeracist.gui.gameScene.BattlePane.BattlePane;
import dev.typeracist.typeracist.logic.characters.Skill;

public class MagicWand extends Skill {
    public MagicWand() {
        super("Mystic Paralysis Wand",
                "A magical wand that temporarily immobilizes enemies, rendering them stunned for one turn.", 3);
    }

    @Override
    public Skill copy() {
        return new MagicWand();
    }

    @Override
    public void useSkill(BattlePane battlePane) {
        // TODO: Implement the skill logic
    }

}
