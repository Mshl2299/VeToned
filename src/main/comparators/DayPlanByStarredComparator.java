package comparators;

import model.DayPlan;

// Represents a comparator that compares DayPlan starred statuses
public class DayPlanByStarredComparator implements java.util.Comparator<DayPlan> {

    // EFFECTS: Compares starred statuses of given DayPlans and returns the comparison
    // Puts starred at the front of the list and unstarred at end
    @Override
    public int compare(DayPlan a, DayPlan b) {
        return b.getStarred().compareTo(a.getStarred());
    }
}
