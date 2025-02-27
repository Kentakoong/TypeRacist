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

        createNode("castle", 75, 490, this.getClass().getResource("/dev/typeracist/typeracist/image/map/castle.png").toString(), "START");
        createNode("shop", 200, 520, this.getClass().getResource("/dev/typeracist/typeracist/image/map/shop.png").toString(), "STORE");
        createNode("chest1", 175, 60, this.getClass().getResource("/dev/typeracist/typeracist/image/map/chest.png").toString(), "REWARD1");
        createNode("chest2", 675, 180, this.getClass().getResource("/dev/typeracist/typeracist/image/map/chest.png").toString(), "REWARD2");
        createNode("anvil1", 275, 60, this.getClass().getResource("/dev/typeracist/typeracist/image/map/anvil.png").toString(), "UPGRADE");
        createNode("sword1", 135, 375, this.getClass().getResource("/dev/typeracist/typeracist/image/map/sword.png").toString(), "BATTLE1");
        createNode("sword2", 200, 250, this.getClass().getResource("/dev/typeracist/typeracist/image/map/sword.png").toString(), "BATTLE2");
        createNode("sword3", 250,400 , this.getClass().getResource("/dev/typeracist/typeracist/image/map/sword.png").toString(), "BATTLE3");
        createNode("sword4", 75, 150, this.getClass().getResource("/dev/typeracist/typeracist/image/map/sword.png").toString(), "BATTLE4");
        createNode("sword5", 350, 150, this.getClass().getResource("/dev/typeracist/typeracist/image/map/sword.png").toString(), "BATTLE5");
        createNode("sword6", 500, 125, this.getClass().getResource("/dev/typeracist/typeracist/image/map/sword.png").toString(), "BATTLE6");
        createNode("sword7", 585, 60, this.getClass().getResource("/dev/typeracist/typeracist/image/map/sword.png").toString(), "BATTLE7");
        createNode("sword8", 700, 50, this.getClass().getResource("/dev/typeracist/typeracist/image/map/sword.png").toString(), "BATTLE8");
        createNode("sword9", 600, 250, this.getClass().getResource("/dev/typeracist/typeracist/image/map/sword.png").toString(), "BATTLE9");
        createNode("skull", 525, 390, this.getClass().getResource("/dev/typeracist/typeracist/image/map/skull.png").toString(), "BOSS");
        createNode("next", 700, 450,this.getClass().getResource("/dev/typeracist/typeracist/image/map/next.png").toString(), "NEXT");

        // Connect nodes visually
        connectNodes("castle", "shop");
        connectNodes("castle", "sword1");
        connectNodes("sword4", "chest1");
        connectNodes("sword9", "chest2");
        connectNodes("sword5", "anvil1");
        connectNodes("sword1", "sword3");
        connectNodes("sword1", "sword2");
        connectNodes("sword2", "sword4");
        connectNodes("sword2", "sword5");
        connectNodes("sword2", "sword5");
        connectNodes("sword5", "sword6");
        connectNodes("sword6", "sword7");
        connectNodes("sword7", "sword8");
        connectNodes("sword6", "sword9");
        connectNodes("skull", "sword9");
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
            case "BATTLE1":
                GameLogic.getInstance().getSceneManager().setScene("BATTLE_SCENE1");
                break;
            case "BATTLE2":
                GameLogic.getInstance().getSceneManager().setScene("BATTLE_SCENE2");
                break;
            case "BATTLE3":
                GameLogic.getInstance().getSceneManager().setScene("BATTLE_SCENE3");
                break;
            case "BATTLE4":
                GameLogic.getInstance().getSceneManager().setScene("BATTLE_SCENE4");
                break;
            case "BATTLE5":
                GameLogic.getInstance().getSceneManager().setScene("BATTLE_SCENE5");
                break;
            case "BATTLE6":
                GameLogic.getInstance().getSceneManager().setScene("BATTLE_SCENE6");
                break;
            case "BATTLE7":
                GameLogic.getInstance().getSceneManager().setScene("BATTLE_SCENE7");
                break;
            case "BATTLE8":
                GameLogic.getInstance().getSceneManager().setScene("BATTLE_SCENE8");
                break;
            case "BATTLE9":
                GameLogic.getInstance().getSceneManager().setScene("BATTLE_SCENE9");
                break;
            case "REWARD1":
                GameLogic.getInstance().getSceneManager().setScene("REWARD_SCENE1");
                break;
            case "REWARD2":
                GameLogic.getInstance().getSceneManager().setScene("REWARD_SCENE2");
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
