package dev.typeracist.typeracist.scene;

import dev.typeracist.typeracist.gui.gameScene.MapNode;
import dev.typeracist.typeracist.logic.global.GameLogic;
import dev.typeracist.typeracist.utils.SceneName;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;

public class MapScene extends Scene {
    private final Pane root;
    private final Label infoLabel;
    private final Button confirmButton;
    private String selectedAction = null;
    private final ImageView character;
    private final Map<String, MapNode> mapNodes = new HashMap<>();

    public MapScene(double width, double height) {
        super(new Pane(), width, height);
        root = (Pane) getRoot();

        //set background to grey
        root.setStyle("-fx-background-color: grey;");

        // Load font
        Font baseFont = Font.loadFont(KeyboardPaneSceneDemo.class.getResourceAsStream("/dev/typeracist/typeracist/fonts/DepartureMono-Regular.otf"), 36);

        // title label
        Label titleLabel = new Label("Arena Map");
        titleLabel.setStyle("-fx-text-fill: black;");
        titleLabel.setLayoutX(50);
        titleLabel.setLayoutY(10);
        titleLabel.setFont(Font.font(baseFont.getName(), 36));
        root.getChildren().add(titleLabel);


        // Info Label to show node descriptions
        infoLabel = new Label("Select a location.");
        infoLabel.setFont(Font.font(baseFont.getName(), 24));
        infoLabel.setStyle("-fx-text-fill: black;");
        infoLabel.setLayoutX(50);
        infoLabel.setLayoutY(60);
        root.getChildren().add(infoLabel);

        // Confirm Button
        confirmButton = new Button("Confirm");
        confirmButton.setLayoutX(50);
        confirmButton.setLayoutY(125);
        confirmButton.setDisable(true); // Disabled until a selection is made
        confirmButton.setOnAction(event -> {
            if (selectedAction != null) {
                navigate(selectedAction);
            }
        });
        root.getChildren().add(confirmButton);

        character = new ImageView(new Image(this.getClass().getResource(GameLogic.getInstance().getSelectedCharacter()).toString()));
        character.setFitWidth(50); // Set character size
        character.setFitHeight(50);
        character.setLayoutX(225); // Initial position (same as "castle")
        character.setLayoutY(540);


        // Create nodes
        createNode("castle", 175, 540, this.getClass().getResource("/dev/typeracist/typeracist/image/map/castle.png").toString(), "START", "The starting point of your journey.");
        createNode("shop", 300, 570, this.getClass().getResource("/dev/typeracist/typeracist/image/map/shop.png").toString(), "STORE","tmp");
        createNode("book", 590, 130, this.getClass().getResource("/dev/typeracist/typeracist/image/map/book.png").toString(), "BOOK","tmp");
        createNode("chest1", 275, 160, this.getClass().getResource("/dev/typeracist/typeracist/image/map/chest.png").toString(), "REWARD1","tmp");
        createNode("chest2", 775, 280, this.getClass().getResource("/dev/typeracist/typeracist/image/map/chest.png").toString(), "REWARD2","tmp");
        createNode("anvil1", 400, 160, this.getClass().getResource("/dev/typeracist/typeracist/image/map/anvil.png").toString(), "UPGRADE","tmp");
        createNode("BATTLE1", 235, 425, this.getClass().getResource("/dev/typeracist/typeracist/image/map/sword.png").toString(), "BATTLE1","tmp");
        createNode("BATTLE2", 300, 350, this.getClass().getResource("/dev/typeracist/typeracist/image/map/sword.png").toString(), "BATTLE2","tmp");
        createNode("BATTLE3", 350,450 , this.getClass().getResource("/dev/typeracist/typeracist/image/map/sword.png").toString(), "BATTLE3","tmp");
        createNode("BATTLE4", 175, 250, this.getClass().getResource("/dev/typeracist/typeracist/image/map/sword.png").toString(), "BATTLE4","tmp");
        createNode("BATTLE5", 450, 250, this.getClass().getResource("/dev/typeracist/typeracist/image/map/sword.png").toString(), "BATTLE5","tmp");
        createNode("BATTLE6", 600, 225, this.getClass().getResource("/dev/typeracist/typeracist/image/map/sword.png").toString(), "BATTLE6","tmp");
        createNode("BATTLE7", 685, 160, this.getClass().getResource("/dev/typeracist/typeracist/image/map/sword.png").toString(), "BATTLE7","tmp");
        createNode("BATTLE8", 800, 160, this.getClass().getResource("/dev/typeracist/typeracist/image/map/sword.png").toString(), "BATTLE8","tmp");
        createNode("BATTLE9", 700, 350, this.getClass().getResource("/dev/typeracist/typeracist/image/map/sword.png").toString(), "BATTLE9","tmp");
        createNode("BOSS", 625, 490, this.getClass().getResource("/dev/typeracist/typeracist/image/map/skull.png").toString(), "BOSS","tmp");
        createNode("next", 800, 550,this.getClass().getResource("/dev/typeracist/typeracist/image/map/next.png").toString(), "NEXT","tmp");

        // Connect nodes visually
        connectNodes("castle", "shop");
        connectNodes("castle", "BATTLE1");
        connectNodes("BATTLE4", "chest1");
        connectNodes("BATTLE9", "chest2");
        connectNodes("BATTLE5", "anvil1");
        connectNodes("BATTLE1", "BATTLE3");
        connectNodes("BATTLE1", "BATTLE2");
        connectNodes("BATTLE2", "BATTLE4");
        connectNodes("BATTLE2", "BATTLE5");
        connectNodes("BATTLE2", "BATTLE5");
        connectNodes("BATTLE5", "BATTLE6");
        connectNodes("BATTLE6", "BATTLE7");
        connectNodes("BATTLE7", "BATTLE8");
        connectNodes("BATTLE6", "BATTLE9");
        connectNodes("BOSS", "BATTLE9");
        connectNodes("BOSS", "next");
        connectNodes("BATTLE7", "book");
        System.out.println("teat1");

        // Test buttons to win battles
        addWinButton("Win BATTLE1", 50, 700, "BATTLE1");
        addWinButton("Win BATTLE2", 150, 700, "BATTLE2");
        addWinButton("Win BATTLE3", 250, 700, "BATTLE3");
        addWinButton("Win BATTLE4", 350, 700, "BATTLE4");
        addWinButton("Win BATTLE5", 450, 700, "BATTLE5");
        addWinButton("Win BATTLE6", 550, 700, "BATTLE6");
        addWinButton("Win BATTLE7", 650, 700, "BATTLE7");
        addWinButton("Win BATTLE8", 750, 700, "BATTLE8");
        addWinButton("Win BATTLE9", 850, 700, "BATTLE9");
        addWinButton("BOSS", 950, 700, "BOSS");

        root.getChildren().add(character);
    }



    private void createNode(String id, double x, double y, String imagePath, String action, String description) {
        MapNode node = new MapNode(x, y, imagePath, action);

        node.setOnAction(event -> {
            selectedAction = action;
            infoLabel.setText(id.toUpperCase() + " - " + description);
            confirmButton.setDisable(false); // Enable confirm button
            moveCharacter(x, y);
        });

        root.getChildren().add(node.getStatusCircle()); // Add circle
        root.getChildren().add(node); // Add button

        mapNodes.put(id, node);
    }

    public void updateNodeColors() {
        character.setImage(new Image(MapScene.class.getResource(GameLogic.getInstance().getSelectedCharacter()).toString()));

        for (MapNode node : mapNodes.values()) {
            node.updateStatusColor();
        }
    }


    private void connectNodes(String from, String to) {
        MapNode node1 = mapNodes.get(from);
        MapNode node2 = mapNodes.get(to);

        if (node1 != null && node2 != null) {
            Line line = new Line(
                    node1.getLayoutX() + 25, node1.getLayoutY() + 25,
                    node2.getLayoutX() + 25, node2.getLayoutY() + 25
            );
            line.setStyle("-fx-stroke: white; -fx-stroke-width: 2;");
            root.getChildren().add(0, line); // Add behind buttons
        }
    }

    // Helper method to determine circle color
    private Color getNodeColor(String action) {
        if ("BOSS".equals(action)) {
            return Color.RED; // Boss fights
        } else if (GameLogic.getInstance().isBattleCleared(action)) {
            return Color.GREEN; // Won battles
        } else if (GameLogic.getInstance().isBattleUnlocked(action)) {
            return Color.YELLOW; // Playable battles
        } else {
            return Color.WHITE; // Locked battles
        }
    }


    private void moveCharacter(double targetX, double targetY) {
        // Center the character on the target
        System.out.println("-----------");
        System.out.println(targetX);
        System.out.println(targetY);
        double finalX = targetX + 0; // Adjust if needed
        double finalY = targetY + 0;

        TranslateTransition transition = new TranslateTransition(Duration.millis(500), character);
        transition.setToX(50);
        transition.setToY(0);
        transition.play();

        // Update character position after animation
        transition.setOnFinished(event -> {
            character.setLayoutX(finalX);
            character.setLayoutY(finalY);
        });
    }

    private void navigate(String action) {
        switch (action) {
            case "START":
                GameLogic.getInstance().getSceneManager().setScene(SceneName.CHARACTERS);
            case "BATTLE1":
                GameLogic.getInstance().getSceneManager().setScene("BATTLE_SCENE1");
                break;
            case "BATTLE2":
                if (GameLogic.getInstance().isBattleCleared("BATTLE1")) {
                    GameLogic.getInstance().getSceneManager().setScene("BATTLE_SCENE2");
                } else {
                    infoLabel.setText("You must clear BATTLE1 first!");
                }
                break;
            case "BATTLE3":
                if (GameLogic.getInstance().isBattleCleared("BATTLE2")) {
                    GameLogic.getInstance().getSceneManager().setScene("BATTLE_SCENE3");
                } else {
                    infoLabel.setText("You must clear BATTLE2 first!");
                }
                break;
            case "BATTLE4":
                if (GameLogic.getInstance().isBattleCleared("BATTLE3")) {
                    GameLogic.getInstance().getSceneManager().setScene("BATTLE_SCENE4");
                } else {
                    infoLabel.setText("You must clear BATTLE3 first!");
                }
                break;
            case "BATTLE5":
                if (GameLogic.getInstance().isBattleCleared("BATTLE4")) {
                    GameLogic.getInstance().getSceneManager().setScene("BATTLE_SCENE5");
                } else {
                    infoLabel.setText("You must clear BATTLE4 first!");
                }
                break;
            case "BATTLE6":
                if (GameLogic.getInstance().isBattleCleared("BATTLE5")) {
                    GameLogic.getInstance().getSceneManager().setScene("BATTLE_SCENE6");
                } else {
                    infoLabel.setText("You must clear BATTLE5 first!");
                }
                break;
            case "BATTLE7":
                if (GameLogic.getInstance().isBattleCleared("BATTLE6")) {
                    GameLogic.getInstance().getSceneManager().setScene("BATTLE_SCENE7");
                } else {
                    infoLabel.setText("You must clear BATTLE6 first!");
                }
                break;
            case "BATTLE8":
                if (GameLogic.getInstance().isBattleCleared("BATTLE7")) {
                    GameLogic.getInstance().getSceneManager().setScene("BATTLE_SCENE8");
                } else {
                    infoLabel.setText("You must clear BATTLE7 first!");
                }
                break;
            case "BATTLE9":
                if (GameLogic.getInstance().isBattleCleared("BATTLE8")) {
                    GameLogic.getInstance().getSceneManager().setScene("BATTLE_SCENE9");
                } else {
                    infoLabel.setText("You must clear BATTLE8 first!");
                }
                break;
            case "REWARD1":
                if (GameLogic.getInstance().isBattleCleared("BATTLE4")) {
                    GameLogic.getInstance().getSceneManager().setScene("REWARD_SCENE1");
                } else {
                    infoLabel.setText("You must clear BATTLE4 first!");
                }
                break;
            case "REWARD2":
                if (GameLogic.getInstance().isBattleCleared("BATTLE9")) {
                    GameLogic.getInstance().getSceneManager().setScene("REWARD_SCENE2");
                } else {
                    infoLabel.setText("You must clear BATTLE9 first!");
                }
                break;
            case "STORE":
                GameLogic.getInstance().getSceneManager().setScene("STORE_SCENE");
                break;
            case "UPGRADE":
                if (GameLogic.getInstance().isBattleCleared("BATTLE5")) {
                    GameLogic.getInstance().getSceneManager().setScene("forgePane");
                } else {
                    infoLabel.setText("You must clear BATTLE5 first!");
                }
                break;
            case "BOSS":
                if (GameLogic.getInstance().isBattleCleared("BATTLE9")) {
                    GameLogic.getInstance().getSceneManager().setScene("BOSS_SCENE");
                } else {
                    infoLabel.setText("You must clear BATTLE9 first!");
                }
                break;
            case "NEXT":
                if (GameLogic.getInstance().isBattleCleared("BOSS")) {
                    GameLogic.getInstance().getSceneManager().setScene("NEXT_MAP");
                } else {
                    infoLabel.setText("You must clear BOSS first!");
                }
                break;
            case "BOOK":
                if (GameLogic.getInstance().isBattleCleared("BATTLE7")) {
                    GameLogic.getInstance().getSceneManager().setScene("enchantPane");
                } else {
                    infoLabel.setText("You must clear BATTLE7 first!");
                }
                break;
        }
    }

    private void addWinButton(String text, double x, double y, String battleName) {
        Button winButton = new Button(text);
        winButton.setLayoutX(x);
        winButton.setLayoutY(y);

        winButton.setOnAction(event -> {
            GameLogic.getInstance().clearBattle(battleName); // Mark as won
            infoLabel.setText(battleName + " cleared!");
            updateNodeColors(); // Refresh colors after winning
        });

        root.getChildren().add(winButton);
    }




}