/**
 * Interface for all encryption algorithms in the educational tool.
 * Each algorithm must implement encryption, decryption, and step display.
 */
public interface EncryptionAlgorithm {
    
    /**
     * Encrypts plaintext using the provided key.
     * @param plaintext The text to encrypt
     * @param key The encryption key
     * @return The encrypted ciphertext
     */
    String encrypt(String plaintext, String key);
    
    /**
     * Decrypts ciphertext using the provided key.
     * @param ciphertext The text to decrypt
     * @param key The decryption key
     * @return The decrypted plaintext
     */
    String decrypt(String ciphertext, String key);
    
    /**
     * Displays the step-by-step process of encryption/decryption for educational purposes.
     * @param text The input text (plaintext or ciphertext)
     * @param key The key being used
     */
    void displaySteps(String text, String key);
}

