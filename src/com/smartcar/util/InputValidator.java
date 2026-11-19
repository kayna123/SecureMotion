package com.smartcar.util;

import java.util.Scanner;

/**
 * Utility class providing robust terminal input handling using Scanner and Wrapper classes.
 * Catches InputMismatchException / NumberFormatException to prevent console crashes.
 */
public class InputValidator {
    private static final Scanner SCANNER = new Scanner(System.in);

    // Private constructor prevents instantiation
    private InputValidator() {}

    /**
     * Reads an integer within [min, max] range.
     */
    public static int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String line = SCANNER.nextLine().trim();
            try {
                // Wrapper class parsing
                int value = Integer.parseInt(line);
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println("Invalid input! Please enter a number between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Invalid format! Please enter an integer value.");
            }
        }
    }

    /**
     * Reads a double value with a lower bound check.
     */
    public static double readDouble(String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            String line = SCANNER.nextLine().trim();
            try {
                // Wrapper class parsing
                double value = Double.parseDouble(line);
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println("Value out of bounds! Enter a number between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Invalid format! Please enter a valid decimal number.");
            }
        }
    }

    /**
     * Reads a non-empty string from terminal.
     */
    public static String readString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = SCANNER.nextLine().trim();
            if (!line.isEmpty()) {
                return line;
            }
            System.out.println("Input cannot be empty. Please try again.");
        }
    }

    /**
     * Reads a simple Yes/No confirmation.
     */
    public static boolean readBoolean(String prompt) {
        while (true) {
            System.out.print(prompt + " (y/n): ");
            String line = SCANNER.nextLine().trim().toLowerCase();
            if ("y".equals(line) || "yes".equals(line)) {
                return true;
            } else if ("n".equals(line) || "no".equals(line)) {
                return false;
            }
            System.out.println("Please enter 'y' for Yes or 'n' for No.");
        }
    }

    /**
     * Pauses and waits for user to press Enter.
     */
    public static void pressEnterToContinue() {
        System.out.print("\nPress [ENTER] to return to menu...");
        SCANNER.nextLine();
    }
}
