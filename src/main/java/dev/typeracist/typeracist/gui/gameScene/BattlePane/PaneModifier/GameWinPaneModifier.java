package dev.typeracist.typeracist.gui.gameScene.BattlePane.PaneModifier;

import dev.typeracist.typeracist.gui.gameScene.BattlePane.BattlePane;
import dev.typeracist.typeracist.logic.gameScene.BattlePaneStateContext;
import dev.typeracist.typeracist.logic.gameScene.BattlePaneStateManager;
import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.utils.ResourceName;
import javafx.scene.control.Label;

public class GameWinPaneModifier extends BasePaneModifier {
    public GameWinPaneModifier(BattlePane battlePane, BattlePaneStateContext context) {
        super(battlePane, context);
    }

    @Override
    public void initialize(BattlePaneStateManager manager) {
        battlePane.getCharacterImage().setImage(ResourceManager.getImage(ResourceName.IMAGE_MAP_ANVIL));
        battlePane.getStatsButton().setDisable(true);
        battlePane.getAttackButton().setDisable(true);
        battlePane.getItemsButton().setDisable(true);
        battlePane.getInformationPane().getChildren().clear();

        Label winMessage = new Label(context.getEnemy().getName() + "took the L");
        winMessage.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 24));

        Label dropValuable = new Label("You earned " + context.getEnemy().getDropXP() + " EXP and "
                + context.getEnemy().getDropCoin() + "coins");
        dropValuable.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 18));

        battlePane.getInformationPane().getChildren().addAll(
                winMessage,
                dropValuable);
    }
}
