package dev.typeracist.typeracist.scene;

import dev.typeracist.typeracist.gui.gameScene.MapNode;
import dev.typeracist.typeracist.logic.global.*;
import dev.typeracist.typeracist.utils.ResourceName;
import dev.typeracist.typeracist.utils.SceneName;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapScene extends BaseScene {
    private final VBox root;
    private final Map<String, MapNode> mapNodes = new HashMap<>();
    private Label infoLabel;
    private Button confirmButton;
    private ImageView character;
    private String selectedAction = null;
    private MapNode currentNode;

    private VBox topContainer;
    private Pane mapContainer;
    private HBox bottomContainer;

    public MapScene(double width, double height) {
        super(new VBox(), width, height);
        root = (VBox) getRoot();
        root.setStyle("-fx-background-color: #484848;");
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10); // Adds spacing between UI elements

        // Load font
        Font baseFont = ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 36);

        // Create UI elements
        topContainer = createTopContainer(width, baseFont);

        // Create spacers
        Region topSpacer = new Region();
        Region bottomSpacer = new Region();
        VBox.setVgrow(topSpacer, Priority.ALWAYS); // Pushes mapContainer to center
        VBox.setVgrow(bottomSpacer, Priority.ALWAYS); // Pushes buttons to bottom

        mapContainer = createNodesAndConnections(height);

        bottomContainer = createBottomButtonContainer(width, height, baseFont);

        // Add all elements with spacers to the VBox layout
        root.getChildren().addAll(topContainer, topSpacer, mapContainer, bottomSpacer, bottomContainer);
    }

    /**
     * Creates the top container including the title, info label, confirm button,
     * and home button.
     */
    private VBox createTopContainer(double width, Font baseFont) {
        VBox topContainer = new VBox();
        topContainer.setAlignment(Pos.TOP_LEFT);
        topContainer.setSpacing(10);
        topContainer.setPadding(new Insets(10));
        topContainer.setPrefWidth(width);
        topContainer.setLayoutX(0);
        topContainer.setLayoutY(10);

        // Create the top bar (Title & Home Button)
        HBox topBar = new HBox();
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setSpacing(10);
        topBar.setPrefWidth(width - 100);

        Label titleLabel = new Label("Arena Map");
        titleLabel.setStyle("-fx-text-fill: white;");
        titleLabel.setFont(Font.font(baseFont.getName(), 36));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button homeButton = new Button("Home");
        homeButton.setFont(Font.font(baseFont.getName(), 18));
        homeButton.setOnAction(event -> GameLogic.getInstance().getSceneManager().setScene(SceneName.MAIN));

        topBar.getChildren().addAll(titleLabel, spacer, homeButton);

        // Info Label
        infoLabel = new Label("Select a location.");
        infoLabel.setFont(Font.font(baseFont.getName(), 24));
        infoLabel.setStyle("-fx-text-fill: white;");
        infoLabel.setWrapText(true);
        infoLabel.setMaxWidth(width - 100);

        // Confirm Button
        confirmButton = new Button("Confirm");
        confirmButton.setFont(Font.font(baseFont.getName(), 18));
        confirmButton.setDisable(true);
        confirmButton.setOnAction(event -> {
            if (selectedAction != null) {
                navigate(selectedAction);
            }
        });

        topContainer.getChildren().addAll(topBar, infoLabel, confirmButton);

        return topContainer;
    }

    /**
     * Creates nodes and connects them visually.
     */
    private Pane createNodesAndConnections(double height) {
        mapContainer = new Pane();

        mapContainer.setPrefSize(625, height * 0.6);
        mapContainer.setMaxWidth(625);
        mapContainer.setStyle("-fx-background-color: #484848;"); // Match background color

        for (BattleInfo battleInfo : BattleNavigation.getAllBattleInfo()) {
            createNode(battleInfo.getBattleName(), battleInfo.getNodeX(), battleInfo.getNodeY(),
                    battleInfo.getBattleImage(), battleInfo, battleInfo.getBattleDescription());
        }

        for (BattleInfo battleInfo : BattleNavigation.getAllBattleInfo()) {
            for (String connectedNodeId : battleInfo.getConnectedNodes()) {
                connectNodes(battleInfo.getBattleName(), connectedNodeId);
            }
        }

        mapContainer.setMaxWidth(625);
        mapContainer.setStyle("-fx-background-color: #484848;"); // Match background color

        createCharacter();

        return mapContainer;
    }

    /**
     * Creates the character image and adds it to the scene.
     */
    private void createCharacter() {
        character = new ImageView(GameLogic.getInstance().getSelectedCharacter().getImage());
        character.setFitWidth(50);
        character.setFitHeight(50);
        mapContainer.getChildren().add(character);
    }

    /**
     * Creates the bottom button container and adds win buttons.
     */
    private HBox createBottomButtonContainer(double width, double height, Font baseFont) {
        HBox bottomButtonContainer = new HBox();
        bottomButtonContainer.setAlignment(Pos.CENTER);
        bottomButtonContainer.setSpacing(10);
        bottomButtonContainer.setPrefWidth(width);
        bottomButtonContainer.setPadding(new Insets(10));

        String[] battleNames = {"BATTLE1", "BATTLE2", "BATTLE3", "BATTLE4", "BATTLE5", "BATTLE6", "BATTLE7", "BATTLE8",
                "BATTLE9", "BOSS"};

        for (String battleName : battleNames) {
            Button winButton = new Button("Win " + battleName);
            winButton.setFont(Font.font(baseFont.getName(), 14));
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

            bottomButtonContainer.getChildren().add(winButton);
        }

        VBox.setVgrow(bottomButtonContainer, Priority.NEVER); // Keep it at the bottom

        return bottomButtonContainer;
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
        mapContainer.getChildren().add(node.getStatusCircle()); // Add status indicator circle
        mapContainer.getChildren().add(node); // Add the node itself

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
            mapContainer.getChildren().addFirst(line);
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
                int battleSceneIndex = convertToBattleSceneIndex(action);

                if (battleSceneIndex != -1) {
                    BattleScene battleScene = (BattleScene) GameLogic.getInstance().getSceneManager().getScene(SceneName.BATTLE);
                    System.out.println("go to" + (battleSceneIndex - 1));
                    battleScene.loadPane(battleSceneIndex - 1);
                    GameLogic.getInstance().getSceneManager().setScene(SceneName.BATTLE);
                }
            } else {
                infoLabel.setText("You must clear " + prerequisite + " first!");
            }
        }
    }

    private int convertToBattleSceneIndex(String battleName) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(battleName);
        return matcher.find() ? Integer.parseInt(matcher.group()) : -1; // Return -1 if no match
    }

    @Override
    public void onSceneEnter() {
        // Load the saved game data to ensure battle statuses are up-to-date
//        if (SaveManager.saveFileExists(SaveManager.SAVE_FILE_CHARACTER)) {
//
//            Character selectedCharacter = GameLogic.getInstance().getSelectedCharacter();
//            selectedCharacter = SaveManager.getCharacter();
//            GameLogic.getInstance().setSelectedCharacter(selectedCharacter);
//        }

        // Set character image
        character.setImage(GameLogic.getInstance().getSelectedCharacter().getImage());

        // Make sure character is positioned at the current node
        if (currentNode != null) {
            character.setLayoutX(currentNode.getLayoutX());
            character.setLayoutY(currentNode.getLayoutY());
        } else {
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
