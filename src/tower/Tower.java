package tower;

import tower.buiildings.Building;
import tower.buiildings.BuildingFactory;
import tower.graphics.TowerGraphics;
import tower.map.LocalMap;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Tower {
    public static void main(String[] args) throws IOException {
        LocalMap localMap = new LocalMap();
        TowerGraphics towerGraphics = new TowerGraphics(localMap);

        BuildingFactory buildingFactory = new BuildingFactory();

        localMap.addBuilding(
                buildingFactory.createByName("Blacksmith", 3, 3)
        );

        localMap.addBuilding(
                buildingFactory.createByName("Leatherworker", 8, 8)
        );

        towerGraphics.repaint();
    }
}
