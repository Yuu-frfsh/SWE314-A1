import java.util.Scanner;

/**
 * Utility class for validating and sanitizing user inputs
 */
public class InputValidator {
    
    private static Scanner scanner = new Scanner(System.in);
    
    /**
     * Get a line of text input from the user
     * @param prompt The message to display to the user
     * @return The user's input as a string
     */
    public static String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    /**
     * Validate that a string is not empty
     * @param input The input string to validate
     * @param fieldName The name of the field (for error messages)
     * @return true if valid, false otherwise
     */
    public static boolean validateNotEmpty(String input, String fieldName) {
        if (input == null || input.isEmpty()) {
            System.out.println("Error: " + fieldName + " cannot be empty!");
            return false;
        }
        return true;
    }
    
    /**
     * Remove all non-alphabetic characters from a string
     * @param text The input text
     * @return Text containing only alphabetic characters
     */
    public static String removeSpecialChars(String text) {
        return text.replaceAll("[^a-zA-Z]", "");
    }
    
    /**
     * Remove all non-alphabetic and non-space characters from a string
     * @param text The input text
     * @return Text containing only alphabetic characters and spaces
     */
    public static String removeSpecialCharsKeepSpaces(String text) {
        return text.replaceAll("[^a-zA-Z ]", "");
    }
    
    /**
     * Convert string to uppercase for consistency
     * @param text The input text
     * @return Uppercase version of the text
     */
    public static String toUpperCase(String text) {
        return text.toUpperCase();
    }
    
    /**
     * Validate that a key is exactly the specified length
     * @param key The key to validate
     * @param expectedLength The expected length
     * @param fieldName The name of the field (for error messages)
     * @return true if valid, false otherwise
     */
    public static boolean validateLength(String key, int expectedLength, String fieldName) {
        if (key.length() != expectedLength) {
            System.out.println("Error: " + fieldName + " must be exactly " + expectedLength + " characters!");
            return false;
        }
        return true;
    }
    
    /**
     * Check if a string contains only alphabetic characters
     * @param text The string to check
     * @return true if alphabetic only, false otherwise
     */
    public static boolean isAlphabetic(String text) {
        return text.matches("[a-zA-Z]+");
    }
    
    /**
     * Check if a string contains any duplicate characters
     * @param text The string to check
     * @return true if duplicates found, false otherwise
     */
    public static boolean hasDuplicates(String text) {
        for (int i = 0; i < text.length(); i++) {
            for (int j = i + 1; j < text.length(); j++) {
                if (text.charAt(i) == text.charAt(j)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Get a yes/no answer from the user
     * @param prompt The question to ask
     * @return true for yes, false for no
     */
    public static boolean getYesNo(String prompt) {
        String input;
        do {
            input = getInput(prompt + " (y/n): ");
            if (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes")) {
                return true;
            } else if (input.equalsIgnoreCase("n") || input.equalsIgnoreCase("no")) {
                return false;
            }
            System.out.println("Please enter 'y' or 'n'");
        } while (true);
    }
}

