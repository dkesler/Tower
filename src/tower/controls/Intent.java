package tower.controls;

import tower.graphics.Panel;

import javax.swing.JPanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.LinkedList;
import java.util.List;

public abstract class Intent implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener {

    private List<Intent> incompatibleIntents = new LinkedList<>();
    protected Panel host;

    public boolean isActive() {
        return true;
    }

    public void registerIncompatibleIntent(Intent incompatibleIntent) {
        incompatibleIntents.add(incompatibleIntent);
    }

    public void attachTo(Panel host) {
        this.host = host;
    }

    public void registerListeners(JPanel jPanel) {};

    protected boolean isActivatable() {

        for (Intent intent : incompatibleIntents) {
            if (intent.isActive()) {
                return false;
            }
        }

        return true;
    }


    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
}
