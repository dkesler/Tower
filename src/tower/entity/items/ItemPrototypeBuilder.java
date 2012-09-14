package tower.entity.items;

import com.google.common.collect.ImmutableMap;
import tower.entity.EntityPrototypeBuilder;
import tower.entity.PropertySetter;

import java.util.Map;

public class ItemPrototypeBuilder implements EntityPrototypeBuilder<ItemPrototype>{

    String name;

    private static final PropertySetter<ItemPrototypeBuilder> namePropertySetter = new PropertySetter<ItemPrototypeBuilder>() {
        @Override
        public void setProperty(String value, ItemPrototypeBuilder itemPrototypeBuilder) {
            itemPrototypeBuilder.name = value;
        }
    };

    private static final Map<String, PropertySetter<ItemPrototypeBuilder>> propertySetters =
            ImmutableMap.<String, PropertySetter<ItemPrototypeBuilder>>builder()
                    .put("name", namePropertySetter)
                    .build();

    public void addProperty(String key, String value) {
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
