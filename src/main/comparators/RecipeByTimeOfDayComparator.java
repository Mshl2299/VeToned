package comparators;

import model.Recipe;

// Represents a comparator that compares Recipe TimeOfDay enumeration
public class RecipeByTimeOfDayComparator implements java.util.Comparator<Recipe> {

    // EFFECTS: Compares TimeOfDay enums of given Recipes and returns the comparison
    // in the default order of the TimeOfDay enumeration
    @Override
    public int compare(Recipe a, Recipe b) {
        return a.getTimeOfDay().compareTo(b.getTimeOfDay());
    }
}
