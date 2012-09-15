package tower.controls;

import tower.graphics.Camera;
import tower.graphics.LocalMapPanel;

import javax.swing.JPanel;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class CameraControlIntent extends Intent {

    private final Camera camera;
    private final JPanel jPanel;
    private final LocalMapPanel localMapPanel;

    private Point dragStart;
    private boolean dragging = false;

    public CameraControlIntent(Camera camera, JPanel jPanel, LocalMapPanel localMapPanel) {
        this.camera = camera;
        this.jPanel = jPanel;
        this.localMapPanel = localMapPanel;

        jPanel.addMouseMotionListener(this);
        jPanel.addMouseListener(this);
        jPanel.addMouseWheelListener(this);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1 && localMapPanel.contains(e.getPoint())) {
            dragging = true;
            dragStart = e.getPoint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        dragging = false;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (dragging) {
            double tx = e.getX() - dragStart.getX();
            double ty = e.getY() - dragStart.getY();
            dragStart = e.getPoint();
            camera.scroll(tx, ty);
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (localMapPanel.contains(e.getPoint())) {
            camera.zoom(e.getWheelRotation());
        }
    }
}
