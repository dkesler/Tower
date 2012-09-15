package tower.entity;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import tower.entity.items.ItemFactory;
import tower.entity.recipes.ItemQuantity;

import java.util.LinkedList;
import java.util.List;

public abstract class ItemQuantityListPropertySetter<BUILDER_TYPE> extends PropertySetter<BUILDER_TYPE, List<ItemQuantity>> {
    @Override
    protected List<ItemQuantity> getPropertyValue(JsonElement value) {
        if (!value.isJsonArray()) {
            onUnrecognizedType(this.getClass().getName(), value);
        }

        List<ItemQuantity> items = new LinkedList<>();
        for (JsonElement itemQuantity : value.getAsJsonArray()) {
            if (!itemQuantity.isJsonObject()) {
                onUnrecognizedType(this.getClass().getName(), itemQuantity);
            }
            items.add(parseItemQuantityFromJson(itemQuantity.getAsJsonObject()));
        }
        return items;
    }

    private ItemQuantity parseItemQuantityFromJson(JsonObject itemQuantity) {
        if (!itemQuantity.has("type") || !itemQuantity.get("type").isJsonPrimitive() || !itemQuantity.get("type").getAsJsonPrimitive().isString()) {
            onUnrecognizedType(this.getClass().getName(), itemQuantity);
        }

        if (!itemQuantity.has("quantity") || !itemQuantity.get("quantity").isJsonPrimitive() || !itemQuantity.get("quantity").getAsJsonPrimitive().isNumber()) {
            onUnrecognizedType(this.getClass().getName(), itemQuantity);
        }

        String itemType = itemQuantity.get("type").getAsString();
        int quantity = itemQuantity.get("quantity").getAsInt();

        return new ItemQuantity(ItemFactory.getPrototype(itemType), quantity);
    }
}
