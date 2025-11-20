package comparators;

import model.DayPlan;

// Represents a comparator that compares DayPlan names in lowercase alphabetical order
public class DayPlanByNameComparator implements java.util.Comparator<DayPlan> {

    // EFFECTS: Compares lowercase names of given DayPlans and returns the comparison
    // in lowercase alphabetical order
    @Override
    public int compare(DayPlan a, DayPlan b) {
        return a.getName().toLowerCase().compareTo(b.getName().toLowerCase());
    }
}
