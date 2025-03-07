package dev.typeracist.typeracist.logic.global;

import dev.typeracist.typeracist.utils.SceneName;
import dev.typeracist.typeracist.utils.ResourceName;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class BattleNavigation {
        // Predefined battle progression
        private static final List<BattleInfo> BATTLE_PROGRESSION = new ArrayList<>();
        private static final Map<String, BattleInfo> BATTLE_MAP = new HashMap<>();

        // Static initializer for battle progression
        static {
                // Create all nodes first
                createNode(SceneName.BATTLE_SCENE1,
                                "Unforseen World - You have stepped out of the portal, what could have been waiting for you?",
                                ResourceManager.getImage(ResourceName.IMAGE_MAP_SWORD),
                                SceneName.BATTLE_SCENE1, null, true, 60, 295);

                createNode(SceneName.BATTLE_SCENE2,
                                "Rural Dirt Road - Next, you have wandered beside the dirt road to somewhere. But something is waiting for you.",
                                ResourceManager.getImage(ResourceName.IMAGE_MAP_SWORD),
                                SceneName.BATTLE_SCENE2, SceneName.BATTLE_SCENE1, true, 125, 220);

                createNode(SceneName.BATTLE_SCENE3,
                                "Fogbound Cemetery - The path lead you to the cemetery, spooky...",
                                ResourceManager.getImage(ResourceName.IMAGE_MAP_SWORD),
                                SceneName.BATTLE_SCENE3, SceneName.BATTLE_SCENE2, true, 175, 320);

                createNode(SceneName.BATTLE_SCENE4,
                                "Wondering in the Woods - And you're walking into the Wailing Woods, you think there's a clue there?",
                                ResourceManager.getImage(ResourceName.IMAGE_MAP_SWORD),
                                SceneName.BATTLE_SCENE4, SceneName.BATTLE_SCENE3, true, 0, 120);

                createNode(SceneName.BATTLE_SCENE5,
                                "Deep down to the Doom - Some clues led you to this cave...",
                                ResourceManager.getImage(ResourceName.IMAGE_MAP_SWORD),
                                SceneName.BATTLE_SCENE5, SceneName.BATTLE_SCENE4, true, 275, 120);

                createNode(SceneName.BATTLE_SCENE6,
                                "Temple of Purgation - Deeper to the cave you go, seeing that there is a underground temple.",
                                ResourceManager.getImage(ResourceName.IMAGE_MAP_SWORD),
                                SceneName.BATTLE_SCENE6, SceneName.BATTLE_SCENE5, true, 425, 95);

                createNode(SceneName.BATTLE_SCENE7,
                                "Meet your Maker? - Maybe you have meet the Creator of the Fire Golem...",
                                ResourceManager.getImage(ResourceName.IMAGE_MAP_SWORD),
                                SceneName.BATTLE_SCENE7, SceneName.BATTLE_SCENE6, true, 510, 30);

                createNode(SceneName.BATTLE_SCENE8,
                                "Enchanted Grove - The talisman of the sorcerer teleport you to the Strange place?",
                                ResourceManager.getImage(ResourceName.IMAGE_MAP_SWORD),
                                SceneName.BATTLE_SCENE8, SceneName.BATTLE_SCENE7, true, 625, 30);

                createNode(SceneName.BATTLE_SCENE9,
                                "Scorched Cliff -  You've touch the runic sign and you found yourself at the tallest cliff, what could've gone wrong?",
                                ResourceManager.getImage(ResourceName.IMAGE_MAP_SWORD),
                                SceneName.BATTLE_SCENE9, SceneName.BATTLE_SCENE8, true, 525, 220);

                createNode(SceneName.BOSS, "Last Battle??? MEET THE GOD OF TYPIST",
                                ResourceManager.getImage(ResourceName.IMAGE_MAP_SKULL),
                                SceneName.BOSS, SceneName.BATTLE_SCENE9, true, 450, 360);

                // Other scenes with prerequisites
                createNode(SceneName.REWARD1, "The chest of the town, where you can find items and coins.",
                                ResourceManager.getImage(ResourceName.IMAGE_MAP_CHEST),
                                SceneName.REWARD1, SceneName.BATTLE_SCENE4, false, 100, 30);

                createNode(SceneName.REWARD2, "The chest of the town, where you can find items and coins.",
                                ResourceManager.getImage(ResourceName.IMAGE_MAP_CHEST),
                                SceneName.REWARD2, SceneName.BATTLE_SCENE9, false, 600, 150);

                createNode(SceneName.NEXT_MAP,
                                "The End - You have reached the end of the map, what could have been waiting for you?",
                                ResourceManager.getImage(ResourceName.IMAGE_MAP_NEXT),
                                SceneName.NEXT_MAP, SceneName.BOSS, false, 625, 420);

                createNode(SceneName.START, "The starting point of your journey.",
                                ResourceManager.getImage(ResourceName.IMAGE_MAP_CASTLE),
                                SceneName.CHARACTERS, null, false, 0, 410);

                createNode(SceneName.STORE, "The shop of the town, where you can buy items and upgrade your equipment.",
                                ResourceManager.getImage(ResourceName.IMAGE_MAP_SHOP),
                                SceneName.SHOP, null, false, 125, 440);

                // Connect nodes
                connectNodes(SceneName.START, SceneName.STORE);
                connectNodes(SceneName.START, SceneName.BATTLE_SCENE1);
                connectNodes(SceneName.BATTLE_SCENE4, SceneName.REWARD1);
                connectNodes(SceneName.BATTLE_SCENE9, SceneName.REWARD2);
                connectNodes(SceneName.BATTLE_SCENE1, SceneName.BATTLE_SCENE3);
                connectNodes(SceneName.BATTLE_SCENE1, SceneName.BATTLE_SCENE2);
                connectNodes(SceneName.BATTLE_SCENE2, SceneName.BATTLE_SCENE4);
                connectNodes(SceneName.BATTLE_SCENE2, SceneName.BATTLE_SCENE5);
                connectNodes(SceneName.BATTLE_SCENE5, SceneName.BATTLE_SCENE6);
                connectNodes(SceneName.BATTLE_SCENE6, SceneName.BATTLE_SCENE7);
                connectNodes(SceneName.BATTLE_SCENE7, SceneName.BATTLE_SCENE8);
                connectNodes(SceneName.BATTLE_SCENE6, SceneName.BATTLE_SCENE9);
                connectNodes(SceneName.BOSS, SceneName.BATTLE_SCENE9);
                connectNodes(SceneName.BOSS, SceneName.NEXT_MAP);
        }

        /**
         * Creates a node and adds it to the battle progression
         */
        private static void createNode(String id, String description, Image image, String sceneName,
                        String prerequisite, boolean isBattle, double x, double y) {
                BattleInfo battleInfo = new BattleInfo(id, description, image, sceneName, prerequisite, isBattle, x, y);
                BATTLE_PROGRESSION.add(battleInfo);
                BATTLE_MAP.put(id, battleInfo);
        }

        /**
         * Connects two nodes bidirectionally
         */
        private static void connectNodes(String from, String to) {
                BattleInfo fromNode = BATTLE_MAP.get(from);
                BattleInfo toNode = BATTLE_MAP.get(to);

                if (fromNode != null && toNode != null) {
                        fromNode.addConnection(to);
                        toNode.addConnection(from);
                }
        }

        /**
         * Gets the navigation details for a given action
         * 
         * @param action The action to navigate
         * @return The BattleInfo for the action, or null if no navigation exists
         */
        public static BattleInfo getNavigationDetails(String action) {
                return BATTLE_MAP.get(action);
        }

        /**
         * Gets all battle information
         * 
         * @return List of all battle information
         */
        public static List<BattleInfo> getAllBattleInfo() {
                return new ArrayList<>(BATTLE_PROGRESSION);
        }

        /**
         * Checks if a battle is unlocked based on its prerequisites
         * 
         * @param battleName The name of the battle to check
         * @return true if the battle is unlocked, false otherwise
         */
        public static boolean isBattleUnlocked(String battleName) {
                BattleInfo battleInfo = getNavigationDetails(battleName);
                return battleInfo != null && battleInfo.getPrerequisiteBattle() == null;
        }

        /**
         * Gets the prerequisite for a given battle
         * 
         * @param battleName The name of the battle
         * @return The prerequisite battle name, or null if no prerequisite exists
         */
        public static String getBattlePrerequisite(String battleName) {
                BattleInfo battleInfo = getNavigationDetails(battleName);
                return battleInfo != null ? battleInfo.getPrerequisiteBattle() : null;
        }

        /**
         * Checks if a specific action is navigable based on its prerequisites
         * 
         * @param action         The action to check
         * @param clearedBattles A set of battles that have been cleared
         * @return true if the action can be navigated, false otherwise
         */
        public static boolean canNavigate(String action, Set<String> clearedBattles) {
                BattleInfo navigationDetails = getNavigationDetails(action);
                if (navigationDetails == null) {
                        return false;
                }

                String prerequisite = navigationDetails.getPrerequisiteBattle();
                return prerequisite == null || (clearedBattles != null && clearedBattles.contains(prerequisite));
        }

        // Private constructor to prevent instantiation
        private BattleNavigation() {
                throw new AssertionError("Cannot instantiate utility class");
        }
}