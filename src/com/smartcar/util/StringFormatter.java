package com.smartcar.util;

/**
 * Utility class demonstrating String manipulation, immutability, formatting, and StringBuilder.
 * Perfect for viva explanations on String vs StringBuilder performance.
 */
public class StringFormatter {

    private StringFormatter() {}

    /**
     * Creates a decorative section header box using StringBuilder.
     */
    public static String createHeader(String title) {
        int width = 50;
        StringBuilder sb = new StringBuilder();
        sb.append("+").append("-".repeat(width - 2)).append("+\n");
        
        // Centering title
        int padding = (width - 2 - title.length()) / 2;
        sb.append("|");
        for (int i = 0; i < padding; i++) {
            sb.append(" ");
        }
        sb.append(title);
        while (sb.length() % (width + 1) < width - 1) {
            sb.append(" ");
        }
        sb.append("|\n");
        sb.append("+").append("-".repeat(width - 2)).append("+");
        return sb.toString();
    }

    /**
     * Formats a key-value status row.
     */
    public static String formatRow(String label, Object value) {
        return String.format("| %-28s : %-15s |", label, value.toString());
    }

    /**
     * Formats alert messages with decorative symbols.
     */
    public static String formatWarning(String warning) {
        StringBuilder sb = new StringBuilder();
        sb.append("[!] WARNING: ").append(warning.toUpperCase());
        return sb.toString();
    }

    /**
     * Formats emergency messages.
     */
    public static String formatEmergency(String message) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n**************************************************\n");
        sb.append("*** EMERGENCY ALERT: ").append(message).append(" ***\n");
        sb.append("**************************************************\n");
        return sb.toString();
    }
}
