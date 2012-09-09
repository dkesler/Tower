package tower.controls;

import tower.buiildings.Building;
import tower.buiildings.BuildingPrototype;
import tower.grid.GridCoord;
import tower.grid.GridUtils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Set;

public class BuildingPlacementIntent {
    public String name;
    public BuildingPrototype prototype;
    public boolean isActive;
    public boolean isValidPlacement;

    public void draw(Graphics2D g2, GridCoord gridCoord) {
        if (isValidPlacement) {
            g2.setColor(new Color(0, 255, 0));
        } else {
            g2.setColor(new Color(255, 0, 0));
        }
        g2.drawRect(
                gridCoord.xPixels,
                gridCoord.yPixels,
                prototype.width * GridUtils.UNIT_SIZE,
                prototype.height * GridUtils.UNIT_SIZE
        );
    }

    public void updateValidity(GridCoord gridCoord, Set<Building> buildings) {
        Building candidate = new Building(prototype, gridCoord);
        for (Building building : buildings) {
            if (candidate.overlaps(building)) {
                isValidPlacement = false;
                return;
            }
        }

        isValidPlacement = true;
    }
}
