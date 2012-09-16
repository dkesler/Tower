package tower.controls;

import tower.entity.buiildings.Building;
import tower.graphics.Camera;
import tower.graphics.MenuPanel;
import tower.graphics.animations.BlinkingRectangle;
import tower.map.LocalMap;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

public class DestroyBuildingIntent extends DrawableIntent {
    private Building selected;
    private boolean isActive;
    private BlinkingRectangle buildingHightlight;

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
            buildingHightlight.draw(g2);
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
            buildingHightlight = new BlinkingRectangle(selected.getLocation(), selected.width(), selected.height(), camera, Color.RED);
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

    public void closeMenu() {
        popup.setVisible(false);
        selected = null;
        isActive = false;
    }
}
