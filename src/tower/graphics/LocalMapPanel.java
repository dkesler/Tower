package tower.graphics;

import tower.controls.CameraControlIntent;
import tower.controls.ContextMenuIntent;
import tower.controls.ViewBuildingDetailsIntent;
import tower.entity.buiildings.Building;
import tower.entity.buiildings.BuildingFactory;
import tower.entity.items.ItemFactory;
import tower.grid.GridCoord;
import tower.map.LocalMap;

import javax.swing.JPanel;
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
