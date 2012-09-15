package tower.entity.recipes;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import tower.entity.EntityPrototypeLoader;
import tower.entity.buiildings.BuildingPrototype;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Recipes {
    private static Map<String, Recipe> recipesByName;
    private static Multimap<BuildingPrototype, Recipe> recipiesByBuildingType;

    private Recipes() {}

    public static void initialize() {
        recipesByName = new EntityPrototypeLoader<>(
                "/details/recipes",
                new RecipeBuilderFactory()
        ).getPrototypes();

        ImmutableMultimap.Builder<BuildingPrototype, Recipe> recipiesByBuildingTypeBuilder = ImmutableMultimap.builder();
        for (Recipe recipe : recipesByName.values()) {
            recipiesByBuildingTypeBuilder.put(recipe.location, recipe);
        }
        recipiesByBuildingType = recipiesByBuildingTypeBuilder.build();
    }

    public static Map<String, Recipe> getRecipesByName() {
        if (recipesByName == null) {
            throw new RuntimeException("Cannot get recepies before they've been initialized");
        }

        return recipesByName;
    }

    public static List<Recipe> getRecipiesForBuilding(BuildingPrototype prototype) {
        if (recipiesByBuildingType == null) {
            throw new RuntimeException("Cannot get recepies before they've been initialized");
        }

        return Lists.newArrayList(recipiesByBuildingType.get(prototype));
    }
}
