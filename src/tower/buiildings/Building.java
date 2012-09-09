package tower.buiildings;

import tower.grid.GridCoord;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

public class Building {

    final private BuildingPrototype prototype;
    final private GridCoord gridCoord;

    public Building(BuildingPrototype prototype, GridCoord gridCoord) {
        this.gridCoord = gridCoord;
        this.prototype = prototype;
    }

    public int leftEdge() {
        return gridCoord.xUnits;
    }

    public int rightEdge() {
        return gridCoord.xUnits + prototype.width;
    }

    public int upperEdge() {
        return gridCoord.yUnits;
    }

    public int lowerEdge() {
        return gridCoord.yUnits + prototype.height;
    }

    public boolean overlaps(Building other) {
        boolean leftEdgeWithinOther = leftEdge() >= other.leftEdge() && leftEdge() < other.rightEdge();
        boolean rightEdgeWithinOther = rightEdge() > other.leftEdge() && rightEdge() <= other.rightEdge();
        boolean horizontalOverlap = leftEdgeWithinOther || rightEdgeWithinOther;

        boolean upperEdgeWithinOther = upperEdge() >= other.upperEdge() && upperEdge() < other.lowerEdge();
        boolean lowerEdgeWithinOther = lowerEdge() > other.upperEdge() && lowerEdge() <= other.lowerEdge();
        boolean verticalOverlap = upperEdgeWithinOther || lowerEdgeWithinOther;

        return horizontalOverlap && verticalOverlap;
    }

    public void draw(Graphics2D graphics) {
        graphics.drawImage(
                prototype.image,
                new AffineTransformOp(
                        AffineTransform.getScaleInstance(.25, .25),
                        AffineTransformOp.TYPE_NEAREST_NEIGHBOR
                ),
                gridCoord.xPixels,
                gridCoord.yPixels
        );
    }


}
