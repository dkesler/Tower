package tower.controls;

import tower.entity.buiildings.Building;
import tower.graphics.BuildingDetailsPanel;
import tower.graphics.Camera;
import tower.graphics.animations.BlinkingRectangle;
import tower.map.LocalMap;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

public class SelectBuildingIntent extends DrawableIntent {

    private final LocalMap localMap;
    private final Camera camera;
    private final BuildingDetailsPanel buildingDetailsPanel;

    private BlinkingRectangle buildingHighlight;

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
                    buildingHighlight = new BlinkingRectangle(selected.getArea(), camera, Color.GREEN);
                } else if (!buildingDetailsPanel.contains(e.getPoint())) {
                    buildingDetailsPanel.setVisible(false);
                    buildingDetailsPanel.setSelected(null);
                    buildingHighlight = null;
                }
            }
        }
    }

    @Override
    public void draw(Graphics2D graphics, Point2D cursor) {
        if (buildingHighlight != null) {
            buildingHighlight.draw(graphics);
        }
    }
}
