package tower.grid;

public class GridCoord {
    public static final int UNIT_SIZE = 16;
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
        int xPixels = xUnits * UNIT_SIZE;
        int yPixels = yUnits * UNIT_SIZE;

        return new GridCoord(xPixels, yPixels, xUnits, yUnits);
    }

    public static GridCoord fromPixels(double xPixels, double yPixels) {
        int xUnits = (int) (xPixels / UNIT_SIZE);
        int yUnits = (int) (yPixels / UNIT_SIZE);

        return fromUnits(xUnits, yUnits);
    }
}
