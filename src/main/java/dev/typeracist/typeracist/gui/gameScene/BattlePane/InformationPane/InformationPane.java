package dev.typeracist.typeracist.gui.gameScene.BattlePane.InformationPane;

import dev.typeracist.typeracist.gui.gameScene.BattlePane.BattlePane;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class InformationPane extends VBox {
    BattlePane battlePane;

    public InformationPane(BattlePane battlePane) {
        setSpacing(5);
        setPadding(new Insets(20));
        setBackground(new Background(new BackgroundFill(Color.GRAY, new CornerRadii(5), Insets.EMPTY)));
        setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));

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
