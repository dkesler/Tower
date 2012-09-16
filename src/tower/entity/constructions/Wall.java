package tower.entity.constructions;

import tower.entity.buiildings.Building;
import tower.grid.Area;
import tower.grid.GridCoord;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;

public class Wall {
    private static final WallPrototype prototype = new WallPrototype();
    private final GridCoord position;

    public Wall(GridCoord position) {
        this.position = position;
    }

    public void draw(Graphics2D graphics2D) {
        graphics2D.drawImage(
                prototype.image,
                new AffineTransformOp(
                        AffineTransform.getScaleInstance(.25, .25),
                        AffineTransformOp.TYPE_NEAREST_NEIGHBOR
                ),
                position.xPixels,
                position.yPixels
        );
    }

    public GridCoord getLocation() {
        return position;
    }

    public boolean overlaps(Area area) {
        return new Area(position).overlaps(area);
    }
}
