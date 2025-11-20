package ui.console;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.*;
import model.enumerations.TimeOfDay;
import model.exceptions.MissingRecipeException;

// Represents the DayPlanBrowser of the Meal Planner application
public class DayPlanBrowserConsole {
    private Scanner scanner;
    private static DayPlanCollection dayPlanBrowserCollection;

    // EFFECTS: Constructs a DayPlanBrowser with empty collection of day plans
    public DayPlanBrowserConsole() {
        scanner = new Scanner(System.in);
        dayPlanBrowserCollection = new DayPlanCollection();
    }

    // EFFECTS: Outputs a list of all day plans and gives options:
    // Create a day plan, Edit existing plan, back home
    public void viewDayPlanBrowser() {
        System.out.println("You have selected: 3 - Day Plan Browser & Create Day Plans");
        System.out.println("Current day plans: " + dayPlanBrowserCollection.getDayPlanNames());
        System.out.println("1 - Create a new day plan");
        System.out.println("2 - View or Edit an existing day plan by index");
        System.out.println("3 - Sort day plans by name");
        System.out.println("0 - Back home");

        handleDayPlanBrowser();
    }

    // EFFECTS: Handles user input based on options from viewDayPlanBrowser
    @SuppressWarnings("methodlength") // 
    private void handleDayPlanBrowser() {
        int option = MealPlannerConsole.getIntInRangeFromUser(0,3);
        switch (option) {
            case 0:
                break;
            case 1:
                if (RecipeBrowserConsole.getRecipeCollection().getRecipes().isEmpty()) {
                    System.out.println("There are no recipes. Please create a recipe first.");
                } else {
                    createDayPlan();
                }
                break;
            case 2:
                if (dayPlanBrowserCollection.getDayPlans().isEmpty()) {
                    System.out.println("There are no day plans. Please create a day plan first.");
                } else {
                    viewDayPlanByIndex();
                }
                break;
            case 3:
                sortDayPlans();
                break;
            default:
                break;
        }
    }

    // EFFECTS: creates a new day plan by allowing user to add recipes from the
    // recipe browser, specifying a time of day for each recipe
    private void createDayPlan() {
        System.out.println("Create a Day Plan:");
        String name = specifyName();
        LocalDate date = specifyDate();
        List<Recipe> recipes = new ArrayList<>();
        recipes = specifyRecipes();

        dayPlanBrowserCollection.addDayPlan(new DayPlan(name, date, recipes));
        System.out.println("Successfully added day plan to the day plan browser!");
    }

    // EFFECTS: Allows user to specify a dayplan name and prevents duplicates
    private String specifyName() {
        String name = "default name";
        boolean validName = false;
        while (!validName) {
            System.out.println("Please specify a name (defaults to default name):");
            name = scanner.nextLine();
            if (dayPlanBrowserCollection.getDayPlanNames().contains(name)) {
                System.out.println("That name is taken. Please choose another.");
            } else {
                validName = true;
            }
        }
        return name;
    }

    // EFFECTS: Continues to ask user for date of format yyyy-mm-dd until user
    // inputs a valid date
    private LocalDate specifyDate() {
        String date = "0";
        while (true) {
            System.out.println("Please specify a date with format yyyy-mm-dd, or 0 for today's date:");
            date = scanner.nextLine();
            if (date.equals("0")) {
                return LocalDate.now();
            } else {
                try {
                    return LocalDate.parse(date);
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date. Try again.");
                }
            }
        }
    }

    // EFFECTS: Allows user to add an unspecified number of recipes
    // to a list of recipes and specify the time of day for each recipe
    // and returns the list
    private List<Recipe> specifyRecipes() {
        List<Recipe> recipes = new ArrayList<>();

        System.out.println("Please specify the recipes (Enter names, or type quit):");
        System.out.println("Current Recipes in Recipe Browser: "
                + RecipeBrowserConsole.getRecipeCollection().getRecipeNames());
        while (true) {
            String recipeName = scanner.nextLine();
            if (recipeName.toLowerCase().equals("quit")) {
                break; // stop from adding more recipes
            }
            try {
                Recipe newRecipe = new Recipe(RecipeBrowserConsole.getRecipeCollection().getRecipeByName(recipeName));
                recipes.add(newRecipe);
                specifyRecipeTimeOfDay(newRecipe);
                System.out.println("Recipes so far: ");
                printRecipesByTOD(recipes);
                System.out.println("Please specify the recipes (Enter names, or type quit):");
                System.out.println("Current Recipes in Recipe Browser: "
                        + RecipeBrowserConsole.getRecipeCollection().getRecipeNames());
            } catch (MissingRecipeException e) {
                System.out.println("Invalid recipe name. Try again.");
            }
        }
        return recipes;
    }

    // EFFECTS: Allows user to specify a time of day for a recipe
    // 0 - Morning, 1 - Afternoon, 2 - Evening, 3 - Unspecified/Snack
    private void specifyRecipeTimeOfDay(Recipe r) {
        System.out.println("Please specify a time of day for the recipe: " + r.getName());
        System.out.println("0 - Morning, 1 - Afternoon, 2 - Evening, 3 - Unspecified/Snack");
        int option = MealPlannerConsole.getIntFromUser();
        switch (option) {
            case 0:
                r.setTimeOfDay(TimeOfDay.MORNING);
                break;
            case 1:
                r.setTimeOfDay(TimeOfDay.AFTERNOON);
                break;
            case 2:
                r.setTimeOfDay(TimeOfDay.EVENING);
                break;
            case 3:
                r.setTimeOfDay(TimeOfDay.UNSPECIFIED);
                break;
            default:
                break;
        }
    }

    // EFFECTS: Returns recipes by time of day given list of recipes
    private static void printRecipesByTOD(List<Recipe> recipes) {
        System.out.println("Recipes: ");
        System.out.println("Morning: " + recipesToLOS(filterByTOD(recipes, TimeOfDay.MORNING)));
        System.out.println("Afternoon: " + recipesToLOS(filterByTOD(recipes, TimeOfDay.AFTERNOON)));
        System.out.println("Evening: " + recipesToLOS(filterByTOD(recipes, TimeOfDay.EVENING)));
        System.out.println("Unspecified: " + recipesToLOS(filterByTOD(recipes, TimeOfDay.UNSPECIFIED)));
    }

    // EFFECTS: Returns the sub-recipes that are a given time of day given list of
    // recipes
    private static List<Recipe> filterByTOD(List<Recipe> recipes, TimeOfDay tod) {
        List<Recipe> filteredRecipes = new ArrayList<>();
        for (Recipe r : recipes) {
            if (r.getTimeOfDay().equals(tod)) {
                filteredRecipes.add(r);
            }
        }
        return filteredRecipes;
    }

    // EFFECTS: Asks user for an index of the DayPlan and displays all fields of the
    // DayPlan,
    // gives options: 1 - Edit day plan, 2 - Remove day plan,
    // 3 - Get ingredients for all recipes in the day plan, 0 - Back home
    private void viewDayPlanByIndex() {
        int index = chooseIndex();
        System.out.println("Options: 1 - Edit chosen day plan, 2 - Remove chosen day plan,");
        System.out.println("3 - Get ingredients, 0 - Back home");
        int option = MealPlannerConsole.getIntFromUser();
        switch (option) {
            case 0:
                break;
            case 1:
                editDayPlan(dayPlanBrowserCollection.getDayPlans().get(index));
                break;
            case 2:
                dayPlanBrowserCollection.getDayPlans().remove(index);
                System.out.println("Successfully removed day plan.");
                break;
            case 3:
                System.out.println(
                        "All ingredients: " + getAllIngredients(dayPlanBrowserCollection.getDayPlans().get(index)));
            default:
                break;
        }
    }

    // EFFECTS: Displays a recipe given an index for the day plan
    // Checks for a valid index
    private int chooseIndex() {
        int index = 0;
        System.out.println("Please enter the index of the day plan:");
        while (true) {
            try {
                index = MealPlannerConsole.getIntFromUser();
                System.out.println("You have chosen the day plan at index: " + index);
                printDayPlan(dayPlanBrowserCollection.getDayPlans().get(index));
                return index;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Index not found. Try again.");
                System.out.println("Day Plans: " + dayPlanBrowserCollection.getDayPlanNames());
            }
        }
    }

    // EFFECTS: displays all fields of a given DayPlan
    public static void printDayPlan(DayPlan dayPlan) {
        System.out.println("Name: " + dayPlan.getName());
        System.out.println("Date: " + dayPlan.getDate());
        System.out.println("Starred: " + dayPlan.getStarred());
        printRecipesByTOD(dayPlan.getRecipes());
    }

    // EFFECTS: Returns list of strings of recipe names given list of recipes
    private static List<String> recipesToLOS(List<Recipe> recipes) {
        List<String> recipeNames = new ArrayList<>();
        for (Recipe r : recipes) {
            recipeNames.add(r.getName());
        }
        return recipeNames;
    }

    // EFFECTS:
    private List<String> getAllIngredients(DayPlan dp) {
        List<String> allIng = new ArrayList<>();
        for (Recipe r : dp.getRecipes()) {
            allIng.addAll(r.getIngredients());
        }
        return allIng;
    }

    // EFFECTS: Re-sets all fields of a given DayPlan
    private void editDayPlan(DayPlan dayPlan) {
        System.out.println("Edit a day plan:");
        dayPlan.setName(specifyName());
        dayPlan.setDate(specifyDate());
        dayPlan.setRecipes(specifyRecipes());
        System.out.println("Please specify if the Day Plan is starred (true or false)");
        dayPlan.setStarred(MealPlannerConsole.getBooleanFromUser());
        System.out.println("Successfully edited day plan:");
        printDayPlan(dayPlan);
    }

    // EFFECTS: Sorts DayPlans by name
    private void sortDayPlans() {
        dayPlanBrowserCollection.sortByName();
        System.out.println("Sorted day plans in alphabetical order.");
        System.out.println("New list: " + dayPlanBrowserCollection.getDayPlanNames());
    }

    public static void setDayPlanCollection(DayPlanCollection dpc) {
        dayPlanBrowserCollection = dpc;
    }

    public static DayPlanCollection getDayPlanCollection() {
        return dayPlanBrowserCollection;
    }

}
