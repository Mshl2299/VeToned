package ui;

import model.Recipe;

import javax.swing.*;

// Represents the recipe display panel for the recipe browser
public class RecipeDisplay extends JPanel {
    private JLabel name;
    private JLabel type;
    private JLabel diet;
    private JLabel ingredients;
    private JLabel cookTime;
    private JLabel starred;

    // EFFECTS: Creates the recipe display panel for the Recipe Browser
    // with stub values for fields
    public RecipeDisplay() {
        super(false);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        name = new JLabel("No Recipe Selected");
        type = new JLabel("type");
        diet = new JLabel("diet");
        ingredients = new JLabel("ingredients");
        cookTime = new JLabel("cook time");
        starred = new JLabel("starred");

        add(name);
        add(type);
        add(diet);
        add(ingredients);
        add(cookTime);
        add(starred);
    }

    // MODIFIES: this
    // EFFECTS: Sets the currently displayed details those of given recipe
    public void setSelectedRecipe(Recipe r) {
        name.setText("Name: " + r.getName());
        type.setText("Type: " + r.getType().toString());
        diet.setText("Diet: " + r.getDiet().toString());
        ingredients.setText("Ingredients: " + String.join(", ", r.getIngredients()));
        cookTime.setText("Cook time: " + String.valueOf(r.getCookTime()));
        starred.setText("Starred? " + String.valueOf(r.getStarred()));
    }
}
