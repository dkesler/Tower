package tower.controls;

import tower.grid.GridCoord;

import java.awt.Graphics2D;

public abstract class DrawableIntent extends Intent {
    public abstract void draw(Graphics2D graphics, GridCoord cursor);
}
