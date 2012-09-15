package tower.graphics;

import tower.controls.UseRecipeIntent;
import tower.controls.ViewBuildingDetailsIntent;
import tower.entity.buiildings.Building;

import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

public class BuildingDetailsPanel extends Panel {
    private final BuildingInventoryPanel buildingInventoryPanel;
    private final BuildingRecipePanel buildingRecipePanel;

    private Building selected;

    public BuildingDetailsPanel(JPanel jPanel) {
        buildingInventoryPanel = new BuildingInventoryPanel();
        buildingInventoryPanel.setHeight(200);

        buildingRecipePanel = new BuildingRecipePanel();

        UseRecipeIntent userRecipeIntent = new UseRecipeIntent(this);
        userRecipeIntent.attachTo(buildingRecipePanel);
        userRecipeIntent.registerListeners(jPanel);

        ViewBuildingDetailsIntent viewBuildingDetailsIntent = new ViewBuildingDetailsIntent(this);
        viewBuildingDetailsIntent.attachTo(buildingInventoryPanel);

        subPanels.add(buildingInventoryPanel);
        subPanels.add(buildingRecipePanel);
    }

    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
        buildingInventoryPanel.setVisible(visible);
        buildingRecipePanel.setVisible(visible);
    }

    @Override
    protected void drawImplSpecific(Graphics2D graphics2D, Point2D mouseCoord) {

    }

    public Building getSelected() {
        return selected;
    }

    public void setSelected(Building selected) {
        this.selected = selected;
    }

    @Override
    public void reflow(Graphics2D graphics2D) {
        buildingInventoryPanel.setWidth(width);
        buildingInventoryPanel.setX(x);
        buildingInventoryPanel.setY(y);

        buildingRecipePanel.setWidth(width);
        buildingRecipePanel.setHeight(height - 200);
        buildingRecipePanel.setX(x);
        buildingRecipePanel.setY(y + 200);
    }
}
