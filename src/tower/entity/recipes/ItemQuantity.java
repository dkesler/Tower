package tower.entity.recipes;

import tower.entity.items.ItemPrototype;

public class ItemQuantity {
    public final ItemPrototype item;
    public final int quantity;

    public ItemQuantity(ItemPrototype item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }
}
