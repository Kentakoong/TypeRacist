package dev.typeracist.typeracist.logic.inventory.item;

import dev.typeracist.typeracist.logic.game.battle.BattlePaneStateManager;
import dev.typeracist.typeracist.logic.global.GameLogic;
import dev.typeracist.typeracist.logic.inventory.ActivateOnTurn;
import dev.typeracist.typeracist.logic.inventory.ActivateOnTurnState;
import dev.typeracist.typeracist.logic.inventory.Item;

import java.util.Random;

public class FireGolemDebris extends Item implements ActivateOnTurn {
    private Random random;

    public FireGolemDebris() {
        super("Fire Golem Debris",
                "Beware, this debris is burning.",
                0,
                null);
        this.random = new Random();
    }

    public int getBurnDamage() {
        return random.nextInt(5, 7);
    }

    @Override
    public void activate(BattlePaneStateManager manager) {
        GameLogic.getInstance().getSelectedCharacter().getHp().damage(getBurnDamage());
        GameLogic.getInstance().getSceneManager().showBreadcrumb(
                "Fire Golem Debris burned you.",
                "You take burning damage from the Fire Golem debris.",
                3000
        );
    }

    @Override
    public ActivateOnTurnState getActivateOnTurnState() {
        return ActivateOnTurnState.BOTH;
    }

    @Override
    public Item copy() {
        return new FireGolemDebris();
    }
}