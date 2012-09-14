package tower.controls;

import tower.entity.buiildings.Building;
import tower.graphics.Camera;
import tower.map.LocalMap;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

public class ViewBuildingDetailsIntent extends DrawableIntent {

    private Building selected;

    private final LocalMap localMap;
    private final Camera camera;
    private final JPanel jPanel;

    public ViewBuildingDetailsIntent(
            LocalMap localMap,
            Camera camera,
            JPanel jPanel
    ) {
        this.localMap = localMap;
        this.camera = camera;
        this.jPanel = jPanel;

        jPanel.addMouseListener(this);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (isActivatable()) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                selected = localMap.getBuildingAt(camera.convertEventToGrid(e));
            }
        }
    }

    @Override
    public void draw(Graphics2D graphics, Point2D cursor) {
        if (selected != null) {
            graphics.setColor(new Color(255, 255, 255));

            int panelY = jPanel.getY();
            int panelX = jPanel.getX();

            int height = panelY + 80;

            graphics.drawString(String.format("%-20s %s", "Item", "Count"), panelX, height);

            for (String itemType : selected.getStoredItems().keySet()) {
                height += 16;
                graphics.drawString(
                        String.format(
                                "%-20s %d\n",
                                itemType,
                                selected.getStoredItems().get(itemType).size()
                        ),
                        panelX,
                        height
                );
            }
        }
    }
}
