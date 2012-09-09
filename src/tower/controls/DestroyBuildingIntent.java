package tower.controls;

import tower.buiildings.Building;
import tower.grid.GridCoord;
import tower.grid.GridUtils;

import java.awt.Color;
import java.awt.Graphics2D;

public class DestroyBuildingIntent implements Intent {
    public Building building;
    public boolean isActive;
    private static final int FRAMES_PER_BLINK = 30;
    private int frames;

    @Override
    public void draw(Graphics2D g2, GridCoord cursor) {
        frames++;

        if (frames < FRAMES_PER_BLINK) {
            g2.setColor(new Color(255, 0, 0));

            g2.drawRect(
                    building.leftEdge() * GridUtils.UNIT_SIZE,
                    building.upperEdge() * GridUtils.UNIT_SIZE,
                    building.width() * GridUtils.UNIT_SIZE,
                    building.height() * GridUtils.UNIT_SIZE
            );
        }

        if (frames == 2 * FRAMES_PER_BLINK) {
            frames = 0;
        }
    }
}
