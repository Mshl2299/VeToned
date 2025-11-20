package model;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DayPlanCollectionTest extends DayPlanTest {
    private DayPlanCollection testDayPlanCollection;
    private List<DayPlan> testDayPlans;

    @BeforeEach
    void runBefore() {
        super.runBefore();
        testDayPlans = new ArrayList<>();
        testDayPlans.add(testDayPlan1);
        testDayPlans.add(testDayPlan2);

        testDayPlanCollection = new DayPlanCollection();
    }

    @Test
    void testConstructor() {
        assertTrue(testDayPlanCollection.getDayPlans().isEmpty());
    }

    @Test
    void testAddDayPlans() {
        testDayPlanCollection.addDayPlan(testDayPlan1);
        testDayPlanCollection.addDayPlan(testDayPlan2);
        assertEquals(testDayPlan1, testDayPlanCollection.getDayPlans().get(0));
        assertEquals(testDayPlan2, testDayPlanCollection.getDayPlans().get(1));
    }

    @Test
    void testSortByName() {
        testDayPlanCollection.addDayPlan(testDayPlan2);
        testDayPlanCollection.addDayPlan(testDayPlan1);
        testDayPlanCollection.sortByName();
        assertEquals(testDayPlan2, testDayPlanCollection.getDayPlans().get(0));
        assertEquals(testDayPlan1, testDayPlanCollection.getDayPlans().get(1));
    }

    @Test
    void testSortByNameEmptyStr() {
        testDayPlan2.setName("");
        testDayPlanCollection.setDayPlans(testDayPlans);
        testDayPlanCollection.sortByName();
        assertEquals(testDayPlan2, testDayPlanCollection.getDayPlans().get(0));
        assertEquals(testDayPlan1, testDayPlanCollection.getDayPlans().get(1));
    }

    @Test
    void testSortByStarred() {
        testDayPlan1.setStarred(true);
        testDayPlanCollection.setDayPlans(testDayPlans);
        testDayPlanCollection.sortByStarred();
        assertEquals(testDayPlan1, testDayPlanCollection.getDayPlans().get(0));
        assertEquals(testDayPlan2, testDayPlanCollection.getDayPlans().get(1));
    }

    @Test
    void testSortByStarredAllStar() {
        testDayPlan1.setStarred(true);
        testDayPlan2.setStarred(true);
        testDayPlanCollection.addDayPlan(testDayPlan2);
        testDayPlanCollection.addDayPlan(testDayPlan1);
        testDayPlanCollection.sortByStarred();
        assertEquals(testDayPlan2, testDayPlanCollection.getDayPlans().get(0));
        assertEquals(testDayPlan1, testDayPlanCollection.getDayPlans().get(1));
    }

    @Test
    void testGetAllDayPlans() {
        testDayPlanCollection.setDayPlans(testDayPlans);
        assertEquals(testDayPlans, testDayPlanCollection.getDayPlans());
    }

    @Test
    void testGetDayPlanNames() {
        List<String> testDayPlanNames = new ArrayList<>();
        testDayPlanCollection.setDayPlans(testDayPlans);
        for (DayPlan dp : testDayPlanCollection.getDayPlans()) {
            testDayPlanNames.add(dp.getName());
        }
        assertEquals(testDayPlanNames, testDayPlanCollection.getDayPlanNames());
    }

    @Test
    void testGetDayPlanByDateNotFound() {
        assertNull(testDayPlanCollection.getDayPlanByDate(LocalDate.of(2024, 10, 20)));
    }

    @Test
    void testGetDayPlanByDate() {
        testDayPlanCollection.setDayPlans(testDayPlans);
        assertEquals(testDayPlan1, testDayPlanCollection.getDayPlanByDate(LocalDate.of(2024, 10, 20)));
    }

    @Test
    void testGetDayPlanByDateLast() {
        testDayPlanCollection.setDayPlans(testDayPlans);
        assertEquals(testDayPlan2, testDayPlanCollection.getDayPlanByDate(LocalDate.now()));
    }

}
