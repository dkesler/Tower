package tower.graphics;

import tower.grid.GridCoord;
import tower.grid.GridUtils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class DrawingUtils {

    private DrawingUtils() {}

    public static void drawRectangle(GridCoord corner1, GridCoord corner2, Color color, Camera camera, Graphics2D graphics) {
        drawRectangle(
                Math.min(corner1.xUnits, corner2.xUnits),
                Math.min(corner1.yUnits, corner2.yUnits),
                Math.abs(corner1.xUnits - corner2.xUnits) + 1,
                (Math.abs(corner1.yUnits - corner2.yUnits) + 1),
                color,
                camera,
                graphics
        );
    }

    public static void drawRectangle(int x, int y, int width, int height, Color color, Camera camera, Graphics2D graphics) {
        graphics.setTransform(camera.getCameraTransform());
        graphics.setColor(color);
        graphics.drawRect(
                x * GridUtils.UNIT_SIZE,
                y * GridUtils.UNIT_SIZE,
                width * GridUtils.UNIT_SIZE,
                height * GridUtils.UNIT_SIZE
        );
        graphics.setTransform(new AffineTransform());
    }

    public static void drawRectangle(GridCoord upperLeft, int width, int height, Color color, Camera camera, Graphics2D graphics2D) {
        drawRectangle(
                upperLeft.xUnits,
                upperLeft.yUnits,
                width,
                height,
                color,
                camera,
                graphics2D
        );
    }
}
