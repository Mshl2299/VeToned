package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.time.LocalDate;

import org.json.JSONArray;
import org.json.JSONObject;
import comparators.*;
import persistence.Writable;

// Represents a collection of day plans that can be sorted by name or if they are starred
public class DayPlanCollection implements Writable {
    private List<DayPlan> dayPlans;

    // EFFECTS: Construct an empty collection of day plans
    public DayPlanCollection() {
        dayPlans = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: Adds a given day plan to the end of the collection
    public void addDayPlan(DayPlan dayPlan) {
        this.dayPlans.add(dayPlan);
    }

    // MODIFIES: this
    // EFFECTS: Sorts list of day plans by name and returns the sorted list
    public List<DayPlan> sortByName() {
        Collections.sort(dayPlans, new DayPlanByNameComparator());
        return dayPlans;
    }

    // MODIFIES: this
    // EFFECTS: Sorts list of day plans by starred and returns the sorted list
    public List<DayPlan> sortByStarred() {
        Collections.sort(dayPlans, new DayPlanByStarredComparator());
        return dayPlans;
    }

    /*
     * Setters & Getters
     */
    public void setDayPlans(List<DayPlan> dayPlans) {
        this.dayPlans = dayPlans;
    }

    public List<DayPlan> getDayPlans() {
        return dayPlans;
    }

    // EFFECTS: Returns a list of names of day plans in the collection
    // If there are no day plans, returns an empty list []
    public List<String> getDayPlanNames() {
        List<String> dayPlanNames = new ArrayList<>();
        for (DayPlan d : dayPlans) {
            dayPlanNames.add(d.getName());
        }
        return dayPlanNames;
    }

    // EFFECTS: Returns a day plan given local date (in format yyyy-mm-dd)
    // If not found, returns null
    public DayPlan getDayPlanByDate(LocalDate date) {
        for (DayPlan d : dayPlans) {
            if (d.getDate().equals(date)) {
                return d;
            }
        }
        return null;
    }

    // EFFECTS: Converts DayPlanCollection to JSON and returns it
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("dayPlans", dayPlansToJson());
        return json;
    }

    // EFFECTS: Converts list of dayPlans to a JSONArray and returns it
    private JSONArray dayPlansToJson() {
        JSONArray jsonArray = new JSONArray();
        for (DayPlan dp : this.dayPlans) {
            jsonArray.put(dp.toJson());
        }
        return jsonArray;
    }
}
