package tower.entity;

import com.google.gson.JsonElement;
import org.apache.commons.io.FileUtils;

import java.io.File;

public abstract class FilePropertySetter<BUILDER_TYPE> extends PropertySetter<BUILDER_TYPE,File> {
    protected File getPropertyValue(JsonElement value) {
        if (!value.isJsonPrimitive() || !value.getAsJsonPrimitive().isString()) {
            onUnrecognizedType(this.getClass().getName(), value);
        }
        return FileUtils.toFile(ClassLoader.class.getResource(value.getAsString()));
    }
}
