package ui;

import persistence.JsonReader;
import persistence.JsonWriter;
import model.Event;
import model.EventLog;

import org.json.JSONException;

import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Represents the meal planner application user interface
public class MealPlanner extends JFrame {
    private static final String RC_LOC = "./data/recipeCollection.json";
    private static final String DPC_LOC = "./data/dayPlanCollection.json";
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;

    private JsonWriter jsonWriterRC;
    private JsonWriter jsonWriterDPC;
    private JsonReader jsonReaderRC;
    private JsonReader jsonReaderDPC;
    private RecipeBrowser recipeBrowser;
    private DayPlanBrowser dpBrowser;

    private JButton saveButton;
    private JButton loadButton;

    // EFFECTS: Constructs a meal planner and runs application,
    // throws FileNotFoundException if file is not found
    public MealPlanner() throws FileNotFoundException {
        super("Vetoned");

        initializeFields();
        initializeGraphics();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                printEvents();
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
    }

    // EFFECTS: Prints all events in EventLog to console
    private void printEvents() {
        for (Event e : EventLog.getInstance()) {
            System.out.println(e.toString());
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes fields of the meal planner
    private void initializeFields() {
        jsonWriterRC = new JsonWriter(RC_LOC);
        jsonReaderRC = new JsonReader(RC_LOC);
        jsonWriterDPC = new JsonWriter(DPC_LOC);
        jsonReaderDPC = new JsonReader(DPC_LOC);
        recipeBrowser = new RecipeBrowser();
        dpBrowser = new DayPlanBrowser();
    }

    // MODIFIES: this
    // EFFECTS: Draws JFrame window for app and generates options
    private void initializeGraphics() {
        setLayout(new BorderLayout());
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);

        createMenuItems();
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Populates menu tabbed pane with menu tabs
    // Recipe Browser (View, Create and sort Recipes)
    // Day Plan Browser (View & Create Day Plans)
    // Save to file, Load from file
    private void createMenuItems() {
        JTabbedPane menu = new JTabbedPane();
        
        menu.addTab("Recipe Browser", recipeBrowser);
        // TODO: implement Day Plan
        menu.addTab("Day Plan Browser (WIP)", makeTextPanel("Day Plan Browser (Unimplemented)"));
        menu.addTab("Save to file", makeSavePanel());
        menu.addTab("Load from file", makeLoadPanel());

        add(menu);
    }

    // EFFECTS: Creates a filler text panel given text
    private JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
    }

    // MODIFIES: this
    // EFFECTS: Creates a save panel with one button for saving
    private JComponent makeSavePanel() {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel("Are you sure? Saving will override existing save.");
        saveButton = new JButton("Save to file");
        saveButton.addActionListener(new ButtonListener());
        JLabel bkg = new JLabel(new ImageIcon("MealPlanImg.jpg"));

        panel.add(filler);
        panel.add(Box.createHorizontalStrut(5));
        panel.add(saveButton);
        panel.add(bkg);

        return panel;
    }

    // MODIFIES: this
    // EFFECTS: Creates a load panel with one button for loading
    private JComponent makeLoadPanel() {
        JLabel bkg = new JLabel(new ImageIcon("MealPlanImg.jpg"));
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel("Are you sure? Loading will override currently save data.");
        loadButton = new JButton("Load from file");
        loadButton.addActionListener(new ButtonListener());

        panel.add(filler);
        panel.add(Box.createHorizontalStrut(5));
        panel.add(loadButton);
        panel.add(bkg);

        return panel;
    }

    // EFFECTS: Saves state of the meal planner to file
    private void saveApp() {
        try {
            jsonWriterRC.open();
            jsonWriterRC.write(recipeBrowser.getRecipeCollection());
            jsonWriterRC.close();
            jsonWriterDPC.open();
            jsonWriterDPC.write(dpBrowser.getDayPlanCollection());
            jsonWriterDPC.close();
            System.out.println("Saved Recipes to " + RC_LOC + " and Day Plans to " + DPC_LOC + "!");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to files: ");
            System.out.println(RC_LOC);
            System.out.println(DPC_LOC);
        }
    }

    // EFFECTS: Loads the state of the meal planner from file
    private void loadApp() {
        try {
            recipeBrowser.setRecipeCollection(jsonReaderRC.readRC());
            recipeBrowser.updateRecipes();
            System.out.println("Loaded Recipe Browser!");

            dpBrowser.setDayPlanCollection(jsonReaderDPC.readDPC());
            System.out.println("Loaded Day Plan Browser!");
        } catch (IOException e) {
            System.out.println("Unable to read from files: ");
            System.out.println(RC_LOC + DPC_LOC);
        } catch (JSONException e) {
            System.out.println("Unable to read JSON from files: ");
            System.out.println(RC_LOC + DPC_LOC);
            System.out.println("Try saving to file to reformat JSON files.");
        }
    }

    // Represents an Action Listener for buttons part of the meal planner
    private class ButtonListener implements ActionListener {
        // EFFECTS: Detects button press: 
        // save to file if saveButton pressed
        // load from file if loadButton pressed
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == saveButton) {

                saveApp();
            } else if (e.getSource() == loadButton) {
                loadApp();
            }
        }
    }

    // EFFECTS: Starts the application
    public static void main(String[] args) {
        try {
            // For the console-based version, replace with:
            // new MealPlannerConsole();
            // Note that this will require an import.
            new MealPlanner();
        } catch (FileNotFoundException e) {
            System.out.println("Error running application: files not found at ");
            System.out.println(RC_LOC);
            System.out.println(DPC_LOC);
        }
    }
}
