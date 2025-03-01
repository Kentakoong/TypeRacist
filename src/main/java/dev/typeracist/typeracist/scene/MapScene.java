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

            character = new ImageView(new Image(this.getClass().getResource("/dev/typeracist/typeracist/image/character/warrior.png").toString()));
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

            // Test buttons to win battles
            addWinButton("Win BATTLE1", 50, 550, "BATTLE1");
            addWinButton("Win BATTLE2", 150, 550, "BATTLE2");
            addWinButton("Win BATTLE3", 250, 550, "BATTLE3");
            addWinButton("Win BATTLE4", 350, 550, "BATTLE4");
            addWinButton("Win BATTLE5", 450, 550, "BATTLE5");
            addWinButton("Win BATTLE6", 550, 550, "BATTLE6");
            addWinButton("Win BATTLE7", 650, 550, "BATTLE7");
            addWinButton("Win BATTLE8", 750, 550, "BATTLE8");
            addWinButton("Win BATTLE9", 850, 550, "BATTLE9");
            addWinButton("BOSS", 950, 550, "BOSS");
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
                GameLogic.getInstance().clearBattle(battleName);
                infoLabel.setText(battleName + " cleared!");
            });
            root.getChildren().add(winButton);
        }



    }
