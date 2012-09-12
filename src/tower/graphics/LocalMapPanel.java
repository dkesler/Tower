package tower.graphics;

import tower.entity.buiildings.BuildingFactory;
import tower.controls.CameraControlIntent;
import tower.controls.ContextMenuIntent;
import tower.controls.CursorTrackingIntent;
import tower.controls.DrawableIntent;
import tower.controls.ViewBuildingDetailsIntent;
import tower.grid.GridCoord;
import tower.map.LocalMap;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.LinkedHashSet;
import java.util.Set;

public class LocalMapPanel {

    final private Set<DrawableIntent> intents = new LinkedHashSet<>();
    final private Camera camera;
    final private JPanel jPanel;

    private GridCoord mouseCoord;

    public LocalMapPanel(final LocalMap localMap, final BuildingFactory buildingFactory) {
        camera = new Camera();


        jPanel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;

                g2.setTransform(camera.getCameraTransform());

                localMap.draw(g2);

                for (DrawableIntent drawableIntent : intents) {
                    drawableIntent.draw(g2, mouseCoord);
                }
            }
        };

        ContextMenuIntent contextMenuIntent = new ContextMenuIntent(jPanel, localMap, camera, this, buildingFactory);
        new CursorTrackingIntent(this, jPanel);
        new CameraControlIntent(camera, jPanel);
        new ViewBuildingDetailsIntent(localMap, camera, jPanel, this)
                .registerIncompatibleIntent(contextMenuIntent.buildingPlacementIntent);
    }

    public void setMouseCoord(MouseEvent e) {
        this.mouseCoord = camera.convertEventToGrid(e);
    }

    public void registerIntent(DrawableIntent drawableIntent) {
        intents.add(drawableIntent);
    }

    public JPanel getjPanel() {
        return jPanel;
    }
}
