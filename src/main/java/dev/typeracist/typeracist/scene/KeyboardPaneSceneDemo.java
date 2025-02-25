package dev.typeracist.typeracist.scene;

import dev.typeracist.typeracist.gui.gameScene.TypingPane;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.List;

public class KeyboardPaneSceneDemo extends Scene {

    public KeyboardPaneSceneDemo(double width, double height) {
        super(new VBox(), width, height);

        VBox root = (VBox) getRoot();
        root.setPadding(new Insets(40));

        TypingPane typingPane = new TypingPane(List.of(
                "hello", "goat", "tile", "pane", "scene", "width", "govern", "typing",
                "random", "keyboard", "practice", "speed", "accuracy", "focus", "challenge", "words",
                "coding", "java", "layout", "center", "highlight", "display", "function", "method",
                "correct", "error", "dynamic", "color", "style", "spacing", "alignment", "visual",
                "improve", "performance", "system", "logical", "expression", "variable", "constant", "input",
                "output", "response", "action", "execute", "sequence", "efficient", "process", "structure",
                "render", "optimize", "interaction", "navigation", "control", "format", "arrange", "develop",
                "adjust", "initialize", "simulate", "evaluate", "compare", "transform"
        ));

        typingPane.setFont(Font.font("Departure Mono", FontWeight.MEDIUM, 24));
        typingPane.setHgap(15);

        root.getChildren().addAll(typingPane);

    }
}
