package tower.map;

import tower.buiildings.Building;

import java.awt.Graphics2D;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

public class LocalMap {
    final private LinkedHashSet<Building> buildings = new LinkedHashSet<Building>();

    public void draw(Graphics2D graphics) {
        for (Building building : buildings) {
            building.draw(graphics);
        }
    }

    public void addBuilding(Building building) {
        buildings.add(building);
    }
}
