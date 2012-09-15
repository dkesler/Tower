package tower.controls;

import tower.entity.buiildings.Building;
import tower.graphics.Camera;
import tower.graphics.MenuPanel;
import tower.graphics.Panel;
import tower.grid.GridUtils;
import tower.map.LocalMap;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class DestroyBuildingIntent extends DrawableIntent {
    private Building selected;
    private boolean isActive;
    private static final int FRAMES_PER_BLINK = 30;
    private int frames;

    final private LocalMap localMap;
    final private Camera camera;
    final private MenuPanel popup;

    public DestroyBuildingIntent(LocalMap localMap, Camera camera) {
        this.localMap = localMap;
        this.camera = camera;
        this.popup = new MenuPanel();
        popup.addOption("Destroy Building");

        isActive = false;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public void draw(Graphics2D g2, Point2D cursor) {
        if (isActive) {

            frames++;

            if (frames < FRAMES_PER_BLINK) {
                g2.setTransform(camera.getCameraTransform());
                g2.setColor(new Color(255, 0, 0));

                g2.drawRect(
                        selected.leftEdge() * GridUtils.UNIT_SIZE,
                        selected.upperEdge() * GridUtils.UNIT_SIZE,
                        selected.width() * GridUtils.UNIT_SIZE,
                        selected.height() * GridUtils.UNIT_SIZE
                );
                g2.setTransform(new AffineTransform());
            }

            if (frames == 2 * FRAMES_PER_BLINK) {
                frames = 0;
            }

            popup.draw(g2, cursor);
        }
    }

    public void setSelectedBuilding(Building selected) {
        this.selected = selected;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            isActive = true;
            popup.setX(e.getX());
            popup.setY(e.getY());
            popup.setVisible(true);
        } else if (isActive) {
            if (popup.contains(e.getPoint())) {
                if (popup.getSelectedOption(e.getPoint()) != null) {
                    isActive = false;
                    localMap.removeBuilding(selected);
                }
            } else {
                isActive = false;
                popup.setVisible(false);
            }
        }

    }
}
