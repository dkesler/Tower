package tower.controls;

import tower.buiildings.Building;
import tower.buiildings.BuildingFactory;
import tower.graphics.Camera;
import tower.graphics.Drawer;
import tower.grid.GridCoord;
import tower.map.LocalMap;

import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class ContextMenuIntent extends Intent {
    private final JPanel jPanel;
    private final LocalMap localMap;
    private final Camera camera;

    private final DestroyBuildingIntent destroyBuildingIntent;
    private final BuildingPlacementIntent buildingPlacementIntent;

    public ContextMenuIntent(JPanel jPanel, LocalMap localMap, Camera camera, Drawer drawer, BuildingFactory buildingFactory) {
        this.jPanel = jPanel;
        this.localMap = localMap;
        this.camera = camera;

        destroyBuildingIntent = new DestroyBuildingIntent(jPanel, drawer, localMap);
        buildingPlacementIntent = new BuildingPlacementIntent(localMap, buildingFactory, drawer, jPanel, camera);

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
