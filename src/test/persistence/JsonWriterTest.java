package persistence;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import model.*;
import model.enumerations.*;

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFileRC() {
        try {
            JsonWriter jw = new JsonWriter("./data/my\0illegal:fileName.json");
            jw.open();
            fail("IOException was not thrown");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testWriterInvalidFileDPC() {
        try {
            JsonWriter jw = new JsonWriter("./data/my\0illegal:fileName.json");
            jw.open();
            fail("IOException was not thrown");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testWriterEmptyRecipeCollection() {
        try {
            RecipeCollection rc = new RecipeCollection();
            JsonWriter jw = new JsonWriter("./data/writerTests/testWriterEmptyRecipeCollection.json");
            jw.open();
            jw.write(rc);
            jw.close();

            JsonReader reader = new JsonReader("./data/writerTests/testWriterEmptyRecipeCollection.json");
            rc = reader.readRC();
            assertEquals(0, rc.getRecipes().size());
        } catch (IOException e) {
            fail("IOException caught - not expected");
        }
    }

    @Test
    void testWriterEmptyDayPlanCollection() {
        try {
            DayPlanCollection dpc = new DayPlanCollection();
            JsonWriter jw = new JsonWriter("./data/writerTests/testWriterEmptyDayPlanCollection.json");
            jw.open();
            jw.write(dpc);
            jw.close();

            JsonReader reader = new JsonReader("./data/writerTests/testWriterEmptyDayPlanCollection.json");
            dpc = reader.readDPC();
            assertEquals(0, dpc.getDayPlans().size());
        } catch (IOException e) {
            fail("IOException caught - expected nothing thrown");
        }
    }

    @SuppressWarnings("methodlength")
    @Test
    void testWriterGeneralRecipeCollection() {
        try {
            RecipeCollection rc = new RecipeCollection();

            List<String> appleIngredients = new ArrayList<>();
            appleIngredients.add("Apple");

            Recipe testRecipeAppleMorningTrue = new Recipe("Apple", MealType.SNACK, Diet.VEGAN, 0, appleIngredients);
            testRecipeAppleMorningTrue.setTimeOfDay(TimeOfDay.MORNING);
            testRecipeAppleMorningTrue.setStarred(true);
            Recipe testRecipeAppleUnspecFalse = new Recipe("Apple", MealType.SNACK, Diet.VEGAN, 0, appleIngredients);

            rc.addRecipe(testRecipeAppleMorningTrue);
            rc.addRecipe(testRecipeAppleUnspecFalse);

            JsonWriter writer = new JsonWriter("./data/writerTests/testWriterGeneralRecipeCollection.json");
            writer.open();
            writer.write(rc);
            writer.close();

            JsonReader reader = new JsonReader("./data/writerTests/testWriterGeneralRecipeCollection.json");
            rc = reader.readRC();
            List<Recipe> recipes = rc.getRecipes();
            assertEquals(2, recipes.size());
            checkRecipe("Apple", MealType.SNACK, Diet.VEGAN, TimeOfDay.MORNING, 0, appleIngredients, true,
                    recipes.get(0));
            checkRecipe("Apple", MealType.SNACK, Diet.VEGAN, TimeOfDay.UNSPECIFIED, 0, appleIngredients,
                    false, recipes.get(1));

        } catch (IOException e) {
            fail("IOException caught - expected nothing thrown");
        }
    }

    @SuppressWarnings("methodlength")
    @Test
    void testWriterGeneralDayPlanCollection() {
        try {
            DayPlanCollection dpc = new DayPlanCollection();

            // Ingredients
            List<String> appleIngredients = new ArrayList<>();
            appleIngredients.add("Apple");
            List<String> grapeIngredients = new ArrayList<>();
            grapeIngredients.add("Grape");
            grapeIngredients.add("Grape");
            grapeIngredients.add("Grape");
            grapeIngredients.add("Grape");
            grapeIngredients.add("Grape");

            // Recipes
            Recipe testRecipeAppleMorningTrue = new Recipe("Apple", MealType.SNACK, Diet.VEGAN, 0, appleIngredients);
            testRecipeAppleMorningTrue.setTimeOfDay(TimeOfDay.MORNING);
            testRecipeAppleMorningTrue.setStarred(true);
            Recipe testRecipeAppleUnspecFalse = new Recipe("Apple", MealType.SNACK, Diet.VEGAN, 0, appleIngredients);
            Recipe testRecipeGrapesEveningTrue = new Recipe("Grapes", MealType.SIDE, Diet.VEGAN, 0, grapeIngredients);
            testRecipeGrapesEveningTrue.setTimeOfDay(TimeOfDay.EVENING);
            testRecipeGrapesEveningTrue.setStarred(true);

            // Lists of recipes
            List<Recipe> testRecipesAppleDay = new ArrayList<>();
            testRecipesAppleDay.add(testRecipeAppleMorningTrue);
            List<Recipe> testRecipesFruitsDay = new ArrayList<>();
            testRecipesFruitsDay.add(testRecipeAppleUnspecFalse);
            testRecipesFruitsDay.add(testRecipeGrapesEveningTrue);

            // DayPlans
            DayPlan testDayPlanAppleDay = new DayPlan("Apple Day", LocalDate.of(2024, 10, 20), testRecipesAppleDay);
            DayPlan testDayPlanFruitsDay = new DayPlan("Fruits Day", LocalDate.of(2027, 12, 31), testRecipesFruitsDay);
            testDayPlanFruitsDay.setStarred(true);

            dpc.addDayPlan(testDayPlanAppleDay);
            dpc.addDayPlan(testDayPlanFruitsDay);

            JsonWriter writer = new JsonWriter("./data/writerTests/testWriterGeneralDayPlanCollection.json");
            writer.open();
            writer.write(dpc);
            writer.close();

            JsonReader reader = new JsonReader("./data/writerTests/testWriterGeneralDayPlanCollection.json");
            dpc = reader.readDPC();
            List<DayPlan> dayPlans = dpc.getDayPlans();
            assertEquals(2, dayPlans.size());
            checkDayPlan("Apple Day", LocalDate.of(2024, 10, 20), testRecipesAppleDay, false, dayPlans.get(0));
            checkDayPlan("Fruits Day", LocalDate.of(2027, 12, 31), testRecipesFruitsDay, true, dayPlans.get(1));

        } catch (IOException e) {
            fail("IOException caught - expected nothing thrown");
        }
    }
}