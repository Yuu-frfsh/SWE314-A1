/**
 * Keyed Transposition Cipher Implementation
 * Uses a 2x5 permutation matrix where the first row represents plaintext indices
 * and the second row represents ciphertext positions (both 1-indexed)
 */
public class KeyedTranspositionCipher implements EncryptionAlgorithm {
    
    /**
     * Encrypts plaintext using keyed transposition with 2x5 matrix
     * @param plaintext The text to encrypt
     * @param key The key in format "plaintextIndices\nciphertextPositions" (two lines)
     * @return The encrypted ciphertext
     */
    @Override
    public String encrypt(String plaintext, String key) {
        // Parse key into two rows
        String[] keyLines = key.trim().split("\n");
        if (keyLines.length < 2) {
            keyLines = key.trim().split("\\|");
        }
        if (keyLines.length < 2) {
            return plaintext; // Invalid key format
        }
        
        // Parse first row: plaintext indices
        String[] plaintextIndicesStr = keyLines[0].trim().split("\\s+");
        // Parse second row: ciphertext positions
        String[] ciphertextPositionsStr = keyLines[1].trim().split("\\s+");
        
        if (plaintextIndicesStr.length != 5 || ciphertextPositionsStr.length != 5) {
            return plaintext; // Must be exactly 5 values in each row
        }
        
        int[] plaintextIndices = new int[5];
        int[] ciphertextPositions = new int[5];
        
        try {
            for (int i = 0; i < 5; i++) {
                plaintextIndices[i] = Integer.parseInt(plaintextIndicesStr[i]);
                ciphertextPositions[i] = Integer.parseInt(ciphertextPositionsStr[i]);
            }
        } catch (NumberFormatException e) {
            return plaintext; // Invalid key format
        }
        
        // Convert to lowercase and remove spaces
        String cleaned = plaintext.toLowerCase().replaceAll(" ", "");
        
        if (cleaned.length() == 0) {
            return plaintext;
        }
        
        // Pad to length divisible by 5 using 'z'
        while (cleaned.length() % 5 != 0) {
            cleaned += 'z';
        }
        
        // Process in blocks of 5
        StringBuilder ciphertext = new StringBuilder();
        int blockCount = cleaned.length() / 5;
        
        for (int block = 0; block < blockCount; block++) {
            String currentBlock = cleaned.substring(block * 5, (block + 1) * 5);
            
            // Apply permutation: character at plaintext index i goes to ciphertext position j
            char[] cipherBlock = new char[5];
            for (int i = 0; i < 5; i++) {
                // plaintextIndices[i] is 1-indexed, convert to 0-indexed
                int plainIndex = plaintextIndices[i] - 1;
                // ciphertextPositions[i] is 1-indexed, convert to 0-indexed
                int cipherPos = ciphertextPositions[i] - 1;
                
                // Character from plaintext index goes to ciphertext position
                if (plainIndex >= 0 && plainIndex < 5 && cipherPos >= 0 && cipherPos < 5) {
                    cipherBlock[cipherPos] = currentBlock.charAt(plainIndex);
                }
            }
            
            // Convert block to uppercase and append
            for (char c : cipherBlock) {
                ciphertext.append(Character.toUpperCase(c));
            }
        }
        
        return ciphertext.toString();
    }
    
    /**
     * Decrypts ciphertext using keyed transposition with 2x5 matrix
     * @param ciphertext The text to decrypt
     * @param key The key in format "plaintextIndices\nciphertextPositions" (two lines)
     * @return The decrypted plaintext
     */
    @Override
    public String decrypt(String ciphertext, String key) {
        // Parse key into two rows
        String[] keyLines = key.trim().split("\n");
        if (keyLines.length < 2) {
            keyLines = key.trim().split("\\|");
        }
        if (keyLines.length < 2) {
            return ciphertext; // Invalid key format
        }
        
        // Parse first row: plaintext indices
        String[] plaintextIndicesStr = keyLines[0].trim().split("\\s+");
        // Parse second row: ciphertext positions
        String[] ciphertextPositionsStr = keyLines[1].trim().split("\\s+");
        
        if (plaintextIndicesStr.length != 5 || ciphertextPositionsStr.length != 5) {
            return ciphertext; // Must be exactly 5 values in each row
        }
        
        int[] plaintextIndices = new int[5];
        int[] ciphertextPositions = new int[5];
        
        try {
            for (int i = 0; i < 5; i++) {
                plaintextIndices[i] = Integer.parseInt(plaintextIndicesStr[i]);
                ciphertextPositions[i] = Integer.parseInt(ciphertextPositionsStr[i]);
            }
        } catch (NumberFormatException e) {
            return ciphertext; // Invalid key format
        }
        
        // Convert to lowercase
        String cleaned = ciphertext.toLowerCase().replaceAll(" ", "");
        
        if (cleaned.length() == 0) {
            return ciphertext;
        }
        
        // Process in blocks of 5
        StringBuilder plaintext = new StringBuilder();
        int blockCount = cleaned.length() / 5;
        
        for (int block = 0; block < blockCount; block++) {
            String currentBlock = cleaned.substring(block * 5, (block + 1) * 5);
            
            // Reverse permutation: character at ciphertext position j came from plaintext index i
            char[] plainBlock = new char[5];
            for (int i = 0; i < 5; i++) {
                // Find which ciphertext position corresponds to this plaintext index
                int plainIndex = plaintextIndices[i] - 1; // Convert to 0-indexed
                int cipherPos = ciphertextPositions[i] - 1; // Convert to 0-indexed
                
                // Character at ciphertext position came from plaintext index
                if (plainIndex >= 0 && plainIndex < 5 && cipherPos >= 0 && cipherPos < 5) {
                    plainBlock[plainIndex] = currentBlock.charAt(cipherPos);
                }
            }
            
            plaintext.append(new String(plainBlock));
        }
        
        // Remove padding 'z' at the end
        String result = plaintext.toString();
        while (result.endsWith("z")) {
            result = result.substring(0, result.length() - 1);
        }
        
        return result;
    }
    
    /**
     * Displays step-by-step transposition process
     * @param text The plaintext
     * @param key The key in format "plaintextIndices\nciphertextPositions"
     */
    @Override
    public void displaySteps(String text, String key) {
        // Parse key into two rows
        String[] keyLines = key.trim().split("\n");
        if (keyLines.length < 2) {
            keyLines = key.trim().split("\\|");
        }
        if (keyLines.length < 2) {
            System.out.println("Invalid key format! Expected two lines.");
            return;
        }
        
        String[] plaintextIndicesStr = keyLines[0].trim().split("\\s+");
        String[] ciphertextPositionsStr = keyLines[1].trim().split("\\s+");
        
        if (plaintextIndicesStr.length != 5 || ciphertextPositionsStr.length != 5) {
            System.out.println("Invalid key! Must have exactly 5 values in each row.");
            return;
        }
        
        System.out.println("\n--- Step-by-Step Keyed Transposition Encryption ---");
        System.out.println("Plaintext: " + text);
        System.out.println("\nPermutation Key (2x5 matrix):");
        System.out.println("  Plaintext indices:    " + keyLines[0].trim());
        System.out.println("  Ciphertext positions: " + keyLines[1].trim());
        
        // Convert to lowercase and remove spaces
        String cleaned = text.toLowerCase().replaceAll(" ", "");
        
        // Pad to length divisible by 5
        String originalCleaned = cleaned;
        while (cleaned.length() % 5 != 0) {
            cleaned += 'z';
        }
        
        if (!originalCleaned.equals(cleaned)) {
            System.out.println("\nPadded message (to make length divisible by 5): " + cleaned);
        }
        
        int blockCount = cleaned.length() / 5;
        System.out.println("\nProcessing in " + blockCount + " block(s) of 5 characters:");
        
        // Show transformation for each block
        for (int block = 0; block < blockCount; block++) {
            String currentBlock = cleaned.substring(block * 5, (block + 1) * 5);
            System.out.println("\nBlock " + (block + 1) + ": " + currentBlock.toUpperCase());
            System.out.println("  Plaintext positions (1-5):");
            for (int i = 0; i < 5; i++) {
                System.out.println("    Position " + (i + 1) + ": " + Character.toUpperCase(currentBlock.charAt(i)));
            }
            
            System.out.println("\n  Applying permutation:");
            for (int i = 0; i < 5; i++) {
                int plainIndex = Integer.parseInt(plaintextIndicesStr[i]) - 1;
                int cipherPos = Integer.parseInt(ciphertextPositionsStr[i]) - 1;
                if (plainIndex >= 0 && plainIndex < 5 && cipherPos >= 0 && cipherPos < 5) {
                    System.out.println("    Plaintext index " + plaintextIndicesStr[i] + " ('" + 
                                     Character.toUpperCase(currentBlock.charAt(plainIndex)) + 
                                     "') → Ciphertext position " + ciphertextPositionsStr[i]);
                }
            }
        }
    }
    
    /**
     * Interactive encryption session
     */
    public void runEncryption() {
        String plaintext = InputValidator.getInput("Enter plaintext: ");
        if (!InputValidator.validateNotEmpty(plaintext, "Plaintext")) {
            return;
        }
        
        System.out.println("\nEnter the 2x5 permutation key:");
        System.out.println("First row (plaintext indices, e.g., 3 1 4 5 2): ");
        String firstRow = InputValidator.getInput("");
        
        if (!InputValidator.validateNotEmpty(firstRow, "First row")) {
            return;
        }
        
        System.out.println("Second row (ciphertext positions, e.g., 1 2 3 4 5): ");
        String secondRow = InputValidator.getInput("");
        
        if (!InputValidator.validateNotEmpty(secondRow, "Second row")) {
            return;
        }
        
        String key = firstRow + "\n" + secondRow;
        
        displaySteps(plaintext, key);
        
        String ciphertext = encrypt(plaintext, key);
        
        System.out.println("\n--- Result ---");
        System.out.println("Ciphertext: " + ciphertext);
    }
    
    /**
     * Interactive decryption session
     */
    public void runDecryption(String ciphertext, String key) {
        ciphertext = ciphertext.toUpperCase();
        
        if (!InputValidator.validateNotEmpty(key, "Key")) {
            return;
        }
        
        System.out.println("\n--- Step-by-Step Keyed Transposition Decryption ---");
        System.out.println("Ciphertext: " + ciphertext);
        
        // Parse and display key
        String[] keyLines = key.trim().split("\n");
        if (keyLines.length < 2) {
            keyLines = key.trim().split("\\|");
        }
        if (keyLines.length >= 2) {
            System.out.println("Permutation Key (2x5 matrix):");
            System.out.println("  Plaintext indices:    " + keyLines[0].trim());
            System.out.println("  Ciphertext positions: " + keyLines[1].trim());
        } else {
            System.out.println("Key: " + key);
        }
        
        System.out.println("\nReversing permutation...");
        
        String plaintext = decrypt(ciphertext, key);
        
        System.out.println("\n--- Result ---");
        System.out.println("Plaintext: " + plaintext);
    }
}

