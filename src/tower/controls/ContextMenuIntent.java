package tower.controls;

import tower.buiildings.Building;
import tower.buiildings.BuildingFactory;
import tower.graphics.Camera;
import tower.graphics.LocalMapPanel;
import tower.map.LocalMap;

import javax.swing.JPanel;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class ContextMenuIntent extends Intent {
    private final JPanel jPanel;
    private final LocalMap localMap;
    private final Camera camera;

    private final DestroyBuildingIntent destroyBuildingIntent;
    private final BuildingPlacementIntent buildingPlacementIntent;

    public ContextMenuIntent(JPanel jPanel, LocalMap localMap, Camera camera, LocalMapPanel localMapPanel, BuildingFactory buildingFactory) {
        this.jPanel = jPanel;
        this.localMap = localMap;
        this.camera = camera;

        destroyBuildingIntent = new DestroyBuildingIntent(jPanel, localMapPanel, localMap);
        buildingPlacementIntent = new BuildingPlacementIntent(localMap, buildingFactory, localMapPanel, jPanel, camera);

        jPanel.addMouseListener(this);
        jPanel.addMouseMotionListener(this);
        jPanel.addKeyListener(this);
    }
    @Override
    public void mouseClicked(final MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            final Building selected = localMap.getBuildingAt(camera.convertEventToGrid(e));
            if (selected != null) {
                destroyBuildingIntent.setSelectedBuilding(selected);
                destroyBuildingIntent.mouseClicked(e);
            } else {
                buildingPlacementIntent.mouseClicked(e);
            }
        } else {
            buildingPlacementIntent.mouseClicked(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        buildingPlacementIntent.mouseMoved(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        buildingPlacementIntent.keyPressed(e);
    }
}
