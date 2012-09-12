package tower.controls;

import tower.buiildings.Building;
import tower.buiildings.BuildingFactory;
import tower.buiildings.BuildingPrototype;
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

public class BuildingPlacementIntent extends DrawableIntent {
    private String name;
    private BuildingPrototype prototype;
    private boolean active;
    private boolean isValidPlacement;

    final private LocalMap localMap;
    final private BuildingFactory buildingFactory;
    final private JMenu createBuildingMenu;
    final private LocalMapPanel localMapPanel;
    final private JPanel jPanel;
    final private Camera camera;

    public BuildingPlacementIntent(LocalMap localMap, BuildingFactory buildingFactory, LocalMapPanel localMapPanel, JPanel jPanel, Camera camera) {
        this.localMap = localMap;
        this.buildingFactory = buildingFactory;
        this.createBuildingMenu = new JMenu();
        this.localMapPanel = localMapPanel;
        this.jPanel = jPanel;
        this.camera = camera;

        this.localMapPanel.registerIntent(this);

        final BuildingPlacementIntent thisIntent = this;
        for (final String building : buildingFactory.getBuildingNames()) {
            JMenuItem menuItem = new JMenuItem(building);
            createBuildingMenu.add(menuItem);
            menuItem.addActionListener(
                    new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            name = building;
                            prototype = thisIntent.buildingFactory.getPrototype(building);
                            active = true;
                        }
                    }
            );
        }
    }

    @Override
    public void draw(Graphics2D g2, GridCoord cursor) {
        if (active)
        {
            if (isValidPlacement) {
                g2.setColor(new Color(0, 255, 0));
            } else {
                g2.setColor(new Color(255, 0, 0));
            }
            g2.drawRect(
                    cursor.xPixels,
                    cursor.yPixels,
                    prototype.width * GridUtils.UNIT_SIZE,
                    prototype.height * GridUtils.UNIT_SIZE
            );
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
        } else if (e.getButton() == MouseEvent.BUTTON1 && active && isValidPlacement) {
            active = false;
            localMap.addBuilding(
                    buildingFactory.createByName(
                            name,
                            camera.convertEventToGrid(e)
                    )
            );
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
