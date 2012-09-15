package tower.controls;

import tower.entity.buiildings.Building;
import tower.graphics.BuildingDetailsPanel;
import tower.graphics.Camera;
import tower.grid.GridUtils;
import tower.map.LocalMap;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class SelectBuildingIntent extends DrawableIntent {

    private final LocalMap localMap;
    private final Camera camera;
    private final BuildingDetailsPanel buildingDetailsPanel;

    private int frames = 0;
    private final static int FRAMES_PER_BLINK = 30;

    public SelectBuildingIntent(
            LocalMap localMap,
            Camera camera,
            BuildingDetailsPanel buildingDetailsPanel
    ) {
        this.localMap = localMap;
        this.camera = camera;
        this.buildingDetailsPanel = buildingDetailsPanel;
    }

    @Override
    public void registerListeners(JPanel jPanel) {
        jPanel.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (isActivatable()) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                Building selected = localMap.getBuildingAt(camera.convertEventToGrid(e));

                if (selected != null) {
                    buildingDetailsPanel.setSelected(selected);
                    buildingDetailsPanel.setVisible(true);
                    frames = 0;
                } else if (!buildingDetailsPanel.contains(e.getPoint())) {
                    buildingDetailsPanel.setVisible(false);
                    buildingDetailsPanel.setSelected(null);
                }
            }
        }
    }

    @Override
    public void draw(Graphics2D graphics, Point2D cursor) {
        Building selected = buildingDetailsPanel.getSelected();
        if (selected != null) {
            frames++;

            if (frames < FRAMES_PER_BLINK) {
                graphics.setTransform(camera.getCameraTransform());
                graphics.setColor(new Color(0, 255, 0));

                graphics.drawRect(
                        selected.leftEdge() * GridUtils.UNIT_SIZE,
                        selected.upperEdge() * GridUtils.UNIT_SIZE,
                        selected.width() * GridUtils.UNIT_SIZE,
                        selected.height() * GridUtils.UNIT_SIZE
                );
                graphics.setTransform(new AffineTransform());
            }

            if (frames == 2 * FRAMES_PER_BLINK) {
                frames = 0;
            }
        }
    }
}
