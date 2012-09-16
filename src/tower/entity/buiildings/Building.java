package tower.entity.buiildings;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import tower.entity.constructions.Wall;
import tower.entity.items.Item;
import tower.entity.items.ItemPrototype;
import tower.entity.recipes.ItemQuantity;
import tower.entity.recipes.Recipe;
import tower.entity.recipes.Recipes;
import tower.grid.GridCoord;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Building {

    final private BuildingPrototype prototype;
    final private GridCoord gridCoord;
    final private Map<String, Integer> items = new HashMap<>();

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
