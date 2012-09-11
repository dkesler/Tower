package tower.controls;

import tower.buiildings.Building;
import tower.graphics.Drawer;
import tower.grid.GridCoord;
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

public class DestroyBuildingIntent extends DrawableIntent {
    private Building selected;
    private boolean isActive;
    private static final int FRAMES_PER_BLINK = 30;
    private int frames;

    final private JPanel jPanel;
    final private Drawer drawer;
    final private JMenu destroyBuildingMenu;
    final private LocalMap localMap;
    final private DestroyBuildingIntent thisIntent;

    final ActionListener destroyBuildingActionListener;

    public DestroyBuildingIntent(JPanel jPanel, Drawer drawer, LocalMap localMap) {
        this.thisIntent = this;
        this.jPanel = jPanel;
        this.drawer = drawer;
        this.localMap = localMap;
        this.destroyBuildingMenu = new JMenu();

        this.drawer.registerIntent(this);

        destroyBuildingActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thisIntent.localMap.removeBuilding(thisIntent.selected);
                thisIntent.isActive = false;
            }
        };
    }

    @Override
    public void draw(Graphics2D g2, GridCoord cursor) {
        if (isActive) {

            frames++;

            if (frames < FRAMES_PER_BLINK) {
                g2.setColor(new Color(255, 0, 0));

                g2.drawRect(
                        selected.leftEdge() * GridUtils.UNIT_SIZE,
                        selected.upperEdge() * GridUtils.UNIT_SIZE,
                        selected.width() * GridUtils.UNIT_SIZE,
                        selected.height() * GridUtils.UNIT_SIZE
                );
            }

            if (frames == 2 * FRAMES_PER_BLINK) {
                frames = 0;
            }
        }
    }

    public void setSelectedBuilding(Building selected) {
        this.selected = selected;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        isActive = true;

        destroyBuildingMenu.removeAll();
        JMenuItem jMenuItem = new JMenuItem("Destroy " + selected.getName());
        jMenuItem.addActionListener(destroyBuildingActionListener);
        destroyBuildingMenu.add(jMenuItem);

        JPopupMenu destroyPopup = destroyBuildingMenu.getPopupMenu();
        destroyPopup.addPopupMenuListener(
                new PopupMenuListener() {
                    @Override
                    public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                    }

                    @Override
                    public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                    }

                    @Override
                    public void popupMenuCanceled(PopupMenuEvent e) {
                        thisIntent.isActive = false;
                    }
                }
        );

        jPanel.add(destroyBuildingMenu);
        destroyPopup.show(jPanel, e.getX(), e.getY());
    }
}
