package dev.typeracist.typeracist.gui.game.battle.modifiers;

import dev.typeracist.typeracist.gui.game.battle.BattlePane;
import dev.typeracist.typeracist.gui.game.information.InfoPaneModifierType;
import dev.typeracist.typeracist.gui.game.information.modifiers.ItemInfoPaneModifier;
import dev.typeracist.typeracist.logic.game.battle.BattlePaneStateContext;
import dev.typeracist.typeracist.logic.game.battle.BattlePaneStateManager;
import dev.typeracist.typeracist.logic.global.GameLogic;

public class PlayerItemSelectionPaneModifier extends BasePaneModifier {
    public PlayerItemSelectionPaneModifier(BattlePane battlePane, BattlePaneStateContext context) {
        super(battlePane, context);
    }

    @Override
    public void initialize(BattlePaneStateManager manager) {
        ItemInfoPaneModifier itemInfoPaneModifier =
                (ItemInfoPaneModifier) battlePane.getInformationPane().setToPane(InfoPaneModifierType.ITEM_SELECTION_PANE);

        if (GameLogic.getInstance().getSelectedCharacter().getInventory().getItems().isEmpty()) {
            returnControl();
        } else {
            itemInfoPaneModifier.getSkipButton().setOnAction(event -> returnControl());
        }

    }
}
