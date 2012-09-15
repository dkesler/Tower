package tower.entity;

import com.google.gson.JsonElement;

public abstract class IntegerPropertySetter<BUILDER_TYPE> extends PropertySetter<BUILDER_TYPE,Integer> {
    protected Integer getPropertyValue(JsonElement value) {
        if (!value.isJsonPrimitive() || !value.getAsJsonPrimitive().isNumber()) {
            onUnrecognizedType(this.getClass().getName(), value);
        }

        return value.getAsInt();
    }
}
