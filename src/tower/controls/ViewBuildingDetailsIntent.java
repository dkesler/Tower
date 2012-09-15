package tower.controls;

import tower.entity.buiildings.Building;
import tower.graphics.BuildingDetailsPanel;
import tower.graphics.BuildingInventoryPanel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

public class ViewBuildingDetailsIntent extends DrawableIntent {

    private final BuildingDetailsPanel buildingDetailsPanel;
    private final BuildingInventoryPanel buildingInventoryPanel;

    public ViewBuildingDetailsIntent(BuildingDetailsPanel buildingDetailsPanel, BuildingInventoryPanel buildingInventoryPanel) {
        this.buildingDetailsPanel = buildingDetailsPanel;
        this.buildingInventoryPanel = buildingInventoryPanel;
    }

    @Override
    public void draw(Graphics2D graphics, Point2D cursor) {
        Building selected = buildingDetailsPanel.getSelected();
        if (selected != null) {
            graphics.setColor(new Color(255, 255, 255));

            int y = buildingInventoryPanel.getY() + 16;
            int x = buildingInventoryPanel.getX() + 5;

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
