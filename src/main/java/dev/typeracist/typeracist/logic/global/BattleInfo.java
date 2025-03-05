package dev.typeracist.typeracist.logic.global;

import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.List;

public class BattleInfo {
    private String battleName;
    private String battleDescription;
    private Image battleImage;
    private String sceneName;
    private String prerequisiteBattle;
    private boolean isBattle;
    private double nodeX;
    private double nodeY;
    private List<String> connectedNodes;

    public BattleInfo(String battleName, String battleDescription, Image battleImage, String sceneName,
            String prerequisiteBattle, boolean isBattle, double nodeX, double nodeY) {
        this.battleName = battleName;
        this.sceneName = sceneName;
        this.prerequisiteBattle = prerequisiteBattle;
        this.isBattle = isBattle;
        this.battleDescription = battleDescription;
        this.battleImage = battleImage;
        this.nodeX = nodeX;
        this.nodeY = nodeY;
        this.connectedNodes = new ArrayList<>();
    }

    public BattleInfo(String battleName, String battleDescription, Image battleImage, String sceneName,
            String prerequisiteBattle, double nodeX, double nodeY) {
        this(battleName, battleDescription, battleImage, sceneName, prerequisiteBattle, false, nodeX, nodeY);
    }

    public String getBattleName() {
        return battleName;
    }

    public String getSceneName() {
        return sceneName;
    }

    public String getPrerequisiteBattle() {
        return prerequisiteBattle;
    }

    public boolean isBattle() {
        return isBattle;
    }

    public String getBattleDescription() {
        return battleDescription;
    }

    public Image getBattleImage() {
        return battleImage;
    }

    public double getNodeX() {
        return nodeX;
    }

    public double getNodeY() {
        return nodeY;
    }

    public List<String> getConnectedNodes() {
        return connectedNodes;
    }

    public void addConnection(String nodeId) {
        if (!connectedNodes.contains(nodeId)) {
            connectedNodes.add(nodeId);
        }
    }
}
