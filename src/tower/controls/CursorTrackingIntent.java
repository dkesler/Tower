package tower.controls;

import tower.graphics.LocalMapPanel;

import javax.swing.JPanel;
import java.awt.event.MouseEvent;

public class CursorTrackingIntent extends Intent {

    private final JPanel jPanel;
    private final LocalMapPanel localMapPanel;

    public CursorTrackingIntent(LocalMapPanel localMapPanel, JPanel jPanel) {
        this.localMapPanel = localMapPanel;
        this.jPanel = jPanel;

        jPanel.addMouseMotionListener(this);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        localMapPanel.setMouseCoord(e);
    }
}
