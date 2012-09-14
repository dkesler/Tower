package tower.entity.items;

import tower.entity.EntityPrototypeLoader;

import java.util.Collection;
import java.util.Map;

public class ItemFactory {
    final Map<String, ItemPrototype> prototypes;

    public ItemFactory() {
        prototypes = new EntityPrototypeLoader<ItemPrototype>(
                "/details/items",
                new ItemPrototypeBuilderFactory()
        ).getPrototypes();
    }

    public Item createByName(String name) {
        ItemPrototype prototype = getPrototype(name);

        return new Item(
                prototype
        );
    }

    public ItemPrototype getPrototype(String name) {
        if (!prototypes.containsKey(name)) {
            throw new RuntimeException("No item prototype for [" + name + "]");
        }

        return prototypes.get(name);
    }

    public Collection<String> getItemNames() {
        return prototypes.keySet();
    }
}
