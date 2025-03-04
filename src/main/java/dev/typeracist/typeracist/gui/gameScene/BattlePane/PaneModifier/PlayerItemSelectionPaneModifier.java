package dev.typeracist.typeracist.gui.gameScene.BattlePane.PaneModifier;

import dev.typeracist.typeracist.gui.gameScene.BattlePane.BattlePane;
import dev.typeracist.typeracist.logic.gameScene.BattlePaneStateContext;
import dev.typeracist.typeracist.logic.gameScene.BattlePaneStateManager;
import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.utils.ResourceName;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class PlayerItemSelectionPaneModifier extends BasePaneModifier {
    public PlayerItemSelectionPaneModifier(BattlePane battlePane, BattlePaneStateContext context) {
        super(battlePane, context);
    }

    @Override
    public void initialize(BattlePaneStateManager manager) {
        battlePane.getInformationPane().getChildren().clear();

        Label modifierName = new Label("Player Item Selection");
        modifierName.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 24));

        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(10));

        for (int i = 1; i <= 3; i++) {
            Button button = createRoundedButton("Item " + i, 50, 50);
            hBox.getChildren().add(button);
        }

        Button skipButton = createRoundedButton("Skip", 50, 50);
        skipButton.setOpacity(0.5);
        skipButton.setOnAction(event -> {
            System.out.println("Skip button pressed");
            returnControl();
        });

        hBox.getChildren().add(skipButton);

        battlePane.getInformationPane().getChildren().addAll(
                modifierName,
                hBox
        );
    }

    private Button createRoundedButton(String text, double width, double height) {
        Button button = new Button(text);
        button.setMinSize(width, height);

        // Set background with rounded corners
        button.setBackground(new Background(new BackgroundFill(
                Color.LIGHTGRAY, new CornerRadii(10), Insets.EMPTY)));

        // Set border with rounded corners
        button.setBorder(new Border(new BorderStroke(
                Color.DARKGRAY, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(1))));

        button.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 14));
        return button;
    }
}
