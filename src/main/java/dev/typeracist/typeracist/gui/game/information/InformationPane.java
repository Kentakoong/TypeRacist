package dev.typeracist.typeracist.gui.game.information;

import dev.typeracist.typeracist.gui.game.battle.BattlePane;
import dev.typeracist.typeracist.gui.game.information.modifiers.BaseInfoPaneModifier;
import dev.typeracist.typeracist.gui.game.information.modifiers.ItemInfoPaneModifier;
import dev.typeracist.typeracist.gui.game.information.modifiers.TextInfoPaneModifier;
import dev.typeracist.typeracist.gui.game.information.modifiers.TypingInfoPaneModifier;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class InformationPane extends VBox {
    BattlePane battlePane;

    public InformationPane(BattlePane battlePane) {
        setSpacing(5);
        setPadding(new Insets(20));
        setBackground(new Background(new BackgroundFill(Color.GRAY, new CornerRadii(5), Insets.EMPTY)));
        setBorder(new Border(
                new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));

        this.battlePane = battlePane;
    }

    public BaseInfoPaneModifier setToPane(InfoPaneModifierType paneType) {
        getChildren().clear();

        BaseInfoPaneModifier newPane = switch (paneType) {
            case TYPING_PANE -> new TypingInfoPaneModifier(battlePane, this);
            case ITEM_SELECTION_PANE -> new ItemInfoPaneModifier(battlePane, this);
            case TEXT -> new TextInfoPaneModifier(this);
        };

        newPane.initialize();
        return newPane;
    }
}
