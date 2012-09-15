package tower.controls;

import tower.entity.buiildings.Building;
import tower.entity.recipes.Recipe;
import tower.graphics.BuildingDetailsPanel;
import tower.graphics.BuildingRecipePanel;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.List;

public class UseRecipeIntent extends DrawableIntent {

    private final BuildingDetailsPanel buildingDetailsPanel;
    private final BuildingRecipePanel buildingRecipePanel;

    public UseRecipeIntent(BuildingDetailsPanel buildingDetailsPanel, BuildingRecipePanel buildingRecipePanel, JPanel jPanel) {
        this.buildingDetailsPanel = buildingDetailsPanel;
        this.buildingRecipePanel = buildingRecipePanel;
        jPanel.addMouseListener(this);
    }

    @Override
    public void draw(Graphics2D graphics, Point2D cursor) {
        Building selected = buildingDetailsPanel.getSelected();
        if (selected != null) {

            int y = buildingRecipePanel.getY() + 16;
            int x = buildingRecipePanel.getX() + 5;

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
                int recipeIdx = (e.getY() - buildingRecipePanel.getY()) / 16;
                List<Recipe> recipies = buildingDetailsPanel.getSelected().getRecipies();

                if (recipeIdx >= 0 && recipeIdx < recipies.size()) {
                    Recipe selectedRecipe = recipies.get(recipeIdx);
                    if (buildingDetailsPanel.getSelected().isRecipeUsable(selectedRecipe)) {
                        buildingDetailsPanel.getSelected().applyRecipe(selectedRecipe);
                    }
                }
            }
        }
    }
}
