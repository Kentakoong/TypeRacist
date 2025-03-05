package dev.typeracist.typeracist.scene;

import dev.typeracist.typeracist.gui.gameScene.MapNode;
import dev.typeracist.typeracist.logic.global.GameLogic;
import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.utils.ResourceName;
import dev.typeracist.typeracist.utils.SceneName;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.*;

public class MapScene extends BaseScene {
    private final Pane root;
    private final Label infoLabel;
    private final Button confirmButton;
    private final ImageView character;
    private final Map<String, MapNode> mapNodes = new HashMap<>();
    private String selectedAction = null;
    private MapNode currentNode;

    public MapScene(double width, double height) {
        super(new Pane(), width, height);
        root = (Pane) getRoot();

        //set background to grey
        root.setStyle("-fx-background-color: #484848;");

        // Load font
        Font baseFont = ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 36);

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
        confirmButton.setLayoutY(100);
        confirmButton.setDisable(true); // Disabled until a selection is made
        confirmButton.setOnAction(event -> {
            if (selectedAction != null) {
                navigate(selectedAction);
            }
        });
        root.getChildren().add(confirmButton);

        character = new ImageView(GameLogic.getInstance().getSelectedCharacter().getImage());
        character.setFitWidth(50); // Set character size
        character.setFitHeight(50);
//        character.setLayoutX(225); // Initial position (same as "castle")
//        character.setLayoutY(540);


        // Create nodes
        createNode("castle", 175, 540, ResourceManager.getImage(ResourceName.IMAGE_MAP_CASTLE), "START",
                "The starting point of your journey.");
        createNode("shop", 300, 570, ResourceManager.getImage(ResourceName.IMAGE_MAP_SHOP), "STORE", "tmp");
        createNode("book", 590, 130, ResourceManager.getImage(ResourceName.IMAGE_MAP_BOOK), "BOOK", "tmp");
        createNode("chest1", 275, 160, ResourceManager.getImage(ResourceName.IMAGE_MAP_CHEST), "REWARD1", "tmp");
        createNode("chest2", 775, 280, ResourceManager.getImage(ResourceName.IMAGE_MAP_CHEST), "REWARD2", "tmp");
        createNode("anvil1", 400, 160, ResourceManager.getImage(ResourceName.IMAGE_MAP_ANVIL), "UPGRADE", "tmp");
        createNode("BATTLE1", 235, 425, ResourceManager.getImage(ResourceName.IMAGE_MAP_SWORD), "BATTLE1", "tmp");
        createNode("BATTLE2", 300, 350, ResourceManager.getImage(ResourceName.IMAGE_MAP_SWORD), "BATTLE2", "tmp");
        createNode("BATTLE3", 350, 450, ResourceManager.getImage(ResourceName.IMAGE_MAP_SWORD), "BATTLE3", "tmp");
        createNode("BATTLE4", 175, 250, ResourceManager.getImage(ResourceName.IMAGE_MAP_SWORD), "BATTLE4", "tmp");
        createNode("BATTLE5", 450, 250, ResourceManager.getImage(ResourceName.IMAGE_MAP_SWORD), "BATTLE5", "tmp");
        createNode("BATTLE6", 600, 225, ResourceManager.getImage(ResourceName.IMAGE_MAP_SWORD), "BATTLE6", "tmp");
        createNode("BATTLE7", 685, 160, ResourceManager.getImage(ResourceName.IMAGE_MAP_SWORD), "BATTLE7", "tmp");
        createNode("BATTLE8", 800, 160, ResourceManager.getImage(ResourceName.IMAGE_MAP_SWORD), "BATTLE8", "tmp");
        createNode("BATTLE9", 700, 350, ResourceManager.getImage(ResourceName.IMAGE_MAP_SWORD), "BATTLE9", "tmp");
        createNode("BOSS", 625, 490, ResourceManager.getImage(ResourceName.IMAGE_MAP_SKULL), "BOSS", "tmp");
        createNode("next", 800, 550, ResourceManager.getImage(ResourceName.IMAGE_MAP_NEXT), "NEXT", "tmp");

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

        currentNode = mapNodes.get("castle");
        character.setLayoutX(currentNode.getLayoutX());
        character.setLayoutY(currentNode.getLayoutY());
    }

    private void createNode(String id, double x, double y, Image image, String action, String description) {
        MapNode node = new MapNode(x, y, image, action);

        node.setOnAction(event -> {
            selectedAction = action;
            infoLabel.setText(id.toUpperCase() + " - " + description);
            confirmButton.setDisable(false); // Enable confirm button
            moveCharacter(node);
        });

        root.getChildren().add(node.getStatusCircle()); // Add circle
        root.getChildren().add(node); // Add button

        mapNodes.put(id, node);
    }

    // Update Map Color

    public void updateNodeColors() {
        for (MapNode node : mapNodes.values()) {
            node.updateStatusColor();
        }
    }

    private void connectNodes(String from, String to) {
        MapNode node1 = mapNodes.get(from);
        MapNode node2 = mapNodes.get(to);

        if (node1 != null && node2 != null) {
            // Add neighbors for pathfinding
            node1.addNeighbor(node2);
            node2.addNeighbor(node1);

            // Draw a line to connect them visually
            Line line = new Line(
                    node1.getLayoutX() + 25, node1.getLayoutY() + 25,
                    node2.getLayoutX() + 25, node2.getLayoutY() + 25);
            line.setStyle("-fx-stroke: white; -fx-stroke-width: 2;");
            root.getChildren().addFirst(line);
        }
    }

    private void moveCharacter(MapNode targetNode) {
        // Get starting node (character's current position)
        MapNode startNode = currentNode;
        System.out.println(startNode);
        if (startNode == null || targetNode == null) return;

        // Find shortest path using BFS
        List<MapNode> path = findShortestPath(startNode, targetNode);
        if (path == null || path.isEmpty()) return;

        // Animate movement along the path
        moveAlongPath(path);
        currentNode = targetNode;
    }

    // 🔥 BFS to find the shortest path
    private List<MapNode> findShortestPath(MapNode start, MapNode target) {
        Map<MapNode, MapNode> cameFrom = new HashMap<>();
        Queue<MapNode> queue = new LinkedList<>();
        queue.add(start);
        cameFrom.put(start, null);

        while (!queue.isEmpty()) {
            MapNode current = queue.poll();
            if (current == target) break; // Found the target node

            for (MapNode neighbor : current.getNeighbors()) {
                if (!cameFrom.containsKey(neighbor)) {
                    queue.add(neighbor);
                    cameFrom.put(neighbor, current);
                }
            }
        }

        // Reconstruct the path
        List<MapNode> path = new ArrayList<>();
        for (MapNode node = target; node != null; node = cameFrom.get(node)) {
            path.add(node);
        }
        Collections.reverse(path); // Reverse to get the correct order

        System.out.println(path);
        return path.size() > 1 ? path : null;
    }

    // 🔥 Move character through the path step-by-step
    private void moveAlongPath(List<MapNode> path) {
        if (path == null || path.isEmpty()) return;

        Iterator<MapNode> iterator = path.iterator();
        moveToNextNode(iterator, path.size());
    }

    private void moveToNextNode(Iterator<MapNode> iterator, int totalTraveled) {
        if (!iterator.hasNext()) return;

        MapNode nextNode = iterator.next();
        double targetX = nextNode.getLayoutX();
        double targetY = nextNode.getLayoutY();

        double deltaX = targetX - character.getLayoutX();
        double deltaY = targetY - character.getLayoutY();
        double timeFactor = 1000;
        if(totalTraveled<=3){
            timeFactor = 500;
        }
        TranslateTransition transition = new TranslateTransition(Duration.millis((double) timeFactor / totalTraveled), character);
        transition.setByX(deltaX);
        transition.setByY(deltaY);
        transition.play();

        transition.setOnFinished(event -> {
            character.setLayoutX(targetX);
            character.setLayoutY(targetY);
            character.setTranslateX(0);
            character.setTranslateY(0);

            // Move to the next node in the path after a slight pause
            PauseTransition pause = new PauseTransition(Duration.millis(50));
            pause.setOnFinished(e -> moveToNextNode(iterator, totalTraveled));
            pause.play();
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
                    GameLogic.getInstance().getSceneManager().setScene(SceneName.CHEST);
                } else {
                    infoLabel.setText("You must clear BATTLE4 first!");
                }
                break;
            case "REWARD2":
                if (GameLogic.getInstance().isBattleCleared("BATTLE9")) {
                    GameLogic.getInstance().getSceneManager().setScene(SceneName.CHEST);
                } else {
                    infoLabel.setText("You must clear BATTLE9 first!");
                }
                break;
            case "STORE":
                GameLogic.getInstance().getSceneManager().setScene(SceneName.SHOP);
                break;
            case "UPGRADE":
                if (GameLogic.getInstance().isBattleCleared("BATTLE5")) {
                    GameLogic.getInstance().getSceneManager().setScene(SceneName.FORGE);
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
                    GameLogic.getInstance().getSceneManager().setScene(SceneName.ENCHANT);
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

    @Override
    public void onSceneEnter() {
        character.setImage(GameLogic.getInstance().getSelectedCharacter().getImage());
        updateNodeColors();
    }

    @Override
    public void onSceneLeave() {

    }

}
