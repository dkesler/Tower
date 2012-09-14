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
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected Color bgColor = Color.BLACK;
    protected Color borderColor = Color.WHITE;
    protected boolean visible;

    protected final List<DrawableIntent> drawableIntents = new LinkedList<>();
    protected final List<Panel> subPanels = new LinkedList<>();

    public final void registerIntent(DrawableIntent intent) {
        drawableIntents.add(intent);
    }

    public final void draw(Graphics2D graphics2D, Point2D mouseCoord) {
        if (visible) {
            graphics2D.setColor(bgColor);
            graphics2D.fillRect(x, y, width, height);

            for (Panel subPanel : subPanels) {
                subPanel.draw(graphics2D, mouseCoord);
            }

            drawImplSpecific(graphics2D, mouseCoord);

            for (DrawableIntent drawableIntent : drawableIntents) {
                drawableIntent.draw(graphics2D, mouseCoord);
            }

            graphics2D.setColor(borderColor);
            graphics2D.drawRect(x, y, width, height);
        }
    }

    protected abstract void drawImplSpecific(Graphics2D graphics2D, Point2D mouseCoord);

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean within(Point2D point) {
        return point.getX() >= x && point.getX() <= x + width && point.getY() >= y && point.getY() <= y + height;
    }
}
