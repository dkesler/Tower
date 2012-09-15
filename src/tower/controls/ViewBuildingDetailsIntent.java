package tower.controls;

import tower.entity.buiildings.Building;
import tower.graphics.BuildingDetailsPanel;
import tower.graphics.Camera;
import tower.map.LocalMap;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

public class ViewBuildingDetailsIntent extends DrawableIntent {

    private final LocalMap localMap;
    private final Camera camera;
    private final BuildingDetailsPanel buildingDetailsPanel;

    public ViewBuildingDetailsIntent(
            LocalMap localMap,
            Camera camera,
            JPanel jPanel,
            BuildingDetailsPanel buildingDetailsPanel
    ) {
        this.localMap = localMap;
        this.camera = camera;
        this.buildingDetailsPanel = buildingDetailsPanel;

        jPanel.addMouseListener(this);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (isActivatable()) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                Building selected = localMap.getBuildingAt(camera.convertEventToGrid(e));

                if (selected != null) {
                    buildingDetailsPanel.setSelected(selected);
                    buildingDetailsPanel.setVisible(true);
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
            graphics.setColor(new Color(255, 255, 255));

            int y = buildingDetailsPanel.getY() + 16;
            int x = buildingDetailsPanel.getX() + 5;

            graphics.drawString(String.format("%-20s %s", "Item", "Count"), x, y);

            for (String itemType : selected.getStoredItems().keySet()) {
                y += 16;
                graphics.drawString(
                        String.format(
                                "%-20s %d\n",
                                itemType,
                                selected.getStoredItems().get(itemType)
                        ),
                        x,
                        y
                );
            }
        }
    }
}
