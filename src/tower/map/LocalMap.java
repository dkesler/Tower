package tower.map;

import tower.entity.buiildings.Building;
import tower.entity.constructions.Wall;
import tower.grid.GridCoord;

import java.awt.Graphics2D;
import java.util.LinkedHashSet;
import java.util.Set;

public class LocalMap {
    final private LinkedHashSet<Building> buildings = new LinkedHashSet<>();
    final private LinkedHashSet<Wall> walls = new LinkedHashSet<>();

    public void draw(Graphics2D graphics) {
        for (Building building : buildings) {
            building.draw(graphics);
        }

        for (Wall wall : walls) {
            wall.draw(graphics);
        }
    }

    public void addBuilding(Building building) {
        buildings.add(building);
    }

    public void addWall(Wall wall) {
        walls.add(wall);
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

    public Set<Wall> getWalls() {
        return walls;
    }
}
