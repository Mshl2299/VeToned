package comparators;

import model.Recipe;

// Represents a comparator that compares Recipe names in lowercase alphabetical order
public class RecipeByNameComparator implements java.util.Comparator<Recipe> {

    // EFFECTS: Compares lowercase names of given Recipes and returns the comparison
    // in lowercase alphabetical order
    @Override
    public int compare(Recipe a, Recipe b) {
        return a.getName().toLowerCase().compareTo(b.getName().toLowerCase());
    }
}
