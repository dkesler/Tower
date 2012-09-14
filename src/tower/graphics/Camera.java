package tower.graphics;

import tower.grid.GridCoord;

import java.awt.Event;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.util.EventObject;

public class Camera {
    private double zoom = 4;
    private int scrollX = 0;
    private int scrollY = 0;

    private final static double MIN_ZOOM = 1;
    private final static double MAX_ZOOM = 10;

    private final static int MAX_SCROLL_X = 0;
    private final static int MAX_SCROLL_Y = 0;
    private final static int MIN_SCROLL_X = -2000;
    private final static int MIN_SCROLL_Y = -2000;


    public GridCoord convertEventToGrid(MouseEvent e) {
        return convertPointToGrid(e.getPoint());
    }

    public GridCoord convertPointToGrid(Point2D src) {
        try {
            Point2D dest = getCameraTransform().inverseTransform(src, null);
            return GridCoord.fromPixels(dest.getX(), dest.getY());
        } catch (NoninvertibleTransformException e1) {
            throw new RuntimeException(e1);
        }
    }

    public AffineTransform getCameraTransform() {
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.translate(scrollX, scrollY);
        affineTransform.scale(4 / zoom, 4 / zoom);
        return affineTransform;
    }

    public void zoom(int amountToZoom) {
        zoom += amountToZoom;
        if (zoom > MAX_ZOOM) zoom = MAX_ZOOM;
        if (zoom < MIN_ZOOM) zoom = MIN_ZOOM;
    }

    public void scroll(double x, double y) {
        scrollX += x;
        scrollY += y;

        if (scrollX > MAX_SCROLL_X) scrollX = MAX_SCROLL_X;
        if (scrollX < MIN_SCROLL_X) scrollX = MIN_SCROLL_X;

        if (scrollY > MAX_SCROLL_Y) scrollY = MAX_SCROLL_Y;
        if (scrollY < MIN_SCROLL_Y) scrollY = MIN_SCROLL_Y;
    }
}
