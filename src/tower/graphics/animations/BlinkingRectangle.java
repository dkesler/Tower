package tower.graphics.animations;

import tower.graphics.Camera;
import tower.graphics.DrawingUtils;
import tower.grid.GridCoord;

import java.awt.Color;
import java.awt.Graphics2D;

public class BlinkingRectangle {
    private final GridCoord corner;
    private final int width;
    private final int height;
    private final Camera camera;
    private final Color color;
    private final int framesPerBlink = 30;

    private int frames = 0;


    public BlinkingRectangle(GridCoord corner, int width, int height, Camera camera, Color color) {
        this.corner = corner;
        this.width = width;
        this.height = height;
        this.camera = camera;
        this.color = color;
    }

    public void draw(Graphics2D graphics) {
        frames++;

        if (frames < framesPerBlink) {
            DrawingUtils.drawRectangle(
                    corner,
                    width,
                    height,
                    color,
                    camera,
                    graphics
            );
        }

        if (frames == 2 * framesPerBlink)
            frames = 0;
    }
}
