package tower.controls;

import tower.buiildings.Building;
import tower.graphics.Camera;
import tower.graphics.LocalMapPanel;
import tower.grid.GridCoord;
import tower.map.LocalMap;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class ViewBuildingDetailsIntent extends DrawableIntent {

    private Building selected;

    private final LocalMap localMap;
    private final Camera camera;

    public ViewBuildingDetailsIntent(LocalMap localMap, Camera camera, JPanel jPanel, LocalMapPanel localMapPanel) {
        this.localMap = localMap;
        this.camera = camera;

        jPanel.addMouseListener(this);
        localMapPanel.registerIntent(this);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            selected = localMap.getBuildingAt(camera.convertEventToGrid(e));
        }
    }

    @Override
    public void draw(Graphics2D graphics, GridCoord cursor) {
        if (selected != null) {
            graphics.setColor(new Color(255, 255, 255));

            int height = 400;
            graphics.drawString(String.format("%-20s %s", "Item", "Count"), 400, height);

            for (String itemType : selected.getStoredItems().keySet()) {
                height += 16;
                graphics.drawString(
                        String.format(
                                "%-20s %d\n",
                                itemType,
                                selected.getStoredItems().get(itemType).size()
                        ), 400, height
                );
            }
        }
    }
}
