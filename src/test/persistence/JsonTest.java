package persistence;

import java.util.List;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import model.*;

public class JsonTest {
    // EFFECTS: Checks that a recipe has the given values for fields
    protected void checkRecipe(String name, Recipe.MealType type, Recipe.Diet diet, Recipe.TimeOfDay tod, int cookTime,
            List<String> ingredients, boolean starred, Recipe recipe) {
        assertEquals(name, recipe.getName());
        assertEquals(type, recipe.getType());
        assertEquals(diet, recipe.getDiet());
        assertEquals(cookTime, recipe.getCookTime());
        checkIngredients(ingredients, recipe.getIngredients());
        assertEquals(tod, recipe.getTimeOfDay());
        assertEquals(starred, recipe.getStarred());
    }

    // EFFECTS: Checks the list of ingredients for a recipe going through each
    // element
    private void checkIngredients(List<String> ingredients, List<String> recipeIng) {
        for (int i = 0; i < ingredients.size(); i++) {
            assertEquals(ingredients.get(i), recipeIng.get(i));
        }
    }

    // EFFECTS: Checks that a day plan has the given values for fields
    protected void checkDayPlan(String name, LocalDate date, List<Recipe> recipes, boolean starred, DayPlan dayPlan) {
        assertEquals(name, dayPlan.getName());
        assertEquals(date, dayPlan.getDate());
        assertEquals(starred, dayPlan.getStarred());
        checkRecipes(recipes, dayPlan.getRecipes());
    }

    // EFFECTS: Checks the list of recipes in a day plan going through each element
    private void checkRecipes(List<Recipe> recipes, List<Recipe> dayPlanRecipes) {
        for (int i = 0; i < recipes.size(); i++) {
            Recipe r = recipes.get(i);
            Recipe dpr = dayPlanRecipes.get(i);
            checkRecipe(r.getName(), r.getType(), r.getDiet(), r.getTimeOfDay(), r.getCookTime(), r.getIngredients(),
                    r.getStarred(), dpr);
        }
    }
}
