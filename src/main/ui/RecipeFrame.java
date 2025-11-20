package ui;

import model.Recipe;
import model.enumerations.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Arrays;

// Represents a pop-up form that allows user to specify and create a new recipe
// from the RecipeBrowser UI
public class RecipeFrame extends JFrame implements ActionListener {
    private RecipeBrowser recipeBrowser;
    private Container container;

    private JLabel name;
    private JTextField nameText;
    private JLabel type;
    private JComboBox<MealType> typeCB;
    private JLabel diet;
    private JComboBox<Diet> dietCB;
    private JLabel cookTime;
    private JSpinner cookTimeSpinner;
    private JLabel ingredients;
    private JTextArea ingredientsText;
    private JLabel starred;
    private JCheckBox starCB;

    private JButton create;

    // EFFECTS: Creates a new pop-up window to create a recipe
    public RecipeFrame(RecipeBrowser parent) {
        setTitle("Create a new recipe");
        setBounds(500, 250, 380, 380);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        container = getContentPane();
        container.setLayout(null);
        recipeBrowser = parent;

        initializeFields();
    }

    // MODIFIES: this
    // EFFECTS: Initializes fields
    private void initializeFields() {
        initName();
        initType();
        initDiet();
        initCookTime();
        initIngredients();
        initStarred();

        initCreateButton();
    }

    // MODIFIES: this
    // EFFECTS: Initializes Name fields
    private void initName() {
        name = new JLabel("Name");
        name.setSize(100, 20);
        name.setLocation(30, 30);

        nameText = new JTextField();
        nameText.setSize(190, 20);
        nameText.setLocation(130, 30);

        container.add(name);
        container.add(nameText);
    }

    // MODIFIES: this
    // EFFECTS: Initializes Type fields
    private void initType() {
        type = new JLabel("Type");
        type.setSize(100, 20);
        type.setLocation(30, 60);

        typeCB = new JComboBox<>(MealType.values());
        typeCB.setSize(130, 20);
        typeCB.setLocation(130, 60);

        container.add(type);
        container.add(typeCB);
    }

    // MODIFIES: this
    // EFFECTS: Initializes Diet fields
    private void initDiet() {
        diet = new JLabel("Diet");
        diet.setSize(100, 20);
        diet.setLocation(30, 90);

        dietCB = new JComboBox<>(Diet.values());
        dietCB.setSize(130, 20);
        dietCB.setLocation(130, 90);

        container.add(diet);
        container.add(dietCB);

    }

    // MODIFIES: this
    // EFFECTS: Initializes CookTime fields
    private void initCookTime() {
        cookTime = new JLabel("Cook Time");
        cookTime.setSize(100, 20);
        cookTime.setLocation(30, 120);

        SpinnerModel model = new SpinnerNumberModel(0, 0, 999, 1);
        cookTimeSpinner = new JSpinner(model);
        cookTimeSpinner.setSize(50, 20);
        cookTimeSpinner.setLocation(130, 120);

        container.add(cookTime);
        container.add(cookTimeSpinner);
    }

    // MODIFIES: this
    // EFFECTS: Initializes Ingredients fields
    private void initIngredients() {
        ingredients = new JLabel("Ingredients");
        ingredients.setSize(100, 20);
        ingredients.setLocation(30, 150);

        ingredientsText = new JTextArea();
        ingredientsText.setSize(200, 50);
        ingredientsText.setLocation(130, 150);

        container.add(ingredients);
        container.add(ingredientsText);
    }

    // MODIFIES: this
    // EFFECTS: Initializes Starred fields
    private void initStarred() {
        starred = new JLabel("Starred?");
        starred.setSize(100, 20);
        starred.setLocation(30, 210);

        starCB = new JCheckBox();
        starCB.setSize(20, 20);
        starCB.setLocation(130, 210);

        container.add(starred);
        container.add(starCB);
    }

    // MODIFIES: this
    // EFFECTS: Initializes the Create/submit button
    private void initCreateButton() {
        create = new JButton("Create Recipe");
        create.setSize(150, 20);
        create.setLocation(30, 240);
        create.addActionListener(this);

        container.add(create);
    }

    // MODIFIES: this
    // EFFECTS: Resets all fields of the form
    private void reset() {
        nameText.setText("");
        typeCB.setSelectedIndex(0);
        dietCB.setSelectedIndex(0);
        cookTimeSpinner.setValue(0);
        ingredientsText.setText("");
        starCB.setSelected(false);
    }

    // MODIFIES: RecipeBrowser
    // EFFECTS: Detects a button press in this frame and handles it
    // If creating a new recipe, create a recipe and adds it to RecipeBrowser
    // If name field is empty, prompts user to enter a name
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == create) {
            String name = nameText.getText();
            if (!name.equals("")) {
                MealType type = (MealType) typeCB.getSelectedItem();
                Diet diet = (Diet) dietCB.getSelectedItem();
                int ct = (int) cookTimeSpinner.getValue();
                List<String> ing = Arrays.asList(ingredientsText.getText().split("\\s*,\\s*"));

                Recipe recipe = new Recipe(name, type, diet, ct, ing);
                recipe.setStarred(starCB.isSelected());
                recipeBrowser.addRecipe(recipe);

                reset();
                setVisible(false);
            } else {
                System.out.println("Please enter a valid name.");
            }

        }
    }
}
