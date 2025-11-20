package persistence;

import java.io.*;

import org.json.JSONObject;
import model.*;

// Represents a writer that saves the JSON representation of a MealPlanner to file
// With a MealPlanner consisting of both a RecipeCollection and DayPlanCollection
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter pw;
    private String dest;

    // EFFECTS: constructs writer to write to given destination file
    public JsonWriter(String dest) {
        this.dest = dest;
    }

    // MODIFIES: this
    // EFFECTS: opens writer for writing, throws FileNotFoundException 
    // if destination file cannot be opened for writing
    public void open() throws FileNotFoundException {
        pw = new PrintWriter(new File(dest));
    }

    // MODIFIES: this
    // EFFECTS: saves JSON representation of RecipeCollection to file
    public void write(RecipeCollection rc) {
        JSONObject json = rc.toJson();
        saveToFile(json.toString(TAB));
        EventLog.getInstance().logEvent(new Event("Saved recipe collection to file."));
    }

    // MODIFIES: this
    // EFFECTS: saves JSON representation of DayPlanCollection to file
    public void write(DayPlanCollection dpc) {
        JSONObject json = dpc.toJson();
        saveToFile(json.toString(TAB));
        // FIXME: implement Day Plans
        // EventLog.getInstance().logEvent(new Event("Saved day plan collection to file."));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        pw.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        pw.print(json);
    }
}
