package tower.grid;

public class GridCoord {
    public final int xPixels;
    public final int yPixels;
    public final int xUnits;
    public final int yUnits;

    private GridCoord(int xPixels, int yPixels, int xUnits, int yUnits) {
        this.xPixels = xPixels;
        this.yPixels = yPixels;
        this.xUnits = xUnits;
        this.yUnits = yUnits;
    }

    public static GridCoord fromUnits(int xUnits, int yUnits) {
        int xPixels = xUnits * GridUtils.UNIT_SIZE;
        int yPixels = yUnits * GridUtils.UNIT_SIZE;

        return new GridCoord(xPixels, yPixels, xUnits, yUnits);
    }

    public static GridCoord fromPixels(int xPixels, int yPixels) {

        int xUnits = xPixels / GridUtils.UNIT_SIZE;
        int yUnits = yPixels / GridUtils.UNIT_SIZE;

        return fromUnits(xUnits, yUnits);
    }
}
