package dev.typeracist.typeracist.gui.gameScene.InformationPane;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class InformationPane extends VBox {
    public InformationPane() {
        setSpacing(5);
        setPadding(new Insets(10));
        setBackground(new Background(new BackgroundFill(Color.GRAY, new CornerRadii(5), Insets.EMPTY)));
        setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));

        switchToPane(InfoPaneType.STATS_OPTION_PANE);
    }

    public void switchToPane(InfoPaneType paneType) {
        getChildren().clear();

        BaseInfoPane newPane = switch (paneType) {
            case STATS_OPTION_PANE -> new StatsOptionInfoPane(this);
            case TYPING_PANE -> new TypingInfoPane(this);
        };

        getChildren().add(newPane);
    }

//    public void switchToFirstContactPane() {
//        getChildren().clear();
//        Font baseFont = Font.loadFont(InformationPane.class.getResourceAsStream("/dev/typeracist/typeracist/fonts/DepartureMono-Regular.otf"), 36);
//
//        VBox vbox = new VBox();
//        vbox.setAlignment(Pos.CENTER_LEFT);
//        vbox.setPadding(new Insets(10));
//        vbox.setSpacing(20);
//
//        HBox hbox = new HBox();
//        hbox.setAlignment(Pos.CENTER);
//        hbox.setSpacing(40);
//
//        Label playerName = new Label("* Player Name");
//        playerName.setFont(Font.font(baseFont.getName(), 18));
//
//        Label enemyName = new Label("* Enemy Name");
//        enemyName.setFont(Font.font(baseFont.getName(), 18));
//
//        hbox.getChildren().addAll(playerName, enemyName);
//
//        vbox.getChildren().addAll(hbox);
//
//        getChildren().add(vbox);
//    }
//
//    public void switchToTypingPane() {
//        getChildren().clear();
//        Font baseFont = Font.loadFont(InformationPane.class.getResourceAsStream("/dev/typeracist/typeracist/fonts/DepartureMono-Regular.otf"), 36);
//
//
//        TimedTypingPane typingPane = new TimedTypingPane(GameLogic.getInstance().getDatasetManager().getDataSet(DatasetName.COMMON_WORDS_1K).getRandomWordsByScoreRange(4, 7, 50));
//
//        Label label = new Label("0/" + typingPane.getTypingTracker().getWords().size());
//        label.setFont(Font.font(baseFont.getName(), 18));
//        label.setAlignment(Pos.CENTER_RIGHT);
//        label.setMaxWidth(Double.MAX_VALUE);
//
//        typingPane.setHighlightColors(typingPane.getBaseColor(), Color.BLACK, Color.TOMATO, Color.DARKRED);
//        typingPane.setFont(Font.font(baseFont.getName(), 24));
//
//        typingPane.setOnFirstType(event -> {
//            typingPane.start();
//        });
//
//        typingPane.setOnType(event -> {
//            label.setText(
//                    typingPane
//                            .getTypingTracker()
//                            .getTypedWordStatuses()
//                            .entrySet()
//                            .stream()
//                            .filter(entry -> entry.getValue() == TypedWordStatus.CORRECTED)
//                            .count() + "/" +
//                            typingPane.getTypingTracker().getWords().size());
//            return event;
//        });
//
//        typingPane.setAlignment(Pos.CENTER_LEFT);
//        typingPane.setSpacing(25);
//        typingPane.setOnStop(event -> {
//                    GameLogic.getInstance().getSceneManager().setToPreviousScene();
//                    System.out.println(
//                            typingPane
//                                    .getTypingTracker()
//                                    .getTypedWordStatuses()
//                                    .entrySet()
//                                    .stream()
//                                    .filter(entry -> entry.getValue() == TypedWordStatus.CORRECTED)
//                                    .count()
//                    );
//                }
//        );
//        setPadding(new Insets(8, 20, 8, 20));
//
//        getChildren().addAll(label, typingPane);
//    }
}
