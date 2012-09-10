package tower.controls;

import tower.graphics.Drawer;

import javax.swing.JPanel;
import java.awt.event.MouseEvent;

public class CursorTrackingIntent extends Intent {

    private final JPanel jPanel;
    private final Drawer drawer;

    public CursorTrackingIntent(Drawer drawer, JPanel jPanel) {
        this.drawer = drawer;
        this.jPanel = jPanel;

        jPanel.addMouseMotionListener(this);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        drawer.setMouseCoord(e);
    }
}
