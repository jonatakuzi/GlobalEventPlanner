package workshop3;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class PlannerConsole {

    private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("EEE, MMM d uuuu");
    private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("HH:mm");

    public static void main(String[] args) {
        // Sample data (acceptance set)
        List<EventForGlobalPlanning> events = List.of(
                new EventForGlobalPlanning("Team standup meeting",
                        ZonedDateTime.of(2025,10,15,9,0,0,0, ZoneId.of("America/New_York")),
                        Duration.ofMinutes(60)),
                new EventForGlobalPlanning("Client sync",
                        ZonedDateTime.of(2025,10,15,6,30,0,0, ZoneId.of("America/Los_Angeles")),
                        Duration.ofMinutes(60)),
                new EventForGlobalPlanning("Tokyo brief",
                        ZonedDateTime.of(2025,10,16,10,0,0,0, ZoneId.of("Asia/Tokyo")),
                        Duration.ofMinutes(45)),
                new EventForGlobalPlanning("London retro",
                        ZonedDateTime.of(2025,10,15,14,0,0,0, ZoneId.of("Europe/London")),
                        Duration.ofMinutes(30))
        );

        Scanner sc = new Scanner(System.in);
        ZoneId currentZone = ZoneId.systemDefault(); // start in local zone
        System.out.println("PlannerConsole — current zone: " + currentZone);
        printSchedule(events, currentZone);

        while (true) {
            System.out.println("\nCommands: zone <ZoneId>   |   list   |   check <i> <j>   |   quit");
            System.out.print("> ");
            String line = sc.nextLine().trim();
            if (line.equalsIgnoreCase("quit")) break;
            if (line.equalsIgnoreCase("list")) {
                printSchedule(events, currentZone);
                continue;
            }
            if (line.startsWith("zone ")) {
                String id = line.substring(5).trim();
                try {
                    currentZone = ZoneId.of(id);
                    System.out.println("Switched to " + currentZone);
                    printSchedule(events, currentZone);
                } catch (Exception e) {
                    System.out.println("Unknown ZoneId. Example: America/New_York, Europe/London, Asia/Tokyo");
                }
                continue;
            }
            if (line.startsWith("check ")) {
                String[] parts = line.split("\\s+");
                if (parts.length == 3) {
                    try {
                        int i = Integer.parseInt(parts[1]);
                        int j = Integer.parseInt(parts[2]);
                        var a = events.get(i);
                        var b = events.get(j);
                        System.out.println("conflicts? " + a.conflictsWith(b) + "  (checked as absolute time)");
                    } catch (Exception e) {
                        System.out.println("Usage: check <i> <j>   with valid indices from the list.");
                    }
                } else {
                    System.out.println("Usage: check <i> <j>");
                }
            }
        }
        System.out.println("Bye!");
    }

    private static void printSchedule(List<EventForGlobalPlanning> events, ZoneId zone) {
        // Sort by start instant
        List<EventForGlobalPlanning> sorted = events.stream()
                .sorted(Comparator.comparing(e -> e.getStart().toInstant()))
                .collect(Collectors.toList());

        System.out.println("\n=== Schedule in " + zone + " ===");
        for (int idx = 0; idx < sorted.size(); idx++) {
            var e = sorted.get(idx);
            var localStart = e.getStart().withZoneSameInstant(zone);
            var localEnd   = e.getEnd().withZoneSameInstant(zone);
            System.out.printf("[%d] %s  %s %s–%s  (%s)%n",
                    idx,
                    localStart.format(DATE),
                    e.getDescription(),
                    localStart.format(TIME),
                    localEnd.format(TIME),
                    e.getStart().getZone());
        }
    }
}