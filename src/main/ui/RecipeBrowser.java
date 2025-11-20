package ui;

import model.*;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

import java.util.ArrayList;
import java.util.List;

// Represents the RecipeBrowser user interface of the Meal Planner application
public class RecipeBrowser extends JPanel implements ListSelectionListener {

    private RecipeCollection recipeBrowserCollection;
    private DefaultListModel<Recipe> recipeModel;

    private JList<Recipe> recipes;
    private RecipeFrame rf;

    private JButton createRecipeButton;
    private JButton sortRecipesButton;
    private RecipeDisplay recipeDisplay;

    // EFFECTS: Creates the recipe browser UI and initializes fields
    public RecipeBrowser() {
        super(new BorderLayout());
        recipeBrowserCollection = new RecipeCollection();
        rf = new RecipeFrame(this);

        recipeModel = new DefaultListModel<Recipe>();
        recipes = new JList<Recipe>(recipeModel);
        recipes.setCellRenderer(new RecipeRenderer());
        recipes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        recipes.setSelectedIndex(0);
        recipes.addListSelectionListener(this);

        initButtons();

        recipeDisplay = new RecipeDisplay();
        JScrollPane listScrollPane = new JScrollPane(recipes);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listScrollPane, recipeDisplay);
        splitPane.setDividerLocation(450);

        add(splitPane, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECtS: Initializes buttons
    private void initButtons() {
        ImageIcon plusIcon = createImageIcon("PlusIconSmall.png");
        createRecipeButton = new JButton("Create a Recipe", plusIcon);
        createRecipeButton.setHorizontalTextPosition(AbstractButton.LEADING);
        createRecipeButton.addActionListener(new ButtonListener());

        sortRecipesButton = new JButton("Sort Recipes by Name");
        sortRecipesButton.setMargin(new Insets(10, 5, 10, 5));
        sortRecipesButton.addActionListener(new ButtonListener());

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.add(createRecipeButton);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(sortRecipesButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        add(buttonPane, BorderLayout.PAGE_START);
    }

    // MODIFIES: this
    // EFFECTS: adds a recipe name to the recipeModel and Collection
    public void addRecipe(Recipe r) {
        recipeModel.addElement(r);
        recipeBrowserCollection.addRecipe(r);
    }

    // MODIFIES: this
    // EFFECTS: Creates a new list, adds current recipes to list, sorts list and
    // updates the recipeModel with new list
    private void sortRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        Object[] recipeObjects = recipeModel.toArray();

        for (Object o : recipeObjects) {
            recipes.add((Recipe) o);
        }

        recipeBrowserCollection.setRecipes(recipes);
        recipeBrowserCollection.sortByName();
        recipeModel.clear();

        for (Recipe r : recipes) {
            recipeModel.addElement(r);
        }
    }

    // MODIFIES: this
    // EFFECTS: Updates the recipes for this recipe browser for display
    public void updateRecipes() {
        recipeModel.clear();

        for (Recipe r : recipeBrowserCollection.getRecipes()) {
            recipeModel.addElement(r);
        }
    }

    // EFFECTS: Returns ImageIcon given path, or null if not found
    // Referenced from ButtonDemo (link above)
    protected ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = RecipeBrowser.class.getResource(path);

        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    // MODIFIES: recipeDisplay
    // EFFECTS: Detects if selected object is changed in ListSelectionListener
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {
            if (recipes.getSelectedIndex() == -1) {
                // Not selected; do nothing
            } else {
                recipeDisplay.setSelectedRecipe(recipeBrowserCollection.getRecipes().get(recipes.getSelectedIndex()));
            }
        }
    }

    /*
     * Setters & Getters
     */
    public void setRecipeCollection(RecipeCollection rc) {
        recipeBrowserCollection = rc;
    }

    public RecipeCollection getRecipeCollection() {
        return recipeBrowserCollection;
    }

    /*
     * Helper classes
     */
    // Represents an Action Listener for Creating a recipe
    private class ButtonListener implements ActionListener {
        // MODIFIES: RecipeBrowser
        // EFFECTS: Detects an action and handles it
        // Creates a pop-up window if creating a recipe
        // Sorts recipes by name if sorting recipes
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == createRecipeButton) {
                rf.setVisible(true);
            } else if (e.getSource() == sortRecipesButton) {
                sortRecipes();
            }

        }
    }

    // Represents a custom JList renderer for recipeModel
    private class RecipeRenderer extends DefaultListCellRenderer {
        // EFFECTS: Renders the list of recipes as recipe names and if starred
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            Recipe r = (Recipe) value;
            if (r.getStarred()) {
                setText(r.getName() + " ⭐");
            } else {
                setText(r.getName());
            }
            
            return this;
        }
    }
}
