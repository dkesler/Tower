package tower.entity.items;

import tower.entity.EntityPrototypeLoader;

import java.util.Collection;
import java.util.Map;

public class ItemFactory {
    private static Map<String, ItemPrototype> prototypes;

    private ItemFactory() {}

    public static void initialize() {
        prototypes = new EntityPrototypeLoader<>(
                "/details/items",
                new ItemPrototypeBuilderFactory()
        ).getPrototypes();
    }


    public static Item createByName(String name) {
        ItemPrototype prototype = getPrototype(name);

        return new Item(
                prototype
        );
    }

    public static ItemPrototype getPrototype(String name) {
        if (prototypes == null) {
            throw new RuntimeException("Items not initialized");
        }

        if (!prototypes.containsKey(name)) {
            throw new RuntimeException("No item prototype for [" + name + "]");
        }

        return prototypes.get(name);
    }

    public static Collection<String> getItemNames() {
        return prototypes.keySet();
    }
}
