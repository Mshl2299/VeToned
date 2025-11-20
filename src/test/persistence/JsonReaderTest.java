package persistence;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import model.*;
import model.enumerations.*;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFileRC() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            reader.readRC();
            fail("IOException expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testReaderNonExistentFileDPC() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            reader.readDPC();
            fail("IOException expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testReaderEmptyRecipeCollection() {
        JsonReader reader = new JsonReader("./data/readerTests/testReaderEmptyRecipeCollection.json");
        try {
            RecipeCollection rc = reader.readRC();
            assertEquals(0, rc.getRecipes().size());
        } catch (IOException e) {
            fail("Couldn't read from file - IOException caught ");
        }
    }

    @Test
    void testReaderEmptyDayPlanCollection() {
        JsonReader reader = new JsonReader("./data/readerTests/testReaderEmptyDayPlanCollection.json");
        try {
            DayPlanCollection dpc = reader.readDPC();
            assertEquals(0, dpc.getDayPlans().size());
        } catch (IOException e) {
            fail("Couldn't read from file - IOException caught ");
        }
    }

    @Test
    void testReaderGeneralRC() {
        JsonReader reader = new JsonReader("./data/readerTests/testReaderGeneralRecipeCollection.json");
        try {
            RecipeCollection rc = reader.readRC();
            List<Recipe> recipes = rc.getRecipes();
            List<String> appleIngredients = new ArrayList<>();
            appleIngredients.add("Apple");
            List<String> omeletteIngredients = new ArrayList<>();
            omeletteIngredients.add("Egg");
            omeletteIngredients.add("Egg");
            omeletteIngredients.add("Egg");

            assertEquals(3, recipes.size());

            checkRecipe("Apple", MealType.SNACK, Diet.VEGAN, TimeOfDay.MORNING, 0, appleIngredients, false,
                    recipes.get(0));
            checkRecipe("omelette", MealType.COURSE, Diet.KETO_VEGETARIAN, TimeOfDay.AFTERNOON, 15, omeletteIngredients,
                    true, recipes.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file - IOException caught ");
        }
    }

    @SuppressWarnings("methodlength")
    @Test
    void testReaderGeneralDPC() {
        JsonReader reader = new JsonReader("./data/readerTests/testReaderGeneralDayPlanCollection.json");
        try {
            DayPlanCollection dpc = reader.readDPC();
            List<DayPlan> dayPlans = dpc.getDayPlans();

            List<String> appleIngredients = new ArrayList<>();
            appleIngredients.add("Apple");
            List<String> grapeIngredients = new ArrayList<>();
            grapeIngredients.add("Grape");
            grapeIngredients.add("Grape");
            grapeIngredients.add("Grape");
            grapeIngredients.add("Grape");
            grapeIngredients.add("Grape");

            Recipe testRecipeAppleMorningTrue = new Recipe("Apple", MealType.SNACK, Diet.VEGAN, 0, appleIngredients);
            testRecipeAppleMorningTrue.setTimeOfDay(TimeOfDay.MORNING);
            testRecipeAppleMorningTrue.setStarred(true);

            Recipe testRecipeAppleUnspecFalse = new Recipe("Apple", MealType.SNACK, Diet.VEGAN, 0, appleIngredients);
            Recipe testRecipeGrapesEveningTrue = new Recipe("Grapes", MealType.SIDE, Diet.VEGAN, 0, grapeIngredients);
            testRecipeGrapesEveningTrue.setTimeOfDay(TimeOfDay.EVENING);
            testRecipeGrapesEveningTrue.setStarred(true);

            List<Recipe> testRecipesAppleDay = new ArrayList<>();
            testRecipesAppleDay.add(testRecipeAppleMorningTrue);

            List<Recipe> testRecipesFruitsDay = new ArrayList<>();
            testRecipesFruitsDay.add(testRecipeAppleUnspecFalse);
            testRecipesFruitsDay.add(testRecipeGrapesEveningTrue);

            assertEquals(2, dayPlans.size());

            checkDayPlan("Apple Day", LocalDate.of(2024, 10, 20), testRecipesAppleDay, false, dayPlans.get(0));
            checkDayPlan("Fruits Day", LocalDate.of(2027, 12, 31), testRecipesFruitsDay, true, dayPlans.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file - IOException caught ");
        }
    }
}
