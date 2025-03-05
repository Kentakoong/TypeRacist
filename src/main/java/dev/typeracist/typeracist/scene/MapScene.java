package dev.typeracist.typeracist.scene;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import dev.typeracist.typeracist.gui.gameScene.MapNode;
import dev.typeracist.typeracist.logic.characters.entities.Character;
import dev.typeracist.typeracist.logic.global.GameLogic;
import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.logic.global.SaveManager;
import dev.typeracist.typeracist.logic.global.BattleInfo;
import dev.typeracist.typeracist.logic.global.BattleNavigation;
import dev.typeracist.typeracist.utils.ResourceName;
import dev.typeracist.typeracist.utils.SceneName;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.util.Duration;

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

        // set background to grey
        root.setStyle("-fx-background-color: #484848;");

        // Load font
        Font baseFont = ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 36);

        // title label
        Label titleLabel = new Label("Arena Map");
        titleLabel.setStyle("-fx-text-fill: white;");
        titleLabel.setLayoutX(50);
        titleLabel.setLayoutY(10);
        titleLabel.setFont(Font.font(baseFont.getName(), 36));
        root.getChildren().add(titleLabel);

        // Home button at top right
        Button homeButton = new Button("Home");
        homeButton.setFont(Font.font(baseFont.getName(), 18));
        homeButton.setLayoutX(width - 100); // Position at top right
        homeButton.setLayoutY(10);
        homeButton.setOnAction(event -> {
            GameLogic.getInstance().getSceneManager().setScene(SceneName.MAIN);
        });
        root.getChildren().add(homeButton);

        // Info Label to show node descriptions
        infoLabel = new Label("Select a location.");
        infoLabel.setFont(Font.font(baseFont.getName(), 24));
        infoLabel.setStyle("-fx-text-fill: white;");
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
        // Create nodes
        for (BattleInfo battleInfo : BattleNavigation.getAllBattleInfo()) {
            createNode(battleInfo.getBattleName(), battleInfo.getNodeX(), battleInfo.getNodeY(),
                    battleInfo.getBattleImage(), battleInfo, battleInfo.getBattleDescription());
        }

        // Connect nodes visually
        for (BattleInfo battleInfo : BattleNavigation.getAllBattleInfo()) {
            for (String connectedNodeId : battleInfo.getConnectedNodes()) {
                connectNodes(battleInfo.getBattleName(), connectedNodeId);
            }
        }

        character = new ImageView(GameLogic.getInstance().getSelectedCharacter().getImage());
        character.setFitWidth(50); // Set character size
        character.setFitHeight(50);
        // Add character to the scene
        root.getChildren().add(character);

        // Add test buttons for clearing battles
        String[] battleNames = { "BATTLE1", "BATTLE2", "BATTLE3", "BATTLE4", "BATTLE5", "BATTLE6", "BATTLE7", "BATTLE8",
                "BATTLE9", "BOSS" };
        for (int i = 0; i < battleNames.length; i++) {
            addWinButton("Win " + battleNames[i], 50 + (i * 100), 700, battleNames[i]);
        }

        currentNode = mapNodes.get("START");
        if (currentNode != null) {
            character.setLayoutX(currentNode.getLayoutX());
            character.setLayoutY(currentNode.getLayoutY());
        }
    }

    /**
     * Creates a map node with the specified properties
     * 
     * @param id          Unique identifier for the node
     * @param x           X-coordinate position
     * @param y           Y-coordinate position
     * @param image       Visual representation image
     * @param battleInfo  BattleInfo object containing navigation details
     * @param description Description text for the node
     */
    private void createNode(String id, double x, double y, Image image, BattleInfo battleInfo, String description) {
        MapNode node = new MapNode(x, y, image, battleInfo);

        // Set up the node's click behavior
        node.setOnAction(event -> {
            selectedAction = battleInfo.getBattleName();
            infoLabel.setText(id.toUpperCase() + " - " + description);
            confirmButton.setDisable(false); // Enable confirm button
            moveCharacter(node);
        });

        // Add visual elements to the scene
        root.getChildren().add(node.getStatusCircle()); // Add status indicator circle
        root.getChildren().add(node); // Add the node itself

        // Store the node for later reference
        mapNodes.put(id, node);
    }

    /**
     * Updates the visual status indicators for all map nodes
     * based on current game state
     */
    public void updateNodeColors() {
        for (MapNode node : mapNodes.values()) {
            node.updateStatusColor();
        }
    }

    /**
     * Creates a visual and logical connection between two map nodes
     * 
     * @param from ID of the source node
     * @param to   ID of the target node
     */
    private void connectNodes(String from, String to) {
        MapNode node1 = mapNodes.get(from);
        MapNode node2 = mapNodes.get(to);

        if (node1 != null && node2 != null) {
            // Add neighbors for pathfinding
            node1.addNeighbor(node2);
            node2.addNeighbor(node1);

            // Calculate center points for the connection line
            double x1 = node1.getLayoutX() + 25;
            double y1 = node1.getLayoutY() + 25;
            double x2 = node2.getLayoutX() + 25;
            double y2 = node2.getLayoutY() + 25;

            // Draw a line to connect them visually
            Line line = new Line(x1, y1, x2, y2);
            line.setStyle("-fx-stroke: white; -fx-stroke-width: 2;");

            // Add the line to the bottom layer so it appears behind nodes
            root.getChildren().addFirst(line);
        }
    }

    private void moveCharacter(MapNode targetNode) {
        if (currentNode == null || targetNode == null) {
            return;
        }

        // Find shortest path using BFS
        List<MapNode> path = findShortestPath(currentNode, targetNode);
        if (path == null || path.isEmpty()) {
            return;
        }

        // Animate movement along the path
        moveAlongPath(path);
        currentNode = targetNode;
    }

    /**
     * Uses Breadth-First Search to find the shortest path between two nodes
     * 
     * @param start  The starting node
     * @param target The target node
     * @return A list of nodes representing the path, or null if no path exists
     */
    private List<MapNode> findShortestPath(MapNode start, MapNode target) {
        // Track which nodes we've visited and how we got there
        Map<MapNode, MapNode> cameFrom = new HashMap<>();
        Queue<MapNode> queue = new LinkedList<>();

        // Initialize the search
        queue.add(start);
        cameFrom.put(start, null);

        // BFS loop
        while (!queue.isEmpty()) {
            MapNode current = queue.poll();

            // If we found the target, we're done
            if (current == target) {
                break;
            }

            // Check all neighbors
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

        // Reverse to get the correct order (start to target)
        Collections.reverse(path);

        return path.size() > 1 ? path : null;
    }

    /**
     * Animates character movement along a path of nodes
     * 
     * @param path The path of nodes to follow
     */
    private void moveAlongPath(List<MapNode> path) {
        if (path == null || path.isEmpty()) {
            return;
        }

        Iterator<MapNode> iterator = path.iterator();
        moveToNextNode(iterator, path.size());
    }

    /**
     * Recursively moves the character from one node to the next along a path
     * 
     * @param iterator   Iterator over the path nodes
     * @param totalNodes Total number of nodes in the path for timing calculations
     */
    private void moveToNextNode(Iterator<MapNode> iterator, int totalNodes) {
        if (!iterator.hasNext()) {
            return;
        }

        MapNode nextNode = iterator.next();
        double targetX = nextNode.getLayoutX();
        double targetY = nextNode.getLayoutY();

        // Calculate movement
        double deltaX = targetX - character.getLayoutX();
        double deltaY = targetY - character.getLayoutY();

        // Adjust animation speed based on path length
        double timeFactor = totalNodes <= 3 ? 500 : 1000;
        double duration = timeFactor / totalNodes;

        // Create and configure the transition
        TranslateTransition transition = new TranslateTransition(Duration.millis(duration), character);
        transition.setByX(deltaX);
        transition.setByY(deltaY);

        // When transition completes, update character position and continue to next
        // node
        transition.setOnFinished(event -> {
            // Reset translation to avoid accumulating offsets
            character.setLayoutX(targetX);
            character.setLayoutY(targetY);
            character.setTranslateX(0);
            character.setTranslateY(0);

            // Small pause before moving to next node
            PauseTransition pause = new PauseTransition(Duration.millis(50));
            pause.setOnFinished(e -> moveToNextNode(iterator, totalNodes));
            pause.play();
        });

        transition.play();
    }

    private void navigate(String action) {
        // Special cases with no prerequisites
        if ("START".equals(action)) {
            GameLogic.getInstance().getSceneManager().setScene(SceneName.CHARACTERS.toString());
            return;
        }

        if ("STORE".equals(action)) {
            GameLogic.getInstance().getSceneManager().setScene(SceneName.SHOP.toString());
            return;
        }

        // Use the new BattleNavigation utility class
        BattleInfo navigationDetails = BattleNavigation.getNavigationDetails(action);

        if (navigationDetails != null) {
            String targetScene = navigationDetails.getSceneName();
            String prerequisite = navigationDetails.getPrerequisiteBattle();

            // Check if the battle is accessible
            boolean canAccess = prerequisite == null ||
                    GameLogic.getInstance().isBattleCleared(prerequisite);

            if (canAccess) {
                GameLogic.getInstance().getSceneManager().setScene(targetScene.toString());
            } else {
                infoLabel.setText("You must clear " + prerequisite + " first!");
            }
        }
    }

    /**
     * Creates a test button that marks a battle as cleared when clicked
     * 
     * @param text       Button label text
     * @param x          X-coordinate position
     * @param y          Y-coordinate position
     * @param battleName Name of the battle to mark as cleared
     */
    private void addWinButton(String text, double x, double y, String battleName) {
        Button winButton = new Button(text);
        winButton.setLayoutX(x);
        winButton.setLayoutY(y);

        System.out.println("battleName: " + battleName);

        winButton.setOnAction(event -> {
            // Clear the battle in game logic
            GameLogic.getInstance().clearBattle(battleName);

            // Update UI feedback
            infoLabel.setText(battleName + " cleared!");

            // Refresh node colors to reflect new game state
            updateNodeColors();

            // Save the game
            SaveManager.saveCharacter();
        });

        root.getChildren().add(winButton);
    }

    @Override
    public void onSceneEnter() {
        // Load the saved game data to ensure battle statuses are up-to-date

        if (SaveManager.saveFileExists(SaveManager.SAVE_FILE_CHARACTER)) {

            Character selectedCharacter = GameLogic.getInstance().getSelectedCharacter();
            selectedCharacter = SaveManager.getCharacter();
            GameLogic.getInstance().setSelectedCharacter(selectedCharacter);
        }

        // Set character image
        character.setImage(GameLogic.getInstance().getSelectedCharacter().getImage());

        // Make sure character is positioned at the current node
        if (currentNode != null) {
            character.setLayoutX(currentNode.getLayoutX());
            character.setLayoutY(currentNode.getLayoutY());
        } else {
            // Default to START if no current node
            currentNode = mapNodes.get("START");
            if (currentNode != null) {
                character.setLayoutX(currentNode.getLayoutX());
                character.setLayoutY(currentNode.getLayoutY());
            }
        }

        // Update node colors based on battle status
        updateNodeColors();
    }

    @Override
    public void onSceneLeave() {

    }

}
