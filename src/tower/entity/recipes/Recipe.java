package tower.entity.recipes;

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


}
