    package dev.typeracist.typeracist.scene;

    import dev.typeracist.typeracist.logic.global.GameLogic;
    import dev.typeracist.typeracist.utils.SceneName;
    import javafx.scene.Scene;
    import javafx.scene.control.Button;
    import javafx.scene.control.Label;
    import javafx.scene.image.Image;
    import javafx.scene.image.ImageView;
    import javafx.scene.layout.Pane;
    import javafx.scene.shape.Line;
    import javafx.scene.text.Font;
    import javafx.animation.TranslateTransition;
    import javafx.util.Duration;

    import java.util.HashMap;
    import java.util.Map;

    public class MapScene extends Scene {
        private final Pane root;
        private final Map<String, Button> nodeButtons = new HashMap<>();
        private final Label infoLabel;
        private final Button confirmButton;
        private String selectedAction = null;
        private final ImageView character;

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
            confirmButton.setLayoutY(100);
            confirmButton.setDisable(true); // Disabled until a selection is made
            confirmButton.setOnAction(event -> {
                if (selectedAction != null) {
                    navigate(selectedAction);
                }
            });
            root.getChildren().add(confirmButton);

            character = new ImageView(new Image(this.getClass().getResource("/dev/typeracist/typeracist/image/map/castle.png").toString()));
            character.setFitWidth(50); // Set character size
            character.setFitHeight(50);
            character.setLayoutX(75); // Initial position (same as "castle")
            character.setLayoutY(490);

            root.getChildren().add(character);



            // Create nodes
            createNode("castle", 75, 490, this.getClass().getResource("/dev/typeracist/typeracist/image/map/castle.png").toString(), "START", "The starting point of your journey.");
            createNode("shop", 200, 520, this.getClass().getResource("/dev/typeracist/typeracist/image/map/shop.png").toString(), "STORE","tmp");
            createNode("book", 490, 80, this.getClass().getResource("/dev/typeracist/typeracist/image/map/book.png").toString(), "BOOK","tmp");
            createNode("chest1", 175, 110, this.getClass().getResource("/dev/typeracist/typeracist/image/map/chest.png").toString(), "REWARD1","tmp");
            createNode("chest2", 675, 230, this.getClass().getResource("/dev/typeracist/typeracist/image/map/chest.png").toString(), "REWARD2","tmp");
            createNode("anvil1", 275, 110, this.getClass().getResource("/dev/typeracist/typeracist/image/map/anvil.png").toString(), "UPGRADE","tmp");
            createNode("sword1", 135, 375, this.getClass().getResource("/dev/typeracist/typeracist/image/map/sword.png").toString(), "BATTLE1","tmp");
            createNode("sword2", 200, 300, this.getClass().getResource("/dev/typeracist/typeracist/image/map/sword.png").toString(), "BATTLE2","tmp");
            createNode("sword3", 250,400 , this.getClass().getResource("/dev/typeracist/typeracist/image/map/sword.png").toString(), "BATTLE3","tmp");
            createNode("sword4", 75, 200, this.getClass().getResource("/dev/typeracist/typeracist/image/map/sword.png").toString(), "BATTLE4","tmp");
            createNode("sword5", 350, 200, this.getClass().getResource("/dev/typeracist/typeracist/image/map/sword.png").toString(), "BATTLE5","tmp");
            createNode("sword6", 500, 175, this.getClass().getResource("/dev/typeracist/typeracist/image/map/sword.png").toString(), "BATTLE6","tmp");
            createNode("sword7", 585, 110, this.getClass().getResource("/dev/typeracist/typeracist/image/map/sword.png").toString(), "BATTLE7","tmp");
            createNode("sword8", 700, 110, this.getClass().getResource("/dev/typeracist/typeracist/image/map/sword.png").toString(), "BATTLE8","tmp");
            createNode("sword9", 600, 300, this.getClass().getResource("/dev/typeracist/typeracist/image/map/sword.png").toString(), "BATTLE9","tmp");
            createNode("skull", 525, 440, this.getClass().getResource("/dev/typeracist/typeracist/image/map/skull.png").toString(), "BOSS","tmp");
            createNode("next", 700, 500,this.getClass().getResource("/dev/typeracist/typeracist/image/map/next.png").toString(), "NEXT","tmp");

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
            connectNodes("sword7", "book");
        }

        private void createNode(String id, double x, double y, String imagePath, String action, String description) {
            ImageView icon = new ImageView(new Image(imagePath));
            icon.setFitWidth(50);
            icon.setFitHeight(50);

            Button button = new Button("", icon);
            button.setStyle("-fx-background-color: transparent;");
            button.setLayoutX(x);
            button.setLayoutY(y);

            // Click action: Move character and navigate
            button.setOnAction(event -> {
                selectedAction = action;
                infoLabel.setText(id.toUpperCase() + " - " + description);
                confirmButton.setDisable(false); // Enable confirm button

                moveCharacter(x, y);
            });

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
                    GameLogic.getInstance().getSceneManager().setScene("forgePane");
                    break;
                case "BOSS":
                    GameLogic.getInstance().getSceneManager().setScene("BOSS_SCENE");
                    break;
                case "NEXT":
                    GameLogic.getInstance().getSceneManager().setScene("NEXT_MAP");
                    break;
                case "BOOK":
                    GameLogic.getInstance().getSceneManager().setScene("enchantPane");
                    break;
            }
        }
    }
