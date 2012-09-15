package tower.controls;

import tower.entity.buiildings.Building;
import tower.entity.buiildings.BuildingFactory;
import tower.entity.buiildings.BuildingPrototype;
import tower.graphics.Camera;
import tower.graphics.LocalMapPanel;
import tower.grid.GridCoord;
import tower.grid.GridUtils;
import tower.map.LocalMap;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class BuildingPlacementIntent extends DrawableIntent {
    private BuildingPrototype prototype;
    private boolean active;
    private boolean isValidPlacement;

    final private LocalMap localMap;
    final private JMenu createBuildingMenu;
    private JPanel jPanel;
    final private Camera camera;

    public BuildingPlacementIntent(LocalMap localMap, Camera camera) {
        this.localMap = localMap;
        this.createBuildingMenu = new JMenu();
        this.camera = camera;

        final BuildingPlacementIntent thisIntent = this;
        for (final String building : BuildingFactory.getBuildingNames()) {
            JMenuItem menuItem = new JMenuItem(building);
            createBuildingMenu.add(menuItem);
            menuItem.addActionListener(
                    new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            prototype = BuildingFactory.getPrototype(building);
                            active = true;
                        }
                    }
            );
        }
    }

    @Override
    public void registerListeners(JPanel jPanel) {
        this.jPanel = jPanel;
    }

    @Override
    public void draw(Graphics2D g2, Point2D cursor) {
        if (active)
        {
            g2.setTransform(camera.getCameraTransform());
            GridCoord cursorOnGrid = camera.convertPointToGrid(cursor);
            if (isValidPlacement) {
                g2.setColor(new Color(0, 255, 0));
            } else {
                g2.setColor(new Color(255, 0, 0));
            }
            g2.drawRect(
                    cursorOnGrid.xPixels,
                    cursorOnGrid.yPixels,
                    prototype.width * GridUtils.UNIT_SIZE,
                    prototype.height * GridUtils.UNIT_SIZE
            );

            g2.setTransform(new AffineTransform());
        }
    }

    private void updateValidity(GridCoord gridCoord) {
        Building candidate = new Building(prototype, gridCoord);
        for (Building building : localMap.getBuildings()) {
            if (candidate.overlaps(building)) {
                isValidPlacement = false;
                return;
            }
        }

        isValidPlacement = true;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!active && e.getButton() == MouseEvent.BUTTON3) {
            jPanel.add(createBuildingMenu);
            createBuildingMenu.getPopupMenu().show(jPanel, e.getX(), e.getY());
        } else if (e.getButton() == MouseEvent.BUTTON1 && active && isValidPlacement && host.contains(e.getPoint())) {
            active = false;
            localMap.addBuilding(new Building(prototype, camera.convertEventToGrid(e)));
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (active) {
            updateValidity(camera.convertEventToGrid(e));
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (active && e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            active = false;
        }
    }

    @Override
    public boolean isActive() {
        return active;
    }
}
