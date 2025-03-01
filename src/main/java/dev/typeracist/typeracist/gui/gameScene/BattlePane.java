package dev.typeracist.typeracist.gui.gameScene;

import dev.typeracist.typeracist.gui.gameScene.InformationPane.InfoPaneType;
import dev.typeracist.typeracist.gui.gameScene.InformationPane.InformationPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class BattlePane extends VBox {
    public BattlePane() {
        setSpacing(10);
        setPadding(new Insets(10));
        setAlignment(Pos.CENTER);

        setBackground(new Background(new BackgroundFill(Color.web("#484848"), CornerRadii.EMPTY, Insets.EMPTY)));

        Label characterImage = new Label("(Character picture.png)");
        characterImage.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        characterImage.setTextFill(Color.WHITE);

        InformationPane enemyInfoBox = new InformationPane();

        // HP Display
        HBox hpBox = new HBox(10);
        hpBox.setAlignment(Pos.CENTER);

        Label playerHp = createHpLabel("ur HP : 69 / 69", Color.BLUE, Color.WHITE);
        Label enemyHp = createHpLabel("Enemy HP : 42 / 42", Color.RED, Color.WHITE);

        hpBox.getChildren().addAll(playerHp, enemyHp);

        // Buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);

        Button attackButton = createButton("Attack!");
        attackButton.setOnAction(event -> {
            enemyInfoBox.switchToPane(InfoPaneType.TYPING_PANE);
        });

        Button statsButton = createButton("Stats");
        statsButton.setOnAction(event -> {
            enemyInfoBox.switchToPane(InfoPaneType.STATS_OPTION_PANE);
        });

        Button itemsButton = createButton("Items");

        buttonBox.getChildren().addAll(attackButton, statsButton, itemsButton);

        // Layout
        getChildren().addAll(characterImage, enemyInfoBox, hpBox, buttonBox);
    }

    private Label createStyledLabel(String text, boolean bold) {
        Label label = new Label(text);
        label.setFont(Font.font("Arial", bold ? FontWeight.BOLD : FontWeight.NORMAL, 14));
        label.setTextFill(Color.LIGHTGRAY);
        return label;
    }

    private Label createHpLabel(String text, Color bgColor, Color textColor) {
        Label label = new Label(text);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        label.setTextFill(textColor);

        // Background for HP labels
        label.setBackground(new Background(new BackgroundFill(bgColor, new CornerRadii(5), Insets.EMPTY)));
        label.setPadding(new Insets(5));

        // Border for HP labels
        label.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(2))));
        return label;
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        button.setPrefWidth(80);
        return button;
    }
}
