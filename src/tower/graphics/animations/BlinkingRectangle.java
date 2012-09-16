package tower.graphics.animations;

import tower.graphics.Camera;
import tower.graphics.DrawingUtils;
import tower.grid.Area;
import tower.grid.GridCoord;

import java.awt.Color;
import java.awt.Graphics2D;

public class BlinkingRectangle {
    private final Area area;
    private final Camera camera;
    private final Color color;
    private final int framesPerBlink = 30;

    private int frames = 0;


    public BlinkingRectangle(Area area, Camera camera, Color color) {
        this.area = area;
        this.camera = camera;
        this.color = color;
    }

    public void draw(Graphics2D graphics) {
        frames++;

        if (frames < framesPerBlink) {
            DrawingUtils.drawRectangle(
                    area.getCorner(),
                    area.getWidth(),
                    area.getHeight(),
                    color,
                    camera,
                    graphics
            );
        }

        if (frames == 2 * framesPerBlink)
            frames = 0;
    }
}
