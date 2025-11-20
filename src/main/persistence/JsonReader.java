package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Stream;
import java.time.LocalDate;

import org.json.*;
import model.*;
import model.enumerations.*;

// Represents a reader that reads the JSON representation of a MealPlanner from file
// with a MealPlanner consisting of both a RecipeCollection and DayPlanCollection
public class JsonReader {
    private String src;

    // EFFECTS: constructs reader to read from given source file
    public JsonReader(String src) {
        this.src = src;
    }

    // EFFECTS: reads RecipeCollection from file and returns it,
    // throws IOException if an error occurs reading data from file
    public RecipeCollection readRC() throws IOException {
        String jsonData = readFile(src);
        JSONObject json = new JSONObject(jsonData);
        EventLog.getInstance().logEvent(new Event("Loaded recipe collection from file."));
        return parseRC(json);
    }

    // EFFECTS: reads DayPlanCollection from file and returns it
    // throws IOException if an error occurs reading data from file
    public DayPlanCollection readDPC() throws IOException {
        String jsonData = readFile(src);
        JSONObject json = new JSONObject(jsonData);
        // FIXME: implement DayPlans
        // EventLog.getInstance().logEvent(new Event("Loaded day plan collection from file."));
        return parseDPC(json);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String src) throws IOException {
        StringBuilder builder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(src), StandardCharsets.UTF_8)) {
            stream.forEach(s -> builder.append(s));
        }

        return builder.toString();
    }

    // EFFECTS: parses RecipeCollection from JSON object and returns it
    private RecipeCollection parseRC(JSONObject json) {
        RecipeCollection rc = new RecipeCollection();
        addRecipes(rc, json);
        return rc;
    }

    // EFFECTS: parses DayPlanCollection from JSON object and returns it
    private DayPlanCollection parseDPC(JSONObject json) {
        DayPlanCollection dpc = new DayPlanCollection();
        addDayPlans(dpc, json);
        return dpc;
    }

    // MODIFIES: rc
    // EFFECTS: parses recipes from JSON and adds them to RecipeCollection
    private void addRecipes(RecipeCollection rc, JSONObject json) {
        JSONArray jsonArray = json.getJSONArray("recipes");
        for (Object j : jsonArray) {
            JSONObject next = (JSONObject) j;
            rc.addRecipe(parseRecipe(next));
        }
    }

    // EFFECTS: parses single recipe from JSON and returns the recipe
    private Recipe parseRecipe(JSONObject json) {
        String name = json.getString("name");
        MealType type = MealType.valueOf(json.getString("type"));
        Diet diet = Diet.valueOf(json.getString("diet"));
        int cookTime = json.getInt("cookTime");
        List<String> ingredients = getIngredients(json.getJSONArray("ingredients"));
        TimeOfDay timeOfDay = TimeOfDay.valueOf(json.getString("timeOfDay"));
        boolean starred = json.getBoolean("starred");

        Recipe recipe = new Recipe(name, type, diet, cookTime, ingredients);
        recipe.setTimeOfDay(timeOfDay);
        recipe.setStarred(starred);
        return recipe;
    }

    // EFFECTS: parses ingredients from json and returns list
    private List<String> getIngredients(JSONArray jsonIngredients) {
        List<String> ingredients = new ArrayList<>();
        for (int i = 0; i < jsonIngredients.length(); i++) {
            ingredients.add(jsonIngredients.getString(i));
        }
        return ingredients;
    }

    // MODIFIES: dpc
    // EFFECTS: parses dayPlans from JSON and adds them to DayPlanCollection
    private void addDayPlans(DayPlanCollection dpc, JSONObject json) {
        JSONArray jsonArray = json.getJSONArray("dayPlans");
        for (Object j : jsonArray) {
            JSONObject next = (JSONObject) j;
            addDayPlan(dpc, next);
        }
    }

    // MODIFIES: dpc
    // EFFECTS: parses single dayPlan from JSON and adds it to DayPlanCollection
    private void addDayPlan(DayPlanCollection dpc, JSONObject json) {
        String name = json.getString("name");
        LocalDate date = LocalDate.parse(json.getString("date"));
        boolean starred = json.getBoolean("starred");
        List<Recipe> recipes = getRecipes(json.getJSONArray("recipes"));

        DayPlan dayPlan = new DayPlan(name, date, recipes);
        dayPlan.setStarred(starred);

        dpc.addDayPlan(dayPlan);
    }

    // EFFECTS: parses recipes from json and returns it
    private List<Recipe> getRecipes(JSONArray jsonRecipes) {
        List<Recipe> recipes = new ArrayList<>();
        for (Object r : jsonRecipes) {
            JSONObject next = (JSONObject) r;
            recipes.add(parseRecipe(next));
        }
        return recipes;
    }

}
