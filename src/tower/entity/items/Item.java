package tower.entity.items;

public class Item {
    final private ItemPrototype prototype;

    public Item(ItemPrototype prototype) {
        this.prototype = prototype;
    }

    public String getName() {
        return prototype.name;
    }
}
