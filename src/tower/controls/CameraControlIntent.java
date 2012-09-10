package tower.controls;

import tower.graphics.Camera;

import javax.swing.JPanel;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class CameraControlIntent extends Intent {

    private final Camera camera;
    private final JPanel jPanel;

    private Point dragStart;
    private boolean dragging = false;

    public CameraControlIntent(Camera camera, JPanel jPanel) {
        this.camera = camera;
        this.jPanel = jPanel;

        jPanel.addMouseMotionListener(this);
        jPanel.addMouseListener(this);
        jPanel.addMouseWheelListener(this);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
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
        camera.zoom(e.getWheelRotation());
    }
}
