package tower.controls;

import tower.entity.buiildings.Building;
import tower.entity.buiildings.BuildingFactory;
import tower.graphics.Camera;
import tower.graphics.LocalMapPanel;
import tower.graphics.Panel;
import tower.map.LocalMap;

import javax.swing.JPanel;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class ContextMenuIntent extends Intent {
    private final LocalMap localMap;
    private final Camera camera;

    private final DestroyBuildingIntent destroyBuildingIntent;
    public final BuildingPlacementIntent buildingPlacementIntent;

    public ContextMenuIntent(LocalMap localMap, Camera camera) {
        this.localMap = localMap;
        this.camera = camera;

        destroyBuildingIntent = new DestroyBuildingIntent( localMap, camera);
        buildingPlacementIntent = new BuildingPlacementIntent(localMap,  camera);

        }

    @Override
    public void registerListeners(JPanel jPanel) {
        jPanel.addMouseListener(this);
        jPanel.addMouseMotionListener(this);
        jPanel.addKeyListener(this);

        destroyBuildingIntent.registerListeners(jPanel);
        buildingPlacementIntent.registerListeners(jPanel);
    }

    @Override
    public void attachTo(Panel host) {
        super.attachTo(host);
        destroyBuildingIntent.attachTo(host);
        buildingPlacementIntent.attachTo(host);
    }

    @Override
    public void mouseClicked(final MouseEvent e) {
        if (host.contains(e.getPoint())) {
            if (e.getButton() == MouseEvent.BUTTON3) {
                final Building selected = localMap.getBuildingAt(camera.convertEventToGrid(e));
                if (selected != null) {
                    destroyBuildingIntent.setSelectedBuilding(selected);
                    destroyBuildingIntent.mouseClicked(e);
                    buildingPlacementIntent.closeMenu();
                } else {
                    buildingPlacementIntent.mouseClicked(e);
                    destroyBuildingIntent.closeMenu();
                }
            } else {
                if (destroyBuildingIntent.isActive()) {
                    destroyBuildingIntent.mouseClicked(e);
                } else {
                    buildingPlacementIntent.mouseClicked(e);
                }
            }
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
