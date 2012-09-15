package tower.controls;

import tower.graphics.Panel;
import tower.grid.GridCoord;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

public abstract class DrawableIntent extends Intent {
    public abstract void draw(Graphics2D graphics, Point2D cursor);

    @Override
    public void attachTo(Panel host) {
        super.attachTo(host);
        host.registerDrawableIntent(this);
    }
}
