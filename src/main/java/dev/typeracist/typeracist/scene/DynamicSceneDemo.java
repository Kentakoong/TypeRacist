package dev.typeracist.typeracist.scene;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class DynamicSceneDemo extends BaseDynamicScene<StackPane> {
    private int currentIndex;

    public DynamicSceneDemo() {
        super(new StackPane());

        for (int i = 0; i < 10; ++i) {
            StackPane pane = createPane(i);
            addPane(i, pane);
        }

        loadPane(currentIndex);
    }

    public DynamicSceneDemo(double width, double height) {
        super(new StackPane(), width, height);

        for (int i = 0; i < 10; ++i) {
            StackPane pane = createPane(i);
            addPane(i, pane);
        }

        loadPane(currentIndex);
    }

    @Override
    public void onSceneEnter() {
        System.out.println("this is called on show (change scene)");
    }

    @Override
    public void onSceneLeave() {
        System.out.println("this is called on show (change scene)");
    }

    private StackPane createPane(int index) {
        StackPane pane = new StackPane();
        pane.setAlignment(Pos.CENTER);

        Label label = new Label("Pane " + index);

        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);

        HBox hbox = new HBox(20);
        hbox.setAlignment(Pos.CENTER);

        Button prevButton = new Button("Previous");
        prevButton.setDisable(index == 0);
        prevButton.setOnAction(event -> loadPreviousPane());

        Button nextButton = new Button("Next");
        nextButton.setDisable(index == 9);
        nextButton.setOnAction(event -> loadNextPane());

        hbox.getChildren().addAll(prevButton, nextButton);
        vbox.getChildren().addAll(label, hbox);

        pane.getChildren().add(vbox);

        return pane;
    }

    private void loadPreviousPane() {
        if (currentIndex > 0) {
            currentIndex--;
            loadPane(currentIndex);
        }
    }

    private void loadNextPane() {
        if (currentIndex < 9) {
            currentIndex++;
            loadPane(currentIndex);
        }
    }
}
