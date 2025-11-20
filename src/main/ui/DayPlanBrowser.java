package ui;

import model.*;

import javax.swing.*;
import java.awt.*;

// Represents the DayPlanBrowser of the Meal Planner application that
// allows user to view day plans, add/remove day plans, sort day plans and edit day plans
public class DayPlanBrowser extends JPanel {
    private DayPlanCollection dayPlanBrowserCollection;

    // EFFECTS: Creates a day plan browser
    public DayPlanBrowser() {
        super(new BorderLayout());
        dayPlanBrowserCollection = new DayPlanCollection();
    }

    /*
     * Setters & Getters
     */
    public void setDayPlanCollection(DayPlanCollection dpc) {
        dayPlanBrowserCollection = dpc;
    }

    public DayPlanCollection getDayPlanCollection() {
        return dayPlanBrowserCollection;
    }

}
