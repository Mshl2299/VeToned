package model;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.exceptions.*;

public class RecipeCollectionTest extends RecipeTest {
    protected List<Recipe> testRecipesAll;
    protected List<Recipe> testRecipesAppleGrape;
    protected List<String> testIngredientsAll;
    protected List<String> testIngredientsAppleGrape;
    protected RecipeCollection testRecipeCollection;

    @BeforeEach
    void runBefore() {
        super.runBefore();
        testRecipesAll = new ArrayList<>();
        testRecipesAll.add(testRecipe1);
        testRecipesAll.add(testRecipe2);
        testRecipesAll.add(testRecipe3);

        testRecipesAppleGrape = new ArrayList<>();
        testRecipesAppleGrape.add(testRecipe1);
        testRecipesAppleGrape.add(testRecipe3);

        testIngredientsAll = new ArrayList<>();
        testIngredientsAll.addAll(testIngredients1);
        testIngredientsAll.addAll(testIngredients2);
        testIngredientsAll.addAll(testIngredients3);

        testIngredientsAppleGrape = new ArrayList<>();
        testIngredientsAppleGrape.addAll(testIngredients1);
        testIngredientsAppleGrape.addAll(testIngredients3);

        testRecipeCollection = new RecipeCollection();
    }

    @Test
    void testConstructor() {
        assertTrue(testRecipeCollection.getRecipes().isEmpty());
    }

    @Test
    void testAddRecipes() {
        testRecipeCollection.addRecipe(testRecipe1);
        testRecipeCollection.addRecipe(testRecipe2);
        assertEquals(2, testRecipeCollection.getRecipes().size());
        assertEquals(testRecipe1, testRecipeCollection.getRecipes().get(0));
        assertEquals(testRecipe2, testRecipeCollection.getRecipes().get(1));
    }

    @Test
    void testSortByName() {
        testRecipeCollection.addRecipe(testRecipe2);
        testRecipeCollection.addRecipe(testRecipe1);
        testRecipeCollection.sortByName();
        assertEquals(2, testRecipeCollection.getRecipes().size());
        assertEquals(testRecipe1, testRecipeCollection.getRecipes().get(0));
        assertEquals(testRecipe2, testRecipeCollection.getRecipes().get(1));
    }

    @Test
    void testSortByNameMixed() {
        testRecipe1.setName("apple");
        testRecipeCollection.addRecipe(testRecipe3);
        testRecipeCollection.addRecipe(testRecipe2);
        testRecipeCollection.addRecipe(testRecipe1);
        testRecipeCollection.sortByName();
        assertEquals(3, testRecipeCollection.getRecipes().size());
        assertEquals(testRecipe1, testRecipeCollection.getRecipes().get(0));
        assertEquals(testRecipe2, testRecipeCollection.getRecipes().get(1));
        assertEquals(testRecipe3, testRecipeCollection.getRecipes().get(2));
    }

    @Test
    void testSortByNameEmptyStr() {
        testRecipe1.setName("");
        testRecipeCollection.addRecipe(testRecipe3);
        testRecipeCollection.addRecipe(testRecipe2);
        testRecipeCollection.addRecipe(testRecipe1);
        testRecipeCollection.sortByName();
        assertEquals(3, testRecipeCollection.getRecipes().size());
        assertEquals(testRecipe1, testRecipeCollection.getRecipes().get(0));
        assertEquals(testRecipe2, testRecipeCollection.getRecipes().get(1));
        assertEquals(testRecipe3, testRecipeCollection.getRecipes().get(2));
    }

    @Test
    void testSetRecipeNameByIndexSingle() {
        testRecipeCollection.addRecipe(testRecipe1);
        try {
            testRecipeCollection.setRecipeNameByIndex("Red Delicious", 0);
            assertEquals("Red Delicious", testRecipe1.getName());
        } catch (DuplicateNameException e) {
            fail("DuplicateNameException caught - expected nothing thrown.");
        }
    }

    @Test
    void testSetRecipeNameByIndexSameName() {
        testRecipeCollection.addRecipe(testRecipe1);
        try {
            testRecipeCollection.setRecipeNameByIndex("Apple", 0);
            fail("DuplicateNameException not thrown");
        } catch (DuplicateNameException e) {
            // expected
        }
    }

    @Test
    void testSetRecipeNameByIndexOtherItem() {
        testRecipeCollection.addRecipe(testRecipe1);
        testRecipeCollection.addRecipe(testRecipe2);
        try {
            testRecipeCollection.setRecipeNameByIndex("Apple Pie", 0);
            fail("DuplicateNameException not thrown");
        } catch (DuplicateNameException e) {
            // expected
        }
    }

    @Test
    void testSetRecipeNameByIndexMultipleSwitch() {
        testRecipeCollection.setRecipes(testRecipesAll);
        try {
            testRecipeCollection.setRecipeNameByIndex("Green Grapes", 2);
            assertEquals("Green Grapes", testRecipeCollection.getRecipes().get(2).getName());
            testRecipeCollection.setRecipeNameByIndex("Grapes", 0);
            assertEquals("Grapes", testRecipeCollection.getRecipes().get(0).getName());
            testRecipeCollection.setRecipeNameByIndex("Apple", 2);
            assertEquals("Apple", testRecipeCollection.getRecipes().get(2).getName());
        } catch (DuplicateNameException e) {
            fail("DuplicateNameException caught - expected nothing thrown");
        }
    }

    @Test
    void testSetRecipeNameByIndexLastInListDupe() {
        testRecipeCollection.setRecipes(testRecipesAll);
        try {
            testRecipeCollection.setRecipeNameByIndex("Apple", 2);
            fail("DuplicateNameException not thrown");
        } catch (DuplicateNameException e) {
            // expected
        }
    }

    @Test
    void testGetAllRecipes() {
        testRecipeCollection.setRecipes(testRecipesAll);
        assertEquals(testRecipesAll, testRecipeCollection.getRecipes());
    }

    @Test
    void testGetRecipeByNameFirst() {
        testRecipeCollection.setRecipes(testRecipesAll);
        try {
            assertEquals(testRecipe1, testRecipeCollection.getRecipeByName("Apple"));
        } catch (MissingRecipeException e) {
            fail("MissingRecipeException caught - expected nothing thrown");
        }
    }

    @Test
    void testGetRecipeByNameLast() {
        testRecipeCollection.setRecipes(testRecipesAll);
        try {
            assertEquals(testRecipe3, testRecipeCollection.getRecipeByName("Grapes"));
        } catch (MissingRecipeException e) {
            fail("MissingRecipeException caught - expected nothing thrown");
        }
    }

    @Test
    void testGetRecipeByNameNotFound() {
        testRecipeCollection.setRecipes(testRecipesAll);
        try {
            testRecipeCollection.getRecipeByName("Pizza");
            fail("MissingRecipeException not thrown");
        } catch (MissingRecipeException e) {
            // expected
        }
    }

    @Test
    void testSetThenGetRecipeName() {
        testRecipeCollection.setRecipes(testRecipesAll);
        try {
            testRecipeCollection.setRecipeNameByIndex("Apple Caramel", 2);
            assertEquals("Apple Caramel", testRecipeCollection.getRecipes().get(2).getName());
            assertEquals(testRecipeCollection.getRecipes().get(2),
                    testRecipeCollection.getRecipeByName("Apple Caramel"));
        } catch (DuplicateNameException e) {
            fail("Caught DuplicateNameException - expected nothing thrown");
        } catch (MissingRecipeException e) {
            fail("Caught MissingRecipeException - expected nothing thrown");
        }
    }

    @Test
    void testGetRecipeNames() {
        List<String> testRecipeNames = new ArrayList<>();
        testRecipeCollection.setRecipes(testRecipesAll);
        for (Recipe t : testRecipesAll) {
            testRecipeNames.add(t.getName());
        }
        assertEquals(testRecipeNames, testRecipeCollection.getRecipeNames());
    }

    @Test
    void testGetRecipeNamesFromList() {      
        List<String> testRecipeNames = new ArrayList<>();
        testRecipeCollection.setRecipes(testRecipesAll);
        List<Recipe> recipesList = testRecipeCollection.getRecipes();
        for (Recipe t : testRecipesAll) {
            testRecipeNames.add(t.getName());
        }
        assertEquals(testRecipeNames, testRecipeCollection.getRecipeNames(recipesList));
    }

    @Test
    void testGetAllIngredients() {
        testRecipeCollection.setRecipes(testRecipesAll);
        assertEquals(testIngredientsAll, testRecipeCollection.getAllIngredients());
    }

    @Test
    void testGetAllIngredientsAfterChange() {
        testRecipeCollection.setRecipes(testRecipesAll);
        testRecipeCollection.getRecipes().get(0).setIngredients(testIngredients2);
        testIngredientsAll = new ArrayList<>();
        testIngredientsAll.addAll(testIngredients2);
        testIngredientsAll.addAll(testIngredients2);
        testIngredientsAll.addAll(testIngredients3);
        assertEquals(testIngredientsAll, testRecipeCollection.getAllIngredients());
    }
}
