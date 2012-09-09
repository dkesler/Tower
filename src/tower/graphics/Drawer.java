package tower.graphics;

import tower.controls.BuildingPlacementIntent;
import tower.controls.Intent;
import tower.grid.GridCoord;
import tower.map.LocalMap;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Drawer extends JPanel {

    final private LocalMap localMap;
    final private Set<Intent> activeIntents = new LinkedHashSet<>();
    final private Camera camera;

    public GridCoord mouseCoord;


    public Drawer(LocalMap localMap, Camera camera) {
        this.localMap = localMap;
        this.camera = camera;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setTransform(camera.getCameraTransform());

        localMap.draw(g2);

        for (Intent intent : activeIntents) {
            intent.draw(g2, mouseCoord);
        }
    }

    public void addActiveIntent(Intent intent) {
        activeIntents.add(intent);
    }

    public void removeActiveIntent(Intent intent) {
        activeIntents.remove(intent);
    }
}
