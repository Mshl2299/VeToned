package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the EventLog class
 */
public class EventLogTest {
    private Event e1;
    private Event e2;
    private Event e3;

    @BeforeEach
    public void loadEvents() {
        e1 = new Event("E1");
        e2 = new Event("E2");
        e3 = new Event("E3");
        EventLog elog = EventLog.getInstance();
        elog.logEvent(e1);
        elog.logEvent(e2);
        elog.logEvent(e3);
    }

    @Test
    public void testLogEvent() {
        List<Event> events = new ArrayList<Event>();

        EventLog elog = EventLog.getInstance();
        for (Event next : elog) {
            events.add(next);
        }

        assertTrue(events.contains(e1));
        assertTrue(events.contains(e2));
        assertTrue(events.contains(e3));
    }

    @Test
    public void testClear() {
        EventLog elog = EventLog.getInstance();
        elog.clear();
        Iterator<Event> itr = elog.iterator();
        assertTrue(itr.hasNext()); // After log is cleared, the clear log event is added
        assertEquals("Event log cleared.", itr.next().getDescription());
        assertFalse(itr.hasNext());
    }
}
