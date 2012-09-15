package tower.entity;

import com.google.gson.JsonElement;
import tower.entity.buiildings.BuildingFactory;
import tower.entity.buiildings.BuildingPrototype;

public abstract class BuildingPrototypePropertySetter<BUILDER_TYPE> extends PropertySetter<BUILDER_TYPE, BuildingPrototype> {

    @Override
    protected BuildingPrototype getPropertyValue(JsonElement value) {
        if (!value.isJsonPrimitive() || !value.getAsJsonPrimitive().isString()) {
            onUnrecognizedType(this.getClass().getName(), value);
        }

        return BuildingFactory.getPrototype(value.getAsString());
    }
}
