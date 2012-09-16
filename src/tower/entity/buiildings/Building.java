package tower.entity.buiildings;

import tower.entity.items.Item;
import tower.entity.recipes.ItemQuantity;
import tower.entity.recipes.Recipe;
import tower.entity.recipes.Recipes;
import tower.grid.Area;
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
    public boolean overlaps(Area other) {
        return getArea().overlaps(other);
    }

    public boolean overlaps(Building other) {
        return getArea().overlaps(other.getArea());
    }

    public Area getArea() {
        return new Area(location, prototype.width, prototype.height);
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
        return getArea().overlaps(new Area(gridCoord));
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
