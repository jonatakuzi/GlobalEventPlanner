package workshop3;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class EventForGlobalPlanning {
    private final String description;
    private final ZonedDateTime start;
    private final Duration duration;

    public EventForGlobalPlanning(String description, ZonedDateTime start, Duration duration) {
        this.description = Objects.requireNonNull(description);
        this.start = Objects.requireNonNull(start);
        this.duration = Objects.requireNonNull(duration);
    }

    public String getDescription() {
        return description;
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public Duration getDuration() {
        return duration;
    }

    public ZonedDateTime getEnd() {
        return start.plus(duration);
    }

    public LocalDate getEventDate() {
        return start.toLocalDate();
    }

    public LocalDate getEventDateInZone(ZoneId targetZone) {
        return start.withZoneSameInstant(targetZone).toLocalDate();
    }

    public LocalTime getEventStartTimeInZone(ZoneId targetZone) {
        return start.withZoneSameInstant(targetZone).toLocalTime();
    }

    public LocalTime getStartLocalTimeInZone() {
        return start.toLocalTime();
    }

    public boolean conflictsWith(EventForGlobalPlanning other) {
        Instant aS = this.start.toInstant();
        Instant aE = this.getEnd().toInstant();
        Instant bS = other.start.toInstant();
        Instant bE = other.getEnd().toInstant();
        return !(aE.compareTo(bS) <= 0 || bE.compareTo(aS) <= 0); // half-open [start,end)
    }

    @Override
    public String toString() {
        String trimmed = description.strip();
        int maxChars = 12;
        String shortDesc = trimmed.length() <= maxChars
                ? trimmed
                : trimmed.substring(0, Math.max(0, maxChars - 1)) + "…";
        String time = start.format(DateTimeFormatter.ofPattern("HH:mm"));
        return shortDesc + " @ " + time;
    }
}