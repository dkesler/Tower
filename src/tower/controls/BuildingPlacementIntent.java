package tower.controls;

import tower.buiildings.Building;
import tower.buiildings.BuildingPrototype;
import tower.grid.GridCoord;
import tower.grid.GridUtils;

import java.awt.Color;
import java.awt.Graphics2D;

public class BuildingPlacementIntent {
    public String name;
    public BuildingPrototype prototype;
    public boolean isActive;

    public void draw(Graphics2D g2, GridCoord gridCoord) {
        g2.setColor(new Color(0, 255, 0));
        g2.drawRect(
                gridCoord.xPixels,
                gridCoord.yPixels,
                prototype.width * GridUtils.UNIT_SIZE,
                prototype.height * GridUtils.UNIT_SIZE
        );
    }
}
