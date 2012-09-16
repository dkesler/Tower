package tower.controls;

import tower.entity.constructions.Wall;
import tower.graphics.Camera;
import tower.graphics.DrawingUtils;
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

    public PlaceConstructionsIntent(Camera camera, LocalMap localMap) {
        this.camera = camera;
        this.localMap = localMap;
    }

    @Override
    public void draw(Graphics2D graphics, Point2D cursor) {
        if (state == PlaceConstructionsState.SELECT_START) {
            GridCoord mouseCursor = camera.convertPointToGrid(cursor);
            DrawingUtils.drawRectangle(mouseCursor, mouseCursor, Color.GREEN, camera, graphics);
        } else if (state == PlaceConstructionsState.CONFIRM) {
            DrawingUtils.drawRectangle(start, end, Color.GREEN, camera, graphics);
        } else if (state == PlaceConstructionsState.SELECT_END) {
            DrawingUtils.drawRectangle(start, camera.convertPointToGrid(cursor), Color.GREEN, camera, graphics);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'c' && state == PlaceConstructionsState.INACTIVE) {
            state = PlaceConstructionsState.SELECT_START;
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER && state == PlaceConstructionsState.CONFIRM) {
            state = PlaceConstructionsState.INACTIVE;
            for (int x = Math.min(start.xUnits, end.xUnits); x < Math.max(start.xUnits, end.xUnits) + 1; ++x) {
                for (int y = Math.min(start.yUnits, end.yUnits); y < Math.max(start.yUnits, end.yUnits) + 1; ++y) {
                    localMap.addWall(new Wall(GridCoord.fromUnits(x, y)));
                }
            }
            start = null;
            end = null;
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            state = PlaceConstructionsState.INACTIVE;
            start = null;
            end = null;
        }
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
            state = PlaceConstructionsState.SELECT_END;
            start = camera.convertEventToGrid(e);
        } else if (state == PlaceConstructionsState.SELECT_END) {
            state = PlaceConstructionsState.CONFIRM;
            end = camera.convertEventToGrid(e);
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
