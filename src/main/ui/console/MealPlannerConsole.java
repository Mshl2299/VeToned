package ui.console;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.json.JSONException;
import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

// Represents the meal planner application
public class MealPlannerConsole {
    private static final String RC_LOC = "./data/recipeCollection.json";
    private static final String DPC_LOC = "./data/dayPlanCollection.json";
    private JsonWriter jsonWriterRC;
    private JsonWriter jsonWriterDPC;
    private JsonReader jsonReaderRC;
    private JsonReader jsonReaderDPC;
    private static Scanner scanner;
    private static RecipeBrowserConsole recipeBrowser;
    private static DayPlanBrowserConsole dayPlanBrowser;

    // EFFECTS: Constructs a meal planner and run application,
    // throws FileNotFoundException if file is not found
    public MealPlannerConsole() throws FileNotFoundException {
        jsonWriterRC = new JsonWriter(RC_LOC);
        jsonReaderRC = new JsonReader(RC_LOC);
        jsonWriterDPC = new JsonWriter(DPC_LOC);
        jsonReaderDPC = new JsonReader(DPC_LOC);

        scanner = new Scanner(System.in);
        recipeBrowser = new RecipeBrowserConsole();
        dayPlanBrowser = new DayPlanBrowserConsole();

        System.out.println("Welcome to the Meal Planner!");
        homepage();
    }

    // EFFECTS: Represents the homepage with main user options:
    // 1 - View Today's Plan, 2 - View & Create Recipes, 3 - View and Create Day
    // Plans, 4 - Save current app, 5 - Load app from file, 0 - Quit the program
    public void homepage() {
        while (true) {
            System.out.println("\nMeal Planner - Homepage");
            System.out.println("Options (please choose a number):");
            System.out.println("1 - Today's plan");
            System.out.println("2 - Recipe Browser & Create Recipes");
            System.out.println("3 - Day Plan Browser & Create Day Plans");
            System.out.println("4 - Save current Recipe Browser and Day Plan Browser");
            System.out.println("5 - Load Recipe Browser and Day Plan Browser");
            System.out.println("0 - Quit application");

            int option = getIntInRangeFromUser(0,5);
            if (option == 0) {
                askForSave();
                break;
            }
            processHomepageOptions(option);
        }
    }

    // EFFECTS: Directs the user given chosen option, throws
    // InvalidInputException if option is not an integer in the range
    public void processHomepageOptions(int option) {
        switch (option) {
            case 1:
                viewToday();
                break;
            case 2:
                viewRecipeBrowser();
                break;
            case 3:
                viewDayPlanBrowser();
                break;
            case 4:
                askForSave();
                break;
            case 5:
                askToOverride();
                break;
            default:
                break; // return to homepage()
        }
    }

    // EFFECTS: Outputs a list of recipes of a day plan given a date,
    // and gives a list of commands: View all ingredients, Edit Day Plan, Back home
    // If there are no day plans, return an error message and send user to homepage
    public void viewToday() {
        DayPlanCollection dayPlanCollection = DayPlanBrowserConsole.getDayPlanCollection();
        System.out.println("You have selected: 1 - Today's Plan");
        if (dayPlanCollection.getDayPlans().isEmpty()) {
            System.out.println("There are no day plans available. Please create a day plan.");
        } else {
            DayPlanBrowserConsole.printDayPlan(dayPlanCollection.getDayPlanByDate(LocalDate.now()));
        }
    }

    // EFFECTS: Checks to see if recipe browser is created and calls view on recipe
    // browser, creates new instance of a recipeBrowser if not present
    public void viewRecipeBrowser() {
        if (recipeBrowser != null) {
            recipeBrowser.viewRecipeBrowser();
        } else {
            recipeBrowser = new RecipeBrowserConsole();
            recipeBrowser.viewRecipeBrowser();
        }
    }

    // EFFECTS: Checks to see if day plan browser is created and calls view on day
    // play browser, creates new instance of a dayPlanBrowser if not present
    public void viewDayPlanBrowser() {
        if (dayPlanBrowser != null) {
            dayPlanBrowser.viewDayPlanBrowser();
        } else {
            dayPlanBrowser = new DayPlanBrowserConsole();
            dayPlanBrowser.viewDayPlanBrowser();
        }
    }

    // EFFECTS: Gives user option to save and Warns user that saving will override
    // existing data
    private void askForSave() {
        System.out.println("Would you like to save your Recipes and Day Plans? yes/no");
        System.out.println("CAUTION: Saving will override existing data on file.");
        while (true) {
            String input = scanner.next();
            if (input.toLowerCase().equals("yes")) {
                saveApp();
                break;
            } else if (input.toLowerCase().equals("no")) {
                break;
            } else {
                System.out.println("Invalid input. Enter either yes or no");
            }
        }
    }

    // EFFECTS: Saves state of application to file
    private void saveApp() {
        try {
            jsonWriterRC.open();
            jsonWriterRC.write(RecipeBrowserConsole.getRecipeCollection());
            jsonWriterRC.close();
            jsonWriterDPC.open();
            jsonWriterDPC.write(DayPlanBrowserConsole.getDayPlanCollection());
            jsonWriterDPC.close();
            System.out.println("Saved Recipes to " + RC_LOC + " and Day Plans to " + DPC_LOC + "!");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to files: ");
            System.out.println(RC_LOC);
            System.out.println(DPC_LOC);
        }
    }

    // EFFECTS: Gives user option to load data, and warns user that loading will
    // override current state
    private void askToOverride() {
        System.out.println("CAUTION: Loading from file will override current data.");
        System.out.println("Are you sure you want to load from file? yes/no");
        while (true) {
            String input = scanner.next();
            if (input.toLowerCase().equals("yes")) {
                loadApp();
                break;
            } else if (input.toLowerCase().equals("no")) {
                break;
            } else {
                System.out.println("Invalid input. Enter either yes or no");
            }
        }
    }

    // EFFECTS: Loads the state of the application from file
    private void loadApp() {
        try {
            RecipeBrowserConsole.setRecipeCollection(jsonReaderRC.readRC());
            System.out.println("Loaded Recipe Browser!");
            DayPlanBrowserConsole.setDayPlanCollection(jsonReaderDPC.readDPC());
            System.out.println("Loaded Day Plan Browser!");
        } catch (IOException e) {
            System.out.println("Unable to read from files: ");
            System.out.println(RC_LOC);
            System.out.println(DPC_LOC);
        } catch (JSONException e) {
            System.out.println("Unable to read JSON from files: ");
            System.out.println(RC_LOC);
            System.out.println(DPC_LOC);
            System.out.println("Try saving to file to reformat JSON files.");
        }
    }

    // EFFECTS: Continues asking for integer input until user inputs an integer
    public static int getIntFromUser() {
        int input = 0;
        while (true) {
            try {
                input = scanner.nextInt();
                return input;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Enter a valid Integer.");
                scanner.next();
            }
        }
    }

    // EFFECTS: Continues asking for a boolean until user inputs a boolean
    public static boolean getBooleanFromUser() {
        while (true) {
            try {
                boolean input = scanner.nextBoolean();
                return input;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Enter either true or false");
                scanner.next();
            }
        }
    }

    // EFFECTS: Continues asking for an integer in given range until user inputs valid int
    public static int getIntInRangeFromUser(int lower, int upper) {
        while (true) {
            int option = getIntFromUser();
            if (option >= lower && option <= upper) {
                return option;
            } else {
                System.out.println("Invalid input. Enter an integer " + lower + "-" + upper + ".");
            }
        }
    }

    // Getters:
    // EFFECTS: Continues asking for a valid date until user inputs one
    public static LocalDate getDateFromUser() {
        while (true) {
            String option = scanner.next();
            try {
                return LocalDate.parse(option);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date. Try again.");
            }
        }
    }
}
