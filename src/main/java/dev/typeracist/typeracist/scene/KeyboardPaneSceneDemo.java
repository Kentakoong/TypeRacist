package dev.typeracist.typeracist.scene;

import dev.typeracist.typeracist.gui.gameScene.TypingPane;
import dev.typeracist.typeracist.logic.gameScene.TypingHardness;
import dev.typeracist.typeracist.logic.global.GameLogic;
import dev.typeracist.typeracist.utils.DatasetName;
import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.utils.ResourceName;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.List;

public class KeyboardPaneSceneDemo extends BaseScene {

    public KeyboardPaneSceneDemo(double width, double height) {
        super(new VBox(), width, height);

        Font baseFont = ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 36);

        VBox root = (VBox) getRoot();
        root.setPadding(new Insets(40));
        root.setSpacing(20);

        HBox hBox = new HBox();

        Region leftSpacer = new Region();
        Region rightSpacer = new Region();

        HBox.setHgrow(leftSpacer, Priority.ALWAYS);
        HBox.setHgrow(rightSpacer, Priority.ALWAYS);

        Label title = new Label("Keyboard Pane Demo");
        title.setFont(Font.font(baseFont.getName(), 36));

        HBox subHbox = new HBox();
        subHbox.setSpacing(10);
        subHbox.setAlignment(Pos.BASELINE_CENTER);

        Label wpm = new Label("0");
        wpm.setFont(Font.font(baseFont.getName(), FontWeight.NORMAL, 36));
        wpm.setTextFill(Color.DARKKHAKI);

        Label unit = new Label("wpm");
        unit.setFont(Font.font(baseFont.getName(), FontWeight.NORMAL, 32));

        subHbox.getChildren().addAll(wpm, unit);
        hBox.getChildren().addAll(title, leftSpacer, rightSpacer, subHbox);

        List<String> selectParagraph = GameLogic.getInstance().getDatasetManager().getDataSet(DatasetName.COMMON_WORDS_1K).getRandomWordsByScoreRange(4.5, 7, 50);

        TypingPane typingPane = new TypingPane(selectParagraph);

        typingPane.setFont(Font.font(baseFont.getName(), FontWeight.NORMAL, 24)); // Use normal weight for typing pane
        typingPane.setHgap(15);
        typingPane.setOnFirstType(event -> {
            typingPane.getTypingTracker().start();

            new Thread(() -> {
                while (typingPane.getTypingTracker().isRunning()) {
                    try {
                        Platform.runLater(() -> {
                            wpm.setText(String.valueOf(Math.round(typingPane.getTypingTracker().calculateCorrectWPM() * 100.0) / 100.0));
                        });
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
        });

        Label hardness = new Label("paragraph hardness: " + TypingHardness.paragraphTypingHardness(selectParagraph, 50));
        hardness.setFont(Font.font(baseFont.getName(), FontWeight.NORMAL, 18));
        hardness.setAlignment(Pos.BOTTOM_LEFT);

        root.getChildren().addAll(hBox, typingPane, hardness);
    }

    @Override
    public void onSceneEnter() {

    }

    @Override
    public void onSceneLeave() {

    }
}
