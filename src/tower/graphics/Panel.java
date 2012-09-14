package tower.graphics;

import tower.controls.DrawableIntent;
import tower.grid.GridCoord;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;

public abstract class Panel {
    private int x;
    private int y;
    private int width;
    private int height;
    private int z;
    private Color color = Color.BLACK;
    private boolean visible;

    protected final List<DrawableIntent> drawableIntents = new LinkedList<>();
    protected final List<Panel> subPanels = new LinkedList<>();

    public final void registerIntent(DrawableIntent intent) {
        drawableIntents.add(intent);
    }

    public final void draw(Graphics2D graphics2D, Point2D mouseCoord) {
        for (Panel subPanel : subPanels) {
            subPanel.draw(graphics2D, mouseCoord);
        }

        drawImplSpecific(graphics2D, mouseCoord);

        for (DrawableIntent drawableIntent : drawableIntents) {
            drawableIntent.draw(graphics2D, mouseCoord);
        }
    }

    protected abstract void drawImplSpecific(Graphics2D graphics2D, Point2D mouseCoord);
}
