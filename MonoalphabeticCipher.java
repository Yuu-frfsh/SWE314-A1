/**
 * Monoalphabetic Substitution Cipher Implementation (Caesar Cipher)
 * Shifts each letter by a fixed number of positions in the alphabet
 */
public class MonoalphabeticCipher implements EncryptionAlgorithm {
    
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    
    /**
     * Normalizes shift value to handle values beyond ±26
     * @param shift The shift value
     * @return Normalized shift value in range [-25, 25]
     */
    private int normalizeShift(int shift) {
        // Handle positive shifts > 26
        while (shift > 25) {
            shift -= 26;
        }
        // Handle negative shifts < -26
        while (shift < -25) {
            shift += 26;
        }
        return shift;
    }
    
    /**
     * Encrypts plaintext using Caesar cipher shift
     * @param plaintext The text to encrypt
     * @param key An integer shift value (string format)
     * @return The encrypted ciphertext
     */
    @Override
    public String encrypt(String plaintext, String key) {
        // Parse integer shift from key string
        int shift;
        try {
            shift = Integer.parseInt(key.trim());
        } catch (NumberFormatException e) {
            return "Error: Invalid key format. Expected an integer.";
        }
        
        // Normalize shift
        shift = normalizeShift(shift);
        
        // Convert to uppercase for consistency
        plaintext = plaintext.toUpperCase();
        
        StringBuilder ciphertext = new StringBuilder();
        
        for (char c : plaintext.toCharArray()) {
            if (Character.isLetter(c)) {
                // Find position in alphabet and apply shift
                int index = ALPHABET.indexOf(c);
                if (index != -1) {
                    int shiftedIndex = (index + shift + 26) % 26;
                    ciphertext.append(ALPHABET.charAt(shiftedIndex));
                }
            } else {
                // Keep non-alphabetic characters as-is
                ciphertext.append(c);
            }
        }
        
        return ciphertext.toString();
    }
    
    /**
     * Decrypts ciphertext using Caesar cipher shift
     * @param ciphertext The text to decrypt
     * @param key An integer shift value (string format)
     * @return The decrypted plaintext
     */
    @Override
    public String decrypt(String ciphertext, String key) {
        // Parse integer shift from key string
        int shift;
        try {
            shift = Integer.parseInt(key.trim());
        } catch (NumberFormatException e) {
            return "Error: Invalid key format. Expected an integer.";
        }
        
        // Normalize shift
        shift = normalizeShift(shift);
        
        // Convert to uppercase for consistency
        ciphertext = ciphertext.toUpperCase();
        
        StringBuilder plaintext = new StringBuilder();
        
        for (char c : ciphertext.toCharArray()) {
            if (Character.isLetter(c)) {
                // Find position in alphabet and apply reverse shift
                int index = ALPHABET.indexOf(c);
                if (index != -1) {
                    int shiftedIndex = (index - shift + 26) % 26;
                    plaintext.append(ALPHABET.charAt(shiftedIndex));
                }
            } else {
                // Keep non-alphabetic characters as-is
                plaintext.append(c);
            }
        }
        
        return plaintext.toString();
    }
    
    /**
     * Displays step-by-step encryption process
     * @param text The plaintext
     * @param key The shift key (integer as string)
     */
    @Override
    public void displaySteps(String text, String key) {
        // Parse shift
        int shift;
        try {
            shift = Integer.parseInt(key.trim());
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid key format. Expected an integer.");
            return;
        }
        
        // Normalize shift
        int originalShift = shift;
        shift = normalizeShift(shift);
        
        text = text.toUpperCase();
        
        System.out.println("\n--- Step-by-Step Monoalphabetic Cipher Encryption ---");
        System.out.println("Shift value: " + originalShift + (originalShift != shift ? " (normalized to " + shift + ")" : ""));
        System.out.println("Alphabet:    " + ALPHABET);
        System.out.println("Plaintext:   " + text);
        System.out.println("\nCharacter Transformations:");
        
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                int index = ALPHABET.indexOf(c);
                if (index != -1) {
                    int shiftedIndex = (index + shift + 26) % 26;
                    char encryptedChar = ALPHABET.charAt(shiftedIndex);
                    System.out.println("  " + c + " (pos " + index + ") + " + shift + " -> " + 
                                      encryptedChar + " (pos " + shiftedIndex + ")");
                } else {
                    System.out.println("  " + c + " -> " + c + " (not in alphabet)");
                }
            } else {
                System.out.println("  " + c + " -> " + c + " (non-letter, kept as-is)");
            }
        }
    }
    
    /**
     * Interactive encryption session
     */
    public void runEncryption() {
        // Get plaintext
        String plaintext = InputValidator.getInput("Enter plaintext: ");
        if (!InputValidator.validateNotEmpty(plaintext, "Plaintext")) {
            return;
        }
        
        // Get and validate key
        String keyInput = InputValidator.getInput("Enter shift key (integer, e.g., 3 or -5): ");
        int shift;
        try {
            shift = Integer.parseInt(keyInput.trim());
            System.out.println("Shift normalized from " + shift + " to " + normalizeShift(shift));
        } catch (NumberFormatException e) {
            System.out.println("Error: Key must be an integer!");
            return;
        }
        
        String key = keyInput.trim();
        
        // Display steps
        displaySteps(plaintext, key);
        
        // Perform encryption
        String ciphertext = encrypt(plaintext, key);
        
        // Display result
        System.out.println("\n--- Result ---");
        System.out.println("Ciphertext: " + ciphertext);
    }
    
    /**
     * Interactive decryption session
     */
    public void runDecryption(String ciphertext, String key) {
        // Parse and validate key
        int shift;
        try {
            shift = Integer.parseInt(key.trim());
        } catch (NumberFormatException e) {
            System.out.println("Error: Key must be an integer!");
            return;
        }
        
        // Normalize shift
        int originalShift = shift;
        shift = normalizeShift(shift);
        if (originalShift != shift) {
            System.out.println("Shift normalized from " + originalShift + " to " + shift);
        }
        
        // Convert to uppercase
        ciphertext = ciphertext.toUpperCase();
        
        // Display decryption process
        System.out.println("\n--- Step-by-Step Monoalphabetic Cipher Decryption ---");
        System.out.println("Shift value: " + shift);
        System.out.println("Alphabet:    " + ALPHABET);
        System.out.println("Ciphertext:  " + ciphertext);
        System.out.println("\nCharacter Transformations:");
        
        for (char c : ciphertext.toCharArray()) {
            if (Character.isLetter(c)) {
                int index = ALPHABET.indexOf(c);
                if (index != -1) {
                    int shiftedIndex = (index - shift + 26) % 26;
                    char decryptedChar = ALPHABET.charAt(shiftedIndex);
                    System.out.println("  " + c + " (pos " + index + ") - " + shift + " -> " + 
                                      decryptedChar + " (pos " + shiftedIndex + ")");
                } else {
                    System.out.println("  " + c + " -> " + c + " (not in alphabet)");
                }
            } else {
                System.out.println("  " + c + " -> " + c + " (non-letter, kept as-is)");
            }
        }
        
        // Perform decryption
        String plaintext = decrypt(ciphertext, key);
        
        // Display result
        System.out.println("\n--- Result ---");
        System.out.println("Plaintext: " + plaintext);
    }
}
