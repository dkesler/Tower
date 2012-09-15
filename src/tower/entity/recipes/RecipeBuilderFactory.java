package tower.entity.recipes;

import tower.entity.EntityPrototypeBuilder;
import tower.entity.EntityPrototypeBuilderFactory;

public class RecipeBuilderFactory implements EntityPrototypeBuilderFactory<EntityPrototypeBuilder<Recipe>> {
    @Override
    public EntityPrototypeBuilder<Recipe> createEntityPrototypeBuilder() {
        return new RecipeBuilder();
    }
}
