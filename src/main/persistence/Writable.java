package persistence;

import org.json.JSONObject;

// Represents an Interface for an object that can be written to file as JSON data
public interface Writable {
    // EFFECTS: returns this as a JSON object
    JSONObject toJson();
}
