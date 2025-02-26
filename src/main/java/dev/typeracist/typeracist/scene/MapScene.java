package dev.typeracist.typeracist.scene;

import dev.typeracist.typeracist.logic.global.GameLogic;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

import java.util.HashMap;
import java.util.Map;

public class MapScene extends Scene {
    private final Pane root;
    private final Map<String, Button> nodeButtons = new HashMap<>();

    public MapScene(double width, double height) {
        super(new Pane(), width, height);
        root = (Pane) getRoot();

        // Create nodes

        //  System.out.println(this.getClass().getResource("/dev/typeracist/typeracist/image/anvil.jpg").toString());

        createNode("sword1", 100, 300, this.getClass().getResource("/dev/typeracist/typeracist/image/map/sword.png").toString(), "BATTLE");
        createNode("chest1", 400, 100, this.getClass().getResource("/dev/typeracist/typeracist/image/map/chest.png").toString(), "REWARD");
        createNode("shop1", 100, 400, this.getClass().getResource("/dev/typeracist/typeracist/image/map/shop.png").toString(), "STORE");
        createNode("anvil1", 250, 250, this.getClass().getResource("/dev/typeracist/typeracist/image/map/anvil.png").toString(), "UPGRADE");
        createNode("skull", 400, 350, this.getClass().getResource("/dev/typeracist/typeracist/image/map/skull.png").toString(), "BOSS");
        createNode("next", 500, 400,this.getClass().getResource("/dev/typeracist/typeracist/image/map/next.png").toString(), "NEXT");

        // Connect nodes visually
        connectNodes("sword1", "chest1");
        connectNodes("sword1", "shop1");
        connectNodes("sword1", "anvil1");
        connectNodes("anvil1", "skull");
        connectNodes("skull", "next");
    }

    private void createNode(String id, double x, double y, String imagePath, String action) {
        ImageView icon = new ImageView(new Image(imagePath));
        icon.setFitWidth(50);
        icon.setFitHeight(50);

        Button button = new Button("", icon);
        button.setStyle("-fx-background-color: transparent;");
        button.setLayoutX(x);
        button.setLayoutY(y);

        button.setOnAction(event -> navigate(action));

        nodeButtons.put(id, button);
        root.getChildren().add(button);
    }

    private void connectNodes(String from, String to) {
        Button node1 = nodeButtons.get(from);
        Button node2 = nodeButtons.get(to);
        if (node1 != null && node2 != null) {
            Line line = new Line(
                    node1.getLayoutX() + 25, node1.getLayoutY() + 25,
                    node2.getLayoutX() + 25, node2.getLayoutY() + 25
            );
            line.setStyle("-fx-stroke: white; -fx-stroke-width: 2;");
            root.getChildren().add(0, line); // Add behind buttons
        }
    }

    private void navigate(String action) {
        switch (action) {
            case "BATTLE":
                GameLogic.getInstance().getSceneManager().setScene("BATTLE_SCENE");
                break;
            case "REWARD":
                GameLogic.getInstance().getSceneManager().setScene("REWARD_SCENE");
                break;
            case "STORE":
                GameLogic.getInstance().getSceneManager().setScene("STORE_SCENE");
                break;
            case "UPGRADE":
                GameLogic.getInstance().getSceneManager().setScene("UPGRADE_SCENE");
                break;
            case "BOSS":
                GameLogic.getInstance().getSceneManager().setScene("BOSS_SCENE");
                break;
            case "NEXT":
                GameLogic.getInstance().getSceneManager().setScene("NEXT_MAP");
                break;
        }
    }
}
