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

    private final static double MIN_ZOOM = 1;
    private final static double MAX_ZOOM = 10;

    public GridCoord convertEventToGrid(MouseEvent e) {

        Point2D src = new Point(e.getX(), e.getY());

        try {
            Point2D dest = getCameraTransform().inverseTransform(src, null);
            return GridCoord.fromPixels(dest.getX(), dest.getY());
        } catch (NoninvertibleTransformException e1) {
            throw new RuntimeException(e1);
        }
    }

    public AffineTransform getCameraTransform() {
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.scale(4 / zoom, 4 / zoom);
        return affineTransform;
    }

    public void zoom(int amountToZoom) {
        zoom += amountToZoom;
        if (zoom > MAX_ZOOM) zoom = MAX_ZOOM;
        if (zoom < MIN_ZOOM) zoom = MIN_ZOOM;
    }
}
