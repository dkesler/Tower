package tower.entity.buiildings;

import com.google.common.collect.ImmutableMap;
import tower.entity.EntityPrototypeBuilder;
import tower.entity.FilePropertySetter;
import tower.entity.IntegerPropertySetter;
import tower.entity.PropertySetter;

import java.io.File;
import java.util.Map;

public class BuildingPrototypeBuilder implements EntityPrototypeBuilder<BuildingPrototype> {

    String name;
    Integer width;
    Integer height;
    File imageFile;

    private static final PropertySetter<BuildingPrototypeBuilder> namePropertySetter = new PropertySetter<BuildingPrototypeBuilder>() {
        @Override
        public void setProperty(String value, BuildingPrototypeBuilder buildingPrototypeBuilder) {
            buildingPrototypeBuilder.name = value;
        }
    };

    private static PropertySetter<BuildingPrototypeBuilder> widthPropertySetter = new IntegerPropertySetter<BuildingPrototypeBuilder>() {
        @Override
        public void setProperty(String value, BuildingPrototypeBuilder buildingPrototypeBuilder) {
            buildingPrototypeBuilder.width = getIntValue(value);
        }
    };

    private static PropertySetter<BuildingPrototypeBuilder> heightPropertySetter = new IntegerPropertySetter<BuildingPrototypeBuilder>() {
        @Override
        public void setProperty(String value, BuildingPrototypeBuilder buildingPrototypeBuilder) {
            buildingPrototypeBuilder.height = getIntValue(value);
        }
    };


    private static PropertySetter<BuildingPrototypeBuilder> imagePropertySetter = new FilePropertySetter<BuildingPrototypeBuilder>() {
        @Override
        public void setProperty(String value, BuildingPrototypeBuilder buildingPrototypeBuilder) {
            buildingPrototypeBuilder.imageFile = getFileValue(value);
        }
    };

    private static final Map<String, PropertySetter<BuildingPrototypeBuilder>> propertySetters =
            ImmutableMap.<String, PropertySetter<BuildingPrototypeBuilder>>builder()
            .put("name", namePropertySetter)
            .put("width", widthPropertySetter)
            .put("height", heightPropertySetter)
            .put("image", imagePropertySetter)
            .build();


    public void addProperty(String key, String value) {
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
