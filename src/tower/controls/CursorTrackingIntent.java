package tower.controls;

import tower.graphics.LocalMapPanel;
import tower.graphics.RootPanel;

import javax.swing.JPanel;
import java.awt.event.MouseEvent;

public class CursorTrackingIntent extends Intent {

    private final JPanel jPanel;
    private final RootPanel rootPanel;

    public CursorTrackingIntent(RootPanel rootPanel, JPanel jPanel) {
        this.rootPanel = rootPanel;
        this.jPanel = jPanel;

        jPanel.addMouseMotionListener(this);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        rootPanel.setMouseCoord(e.getPoint());
    }
}
