package dev.typeracist.typeracist.gui.gameScene.BattlePane.InformationPane;

import dev.typeracist.typeracist.gui.gameScene.BattlePane.BattlePane;
import dev.typeracist.typeracist.gui.global.ThemedButton;
import dev.typeracist.typeracist.logic.global.GameLogic;
import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.logic.inventory.ActivateNow;
import dev.typeracist.typeracist.logic.inventory.ActivateOnTurn;
import dev.typeracist.typeracist.logic.inventory.Item;
import dev.typeracist.typeracist.utils.ResourceName;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemInfoPaneModifier extends BaseInfoPaneModifier {
    private final List<Button> itemButtons;
    private final BattlePane battlePane;
    private Button skipButton;

    public ItemInfoPaneModifier(BattlePane battlePane, InformationPane informationPane) {
        super(informationPane);
        this.battlePane = battlePane;
        this.itemButtons = new ArrayList<>();
    }

    public List<Button> getItemButtons() {
        return itemButtons;
    }

    public Button getSkipButton() {
        return skipButton;
    }

    @Override
    protected void initialize() {
        informationPane.getChildren().clear();
        Label modifierName = new Label("Player Item Selection");
        modifierName.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 18));

        Label instructionLabel = new Label("Choose item to help you perform");
        instructionLabel.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 14));
        instructionLabel.setAlignment(Pos.CENTER);

        TilePane tilepane = new TilePane();
        tilepane.setHgap(10);
        tilepane.setVgap(10);
        tilepane.setAlignment(Pos.CENTER);
        tilepane.setPadding(new Insets(10));

        for (var entry : GameLogic.getInstance().getSelectedCharacter().getInventory().getItems().entrySet()) {
            Item item = entry.getKey();
            int amount = entry.getValue();
            Button button = createRoundedButton(item.getName() + " x " + amount, item.getImage(), 50, 50);
            itemButtons.add(button);
            tilepane.getChildren().add(button);

            button.setOnAction(event ->
                    GameLogic.getInstance().getSceneManager().showPopUp(createItemContent(entry, button), 400, 450)
            );
        }

        skipButton = createRoundedButton("Skip", ResourceManager.getImage(ResourceName.IMAGE_MAP_NEXT), 50, 50);
        skipButton.setOpacity(0.8);
        tilepane.getChildren().add(skipButton);

        informationPane.setAlignment(Pos.CENTER);
        informationPane.getChildren().addAll(
                modifierName,
                instructionLabel,
                tilepane
        );
    }

    private Button createRoundedButton(String text, Image image, double width, double height) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);

        VBox content = new VBox(imageView, new Label(text));
        content.setAlignment(Pos.CENTER);
        content.setSpacing(5);

        Label label = (Label) content.getChildren().get(1);
        label.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 12));
        label.setTextFill(Color.BLACK);

        Button button = new Button();
        button.setMaxWidth(Double.MAX_VALUE);
        button.setGraphic(content);
        button.setPadding(new Insets(10));

        button.setBackground(new Background(new BackgroundFill(
                Color.LIGHTGRAY, new CornerRadii(10), Insets.EMPTY)));

        button.setBorder(new Border(new BorderStroke(
                Color.DARKGRAY, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(1))));

        return button;
    }

    public Pane createItemContent(Map.Entry<Item, Integer> entry, Button buttonReference) {
        Item item = entry.getKey();
        int amount = entry.getValue();

        ImageView imageView = new ImageView(item.getImage());
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        Text itemNameText = new Text(item.getName() + " ");
        itemNameText.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 20));
        itemNameText.setFill(Color.BLACK);

        Text amountText = new Text("x " + amount);
        amountText.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 20));
        amountText.setFill(Color.GREEN);

        TextFlow nameLabel = new TextFlow(itemNameText, amountText);
        nameLabel.setTextAlignment(TextAlignment.CENTER);

        Label descriptionLabel = new Label(item.getDescription());
        descriptionLabel.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 14));
        descriptionLabel.setAlignment(Pos.CENTER);
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxWidth(250);
        descriptionLabel.setTextFill(Color.web("#383838"));

        Button useItemButton = new ThemedButton("Use item", Color.DARKGREEN);
        useItemButton.setTextFill(Color.WHITE);

        useItemButton.setOnAction(e -> {
            GameLogic.getInstance().getSelectedCharacter().getInventory().removeItem(item, 1);

            if (item instanceof ActivateNow) {
                ((ActivateNow) item).activate();
                battlePane.updateHealthBars();
            }
            if (item instanceof ActivateOnTurn) {
                battlePane.getStateContext().getCurrentTurnContext().addItemUsed((Item & ActivateOnTurn) item);
            }

            if (GameLogic.getInstance().getSelectedCharacter().getInventory().getItemAmount(item) == 0) {
                useItemButton.setDisable(true);
                buttonReference.setDisable(true);

            } else {
                ((Label) ((VBox) buttonReference.getGraphic()).getChildren().get(1)).setText(
                        item.getName() + " x " + GameLogic.getInstance().getSelectedCharacter().getInventory().getItemAmount(item) + " (used)"
                );
            }

            if (GameLogic.getInstance().getSceneManager().isPopUpOpen()) {
                GameLogic.getInstance().getSceneManager().closePopUp();
            }
        });

        VBox contentLayout = new VBox(10);
        contentLayout.getChildren().addAll(imageView, nameLabel, descriptionLabel, useItemButton);
        contentLayout.setAlignment(Pos.CENTER);

        return contentLayout;
    }
}
