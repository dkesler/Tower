package tower.graphics;

import tower.controls.CursorTrackingIntent;
import tower.controls.DrawableIntent;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

public class RootPanel extends Panel {

    private final JPanel jPanel;
    private Point2D mouseCoord;
    private final CursorTrackingIntent cursorTrackingIntent;

    public RootPanel() {
        final RootPanel thisPanel = this;

        jPanel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                thisPanel.draw((Graphics2D) g);
            }
        };
        jPanel.setFocusable(true);

        LocalMapPanel localMapPanel = new LocalMapPanel(jPanel);
        subPanels.add(localMapPanel);

        cursorTrackingIntent = new CursorTrackingIntent(this, jPanel);

    }

    public JPanel getjPanel() {
        return jPanel;
    }

    private void draw(Graphics2D graphics2D) {
        this.draw(graphics2D, mouseCoord);
    }

    @Override
    protected void drawImplSpecific(Graphics2D graphics2D, Point2D mouseCoord) {
        for (Panel subPanel : subPanels) {
            subPanel.draw(graphics2D, mouseCoord);
        }

        for (DrawableIntent drawableIntent : drawableIntents) {
            drawableIntent.draw(graphics2D, mouseCoord);
        }
    }

    public void setMouseCoord(Point2D mouseCoord) {
        this.mouseCoord = mouseCoord;
    }
}
