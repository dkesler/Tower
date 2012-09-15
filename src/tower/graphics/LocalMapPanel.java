package tower.graphics;

import tower.map.LocalMap;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class LocalMapPanel extends Panel {

    final private Camera camera;
    final private LocalMap localMap;

    public LocalMapPanel() {
        this.camera = new Camera();
        this.localMap = new LocalMap();
        this.visible = true;
    }

    @Override
    protected void drawImplSpecific(Graphics2D graphics2D, Point2D mouseCoord) {
        graphics2D.setTransform(camera.getCameraTransform());
        localMap.draw(graphics2D);
        graphics2D.setTransform(new AffineTransform());
    }

    public Camera getCamera() {
        return camera;
    }

    public LocalMap getLocalMap() {
        return localMap;
    }
}
