package dev.typeracist.typeracist.scene;

import dev.typeracist.typeracist.logic.GameLogic;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class CharacterScene extends Scene {
    public CharacterScene(double width, double height) {
        super(new VBox(), width, height);

        VBox root = (VBox) getRoot();
        root.setAlignment(Pos.TOP_CENTER);
        root.setSpacing(20);

        HBox navigationBar = new HBox();
        navigationBar.setAlignment(Pos.CENTER);
        navigationBar.setPadding(new Insets(40));
        navigationBar.setPrefWidth(width);

        // Buttons for navigation
        Button previousButton = new Button("Previous");
        previousButton.setOnAction(event -> GameLogic.getInstance().getSceneManager().setToPreviousScene());

        Button nextButton = new Button("Next"); // Example for Next Button
        nextButton.setDisable(true);

        // Make sure the buttons align at the left and right edges
        HBox leftBox = new HBox(previousButton);
        leftBox.setAlignment(Pos.CENTER_LEFT);
        HBox rightBox = new HBox(nextButton);
        rightBox.setAlignment(Pos.CENTER_RIGHT);

        // Push buttons to the edges by setting horizontal grow
        HBox.setHgrow(leftBox, javafx.scene.layout.Priority.ALWAYS);
        HBox.setHgrow(rightBox, javafx.scene.layout.Priority.ALWAYS);

        navigationBar.getChildren().addAll(leftBox, new Label(), rightBox);

        Label title = new Label("Choose your character.");
        title.setFont(Font.font("JetBrainsMono NF", FontWeight.BOLD, 24));

        TextField field = new TextField();

        root.getChildren().addAll(navigationBar, title, field);
    }
}
