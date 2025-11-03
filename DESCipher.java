/**
 * DES Cipher Implementation (Simplified Educational Version)
 * Implements a simplified version of DES for educational purposes
 * Note: This is NOT secure and should not be used for actual encryption
 */
public class DESCipher implements EncryptionAlgorithm {
    
    // Simplified S-boxes (educational purpose only)
    private static final int[][] S1 = {
        {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
        {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
        {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
        {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}
    };
    
    // Simplified initial permutation
    private static final int[] IP = {
        58, 50, 42, 34, 26, 18, 10, 2, 60, 52, 44, 36, 28, 20, 12, 4,
        62, 54, 46, 38, 30, 22, 14, 6, 64, 56, 48, 40, 32, 24, 16, 8,
        57, 49, 41, 33, 25, 17, 9, 1, 59, 51, 43, 35, 27, 19, 11, 3,
        61, 53, 45, 37, 29, 21, 13, 5, 63, 55, 47, 39, 31, 23, 15, 7
    };
    
    // Simplified final permutation
    private static final int[] FP = {
        40, 8, 48, 16, 56, 24, 64, 32, 39, 7, 47, 15, 55, 23, 63, 31,
        38, 6, 46, 14, 54, 22, 62, 30, 37, 5, 45, 13, 53, 21, 61, 29,
        36, 4, 44, 12, 52, 20, 60, 28, 35, 3, 43, 11, 51, 19, 59, 27,
        34, 2, 42, 10, 50, 18, 58, 26, 33, 1, 41, 9, 49, 17, 57, 25
    };
    
    /**
     * Encrypts plaintext using simplified DES
     * @param plaintext The 8-byte plaintext
     * @param key The 8-byte key
     * @return The encrypted ciphertext
     */
    @Override
    public String encrypt(String plaintext, String key) {
        // Simplified: just XOR with key for educational purposes
        // Real DES is much more complex with 16 rounds
        
        if (plaintext == null || key == null || plaintext.length() > 8 || key.length() != 8) {
            return "Error: DES requires exactly 8-byte key and up to 8-byte plaintext";
        }
        
        // Pad plaintext to 8 bytes if needed
        StringBuilder padded = new StringBuilder(plaintext);
        while (padded.length() < 8) {
            padded.append(' ');
        }
        
        plaintext = padded.toString();
        
        // Simple XOR cipher for educational demonstration
        StringBuilder ciphertext = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            char plainChar = plaintext.charAt(i);
            char keyChar = key.charAt(i % key.length());
            char cipherChar = (char)(plainChar ^ keyChar);
            ciphertext.append(String.format("%02X", (int)cipherChar & 0xFF));
        }
        
        return ciphertext.toString();
    }
    
    /**
     * Decrypts ciphertext using simplified DES
     * @param ciphertext The encrypted text (hex format)
     * @param key The 8-byte key
     * @return The decrypted plaintext
     */
    @Override
    public String decrypt(String ciphertext, String key) {
        if (key == null || key.length() != 8) {
            return "Error: DES requires exactly 8-byte key";
        }
        
        try {
            // Convert hex string to bytes
            byte[] cipherBytes = new byte[8];
            for (int i = 0; i < 8; i++) {
                if (i * 2 + 1 < ciphertext.length()) {
                    String hex = ciphertext.substring(i * 2, i * 2 + 2);
                    cipherBytes[i] = (byte)Integer.parseInt(hex, 16);
                }
            }
            
            // Simple XOR decryption
            StringBuilder plaintext = new StringBuilder();
            for (int i = 0; i < 8; i++) {
                char cipherChar = (char)(cipherBytes[i] & 0xFF);
                char keyChar = key.charAt(i % key.length());
                char plainChar = (char)(cipherChar ^ keyChar);
                plaintext.append(plainChar);
            }
            
            return plaintext.toString().trim(); // Remove padding
        } catch (Exception e) {
            return "Error: Invalid ciphertext format";
        }
    }
    
    /**
     * Displays step-by-step DES encryption process
     * @param text The plaintext
     * @param key The key
     */
    @Override
    public void displaySteps(String text, String key) {
        System.out.println("\n--- Step-by-Step Simplified DES Encryption ---");
        System.out.println("NOTE: This is a simplified educational version, not secure DES");
        System.out.println("\nPlaintext: " + text);
        System.out.println("Key: " + key);
        
        // Pad if needed
        StringBuilder padded = new StringBuilder(text);
        while (padded.length() < 8) {
            padded.append(' ');
        }
        text = padded.toString();
        
        System.out.println("\nCharacter-by-character XOR with key:");
        for (int i = 0; i < Math.min(8, text.length()); i++) {
            char plainChar = text.charAt(i);
            char keyChar = key.charAt(i % key.length());
            char cipherChar = (char)(plainChar ^ keyChar);
            System.out.println(String.format("  %c (%d) XOR %c (%d) = %c (%d)", 
                plainChar, (int)plainChar, 
                keyChar, (int)keyChar, 
                cipherChar, (int)cipherChar));
        }
    }
    
    /**
     * Interactive encryption session
     */
    public void runEncryption() {
        System.out.println("\n=== Simplified DES Cipher ===");
        System.out.println("WARNING: This is an educational simplified version, NOT secure DES!");
        
        String plaintext = InputValidator.getInput("Enter plaintext (up to 8 characters): ");
        if (!InputValidator.validateNotEmpty(plaintext, "Plaintext")) {
            return;
        }
        
        String key = InputValidator.getInput("Enter 8-character key: ");
        if (!InputValidator.validateNotEmpty(key, "Key")) {
            return;
        }
        
        if (!InputValidator.validateLength(key, 8, "Key")) {
            return;
        }
        
        displaySteps(plaintext, key);
        
        String ciphertext = encrypt(plaintext, key);
        
        System.out.println("\n--- Result ---");
        System.out.println("Ciphertext (hex): " + ciphertext);
    }
}

