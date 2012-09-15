package tower.entity.recipes;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import tower.entity.buiildings.BuildingPrototype;

import java.util.List;

public class Recipe {
    public final List<ItemQuantity> reagents;
    public final List<ItemQuantity> results;
    public final BuildingPrototype location;

    public Recipe(List<ItemQuantity> reagents, List<ItemQuantity> results, BuildingPrototype location) {
        this.reagents = ImmutableList.copyOf(reagents);
        this.results = ImmutableList.copyOf(results);
        this.location = location;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        Joiner.on(" + ").appendTo(stringBuilder, reagents);
        stringBuilder.append(" -> ");
        Joiner.on(" + ").appendTo(stringBuilder, results);

        return stringBuilder.toString();
    }
}
