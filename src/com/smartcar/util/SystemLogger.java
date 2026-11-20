package com.smartcar.util;

import java.util.ArrayList;
import java.util.List;

/**
 * System event logger using Java Collections Framework (ArrayList).
 * Demonstrates Collections usage, auto-resizing lists, and audit trails.
 */
public class SystemLogger {
    private static final List<String> eventLogs = new ArrayList<>();

    private SystemLogger() {}

    /**
     * Records an informational event.
     */
    public static void log(String message) {
        String entry = "[INFO] " + message;
        eventLogs.add(entry);
    }

    /**
     * Records a warning event.
     */
    public static void logWarning(String message) {
        String entry = "[WARN] " + message;
        eventLogs.add(entry);
    }

    /**
     * Records an alert or emergency event.
     */
    public static void logAlert(String message) {
        String entry = "[ALERT] " + message;
        eventLogs.add(entry);
    }

    /**
     * Returns a copy of recent logs.
     */
    public static List<String> getRecentLogs(int count) {
        int size = eventLogs.size();
        int startIndex = Math.max(0, size - count);
        return new ArrayList<>(eventLogs.subList(startIndex, size));
    }

    /**
     * Displays recent system activity in the console.
     */
    public static void printRecentLogs() {
        System.out.println("\n--- RECENT SYSTEM LOGS ---");
        if (eventLogs.isEmpty()) {
            System.out.println("No logs recorded yet.");
            return;
        }
        List<String> recent = getRecentLogs(5);
        for (String log : recent) {
            System.out.println("  " + log);
        }
    }
}
