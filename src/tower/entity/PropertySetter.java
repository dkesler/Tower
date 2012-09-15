package tower.entity;

import com.google.gson.JsonElement;

public abstract class PropertySetter<BUILDER_TYPE,PROPERTY_TYPE> {
    public abstract void setProperty(JsonElement value, BUILDER_TYPE builderType);
    protected abstract PROPERTY_TYPE getPropertyValue(JsonElement value);

    protected void onUnrecognizedType(String className, JsonElement value) {
        throw new RuntimeException(className + " could not recognize json element with value [" + value + "]");
    }

}
