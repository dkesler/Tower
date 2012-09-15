package tower;

import tower.entity.buiildings.BuildingFactory;
import tower.entity.items.ItemFactory;
import tower.entity.recipes.Recipe;
import tower.entity.recipes.Recipes;
import tower.graphics.TowerGraphics;

import java.io.IOException;

public class Tower {
    public static void main(String[] args) throws IOException {
        BuildingFactory.initialize();
        ItemFactory.initialize();
        Recipes.initialize();
        TowerGraphics towerGraphics = new TowerGraphics();

        while (true) {
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                throw new RuntimeException("Exception while sleeping", e);
            }

            towerGraphics.repaint();
        }
    }
}
