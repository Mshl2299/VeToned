package model;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

// Represents a recipe for a meal with a name, meal type, diet type,
// time of day to be eaten, estimated cook time, list of ingredients,
// and if it is starred
public class Recipe implements Writable {
    public enum Diet {
        KETO_VEGAN, KETO_VEGETARIAN, KETO, VEGAN, VEGETARIAN, NON_VEGETARIAN,
    }
    public enum MealType {
        COURSE, SIDE, SNACK, OTHER
    }
    public enum TimeOfDay {
        MORNING, LATE_MORNING, AFTERNOON, LATE_AFTERNOON, EVENING, LATE_EVENING, UNSPECIFIED
    }

    private String name;
    private MealType type;
    private Diet diet;
    private TimeOfDay timeOfDay;
    private int cookTime;
    private List<String> ingredients;
    private boolean starred;

    // EFFECTS: Constructs a recipe given name, type, diet, cookTime, and list of
    // ingredients, with an unspecified time of day and is not starred
    public Recipe(String name, MealType type, Diet diet, int cookTime, List<String> ingredients) {
        this.name = name;
        this.type = type;
        this.diet = diet;
        this.cookTime = cookTime;
        this.ingredients = ingredients;
        this.timeOfDay = TimeOfDay.UNSPECIFIED;
        this.starred = false;
    }

    // EFFECTS: Constructs a recipe given another recipe; 
    // creates a clone of given recipe with same fields
    public Recipe(Recipe recipe) {
        this.name = recipe.getName();
        this.type = recipe.getType();
        this.diet = recipe.getDiet();
        this.timeOfDay = recipe.getTimeOfDay();
        this.cookTime = recipe.getCookTime();
        this.ingredients = recipe.getIngredients();
        this.starred = recipe.getStarred();
    }

    /* 
     * Setters & Getters
     */
    public void setName(String name) {
        this.name = name;
    }

    public void setType(MealType type) {
        this.type = type;
    }

    public void setDiet(Diet diet) {
        this.diet = diet;
    }

    public void setTimeOfDay(TimeOfDay timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public void setCookTime(int cookTime) {
        this.cookTime = cookTime;
    }

    public void setIngredients(List<String> ing) {
        this.ingredients = ing;
    }

    public void setStarred(boolean starred) {
        this.starred = starred;
    }

    public String getName() {
        return this.name;
    }

    public MealType getType() {
        return this.type;
    }

    public Diet getDiet() {
        return this.diet;
    }

    public TimeOfDay getTimeOfDay() {
        return this.timeOfDay;
    }

    public int getCookTime() {
        return this.cookTime;
    }

    public List<String> getIngredients() {
        return this.ingredients;
    }

    public Boolean getStarred() {
        return this.starred;
    }

    // EFFECTS: Converts Recipe (name, type, diet, timeOfDay, cookTime, ingredients
    // and starred) to JSON and returns it
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("type", type);
        json.put("diet", diet);
        json.put("timeOfDay", timeOfDay);
        json.put("cookTime", cookTime);
        json.put("ingredients", ingredientsToJson());
        json.put("starred", starred);
        return json;
    }

    // EFFECTS: Converts recipe list of ingredients to a JSONArray 
    // and returns it
    private JSONArray ingredientsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (String i : this.ingredients) {
            jsonArray.put(i);
        }
        return jsonArray;
    }
}
