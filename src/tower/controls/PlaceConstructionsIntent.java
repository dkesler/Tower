package tower.controls;

import tower.entity.buiildings.Building;
import tower.entity.constructions.Wall;
import tower.graphics.Camera;
import tower.graphics.DrawingUtils;
import tower.graphics.animations.BlinkingRectangle;
import tower.grid.Area;
import tower.grid.GridCoord;
import tower.map.LocalMap;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

public class PlaceConstructionsIntent extends DrawableIntent {

    private final Camera camera;
    private final LocalMap localMap;

    private PlaceConstructionsState state = PlaceConstructionsState.INACTIVE;
    private GridCoord start;
    private GridCoord end;
    private boolean validPlacement;
    private BlinkingRectangle buildingHighlight;

    public PlaceConstructionsIntent(Camera camera, LocalMap localMap) {
        this.camera = camera;
        this.localMap = localMap;
    }

    @Override
    public void draw(Graphics2D graphics, Point2D cursor) {
        if (state == PlaceConstructionsState.SELECT_START) {
            GridCoord mouseCursor = camera.convertPointToGrid(cursor);
            validatePlacement(mouseCursor, mouseCursor);
            DrawingUtils.drawRectangle(
                    mouseCursor,
                    mouseCursor,
                    validPlacement ? Color.GREEN : Color.RED,
                    camera,
                    graphics
            );
        } else if (state == PlaceConstructionsState.CONFIRM) {
            validatePlacement(start, end);
            buildingHighlight.draw(graphics);
        } else if (state == PlaceConstructionsState.SELECT_END) {
            GridCoord mouseCursor = camera.convertPointToGrid(cursor);
            validatePlacement(start, mouseCursor);
            DrawingUtils.drawRectangle(
                    start,
                    mouseCursor,
                    validPlacement ? Color.GREEN : Color.RED,
                    camera,
                    graphics
            );
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'c' && state == PlaceConstructionsState.INACTIVE) {
            state = PlaceConstructionsState.SELECT_START;
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER && state == PlaceConstructionsState.CONFIRM) {
            validatePlacement(start, end);
            if (validPlacement) {
                state = PlaceConstructionsState.INACTIVE;
                for (int x = Math.min(start.xUnits, end.xUnits); x < Math.max(start.xUnits, end.xUnits) + 1; ++x) {
                    for (int y = Math.min(start.yUnits, end.yUnits); y < Math.max(start.yUnits, end.yUnits) + 1; ++y) {
                        localMap.addWall(new Wall(GridCoord.fromUnits(x, y)));
                    }
                }
                start = null;
                end = null;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            state = PlaceConstructionsState.INACTIVE;
            start = null;
            end = null;
        }
    }

    private void validatePlacement(GridCoord corner1, GridCoord corner2) {

        for (Building building : localMap.getBuildings()) {
            if (building.overlaps(new Area(corner1, corner2))) {
                validPlacement = false;
                return;
            }
        }

        for (Wall wall : localMap.getWalls()) {
            if (wall.overlaps(new Area(corner1, corner2))) {
                validPlacement = false;
                return;
            }
        }

        validPlacement = true;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() != MouseEvent.BUTTON1) {
            return;
        }

        if (state == PlaceConstructionsState.INACTIVE) {
            return;
        }

        if (state == PlaceConstructionsState.CONFIRM ) {
            return;
        }

        if (!host.contains(e.getPoint())) {
            return;
        }

        if (state == PlaceConstructionsState.SELECT_START) {

            GridCoord start = camera.convertEventToGrid(e);
            validatePlacement(start, start);
            if (validPlacement) {
                state = PlaceConstructionsState.SELECT_END;
                this.start = start;
            }
        } else if (state == PlaceConstructionsState.SELECT_END) {
            GridCoord end = camera.convertEventToGrid(e);
            validatePlacement(start, end);
            if (validPlacement) {
                state = PlaceConstructionsState.CONFIRM;
                this.end = end;
                buildingHighlight = new BlinkingRectangle(new Area(start, end), camera, Color.GREEN);
            }
        }
    }

    @Override
    public boolean isActive() {
        return state != PlaceConstructionsState.INACTIVE;
    }

    @Override
    public void registerListeners(JPanel jPanel) {
        jPanel.addKeyListener(this);
        jPanel.addMouseListener(this);
    }

    private static enum PlaceConstructionsState {
        INACTIVE,
        SELECT_START,
        SELECT_END,
        CONFIRM
    }
}
