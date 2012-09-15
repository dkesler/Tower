package tower.entity;

import com.google.gson.JsonElement;

public interface EntityPrototypeBuilder<E> {
    void addProperty(String property, JsonElement value);
    boolean isValid();
    String getName();
    E toEntityPrototype();
}
