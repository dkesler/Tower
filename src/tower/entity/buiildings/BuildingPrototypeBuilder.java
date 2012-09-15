package tower.entity.buiildings;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import tower.entity.EntityPrototypeBuilder;
import tower.entity.FilePropertySetter;
import tower.entity.IntegerPropertySetter;
import tower.entity.PropertySetter;
import tower.entity.StringPropertySetter;

import java.io.File;
import java.util.Map;

public class BuildingPrototypeBuilder implements EntityPrototypeBuilder<BuildingPrototype> {

    String name;
    Integer width;
    Integer height;
    File imageFile;

    private static final PropertySetter<BuildingPrototypeBuilder, String> namePropertySetter = new StringPropertySetter<BuildingPrototypeBuilder>() {
        @Override
        public void setProperty(JsonElement value, BuildingPrototypeBuilder buildingPrototypeBuilder) {
            buildingPrototypeBuilder.name = value.getAsString();
        }
    };

    private static PropertySetter<BuildingPrototypeBuilder, Integer> widthPropertySetter = new IntegerPropertySetter<BuildingPrototypeBuilder>() {
        @Override
        public void setProperty(JsonElement value, BuildingPrototypeBuilder buildingPrototypeBuilder) {
            buildingPrototypeBuilder.width = getPropertyValue(value);
        }
    };

    private static PropertySetter<BuildingPrototypeBuilder, Integer> heightPropertySetter = new IntegerPropertySetter<BuildingPrototypeBuilder>() {
        @Override
        public void setProperty(JsonElement value, BuildingPrototypeBuilder buildingPrototypeBuilder) {
            buildingPrototypeBuilder.height = getPropertyValue(value);
        }
    };


    private static PropertySetter<BuildingPrototypeBuilder, File> imagePropertySetter = new FilePropertySetter<BuildingPrototypeBuilder>() {
        @Override
        public void setProperty(JsonElement value, BuildingPrototypeBuilder buildingPrototypeBuilder) {
            buildingPrototypeBuilder.imageFile = getPropertyValue(value);
        }
    };

    private static final Map<String, PropertySetter<BuildingPrototypeBuilder, ?>> propertySetters =
            ImmutableMap.<String, PropertySetter<BuildingPrototypeBuilder, ?>>builder()
            .put("name", namePropertySetter)
            .put("width", widthPropertySetter)
            .put("height", heightPropertySetter)
            .put("image", imagePropertySetter)
            .build();


    public void addProperty(String key, JsonElement value) {
        if (!propertySetters.containsKey(key)) {
            throw new RuntimeException("Exception occurred setting property [" + key + "]: no setter exists");
        }

        propertySetters.get(key).setProperty(value, this);
    }

    public boolean isValid() {
        return name != null && width != null && height != null && imageFile != null;
    }

    public String getName() {
        return name;
    }

    public BuildingPrototype toEntityPrototype() {
        return new BuildingPrototype(
                name,
                imageFile,
                height,
                width
        );
    }
}
