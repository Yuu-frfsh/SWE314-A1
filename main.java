import java.util.Scanner;

/**
 * Main entry point for the Educational Encryption/Decryption Tool
 * Provides menu-driven interface for users to select and use different algorithms
 */
public class main {
    
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        printWelcome();
        
        boolean running = true;
        while (running) {
            printMenu();
            int choice = getChoice();
            
            switch (choice) {
                case 1:
                    runMonoalphabeticEncryption();
                    break;
                case 2:
                    runVigenereEncryption();
                    break;
                case 3:
                    runPlayfairEncryption();
                    break;
                case 4:
                    runKeyedTranspositionEncryption();
                    break;
                case 5:
                    runCombinedEncryption();
                    break;
                case 6:
                    runDESEncryption();
                    break;
                case 7:
                    runDecryption();
                    break;
                case 8:
                    runCryptanalysis();
                    break;
                case 0:
                    running = false;
                    System.out.println("\nThank you for using the Educational Encryption Tool!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        
        scanner.close();
    }
    
    /**
     * Print welcome message
     */
    private static void printWelcome() {
        System.out.println("\n=========================================");
        System.out.println("  Educational Encryption Tool");
        System.out.println("  SWE 314 - Software Security Engineering");
        System.out.println("=========================================\n");
        System.out.println("Welcome! This tool helps you learn encryption/decryption algorithms.");
        System.out.println("Each operation will show you step-by-step how the algorithm works.\n");
    }
    
    /**
     * Print main menu options
     */
    private static void printMenu() {
        System.out.println("\n----- Main Menu -----");
        System.out.println("ENCRYPTION:");
        System.out.println("  1. Monoalphabetic Substitution");
        System.out.println("  2. Vigenere Cipher");
        System.out.println("  3. Playfair Cipher");
        System.out.println("  4. Keyed Transposition");
        System.out.println("  5. Combined Encryption");
        System.out.println("  6. DES");
        System.out.println("\nDECRYPTION:");
        System.out.println("  7. Decrypt Text");
        System.out.println("\nCRYPTANALYSIS:");
        System.out.println("  8. Frequency Analysis");
        System.out.println("\n  0. Exit");
        System.out.print("\nEnter your choice: ");
    }
    
    /**
     * Get user's menu choice
     * @return The choice as an integer
     */
    private static int getChoice() {
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            return choice;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    /**
     * Run Monoalphabetic encryption
     */
    private static void runMonoalphabeticEncryption() {
        System.out.println("\n=== Monoalphabetic Substitution Cipher ===");
        MonoalphabeticCipher cipher = new MonoalphabeticCipher();
        cipher.runEncryption();
    }
    
    /**
     * Run Vigenere encryption
     */
    private static void runVigenereEncryption() {
        System.out.println("\n=== Vigenere Cipher ===");
        VigenereCipher cipher = new VigenereCipher();
        cipher.runEncryption();
    }
    
    /**
     * Run Playfair encryption
     */
    private static void runPlayfairEncryption() {
        System.out.println("\n=== Playfair Cipher ===");
        PlayfairCipher cipher = new PlayfairCipher();
        cipher.runEncryption();
    }
    
    /**
     * Run Keyed Transposition encryption
     */
    private static void runKeyedTranspositionEncryption() {
        System.out.println("\n=== Keyed Transposition Cipher ===");
        KeyedTranspositionCipher cipher = new KeyedTranspositionCipher();
        cipher.runEncryption();
    }
    
    /**
     * Run Combined encryption
     */
    private static void runCombinedEncryption() {
        System.out.println("\n=== Combined Encryption ===");
        CombinedCipher cipher = new CombinedCipher();
        cipher.runEncryption();
    }
    
    /**
     * Run DES encryption
     */
    private static void runDESEncryption() {
        System.out.println("\n=== DES Cipher ===");
        DESCipher cipher = new DESCipher();
        cipher.runEncryption();
    }
    
    /**
     * Run decryption menu
     */
    private static void runDecryption() {
        System.out.println("\n=== Decryption ===");
        System.out.println("Select algorithm to decrypt:");
        System.out.println("  1. Monoalphabetic Substitution");
        System.out.println("  2. Keyed Transposition");
        System.out.print("Enter your choice: ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            String ciphertext = InputValidator.getInput("Enter ciphertext: ");
            
            String key;
            if (choice == 2) {
                // Keyed Transposition requires 2-line key format
                System.out.println("\nEnter the 2x5 permutation key:");
                System.out.println("First row (plaintext indices, e.g., 3 1 4 5 2): ");
                String firstRow = InputValidator.getInput("");
                System.out.println("Second row (ciphertext positions, e.g., 1 2 3 4 5): ");
                String secondRow = InputValidator.getInput("");
                key = firstRow + "\n" + secondRow;
            } else {
                key = InputValidator.getInput("Enter key: ");
            }
            
            switch (choice) {
                case 1:
                    MonoalphabeticCipher cipher1 = new MonoalphabeticCipher();
                    cipher1.runDecryption(ciphertext, key);
                    break;
                case 2:
                    KeyedTranspositionCipher cipher2 = new KeyedTranspositionCipher();
                    cipher2.runDecryption(ciphertext, key);
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }
    
    /**
     * Run cryptanalysis (Frequency Analysis)
     */
    private static void runCryptanalysis() {
        System.out.println("\n=== Frequency Analysis ===");
        FrequencyAnalysis analyzer = new FrequencyAnalysis();
        analyzer.runAnalysis();
    }
}
