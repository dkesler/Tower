package tower.grid;

public class Area {
    private final GridCoord upperLeft;
    private final int width;
    private final int height;

    public Area(GridCoord corner, int width, int height) {
        this.upperLeft = corner;
        this.width = width;
        this.height = height;

        assert(width > 0);
        assert(height > 0);
    }

    public Area(GridCoord corner) {
        this.upperLeft = corner;
        width = 1;
        height = 1;
    }

    public Area(GridCoord corner1, GridCoord corner2) {
        int x = Math.min(corner1.xUnits, corner2.xUnits);
        int y = Math.min(corner1.yUnits, corner2.yUnits);

        this.upperLeft = GridCoord.fromUnits(x, y);
        this.width = Math.abs(corner1.xUnits - corner2.xUnits) + 1;
        this.height = Math.abs(corner1.yUnits - corner2.yUnits) + 1;

        assert (width > 0);
        assert (height > 0);
    }

    public int leftEdge() {
        return upperLeft.xUnits;
    }

    public int rightEdge() {
        return upperLeft.xUnits + width - 1;
    }

    public int upperEdge() {
        return upperLeft.yUnits;
    }

    public int lowerEdge() {
        return upperLeft.yUnits + height - 1;
    }

    public boolean overlaps(Area other) {
        int otherLeftEdge = other.leftEdge();
        int otherRightEdge = other.rightEdge();
        int otherLowerEdge = other.lowerEdge();
        int otherUpperEdge = other.upperEdge();

        return overlaps(otherLeftEdge, otherRightEdge, otherLowerEdge, otherUpperEdge);
    }

    private boolean overlaps(int otherLeftEdge, int otherRightEdge, int otherLowerEdge, int otherUpperEdge) {
        boolean leftEdgeWithinOther = leftEdge() >= otherLeftEdge && leftEdge() <= otherRightEdge;
        boolean othersLeftEdgeWithinMe = otherLeftEdge >= leftEdge() && otherLeftEdge <= rightEdge();
        boolean horizontalOverlap = leftEdgeWithinOther || othersLeftEdgeWithinMe;

        if (!horizontalOverlap) {
            return false;
        }

        boolean upperEdgeWithinOther = upperEdge() >= otherUpperEdge && upperEdge() <= otherLowerEdge;
        boolean othersUpperEdgeWithinMe = otherUpperEdge >= upperEdge() && otherUpperEdge <= lowerEdge();
        boolean verticalOverlap = upperEdgeWithinOther || othersUpperEdgeWithinMe;

        return verticalOverlap;
    }

}
