package tower.entity.buiildings;

import tower.entity.EntityPrototypeBuilder;
import tower.entity.EntityPrototypeBuilderFactory;

public class BuildingPrototypeBuilderFactory implements EntityPrototypeBuilderFactory<EntityPrototypeBuilder<BuildingPrototype>> {
    @Override
    public EntityPrototypeBuilder<BuildingPrototype> createEntityPrototypeBuilder() {
        return new BuildingPrototypeBuilder();
    }
}
