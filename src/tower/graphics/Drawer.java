package tower.graphics;

import tower.controls.DrawableIntent;
import tower.grid.GridCoord;
import tower.map.LocalMap;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.LinkedHashSet;
import java.util.Set;

public class Drawer extends JPanel {

    final private LocalMap localMap;
    final private Set<DrawableIntent> activeDrawableIntents = new LinkedHashSet<>();
    final private Camera camera;

    private GridCoord mouseCoord;

    public Drawer(LocalMap localMap, Camera camera) {
        this.localMap = localMap;
        this.camera = camera;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setTransform(camera.getCameraTransform());

        localMap.draw(g2);

        for (DrawableIntent drawableIntent : activeDrawableIntents) {
            drawableIntent.draw(g2, mouseCoord);
        }
    }

    public void setMouseCoord(MouseEvent e) {
        this.mouseCoord = camera.convertEventToGrid(e);
    }

    public void addActiveIntent(DrawableIntent drawableIntent) {
        activeDrawableIntents.add(drawableIntent);
    }

    public void removeActiveIntent(DrawableIntent drawableIntent) {
        activeDrawableIntents.remove(drawableIntent);
    }
}
