import java.util.Scanner;

/**
 * Combined Cipher Implementation
 * Combines Monoalphabetic substitution with another cipher (Vigenere, Playfair, or Transposition)
 */
public class CombinedCipher implements EncryptionAlgorithm {
    
    private static Scanner scanner = new Scanner(System.in);
    
    private EncryptionAlgorithm algorithm2;
    private String algorithm2Name;
    
    /**
     * Encrypts plaintext using combined ciphers
     * @param plaintext The text to encrypt
     * @param key The combined key (format: monoKey|secondKey)
     * @return The encrypted ciphertext
     */
    @Override
    public String encrypt(String plaintext, String key) {
        // Key format: "monoKey|secondKey"
        String[] keys = key.split("\\|");
        if (keys.length != 2) {
            return "Error: Key must be in format 'monoKey|secondKey'";
        }
        
        String monoKey = keys[0].trim();
        String secondKey = keys[1].trim();
        
        // Apply monoalphabetic first
        MonoalphabeticCipher mono = new MonoalphabeticCipher();
        String intermediate = mono.encrypt(plaintext, monoKey);
        
        // Then apply second algorithm
        String ciphertext = algorithm2.encrypt(intermediate, secondKey);
        
        return ciphertext;
    }
    
    /**
     * Decrypts ciphertext using combined ciphers
     * @param ciphertext The text to decrypt
     * @param key The combined key
     * @return The decrypted plaintext
     */
    @Override
    public String decrypt(String ciphertext, String key) {
        // Key format: "monoKey|secondKey"
        String[] keys = key.split("\\|");
        if (keys.length != 2) {
            return "Error: Key must be in format 'monoKey|secondKey'";
        }
        
        String monoKey = keys[0].trim();
        String secondKey = keys[1].trim();
        
        // Apply second algorithm decryption first
        String intermediate = algorithm2.decrypt(ciphertext, secondKey);
        
        // Then apply monoalphabetic decryption
        MonoalphabeticCipher mono = new MonoalphabeticCipher();
        String plaintext = mono.decrypt(intermediate, monoKey);
        
        return plaintext;
    }
    
    /**
     * Displays step-by-step combined encryption process
     * @param text The plaintext
     * @param key The combined key
     */
    @Override
    public void displaySteps(String text, String key) {
        String[] keys = key.split("\\|");
        if (keys.length != 2) {
            System.out.println("Error: Key must be in format 'monoKey|secondKey'");
            return;
        }
        
        String monoKey = keys[0].trim();
        String secondKey = keys[1].trim();
        
        System.out.println("\n--- Step-by-Step Combined Encryption ---");
        System.out.println("Algorithm 1: Monoalphabetic (Caesar Shift)");
        System.out.println("Algorithm 2: " + algorithm2Name);
        System.out.println("\nOriginal plaintext: " + text);
        
        // Step 1: Monoalphabetic
        MonoalphabeticCipher mono = new MonoalphabeticCipher();
        mono.displaySteps(text, monoKey);
        String step1 = mono.encrypt(text, monoKey);
        System.out.println("\nAfter Monoalphabetic: " + step1);
        
        // Step 2: Second algorithm
        System.out.println("\n--- Applying " + algorithm2Name + " ---");
        if (algorithm2 instanceof VigenereCipher) {
            ((VigenereCipher)algorithm2).displaySteps(step1, secondKey);
        } else if (algorithm2 instanceof PlayfairCipher) {
            ((PlayfairCipher)algorithm2).displaySteps(step1, secondKey);
        } else {
            ((KeyedTranspositionCipher)algorithm2).displaySteps(step1, secondKey);
        }
    }
    
    /**
     * Interactive encryption session
     */
    public void runEncryption() {
        // Choose second algorithm
        chooseSecondAlgorithm();
        
        if (algorithm2 == null) {
            return;
        }
        
        System.out.println("\n=== Combined Cipher Encryption ===");
        System.out.println("Combining: Monoalphabetic (Caesar) + " + algorithm2Name);
        
        String plaintext = InputValidator.getInput("Enter plaintext: ");
        if (!InputValidator.validateNotEmpty(plaintext, "Plaintext")) {
            return;
        }
        
        // Get monoalphabetic key (integer shift)
        String monoKey = InputValidator.getInput("Enter Monoalphabetic shift key (integer, e.g., 3 or -5): ");
        
        if (!InputValidator.validateNotEmpty(monoKey, "Monoalphabetic key")) {
            return;
        }
        
        // Validate it's an integer
        try {
            Integer.parseInt(monoKey.trim());
        } catch (NumberFormatException e) {
            System.out.println("Error: Monoalphabetic key must be an integer!");
            return;
        }
        
        // Get second algorithm key
        String secondKey;
        if (algorithm2 instanceof VigenereCipher) {
            secondKey = InputValidator.getInput("Enter Vigenere keyword: ");
            secondKey = secondKey.toUpperCase();
        } else if (algorithm2 instanceof PlayfairCipher) {
            secondKey = InputValidator.getInput("Enter Playfair keyword: ");
            secondKey = secondKey.toUpperCase();
        } else {
            // Keyed Transposition requires 2-line key format
            System.out.println("\nEnter the 2x5 permutation key:");
            System.out.println("First row (plaintext indices, e.g., 3 1 4 5 2): ");
            String firstRow = InputValidator.getInput("");
            System.out.println("Second row (ciphertext positions, e.g., 1 2 3 4 5): ");
            String secondRow = InputValidator.getInput("");
            secondKey = firstRow + "\n" + secondRow;
        }
        
        if (!InputValidator.validateNotEmpty(secondKey, "Second key")) {
            return;
        }
        
        // Combine keys
        String combinedKey = monoKey + "|" + secondKey;
        
        displaySteps(plaintext, combinedKey);
        
        String ciphertext = encrypt(plaintext, combinedKey);
        
        System.out.println("\n--- Final Result ---");
        System.out.println("Ciphertext: " + ciphertext);
    }
    
    /**
     * Choose the second algorithm to combine
     */
    private void chooseSecondAlgorithm() {
        System.out.println("\nSelect second algorithm to combine with Monoalphabetic:");
        System.out.println("  1. Vigenere");
        System.out.println("  2. Playfair");
        System.out.println("  3. Keyed Transposition");
        
        int choice = -1;
        try {
            choice = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid choice");
            return;
        }
        
        switch (choice) {
            case 1:
                algorithm2 = new VigenereCipher();
                algorithm2Name = "Vigenere Cipher";
                break;
            case 2:
                algorithm2 = new PlayfairCipher();
                algorithm2Name = "Playfair Cipher";
                break;
            case 3:
                algorithm2 = new KeyedTranspositionCipher();
                algorithm2Name = "Keyed Transposition";
                break;
            default:
                System.out.println("Invalid choice");
                algorithm2 = null;
        }
    }
}

