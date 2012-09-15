package tower.entity;

import com.google.gson.JsonElement;

public abstract class StringPropertySetter<BUILDER_TYPE> extends PropertySetter<BUILDER_TYPE, String> {
    @Override
    protected String getPropertyValue(JsonElement value) {
        if (!value.isJsonPrimitive() || !value.getAsJsonPrimitive().isString()) {
            onUnrecognizedType(this.getClass().getName(), value);
        }

        return value.getAsString();
    }
}
