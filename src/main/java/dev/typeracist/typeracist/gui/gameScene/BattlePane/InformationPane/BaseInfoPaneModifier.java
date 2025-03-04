package dev.typeracist.typeracist.gui.gameScene.BattlePane.InformationPane;

import javafx.scene.layout.VBox;

public abstract class BaseInfoPaneModifier extends VBox {
    final protected InformationPane subPaneNavigator;

    public BaseInfoPaneModifier(InformationPane informationPane) {
        this.subPaneNavigator = informationPane;
    }

    public void switchToPane(InfoPaneModifierType paneType) {
        getChildren().clear();

        BaseInfoPaneModifier newPane;
        switch (paneType) {
            case TYPING_PANE -> newPane = new TypingInfoPaneModifier(subPaneNavigator);
            default -> throw new IllegalArgumentException("Unknown pane type: " + paneType);
        }

        newPane.initialize();
        getChildren().add(newPane);
    }

    protected abstract void initialize();
}
