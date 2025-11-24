package workshop3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EventForGlobalPlanningTest {

    private EventForGlobalPlanning nyMorning;
    private EventForGlobalPlanning laOverlap;

    @BeforeEach
    void setUp() {
        ZoneId ny = ZoneId.of("America/New_York");
        ZoneId la = ZoneId.of("America/Los_Angeles");

        ZonedDateTime nyStart = ZonedDateTime.of(2025, 10, 15, 9, 0, 0, 0, ny);
        nyMorning = new EventForGlobalPlanning("Team standup meeting", nyStart, Duration.ofMinutes(60));

        ZonedDateTime laStart = ZonedDateTime.of(2025, 10, 15, 6, 30, 0, 0, la);
        laOverlap = new EventForGlobalPlanning("Client sync", laStart, Duration.ofMinutes(60));
    }

    @Test
    void testToString() {
        String s = nyMorning.toString();
        assertTrue(s.contains("09:00"));
        assertTrue(s.startsWith("Team stand"));
    }

    @Test
    void testGetEventDate() {
        assertEquals(LocalDate.of(2025, 10, 15), nyMorning.getEventDate());
    }

    @Test
    void testGetEventDateInZone() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.of(2025, 10, 15), nyMorning.getEventDateInZone(tokyo));
    }

    @Test
    void testGetEventStartTimeInZone() {
        ZoneId la = ZoneId.of("America/Los_Angeles");
        assertEquals(LocalTime.of(6, 0), nyMorning.getEventStartTimeInZone(la));
    }

    @Test
    void testGetStartLocalTimeInZone() {
        assertEquals(LocalTime.of(9, 0), nyMorning.getStartLocalTimeInZone());
    }

    @Test
    void testConflictsWith_overlappingAcrossZones() {
        assertTrue(nyMorning.conflictsWith(laOverlap));
        assertTrue(laOverlap.conflictsWith(nyMorning));
    }

    @Test
    void testConflictsWith_backToBackNoConflict() {
        EventForGlobalPlanning next =
                new EventForGlobalPlanning("Next item", nyMorning.getStart().plusMinutes(60), Duration.ofMinutes(30));
        assertFalse(nyMorning.conflictsWith(next));
        assertFalse(next.conflictsWith(nyMorning));
    }
}