package tower.entity.items;

import tower.entity.EntityPrototypeBuilder;
import tower.entity.EntityPrototypeBuilderFactory;

public class ItemPrototypeBuilderFactory implements EntityPrototypeBuilderFactory<EntityPrototypeBuilder<ItemPrototype>> {
    @Override
    public EntityPrototypeBuilder<ItemPrototype> createEntityPrototypeBuilder() {
        return new ItemPrototypeBuilder();
    }
}
