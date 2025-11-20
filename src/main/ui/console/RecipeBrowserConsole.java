package ui.console;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.*;
import model.enumerations.*;

// Represents the RecipeBrowser of the Meal Planner application
public class RecipeBrowserConsole {
    private Scanner scanner;
    private static RecipeCollection recipeBrowserCollection;

    // Create an instance of a recipe browser with an empty collection
    public RecipeBrowserConsole() {
        scanner = new Scanner(System.in);
        recipeBrowserCollection = new RecipeCollection();
    }

    // EFFECTS: Outputs a list of all recipes by name and lists options
    public void viewRecipeBrowser() {
        System.out.println("You have selected: 2 - Recipe Browser & Create Recipes");
        System.out.println("Current recipes:" + recipeBrowserCollection.getRecipeNames());
        System.out.println("1 - Create a new recipe");
        System.out.println("2 - View or Edit an existing recipe by index");
        System.out.println("3 - Sort recipes by name");
        System.out.println("4 - Get all ingredients");
        System.out.println("0 - Back home");
        handleRecipeBrowser();
    }

    // EFFECTS: Handles user option choice from RecipeBrowser
    public void handleRecipeBrowser() {
        int option = MealPlannerConsole.getIntInRangeFromUser(0,4);
        switch (option) {
            case 0:
                break;
            case 1:
                createRecipe();
                break;
            case 2:
                if (recipeBrowserCollection.getRecipes().isEmpty()) {
                    System.out.println("Recipe Browser is empty. Create a recipe first.");
                } else {
                    viewRecipe();
                }
                break;
            case 3:
                sortRecipe();
                break;
            case 4:
                System.out.println("All ingredients: " + recipeBrowserCollection.getAllIngredients());
                break;
            default:
                break;
        }
    }

    // EFFECTS: Asks user for each required field of a recipe, constructs a recipe
    // and adds it to the list of recipes in the recipe browser
    public void createRecipe() {
        String name;
        MealType type;
        Diet diet;
        int cookTime;
        List<String> ingredients = new ArrayList<>();

        System.out.println("Create a recipe:");
        name = specifyName();
        type = specifyType();

        diet = specifyDiet();
        System.out.println("Please specify the cook time in minutes, 0 if none:");
        cookTime = MealPlannerConsole.getIntFromUser();
        ingredients = specifyIngredients();
        recipeBrowserCollection.addRecipe(new Recipe(name, type, diet, cookTime, ingredients));
        System.out.println("Successfully added recipe to the recipe browser!");
    }

    // EFFECTS: Allows user to specify a recipe name and prevents duplicates
    public String specifyName() {
        String name = "default name";
        boolean validName = false;
        while (!validName) {
            System.out.println("Please specify a name (defaults to default name):");
            name = scanner.nextLine();
            if (recipeBrowserCollection.getRecipeNames().contains(name)) {
                System.out.println("That name is taken. Please choose another.");
            } else {
                validName = true;
            }
        }
        return name;
    }

    // EFFECTS: Allows user to specify a type of meal as a Course, Side dish or
    // Snack, Default to Snack
    public MealType specifyType() {
        System.out.println("Please specify a type of meal (default = Snack):");
        System.out.println("1 - Course, 2 - Side, 3 - Snack");
        int option = MealPlannerConsole.getIntFromUser();

        switch (option) {
            case 1:
                return MealType.COURSE;
            case 2:
                return MealType.SIDE;
            case 3:
                return MealType.SNACK;
            default:
                return MealType.SNACK;
        }
    }

    // EFFECTS: Allows user to specify a diet given options: Keto, Vegan,
    // Vegetarian, Keto-Vegan, Keto-Vegetarian, Non-Vegetarian
    // Default to Non-vegetarian
    public Diet specifyDiet() {
        System.out.println("Please specify a diet (default = Non-Vegetarian):");
        System.out.println("1- Keto, 2- Vegetarian, 3- Vegan, 4- Keto-Vegetarian, 5- Keto-Vegan, 6- Non-Vegetarian");
        int option = MealPlannerConsole.getIntFromUser();

        switch (option) {
            case 1:
                return Diet.KETO;
            case 2:
                return Diet.VEGETARIAN;
            case 3:
                return Diet.VEGAN;
            case 4:
                return Diet.KETO_VEGETARIAN;
            case 5:
                return Diet.KETO_VEGAN;
            case 6:
                return Diet.NON_VEGETARIAN;
            default:
                return Diet.NON_VEGETARIAN;
        }
    }

    // EFFECTS: Allows user to add an unspecified number of ingredients
    // to a list of ingredients and returns the list
    // Allow numbers
    public List<String> specifyIngredients() {
        System.out.println("Please specify the ingredients (Enter names, or type quit):");
        List<String> ingredients = new ArrayList<>();
        while (true) {
            System.out.println("Enter next ingredient name: ");
            String option = scanner.nextLine();
            if (option.toLowerCase().equals("quit")) {
                break;
            }
            ingredients.add(option);
            System.out.println("Ingredients so far: " + ingredients);
        }
        return ingredients;
    }

    // EFFECTS: Asks user for an index of the recipe and displays all fields of the
    // recipe,
    // gives options: 1 - Edit recipe, 2 - Remove recipe, 0 - Back to Recipe Browser
    public void viewRecipe() {
        int index = chooseIndex();
        System.out.println("Options: 1 - Edit chosen recipe, 2 - Remove chosen recipe, 0 - Back home");
        int option = MealPlannerConsole.getIntInRangeFromUser(0,2);
        switch (option) {
            case 0:
                break;
            case 1:
                editRecipe(recipeBrowserCollection.getRecipes().get(index));
                break;
            case 2:
                recipeBrowserCollection.getRecipes().remove(index);
                System.out.println("Removed the recipe at index: " + index + " from the recipe browser.");
            default:
                break;
        }
    }

    // EFFECTS: Displays a recipe given an index for the recipe
    // Checks for a valid index
    public int chooseIndex() {
        int index = 0;
        System.out.println("Please enter the index of the recipe:");
        while (true) {
            try {
                index = MealPlannerConsole.getIntFromUser();
                System.out.println("You have chosen the recipe at index: " + index);
                printRecipe(recipeBrowserCollection.getRecipes().get(index));
                return index;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Index not found. Try again.");
                System.out.println("Recipes: " + recipeBrowserCollection.getRecipeNames());
            }
        }
    }

    // EFFECTS: displays all fields of a given recipe
    public void printRecipe(Recipe recipe) {
        System.out.println("Name: " + recipe.getName());
        System.out.println("Type: " + recipe.getType());
        System.out.println("Diet: " + recipe.getDiet());
        System.out.println("Time of Day: " + recipe.getTimeOfDay());
        System.out.println("Cook time: " + recipe.getCookTime());
        System.out.println("Ingredients: " + recipe.getIngredients());
        System.out.println("Starred: " + recipe.getStarred());
    }

    // EFFECTS: Re-sets all fields of a given recipe
    public void editRecipe(Recipe recipe) {
        System.out.println("Edit a recipe (NEW details):");
        recipe.setName(specifyName());
        recipe.setType(specifyType());
        recipe.setDiet(specifyDiet());
        System.out.println("Please specify the cook time in minutes, 0 if none:");
        recipe.setCookTime(MealPlannerConsole.getIntFromUser());
        recipe.setIngredients(specifyIngredients());
        System.out.println("Please specify if recipe is starred (true or false)");
        recipe.setStarred(MealPlannerConsole.getBooleanFromUser());
        System.out.println("Successfully edited recipe! => ");
        printRecipe(recipe);
    }

    // EFFECTS: Sorts recipe by name
    public void sortRecipe() {
        recipeBrowserCollection.sortByName();
        System.out.println("Sorted recipes in alphabetical order.");
        System.out.println("New list: " + recipeBrowserCollection.getRecipeNames());
    }

    // EFFECTS: Converts a list of Recipes to a list of Strings of the corresponding
    // recipe names
    // and returns the list
    public static List<String> getRecipeNames(List<Recipe> recipes) {
        List<String> recipeNames = new ArrayList<>();
        for (Recipe r : recipes) {
            recipeNames.add(r.getName());
        }
        return recipeNames;
    }

    public static void setRecipeCollection(RecipeCollection rc) {
        recipeBrowserCollection = rc;
    }

    public static RecipeCollection getRecipeCollection() {
        return recipeBrowserCollection;
    }
}
