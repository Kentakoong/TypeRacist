package dev.typeracist.typeracist.gui.gameScene.BattlePane.InformationPane;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class InformationPane extends VBox {
    public InformationPane() {
        setSpacing(5);
        setPadding(new Insets(20));
        setBackground(new Background(new BackgroundFill(Color.GRAY, new CornerRadii(5), Insets.EMPTY)));
        setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));

        setToPane(InfoPaneModifierType.STATS_OPTION_PANE);
    }

    public BaseInfoPaneModifier setToPane(InfoPaneModifierType paneType) {
        getChildren().clear();

        BaseInfoPaneModifier newPane = switch (paneType) {
            case STATS_OPTION_PANE -> new StatsOptionInfoPaneModifier(this);
            case TYPING_PANE -> new TypingInfoPaneModifier(this);
        };

        newPane.initialize();
        getChildren().add(newPane);

        return newPane;
    }
}
