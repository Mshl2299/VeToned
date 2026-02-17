package model;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RecipeTest {

    protected List<String> testIngredients1;
    protected List<String> testIngredients2;
    protected List<String> testIngredients3;
    protected Recipe testRecipe1;
    protected Recipe testRecipe2;
    protected Recipe testRecipe3;
    private Recipe testRecipe1clone;

    @BeforeEach
    void runBefore() {
        testIngredients1 = new ArrayList<>();
        testIngredients1.add("Apple");

        testIngredients2 = new ArrayList<>();
        testIngredients2.add("Apple");
        testIngredients2.add("Pastry");
        testIngredients2.add("Sugar");

        testIngredients3 = new ArrayList<>();
        testIngredients3.add("Grape");
        testIngredients3.add("Grape");
        testIngredients3.add("Grape");

        testRecipe1 = new Recipe("Apple", Recipe.MealType.SNACK, Recipe.Diet.VEGAN, 0, testIngredients1);
        testRecipe2 = new Recipe("Apple Pie", Recipe.MealType.SNACK, Recipe.Diet.VEGAN, 60, testIngredients2);
        testRecipe3 = new Recipe("Grapes", Recipe.MealType.SNACK, Recipe.Diet.VEGAN, 0, testIngredients3);
        testRecipe1clone = new Recipe(testRecipe1);
    }

    @Test
    void testConstructor() {
        assertEquals("Apple", testRecipe1.getName());
        assertEquals(Recipe.MealType.SNACK, testRecipe1.getType());
        assertEquals(Recipe.Diet.VEGAN, testRecipe1.getDiet());
        assertEquals(0, testRecipe1.getCookTime());
        assertEquals(testIngredients1, testRecipe1.getIngredients());
        assertEquals(Recipe.TimeOfDay.UNSPECIFIED, testRecipe1.getTimeOfDay());
        assertFalse(testRecipe1.getStarred());
    }

    @Test
    void testCloneConstructor() {
        assertEquals("Apple", testRecipe1clone.getName());
        assertEquals(Recipe.MealType.SNACK, testRecipe1clone.getType());
        assertEquals(Recipe.Diet.VEGAN, testRecipe1clone.getDiet());
        assertEquals(0, testRecipe1clone.getCookTime());
        assertEquals(testIngredients1, testRecipe1clone.getIngredients());
        assertEquals(Recipe.TimeOfDay.UNSPECIFIED, testRecipe1clone.getTimeOfDay());
        assertFalse(testRecipe1clone.getStarred());
    }

    @Test
    void testSetName() {
        testRecipe1.setName("Green Apple");
        assertEquals("Green Apple", testRecipe1.getName());
    }

    @Test
    void testSetNameMultiple() {
        testRecipe1.setName("Red Apple");
        testRecipe1.setName("Green Apple");
        assertEquals("Green Apple", testRecipe1.getName());
    }

    @Test
    void testSetType() {
        testRecipe1.setType(Recipe.MealType.SIDE);
        assertEquals(Recipe.MealType.SIDE, testRecipe1.getType());
    }

    @Test
    void testSetTypeMultiple() {
        testRecipe1.setType(Recipe.MealType.COURSE);
        testRecipe1.setType(Recipe.MealType.SIDE);
        assertEquals(Recipe.MealType.SIDE, testRecipe1.getType());
    }

    @Test
    void testSetDiet() {
        testRecipe1.setDiet(Recipe.Diet.VEGETARIAN);
        assertEquals(Recipe.Diet.VEGETARIAN, testRecipe1.getDiet());
    }

    @Test
    void testSetDietMultiple() {
        testRecipe1.setDiet(Recipe.Diet.VEGETARIAN);
        testRecipe1.setDiet(Recipe.Diet.KETO_VEGAN);
        assertEquals(Recipe.Diet.KETO_VEGAN, testRecipe1.getDiet());
    }

    @Test
    void testSetTimeOfDay() {
        testRecipe1.setTimeOfDay(Recipe.TimeOfDay.MORNING);
        assertEquals(Recipe.TimeOfDay.MORNING, testRecipe1.getTimeOfDay());
    }

    @Test
    void testSetTimeOfDayMultiple() {
        testRecipe1.setTimeOfDay(Recipe.TimeOfDay.MORNING);
        testRecipe1.setTimeOfDay(Recipe.TimeOfDay.MORNING);
        assertEquals(Recipe.TimeOfDay.MORNING, testRecipe1.getTimeOfDay());
    }

    @Test
    void testSetCookTime() {
        testRecipe1.setCookTime(10);
        assertEquals(10, testRecipe1.getCookTime());
    }

    @Test
    void testSetCookTimeMultiple() {
        testRecipe1.setCookTime(10);
        testRecipe1.setCookTime(1);
        assertEquals(1, testRecipe1.getCookTime());
    }

    @Test
    void testSetIngredients() {
        testRecipe1.setIngredients(testIngredients2);
        assertEquals(testIngredients2, testRecipe1.getIngredients());
    }

    @Test
    void testSetIngredientsMultiple() {
        testRecipe1.setIngredients(testIngredients2);
        testRecipe1.setIngredients(testIngredients1);
        assertEquals(testIngredients1, testRecipe1.getIngredients());
    }

    @Test
    void testSetStarred() {
        testRecipe1.setStarred(true);
        assertTrue(testRecipe1.getStarred());
    }

    @Test
    void testSetStarredTrueFalse() {
        testRecipe1.setStarred(true);
        testRecipe1.setStarred(false);
        assertFalse(testRecipe1.getStarred());
    }
}
