package tower;

import tower.buiildings.Building;
import tower.buiildings.BuildingFactory;
import tower.graphics.TowerGraphics;
import tower.grid.GridCoord;
import tower.items.ItemFactory;
import tower.map.LocalMap;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Tower {
    public static void main(String[] args) throws IOException {
        LocalMap localMap = new LocalMap();
        BuildingFactory buildingFactory = new BuildingFactory();
        ItemFactory itemFactory = new ItemFactory();

        TowerGraphics towerGraphics = new TowerGraphics(localMap, buildingFactory);


        Building blacksmith = buildingFactory.createByName("Blacksmith", GridCoord.fromUnits(3, 3));

        blacksmith.addItem(itemFactory.createByName("Iron Ore"));

        localMap.addBuilding(
                blacksmith
        );

        localMap.addBuilding(
                buildingFactory.createByName("Leatherworker", GridCoord.fromUnits(8, 8))
        );

        while (true) {
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

            towerGraphics.repaint();

        }
    }
}
