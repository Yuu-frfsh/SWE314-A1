/**
 * Monoalphabetic Substitution Cipher Implementation
 * Replaces each letter with another letter based on a fixed mapping
 */
public class MonoalphabeticCipher implements EncryptionAlgorithm {
    
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    
    /**
     * Encrypts plaintext using monoalphabetic substitution
     * @param plaintext The text to encrypt
     * @param key A 26-character permutation of the alphabet
     * @return The encrypted ciphertext
     */
    @Override
    public String encrypt(String plaintext, String key) {
        // Convert to uppercase for consistency
        plaintext = plaintext.toUpperCase();
        key = key.toUpperCase();
        
        StringBuilder ciphertext = new StringBuilder();
        
        for (char c : plaintext.toCharArray()) {
            if (Character.isLetter(c)) {
                // Find position in alphabet and substitute
                int index = ALPHABET.indexOf(c);
                if (index != -1) {
                    ciphertext.append(key.charAt(index));
                }
            } else {
                // Keep non-alphabetic characters as-is
                ciphertext.append(c);
            }
        }
        
        return ciphertext.toString();
    }
    
    /**
     * Decrypts ciphertext using monoalphabetic substitution
     * @param ciphertext The text to decrypt
     * @param key The 26-character substitution key
     * @return The decrypted plaintext
     */
    @Override
    public String decrypt(String ciphertext, String key) {
        // Convert to uppercase for consistency
        ciphertext = ciphertext.toUpperCase();
        key = key.toUpperCase();
        
        StringBuilder plaintext = new StringBuilder();
        
        for (char c : ciphertext.toCharArray()) {
            if (Character.isLetter(c)) {
                // Find position in key and map back to alphabet
                int index = key.indexOf(c);
                if (index != -1) {
                    plaintext.append(ALPHABET.charAt(index));
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
     * @param key The substitution key
     */
    @Override
    public void displaySteps(String text, String key) {
        text = text.toUpperCase();
        key = key.toUpperCase();
        
        System.out.println("\n--- Step-by-Step Encryption ---");
        System.out.println("Alphabet:  " + ALPHABET);
        System.out.println("Key:       " + key);
        System.out.println("\nPlaintext: " + text);
        System.out.println("\nCharacter Transformations:");
        
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                int index = ALPHABET.indexOf(c);
                if (index != -1) {
                    System.out.println("  " + c + " -> " + key.charAt(index) + " (position " + index + ")");
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
        String key = InputValidator.getInput("Enter 26-character substitution key: ");
        key = key.toUpperCase();
        
        if (!InputValidator.validateNotEmpty(key, "Key")) {
            return;
        }
        
        if (!InputValidator.validateLength(key, 26, "Key")) {
            return;
        }
        
        if (!InputValidator.isAlphabetic(key)) {
            System.out.println("Error: Key must contain only alphabetic characters!");
            return;
        }
        
        if (InputValidator.hasDuplicates(key)) {
            System.out.println("Error: Key must not contain duplicate characters!");
            return;
        }
        
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
        // Convert to uppercase
        ciphertext = ciphertext.toUpperCase();
        key = key.toUpperCase();
        
        // Validate key
        if (!InputValidator.validateNotEmpty(key, "Key")) {
            return;
        }
        
        if (!InputValidator.validateLength(key, 26, "Key")) {
            return;
        }
        
        if (!InputValidator.isAlphabetic(key)) {
            System.out.println("Error: Key must contain only alphabetic characters!");
            return;
        }
        
        if (InputValidator.hasDuplicates(key)) {
            System.out.println("Error: Key must not contain duplicate characters!");
            return;
        }
        
        // Display decryption process
        System.out.println("\n--- Step-by-Step Decryption ---");
        System.out.println("Alphabet:   " + ALPHABET);
        System.out.println("Key:        " + key);
        System.out.println("Ciphertext: " + ciphertext);
        System.out.println("\nCharacter Transformations:");
        
        for (char c : ciphertext.toCharArray()) {
            if (Character.isLetter(c)) {
                int index = key.indexOf(c);
                if (index != -1) {
                    System.out.println("  " + c + " -> " + ALPHABET.charAt(index) + " (position " + index + ")");
                } else {
                    System.out.println("  " + c + " -> " + c + " (not in key)");
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

