package tower.graphics;

import tower.controls.UseRecipeIntent;
import tower.entity.buiildings.Building;

import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

public class BuildingDetailsPanel extends Panel {

    private Building selected;

    public BuildingDetailsPanel(JPanel jPanel) {
        registerIntent(new UseRecipeIntent(this, jPanel));
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
}
