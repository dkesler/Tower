package tower.entity.buiildings;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import tower.grid.GridCoord;
import tower.entity.items.Item;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

public class Building {

    final private BuildingPrototype prototype;
    final private GridCoord gridCoord;
    final private Multimap<String, Item> storedItems = HashMultimap.create();

    public Building(BuildingPrototype prototype, GridCoord gridCoord) {
        this.gridCoord = gridCoord;
        this.prototype = prototype;
    }

    public String getName() {
        return prototype.name;
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


    public boolean overlaps(GridCoord gridCoord) {
        boolean horizontalOverlap = gridCoord.xUnits >= leftEdge() && gridCoord.xUnits < rightEdge();
        boolean verticalOverlap = gridCoord.yUnits >= upperEdge() && gridCoord.yUnits < lowerEdge();

        return horizontalOverlap && verticalOverlap;
    }

    public int width() {
        return prototype.width;
    }

    public int height() {
        return prototype.height;
    }

    public void addItem(Item item) {
        storedItems.put(item.getName(), item);
    }

    public Multimap<String, Item> getStoredItems() {
        return storedItems;
    }
}
