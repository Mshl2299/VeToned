package model;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import comparators.RecipeByNameComparator;
import model.exceptions.*;
import persistence.Writable;

// Represents a collection of recipes that can be sorted by recipe fields (name, timeOfDay)
public class RecipeCollection implements Writable {
    protected List<Recipe> recipes;

    // EFFECTS: Constructs an empty collection of recipes
    public RecipeCollection() {
        recipes = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: Adds a given recipe to the collection
    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
        EventLog.getInstance().logEvent(new Event("Added Recipe: " + recipe.getName()));
    }

    // MODIFIES: this
    // EFFECTS: Sorts list of recipes by name and returns the sorted list
    public List<Recipe> sortByName() {
        Collections.sort(recipes, new RecipeByNameComparator());
        EventLog.getInstance().logEvent(new Event("Sorted Recipes by name"));
        return recipes;
    }

    /*
     * Setters & Getters
     */
    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    // MODIFIES: this
    // EFFECTS: Sets the name of a recipe given a name and index, throws
    // DuplicateNameException if name is already in list of recipes
    public void setRecipeNameByIndex(String newName, int index) throws DuplicateNameException {
        if (this.getRecipeNames().contains(newName)) {
            throw new DuplicateNameException();
        } else {
            recipes.get(index).setName(newName);
        }
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    // EFFECTS: Returns recipe with given name in collection,
    // throws MissingRecipeException if recipe is not found
    public Recipe getRecipeByName(String name) throws MissingRecipeException {
        for (Recipe r : recipes) {
            if (r.getName().equals(name)) {
                return r;
            }
        }
        throw new MissingRecipeException();
    }

    // EFFECTS: Returns a list of names of recipes in the collection
    // If collection is empty, returns an empty list []
    public List<String> getRecipeNames() {
        List<String> recipeNames = new ArrayList<>();
        for (Recipe recipe : recipes) {
            recipeNames.add(recipe.getName());
        }
        return recipeNames;
    }

    // EFFECTS: Returns a list of names of recipes in the collection given list of
    // recipes, list is empty returns an empty list []
    public List<String> getRecipeNames(List<Recipe> recipes) {
        List<String> recipeNames = new ArrayList<>();
        for (Recipe recipe : recipes) {
            recipeNames.add(recipe.getName());
        }
        return recipeNames;
    }

    // EFFECTS: Adds all ingredients for each recipe in collection to the end
    // of a list and returns the list
    public List<String> getAllIngredients() {
        List<String> ingredients = new ArrayList<>();
        for (Recipe recipe : recipes) {
            ingredients.addAll(recipe.getIngredients());
        }
        return ingredients;
    }

    // EFFECTS: Converts RecipeCollection to JSON and returns it
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("recipes", recipesToJson());
        return json;
    }

    // EFFECTS: Converts list of recipes to a JSONArray and returns it
    protected JSONArray recipesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Recipe r : this.recipes) {
            jsonArray.put(r.toJson());
        }
        return jsonArray;
    }

}