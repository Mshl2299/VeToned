package model;

import java.util.List;
import java.util.Collections;
import java.time.LocalDate;

import org.json.JSONObject;
import comparators.RecipeByTimeOfDayComparator;

// Represents a day plan for a collection of recipes with a name, date and if it is starred
public class DayPlan extends RecipeCollection {
    private String name;
    private LocalDate date;
    private boolean starred;

    // EFFECTS: Constructs a day plan given a name, date and list of recipes
    // and is not starred
    public DayPlan(String name, LocalDate date, List<Recipe> recipes) {
        this.name = name;
        this.date = date;
        this.recipes = recipes;
        this.starred = false;
    }

    // MODIFIES: this
    // EFFECTS: Sorts list of recipes by time of day (Morning Afternoon Evening
    // Unspecified) and returns the sorted list
    public List<Recipe> sortByTimeOfDay() {
        Collections.sort(this.recipes, new RecipeByTimeOfDayComparator());
        return this.recipes;
    }

    /*
     * Setters & Getters
     */

    public void setName(String name) {
        this.name = name;
    }

    // EFFECTS: Takes a string in format yyyy-mm-dd, converts it to LocalDate
    // and sets the date to the converted value
    public void setDate(String date) {
        this.date = LocalDate.parse(date);
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setStarred(boolean starred) {
        this.starred = starred;
    }

    public String getName() {
        return this.name;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Boolean getStarred() {
        return this.starred;
    }

    // EFFECTS: Converts DayPlan name, date, recipes and starred to JSON
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("date", date);
        json.put("recipes", recipesToJson());
        json.put("starred", starred);
        return json;
    }

}
