package tower.entity.recipes;

import tower.entity.EntityPrototypeLoader;

import java.util.Map;

public class Recipes {
    private static Map<String, Recipe> recipesByName;

    private Recipes() {}

    public static void initialize() {
        recipesByName = new EntityPrototypeLoader<Recipe>(
                "/details/recipes",
                new RecipeBuilderFactory()
        ).getPrototypes();
    }

    public static Map<String, Recipe> getRecipesByName() {
        if (recipesByName == null) {
            throw new RuntimeException("Cannot get recepies before they've been initialized");
        }

        return recipesByName;
    }
}
