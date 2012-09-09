package tower.map;

import tower.buiildings.Building;
import tower.grid.GridCoord;

import java.awt.Graphics2D;
import java.util.LinkedHashSet;
import java.util.Set;

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

    public Set<Building> getBuildings() {
        return buildings;
    }

    public Building getBuildingAt(GridCoord gridCoord) {
        for (Building building : buildings) {
            if (building.overlaps(gridCoord)) {
                return building;
            }
        }

        return null;
    }

    public void removeBuilding(Building building) {
        buildings.remove(building);
    }
}
