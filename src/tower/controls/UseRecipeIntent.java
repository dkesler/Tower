package tower.controls;

import tower.entity.buiildings.Building;
import tower.entity.recipes.Recipe;
import tower.graphics.BuildingDetailsPanel;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.List;

public class UseRecipeIntent extends DrawableIntent {

    private final BuildingDetailsPanel buildingDetailsPanel;

    public UseRecipeIntent(BuildingDetailsPanel buildingDetailsPanel, JPanel jPanel) {
        this.buildingDetailsPanel = buildingDetailsPanel;
        jPanel.addMouseListener(this);
    }

    @Override
    public void draw(Graphics2D graphics, Point2D cursor) {
        Building selected = buildingDetailsPanel.getSelected();
        if (selected != null) {

            int y = buildingDetailsPanel.getY() + 200;
            int x = buildingDetailsPanel.getX() + 5;

            Collection<Recipe> recipes = selected.getRecipies();

            for (Recipe recipe : recipes) {
                if (selected.isRecipeUsable(recipe)) {
                    graphics.setColor(Color.WHITE);
                } else {
                    graphics.setColor(Color.GRAY);
                }
                graphics.drawString(recipe.toString(), x, y);
                y += 16;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1 && buildingDetailsPanel.getSelected() != null) {
            if (buildingDetailsPanel.contains(e.getPoint())) {
                int recipeIdx = (e.getY() - 200) / 16;
                List<Recipe> recipies = buildingDetailsPanel.getSelected().getRecipies();
                Recipe selectedRecipe = recipies.get(recipeIdx);
                if (recipeIdx >= 0 && recipeIdx < recipies.size() && buildingDetailsPanel.getSelected().isRecipeUsable(selectedRecipe)) {
                    buildingDetailsPanel.getSelected().applyRecipe(selectedRecipe);
                }
            }
        }
    }
}
