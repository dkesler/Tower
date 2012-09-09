package tower.controls;

import tower.grid.GridCoord;

import java.awt.Graphics2D;

public interface Intent {
    void draw(Graphics2D graphics, GridCoord cursor);
}
