package dev.typeracist.typeracist.gui.gameScene.BattlePane;

import dev.typeracist.typeracist.gui.gameScene.BattlePane.InformationPane.InformationPane;
import dev.typeracist.typeracist.gui.global.ThemedButton;
import dev.typeracist.typeracist.logic.gameScene.BattlePaneStateContext;
import dev.typeracist.typeracist.logic.global.GameLogic;
import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.utils.ResourceName;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;


public class BattlePane extends VBox {
    private BattlePaneStateContext battlePaneStateContext;
    private InformationPane informationPane;
    private ImageView characterImage;
    private HealthBar playerHpBar;
    private HealthBar enemyHpBar;
    private Button statsButton;

    public BattlePane(BattlePaneStateContext battleSceneStateContext) {
        this.battlePaneStateContext = battleSceneStateContext;

        setSpacing(10);
        setPadding(new Insets(20, 10, 10, 10));
        setAlignment(Pos.TOP_CENTER);
        setBackground(new Background(new BackgroundFill(Color.web("#484848"), CornerRadii.EMPTY, Insets.EMPTY)));

        setFocusTraversable(true);
        initialize();
    }

    private TextFlow createStatLabel(String statName, int total, int base, int extra) {
        Text statText = new Text(statName + ": " + total + " ");
        statText.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 16));

        Text detailsText;
        if (extra != 0) {
            detailsText = new Text("(" + base + " + " + extra + ")");
        } else {
            detailsText = new Text("");
        }
        detailsText.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 16));
        detailsText.setFill(Color.GREEN);

        return new TextFlow(statText, detailsText);
    }

    public HBox createStatsPane() {
        // Helper method to create stat labels with formatted text

        // First VBox (Player)
        VBox vbox1 = new VBox(10);
        vbox1.setAlignment(Pos.CENTER);
        vbox1.setBackground(new Background(new BackgroundFill(Color.web("#E3E3E3"), new CornerRadii(10), Insets.EMPTY)));
        vbox1.setPadding(new Insets(20));
        vbox1.setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(2))));

        ImageView characterImage = new ImageView(GameLogic.getInstance().getSelectedCharacter().getImage());
        Label characterNameLabel = new Label(GameLogic.getInstance().getPlayerName());
        characterNameLabel.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 18));

        TextFlow characterAtkLabel = createStatLabel("ATK", GameLogic.getInstance().getSelectedCharacter().getTotalAtk(),
                GameLogic.getInstance().getSelectedCharacter().getBaseAtk(), GameLogic.getInstance().getSelectedCharacter().getExtraAtk());
        TextFlow characterHpLabel = createStatLabel("HP", GameLogic.getInstance().getSelectedCharacter().getHp().getCurrentHP(), 0, 0);
        TextFlow characterDefLabel = createStatLabel("DEF", GameLogic.getInstance().getSelectedCharacter().getTotalDef(),
                GameLogic.getInstance().getSelectedCharacter().getBaseDef(), GameLogic.getInstance().getSelectedCharacter().getExtraDef());

        vbox1.getChildren().addAll(characterImage, characterNameLabel, characterAtkLabel, characterHpLabel, characterDefLabel);

        // Second VBox (Enemy)
        VBox vbox2 = new VBox(10);
        vbox2.setAlignment(Pos.CENTER);
        vbox2.setBackground(new Background(new BackgroundFill(Color.web("#E3E3E3"), new CornerRadii(10), Insets.EMPTY)));
        vbox2.setPadding(new Insets(20));
        vbox2.setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(2))));

        ImageView enemyImage = new ImageView(battlePaneStateContext.getEnemy().getImage());
        Label enemyNameLabel = new Label(battlePaneStateContext.getEnemy().getName());
        enemyNameLabel.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 18));

        TextFlow enemyAtkLabel = createStatLabel("ATK", battlePaneStateContext.getEnemy().getTotalAtk(),
                battlePaneStateContext.getEnemy().getBaseAtk(), battlePaneStateContext.getEnemy().getExtraAtk());
        TextFlow enemyHpLabel = createStatLabel("HP", battlePaneStateContext.getEnemy().getHp().getCurrentHP(), 0, 0);
        TextFlow enemyDefLabel = createStatLabel("DEF", battlePaneStateContext.getEnemy().getTotalDef(),
                battlePaneStateContext.getEnemy().getBaseDef(), battlePaneStateContext.getEnemy().getExtraDef());

        vbox2.getChildren().addAll(enemyImage, enemyNameLabel, enemyAtkLabel, enemyHpLabel, enemyDefLabel);

        // HBox containing both VBoxes
        HBox hbox = new HBox(20);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(vbox1, vbox2);

        return hbox;
    }

    public BattlePaneStateContext getStateContext() {
        return battlePaneStateContext;
    }

    public InformationPane getInformationPane() {
        return informationPane;
    }

    public ImageView getCharacterImage() {
        return characterImage;
    }

    public HealthBar getPlayerHpBar() {
        return playerHpBar;
    }

    public HealthBar getEnemyHpBar() {
        return enemyHpBar;
    }

    private void initialize() {
        // Create exit button
        statsButton = new ThemedButton("Show stats", Color.DARKGREY);
        statsButton.setTextFill(Color.BLACK);
        statsButton.setFocusTraversable(false);
        statsButton.setOnMouseClicked(e -> {
            GameLogic.getInstance().getSceneManager().showPopUp(
                    createStatsPane(), getWidth() - 50, getHeight() - 50
            );
        });

        HBox buttonContainer = new HBox(statsButton);
        buttonContainer.setAlignment(Pos.TOP_RIGHT);
        buttonContainer.prefWidthProperty().bind(this.widthProperty());
        buttonContainer.setPadding(new Insets(5));

        characterImage = new ImageView(battlePaneStateContext.getEnemy().getImage());
        informationPane = new InformationPane(this);
        informationPane.setPrefHeight(240);
        informationPane.prefWidthProperty().bind(this.widthProperty());
        informationPane.maxWidthProperty().bind(this.widthProperty());

        // Replace Label HP bars with HealthBars
        playerHpBar = new HealthBar(GameLogic.getInstance().getSelectedCharacter().getHp(), Color.BLUE);
        enemyHpBar = new HealthBar(battlePaneStateContext.getEnemy().getHp(), Color.RED);

        HBox hpBox = new HBox(10, playerHpBar, enemyHpBar);
        hpBox.setAlignment(Pos.CENTER);

        getChildren().addAll(buttonContainer, characterImage, informationPane, hpBox);
    }

    public void updateHealthBars() {
        playerHpBar.updateHealthBar();
        enemyHpBar.updateHealthBar();
    }
}