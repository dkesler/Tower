package tower.controls;

import tower.entity.buiildings.Building;
import tower.entity.buiildings.BuildingFactory;
import tower.entity.buiildings.BuildingPrototype;
import tower.entity.constructions.Wall;
import tower.graphics.Camera;
import tower.graphics.MenuPanel;
import tower.grid.GridCoord;
import tower.grid.GridUtils;
import tower.map.LocalMap;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class BuildingPlacementIntent extends DrawableIntent {
    private BuildingPrototype prototype;
    private boolean active;
    private boolean isValidPlacement;

    final private LocalMap localMap;
    final private Camera camera;
    final private MenuPanel menuPanel;

    public BuildingPlacementIntent(LocalMap localMap, Camera camera) {
        this.localMap = localMap;
        this.camera = camera;
        this.menuPanel = new MenuPanel();

        for (String building : BuildingFactory.getBuildingNames()) {
            menuPanel.addOption(building);
        }
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
        menuPanel.draw(g2, cursor);
    }

    private void updateValidity(GridCoord gridCoord) {
        Building candidate = new Building(prototype, gridCoord);
        for (Building building : localMap.getBuildings()) {
            if (candidate.overlaps(building)) {
                isValidPlacement = false;
                return;
            }
        }

        for (Wall wall : localMap.getWalls()) {
            if (candidate.overlaps(wall.getLocation())) {
                isValidPlacement = false;
                return;
            }
        }

        isValidPlacement = true;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!active && e.getButton() == MouseEvent.BUTTON3) {
            menuPanel.setVisible(true);
            menuPanel.setX(e.getX());
            menuPanel.setY(e.getY());
        } else if (e.getButton() == MouseEvent.BUTTON1 && active && isValidPlacement && host.contains(e.getPoint())) {
            active = false;
            localMap.addBuilding(new Building(prototype, camera.convertEventToGrid(e)));
        } else if (e.getButton() == MouseEvent.BUTTON1 && !active && menuPanel.contains(e.getPoint())) {
            String selectedOption = menuPanel.getSelectedOption(e.getPoint());
            if (selectedOption != null) {
                prototype = BuildingFactory.getPrototype(selectedOption);
                active = true;
            }
            menuPanel.setVisible(false);
        } else if (e.getButton() == MouseEvent.BUTTON1 && !active && !menuPanel.contains(e.getPoint())) {
            menuPanel.setVisible(false);
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

    public void closeMenu() {
        menuPanel.setVisible(false);
    }
}
