package tower.graphics;

import tower.controls.BuildingPlacementIntent;
import tower.controls.Intent;
import tower.grid.GridCoord;
import tower.map.LocalMap;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Drawer extends JPanel {

    final private LocalMap localMap;
    final private Set<Intent> activeIntents = new LinkedHashSet<>();

    public int mouseX;
    public int mouseY;

    public Drawer(LocalMap localMap) {
        this.localMap = localMap;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        localMap.draw(g2);

        for (Intent intent : activeIntents) {
            intent.draw(g2, GridCoord.fromPixels(mouseX, mouseY));
        }
    }

    public void addActiveIntent(Intent intent) {
        activeIntents.add(intent);
    }

    public void removeActiveIntent(Intent intent) {
        activeIntents.remove(intent);
    }
}
