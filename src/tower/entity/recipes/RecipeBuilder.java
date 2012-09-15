package tower.entity.recipes;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import tower.entity.BuildingPrototypePropertySetter;
import tower.entity.EntityPrototypeBuilder;
import tower.entity.ItemQuantityListPropertySetter;
import tower.entity.PropertySetter;
import tower.entity.StringPropertySetter;
import tower.entity.buiildings.BuildingPrototype;

import java.util.List;
import java.util.Map;

public class RecipeBuilder implements EntityPrototypeBuilder<Recipe> {

    private BuildingPrototype location;
    private List<ItemQuantity> reagents;
    private List<ItemQuantity> results;
    private String name;

    private static final PropertySetter<RecipeBuilder, String> namePropertySetter = new StringPropertySetter<RecipeBuilder>() {
        @Override
        public void setProperty(JsonElement value, RecipeBuilder builder) {
            builder.name = getPropertyValue(value);
        }
    };

    private static final PropertySetter<RecipeBuilder, List<ItemQuantity>> reagentsPropertySetter = new ItemQuantityListPropertySetter<RecipeBuilder>() {
        @Override
        public void setProperty(JsonElement value, RecipeBuilder builder) {
            builder.reagents = getPropertyValue(value);
        }
    };

    private static final PropertySetter<RecipeBuilder, List<ItemQuantity>> resultsPropertySetter = new ItemQuantityListPropertySetter<RecipeBuilder>() {
        @Override
        public void setProperty(JsonElement value, RecipeBuilder builder) {
            builder.results = getPropertyValue(value);
        }
    };

    private static final PropertySetter<RecipeBuilder, BuildingPrototype> locationPropertySetter = new BuildingPrototypePropertySetter<RecipeBuilder>() {
        @Override
        public void setProperty(JsonElement value, RecipeBuilder builder) {
            builder.location = getPropertyValue(value);
        }
    };

    private static final Map<String, PropertySetter<RecipeBuilder, ?>> propertySetters =
            ImmutableMap.<String, PropertySetter<RecipeBuilder, ?>>builder()
            .put("name", namePropertySetter)
            .put("location", locationPropertySetter)
            .put("results", resultsPropertySetter)
            .put("reagents", reagentsPropertySetter)
            .build();

    @Override
    public void addProperty(String property, JsonElement value) {
        propertySetters.get(property).setProperty(value, this);
    }

    @Override
    public boolean isValid() {
        return location != null && reagents != null && results != null && name != null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Recipe toEntityPrototype() {
        return new Recipe(reagents, results, location);
    }
}
