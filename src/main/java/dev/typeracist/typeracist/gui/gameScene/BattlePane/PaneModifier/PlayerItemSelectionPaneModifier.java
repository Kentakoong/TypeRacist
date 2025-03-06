package dev.typeracist.typeracist.gui.gameScene.BattlePane.PaneModifier;

import dev.typeracist.typeracist.gui.gameScene.BattlePane.BattlePane;
import dev.typeracist.typeracist.gui.gameScene.BattlePane.InformationPane.InfoPaneModifierType;
import dev.typeracist.typeracist.gui.gameScene.BattlePane.InformationPane.ItemInfoPaneModifier;
import dev.typeracist.typeracist.logic.gameScene.BattlePaneStateContext;
import dev.typeracist.typeracist.logic.gameScene.BattlePaneStateManager;
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
