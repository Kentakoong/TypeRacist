package dev.typeracist.typeracist.gui.gameScene.InformationPane;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public abstract class BaseInfoPane extends VBox {
    final protected Font baseFont;
    final protected InformationPane subPaneNavigator;

    public BaseInfoPane(InformationPane subPaneNavigator) {
        this.subPaneNavigator = subPaneNavigator;

        setSpacing(5);
        setPadding(new Insets(10));
        setBackground(new Background(new BackgroundFill(Color.GRAY, new CornerRadii(5), Insets.EMPTY)));
        setBorder(new Border(new BorderStroke(Color.LIGHTGREY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));

        baseFont = loadBaseFont();
    }

    public void initialize() {
        initializeContent();
    }

    public void switchToPane(InfoPaneType paneType) {
        getChildren().clear();

        BaseInfoPane newPane;
        switch (paneType) {
            case STATS_OPTION_PANE -> newPane = new StatsOptionInfoPane(subPaneNavigator);
            case TYPING_PANE -> newPane = new TypingInfoPane(subPaneNavigator);
            default -> throw new IllegalArgumentException("Unknown pane type: " + paneType);
        }

        getChildren().add(newPane);
    }

    protected Font loadBaseFont() {
        return Font.loadFont(getClass().getResourceAsStream("/dev/typeracist/typeracist/fonts/DepartureMono-Regular.otf"), 36);
    }

    protected abstract void initializeContent();
}
