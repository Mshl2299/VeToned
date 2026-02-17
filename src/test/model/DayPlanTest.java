package model;

import java.time.LocalDate;
// Referenced from https://docs.oracle.com/javase/8/docs/api/java/time/LocalDate.html

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DayPlanTest extends RecipeCollectionTest {
    protected DayPlan testDayPlan1;
    protected DayPlan testDayPlan2;

    @BeforeEach
    void runBefore() {
        super.runBefore();

        testRecipe1.setTimeOfDay(Recipe.TimeOfDay.AFTERNOON);
        testRecipe2.setTimeOfDay(Recipe.TimeOfDay.EVENING);
        testRecipe3.setTimeOfDay(Recipe.TimeOfDay.MORNING);
        testDayPlan1 = new DayPlan("Fruit day", LocalDate.of(2024, 10, 20), testRecipesAll);
        testDayPlan2 = new DayPlan("Apple and Grapes", LocalDate.now(), testRecipesAppleGrape);
    }

    @Test
    void testConstructor() {
        assertEquals("Fruit day", testDayPlan1.getName());
        assertEquals(testRecipesAll, testDayPlan1.getRecipes());
        assertEquals(LocalDate.of(2024, 10, 20), testDayPlan1.getDate());
        assertFalse(testDayPlan1.getStarred());

        assertEquals("Apple and Grapes", testDayPlan2.getName());
        assertEquals(testRecipesAppleGrape, testDayPlan2.getRecipes());
        assertEquals(LocalDate.now(), testDayPlan2.getDate());
        assertFalse(testDayPlan2.getStarred());
    }

    @Test
    void testSortByTimeOfDay() {
        testDayPlan1.sortByTimeOfDay();
        assertEquals(testRecipe3, testDayPlan1.getRecipes().get(0));
        assertEquals(testRecipe1, testDayPlan1.getRecipes().get(1));
        assertEquals(testRecipe2, testDayPlan1.getRecipes().get(2));
        assertEquals(3, testDayPlan1.getRecipes().size());
    }

    @Test
    void testSortByTimeOfDayDupe() {
        testRecipe2.setTimeOfDay(Recipe.TimeOfDay.MORNING);
        testDayPlan1.sortByTimeOfDay();
        assertEquals(testRecipe2, testDayPlan1.getRecipes().get(0));
        assertEquals(testRecipe3, testDayPlan1.getRecipes().get(1));
        assertEquals(testRecipe1, testDayPlan1.getRecipes().get(2));
        assertEquals(3, testDayPlan1.getRecipes().size());
    }

    @Test
    void testSetName() {
        testDayPlan1.setName("Fruitier day");
        assertEquals("Fruitier day", testDayPlan1.getName());
    }

    @Test
    void testSetNameMultiple() {
        testDayPlan1.setName("Monday");
        testDayPlan1.setName("Lots of fruits");
        assertEquals("Lots of fruits", testDayPlan1.getName());
    }

    @Test
    void testSetDate() {
        testDayPlan1.setDate("2022-10-13");
        assertEquals(LocalDate.of(2022, 10, 13), testDayPlan1.getDate());
    }

    @Test
    void testSetDateLocalDate() {
        testDayPlan1.setDate(LocalDate.of(2022, 10, 13));
        assertEquals(LocalDate.of(2022, 10, 13), testDayPlan1.getDate());
    }

    @Test
    void testSetDateMultiple() {
        testDayPlan1.setDate("2023-10-13");
        testDayPlan1.setDate("2024-06-01");
        assertEquals(LocalDate.of(2024, 6, 1), testDayPlan1.getDate());
    }

    @Test
    void testSetDateMixed() {
        testDayPlan1.setDate(LocalDate.of(2022, 10, 13));
        testDayPlan1.setDate("2024-06-01");
        assertEquals(LocalDate.of(2024, 6, 1), testDayPlan1.getDate());
    }

    @Test
    void testSetStarred() {
        testDayPlan1.setStarred(true);
        assertTrue(testDayPlan1.getStarred());
    }

    @Test
    void testSetStarredTrueFalse() {
        testDayPlan1.setStarred(true);
        testDayPlan1.setStarred(false);
        assertFalse(testDayPlan1.getStarred());
    }

}
