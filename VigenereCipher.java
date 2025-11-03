/**
 * Vigenere Cipher Implementation
 * Uses a keyword to shift each letter by the position of the keyword letter
 */
public class VigenereCipher implements EncryptionAlgorithm {
    
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    
    /**
     * Encrypts plaintext using Vigenere cipher
     * @param plaintext The text to encrypt
     * @param key The keyword for shifting
     * @return The encrypted ciphertext
     */
    @Override
    public String encrypt(String plaintext, String key) {
        plaintext = plaintext.toUpperCase();
        key = key.toUpperCase();
        
        // Handle empty key
        if (key.isEmpty()) {
            return plaintext;
        }
        
        StringBuilder ciphertext = new StringBuilder();
        int keyIndex = 0;
        
        for (char c : plaintext.toCharArray()) {
            if (Character.isLetter(c)) {
                // Get plaintext letter position
                int plaintextPos = ALPHABET.indexOf(c);
                
                // Get key letter position
                char keyChar = key.charAt(keyIndex % key.length());
                int keyPos = ALPHABET.indexOf(keyChar);
                
                // Shift by key position (mod 26)
                int ciphertextPos = (plaintextPos + keyPos) % 26;
                ciphertext.append(ALPHABET.charAt(ciphertextPos));
                
                keyIndex++; // Move to next key letter
            } else {
                ciphertext.append(c); // Keep non-letters as-is
            }
        }
        
        return ciphertext.toString();
    }
    
    /**
     * Decrypts ciphertext using Vigenere cipher
     * @param ciphertext The text to decrypt
     * @param key The keyword used for encryption
     * @return The decrypted plaintext
     */
    @Override
    public String decrypt(String ciphertext, String key) {
        ciphertext = ciphertext.toUpperCase();
        key = key.toUpperCase();
        
        // Handle empty key
        if (key.isEmpty()) {
            return ciphertext;
        }
        
        StringBuilder plaintext = new StringBuilder();
        int keyIndex = 0;
        
        for (char c : ciphertext.toCharArray()) {
            if (Character.isLetter(c)) {
                // Get ciphertext letter position
                int ciphertextPos = ALPHABET.indexOf(c);
                
                // Get key letter position
                char keyChar = key.charAt(keyIndex % key.length());
                int keyPos = ALPHABET.indexOf(keyChar);
                
                // Reverse shift (add 26 to handle negative)
                int plaintextPos = (ciphertextPos - keyPos + 26) % 26;
                plaintext.append(ALPHABET.charAt(plaintextPos));
                
                keyIndex++; // Move to next key letter
            } else {
                plaintext.append(c); // Keep non-letters as-is
            }
        }
        
        return plaintext.toString();
    }
    
    /**
     * Displays step-by-step Vigenere encryption process
     * @param text The plaintext
     * @param key The keyword
     */
    @Override
    public void displaySteps(String text, String key) {
        text = text.toUpperCase();
        key = key.toUpperCase();
        
        System.out.println("\n--- Step-by-Step Vigenere Encryption ---");
        System.out.println("Alphabet:  " + ALPHABET);
        System.out.println("Keyword:   " + key);
        System.out.println("\nPlaintext: " + text);
        System.out.println("\nCharacter Transformations:");
        
        int keyIndex = 0;
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                char keyChar = key.charAt(keyIndex % key.length());
                int plaintextPos = ALPHABET.indexOf(c);
                int keyPos = ALPHABET.indexOf(keyChar);
                int ciphertextPos = (plaintextPos + keyPos) % 26;
                
                System.out.println(String.format("  %c + %c = %c (pos %d + pos %d = pos %d)", 
                    c, keyChar, ALPHABET.charAt(ciphertextPos), plaintextPos, keyPos, ciphertextPos));
                
                keyIndex++;
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
        String key = InputValidator.getInput("Enter keyword: ");
        key = key.toUpperCase();
        
        if (!InputValidator.validateNotEmpty(key, "Keyword")) {
            return;
        }
        
        if (!InputValidator.isAlphabetic(key)) {
            System.out.println("Error: Keyword must contain only alphabetic characters!");
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
        if (!InputValidator.validateNotEmpty(key, "Keyword")) {
            return;
        }
        
        if (!InputValidator.isAlphabetic(key)) {
            System.out.println("Error: Keyword must contain only alphabetic characters!");
            return;
        }
        
        // Display decryption process
        System.out.println("\n--- Step-by-Step Vigenere Decryption ---");
        System.out.println("Alphabet:   " + ALPHABET);
        System.out.println("Keyword:    " + key);
        System.out.println("Ciphertext: " + ciphertext);
        System.out.println("\nCharacter Transformations:");
        
        int keyIndex = 0;
        for (char c : ciphertext.toCharArray()) {
            if (Character.isLetter(c)) {
                char keyChar = key.charAt(keyIndex % key.length());
                int ciphertextPos = ALPHABET.indexOf(c);
                int keyPos = ALPHABET.indexOf(keyChar);
                int plaintextPos = (ciphertextPos - keyPos + 26) % 26;
                
                System.out.println(String.format("  %c - %c = %c (pos %d - pos %d = pos %d)", 
                    c, keyChar, ALPHABET.charAt(plaintextPos), ciphertextPos, keyPos, plaintextPos));
                
                keyIndex++;
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

