package tower.entity.items;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import tower.entity.EntityPrototypeBuilder;
import tower.entity.PropertySetter;
import tower.entity.StringPropertySetter;

import java.util.Map;

public class ItemPrototypeBuilder implements EntityPrototypeBuilder<ItemPrototype>{

    String name;

    private static final PropertySetter<ItemPrototypeBuilder, String> namePropertySetter = new StringPropertySetter<ItemPrototypeBuilder>() {
        @Override
        public void setProperty(JsonElement value, ItemPrototypeBuilder itemPrototypeBuilder) {
            itemPrototypeBuilder.name = getPropertyValue(value);
        }
    };

    private static final Map<String, PropertySetter<ItemPrototypeBuilder, ?>> propertySetters =
            ImmutableMap.<String, PropertySetter<ItemPrototypeBuilder, ?>>builder()
                    .put("name", namePropertySetter)
                    .build();

    public void addProperty(String key, JsonElement value) {
        if (!propertySetters.containsKey(key)) {
            throw new RuntimeException("Exception occurred setting property [" + key + "]: no setter exists");
        }

        propertySetters.get(key).setProperty(value, this);
    }

    public boolean isValid() {
        return name != null;
    }

    public String getName() {
        return name;
    }

    public ItemPrototype toEntityPrototype() {
        return new ItemPrototype(
                name
        );
    }
}
