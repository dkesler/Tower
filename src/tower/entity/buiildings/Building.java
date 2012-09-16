package tower.entity.buiildings;

import tower.entity.items.Item;
import tower.entity.recipes.ItemQuantity;
import tower.entity.recipes.Recipe;
import tower.entity.recipes.Recipes;
import tower.grid.GridCoord;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Building {

    final private BuildingPrototype prototype;
    final private GridCoord location;
    final private Map<String, Integer> items = new HashMap<>();

    public Building(BuildingPrototype prototype, GridCoord location) {
        this.location = location;
        this.prototype = prototype;
    }

    public String getName() {
        return prototype.name;
    }

    public int leftEdge() {
        return location.xUnits;
    }

    public int rightEdge() {
        return location.xUnits + prototype.width - 1;
    }

    public int upperEdge() {
        return location.yUnits;
    }

    public int lowerEdge() {
        return location.yUnits + prototype.height - 1;
    }

    public GridCoord getLocation() {
        return location;
    }

    public boolean overlaps(GridCoord corner1, GridCoord corner2) {
        int otherLeftEdge = Math.min(corner1.xUnits, corner2.xUnits);
        int otherRightEdge = Math.max(corner1.xUnits, corner2.xUnits);
        int otherUpperEdge = Math.min(corner1.yUnits, corner2.yUnits);
        int otherLowerEdge = Math.max(corner1.yUnits, corner2.yUnits);

        return overlaps(otherLeftEdge, otherRightEdge, otherLowerEdge, otherUpperEdge);
    }

    public boolean overlaps(Building other) {
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

    public void draw(Graphics2D graphics) {
        graphics.drawImage(
                prototype.image,
                new AffineTransformOp(
                        AffineTransform.getScaleInstance(.25, .25),
                        AffineTransformOp.TYPE_NEAREST_NEIGHBOR
                ),
                location.xPixels,
                location.yPixels
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
        addItems(item.getName(), 1);
    }

    private void addItems(String itemType, int quantity) {
        if (items.containsKey(itemType)) {
            items.put(itemType, items.get(itemType) + quantity);
        } else {
            items.put(itemType, quantity);
        }
    }

    private void removeItems(String itemType, int quantity) {
        if (!items.containsKey(itemType) || items.get(itemType) < quantity) {
            int actualQuantity = items.containsKey(itemType) ? items.get(itemType) : 0;
            throw new RuntimeException("Can't remove " + quantity + " items of type [" + itemType + "]: only have " + actualQuantity);
        }

        int remaining = items.get(itemType) - quantity;
        if (remaining > 0) {
            items.put(itemType, remaining);
        } else {
            items.remove(itemType);
        }
    }

    public Map<String, Integer> getStoredItems() {
        return items;
    }

    public List<Recipe> getRecipies() {
        return Recipes.getRecipiesForBuilding(prototype);
    }

    public boolean isRecipeUsable(Recipe selectedRecipe) {
        for (ItemQuantity itemQuantity : selectedRecipe.reagents) {
            if (!items.containsKey(itemQuantity.item.name) || items.get(itemQuantity.item.name) < itemQuantity.quantity) {
                return false;
            }
        }

        return true;
    }

    public void applyRecipe(Recipe selectedRecipe) {
        for (ItemQuantity itemQuantity : selectedRecipe.reagents) {
            removeItems(itemQuantity.item.name, itemQuantity.quantity);
        }

        for (ItemQuantity itemQuantity : selectedRecipe.results) {
            addItems(itemQuantity.item.name, itemQuantity.quantity);
        }
    }


}
