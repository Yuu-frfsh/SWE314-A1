import java.util.Arrays;

/**
 * Keyed Transposition Cipher Implementation
 * Arranges plaintext in rows and reads columns based on key order
 */
public class KeyedTranspositionCipher implements EncryptionAlgorithm {
    
    /**
     * Encrypts plaintext using keyed transposition
     * @param plaintext The text to encrypt
     * @param key The ordering key (numeric)
     * @return The encrypted ciphertext
     */
    @Override
    public String encrypt(String plaintext, String key) {
        plaintext = plaintext.toUpperCase();
        
        // Remove spaces for transposition
        String cleaned = plaintext.replaceAll(" ", "");
        
        // Parse key to get column order
        String[] keyParts = key.trim().split("\\s+");
        int cols = keyParts.length;
        
        if (cols == 0 || cleaned.length() == 0) {
            return plaintext;
        }
        
        // Calculate rows needed
        int rows = (cleaned.length() + cols - 1) / cols; // Ceiling division
        
        // Create grid and fill with plaintext
        char[][] grid = new char[rows][cols];
        int charIndex = 0;
        
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (charIndex < cleaned.length()) {
                    grid[row][col] = cleaned.charAt(charIndex++);
                } else {
                    grid[row][col] = 'X'; // Padding
                }
            }
        }
        
        // Create column index order from key
        ColumnIndex[] columns = new ColumnIndex[cols];
        for (int i = 0; i < cols; i++) {
            try {
                columns[i] = new ColumnIndex(i, Integer.parseInt(keyParts[i]));
            } catch (NumberFormatException e) {
                // If not numeric, use alphabetical order
                columns[i] = new ColumnIndex(i, keyParts[i].charAt(0));
            }
        }
        
        // Sort columns by their key values
        Arrays.sort(columns);
        
        // Read columns in sorted order
        StringBuilder ciphertext = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (ColumnIndex colIndex : columns) {
                ciphertext.append(grid[i][colIndex.index]);
            }
        }
        
        return ciphertext.toString();
    }
    
    /**
     * Decrypts ciphertext using keyed transposition
     * @param ciphertext The text to decrypt
     * @param key The ordering key
     * @return The decrypted plaintext
     */
    @Override
    public String decrypt(String ciphertext, String key) {
        ciphertext = ciphertext.toUpperCase();
        
        // Parse key to get column order
        String[] keyParts = key.trim().split("\\s+");
        int cols = keyParts.length;
        
        if (cols == 0 || ciphertext.length() == 0) {
            return ciphertext;
        }
        
        int totalChars = ciphertext.length();
        int rows = totalChars / cols;
        
        // Create column index order
        ColumnIndex[] columns = new ColumnIndex[cols];
        for (int i = 0; i < cols; i++) {
            try {
                columns[i] = new ColumnIndex(i, Integer.parseInt(keyParts[i]));
            } catch (NumberFormatException e) {
                columns[i] = new ColumnIndex(i, keyParts[i].charAt(0));
            }
        }
        
        // Sort by key values
        Arrays.sort(columns);
        
        // Reconstruct grid by reading in sorted order
        char[][] grid = new char[rows][cols];
        int charIndex = 0;
        
        for (int i = 0; i < rows; i++) {
            for (ColumnIndex colIndex : columns) {
                if (charIndex < ciphertext.length()) {
                    grid[i][colIndex.index] = ciphertext.charAt(charIndex++);
                }
            }
        }
        
        // Read grid row by row
        StringBuilder plaintext = new StringBuilder();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                plaintext.append(grid[row][col]);
            }
        }
        
        // Remove padding Xs at the end
        String result = plaintext.toString();
        while (result.endsWith("X")) {
            result = result.substring(0, result.length() - 1);
        }
        
        return result;
    }
    
    /**
     * Displays step-by-step transposition process
     * @param text The plaintext
     * @param key The ordering key
     */
    @Override
    public void displaySteps(String text, String key) {
        text = text.toUpperCase();
        String cleaned = text.replaceAll(" ", "");
        String[] keyParts = key.trim().split("\\s+");
        int cols = keyParts.length;
        
        if (cols == 0) {
            System.out.println("Invalid key!");
            return;
        }
        
        int rows = (cleaned.length() + cols - 1) / cols;
        
        System.out.println("\n--- Step-by-Step Keyed Transposition Encryption ---");
        System.out.println("Plaintext: " + text);
        System.out.println("Key: " + key);
        System.out.println("\nCreate grid with " + rows + " rows and " + cols + " columns:");
        
        // Create and display grid
        char[][] grid = new char[rows][cols];
        int charIndex = 0;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (charIndex < cleaned.length()) {
                    grid[row][col] = cleaned.charAt(charIndex++);
                } else {
                    grid[row][col] = 'X';
                }
            }
        }
        
        // Show grid
        System.out.print("    ");
        for (int i = 0; i < cols; i++) {
            System.out.printf("%3d ", i + 1);
        }
        System.out.println();
        
        for (int row = 0; row < rows; row++) {
            System.out.print((row + 1) + ": ");
            for (int col = 0; col < cols; col++) {
                System.out.printf(" %c  ", grid[row][col]);
            }
            System.out.println();
        }
        
        // Show column reading order
        System.out.println("\nColumn reading order (based on key):");
        ColumnIndex[] columns = new ColumnIndex[cols];
        for (int i = 0; i < cols; i++) {
            try {
                columns[i] = new ColumnIndex(i, Integer.parseInt(keyParts[i]));
            } catch (NumberFormatException e) {
                columns[i] = new ColumnIndex(i, keyParts[i].charAt(0));
            }
        }
        Arrays.sort(columns);
        
        for (int i = 0; i < columns.length; i++) {
            System.out.println("  " + (i + 1) + ". Read column " + (columns[i].index + 1));
        }
    }
    
    /**
     * Helper class to sort columns by key values
     */
    private static class ColumnIndex implements Comparable<ColumnIndex> {
        int index;
        Comparable key;
        
        ColumnIndex(int index, Comparable key) {
            this.index = index;
            this.key = key;
        }
        
        @Override
        public int compareTo(ColumnIndex other) {
            if (key instanceof Integer && other.key instanceof Integer) {
                return Integer.compare((Integer)key, (Integer)other.key);
            } else {
                return key.toString().compareTo(other.key.toString());
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
        
        System.out.println("Enter key as numbers separated by spaces (e.g., 3 1 4 2 5): ");
        String key = InputValidator.getInput("Or enter letters for alphabetical ordering: ");
        
        if (!InputValidator.validateNotEmpty(key, "Key")) {
            return;
        }
        
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
        System.out.println("Key: " + key);
        System.out.println("\nReconstruct grid based on key ordering...");
        
        String plaintext = decrypt(ciphertext, key);
        
        System.out.println("\n--- Result ---");
        System.out.println("Plaintext: " + plaintext);
    }
}

