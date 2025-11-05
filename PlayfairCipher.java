import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Playfair Cipher Implementation
 * Uses a 5x5 matrix to encrypt digraphs (pairs of letters)
 */
public class PlayfairCipher implements EncryptionAlgorithm {
    
    private static final String ALPHABET = "ABCDEFGHIKLMNOPQRSTUVWXYZ"; // J removed for 5x5 grid
    
    /**
     * Builds the Playfair 5x5 key matrix
     * @param key The keyword
     * @return A 25-character string representing the matrix
     */
    private String buildMatrix(String key) {
        // Remove duplicate letters from key
        Set<Character> used = new HashSet<>();
        StringBuilder matrix = new StringBuilder();
        
        // Add key letters first
        for (char c : key.toCharArray()) {
            char upper = Character.toUpperCase(c);
            if (upper == 'J') upper = 'I'; // Treat J as I
            if (Character.isLetter(upper) && !used.contains(upper)) {
                matrix.append(upper);
                used.add(upper);
            }
        }
        
        // Add remaining alphabet letters
        for (char c : ALPHABET.toCharArray()) {
            if (!used.contains(c)) {
                matrix.append(c);
            }
        }
        
        return matrix.toString();
    }
    
    /**
     * Gets row and column of a letter in the matrix
     * @param letter The letter to find
     * @param matrix The 5x5 matrix
     * @return An int[] with [row, col]
     */
    private int[] getPosition(char letter, String matrix) {
        if (letter == 'J') letter = 'I';
        int index = matrix.indexOf(letter);
        if (index == -1) return null;
        return new int[]{index / 5, index % 5};
    }
    
    /**
     * Prepares plaintext for Playfair encryption
     * @param text The plaintext
     * @return List of digraphs (pairs)
     */
    private List<String> prepareDigraphs(String text) {
        text = text.toUpperCase();
        
        // Remove non-letters and replace J with I
        StringBuilder cleaned = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                if (c == 'J') {
                    cleaned.append('I');
                } else {
                    cleaned.append(c);
                }
            }
        }
        
        String plaintext = cleaned.toString();
        List<String> digraphs = new ArrayList<>();
        int i = 0;
        
        while (i < plaintext.length()) {
            if (i == plaintext.length() - 1) {
                // Last letter, add X as padding
                digraphs.add(plaintext.charAt(i) + "X");
                i++;
            } else if (plaintext.charAt(i) == plaintext.charAt(i + 1)) {
                // Same letter pair, insert X
                digraphs.add(plaintext.charAt(i) + "X");
                i++;
            } else {
                // Normal pair
                digraphs.add(plaintext.substring(i, i + 2));
                i += 2;
            }
        }
        
        return digraphs;
    }
    
    /**
     * Encrypts plaintext using Playfair cipher
     * @param plaintext The text to encrypt
     * @param key The keyword
     * @return The encrypted ciphertext
     */
    @Override
    public String encrypt(String plaintext, String key) {
        plaintext = plaintext.toUpperCase();
        key = key.toUpperCase();
        
        String matrix = buildMatrix(key);
        List<String> digraphs = prepareDigraphs(plaintext);
        StringBuilder ciphertext = new StringBuilder();
        
        for (String pair : digraphs) {
            char a = pair.charAt(0);
            char b = pair.charAt(1);
            
            int[] posA = getPosition(a, matrix);
            int[] posB = getPosition(b, matrix);
            
            if (posA == null || posB == null) {
                ciphertext.append(pair); // Keep as-is if not found
                continue;
            }
            
            int rowA = posA[0], colA = posA[1];
            int rowB = posB[0], colB = posB[1];
            
            if (rowA == rowB) {
                // Same row: shift right
                char encA = matrix.charAt(rowA * 5 + (colA + 1) % 5);
                char encB = matrix.charAt(rowB * 5 + (colB + 1) % 5);
                ciphertext.append(encA).append(encB);
            } else if (colA == colB) {
                // Same column: shift down
                char encA = matrix.charAt(((rowA + 1) % 5) * 5 + colA);
                char encB = matrix.charAt(((rowB + 1) % 5) * 5 + colB);
                ciphertext.append(encA).append(encB);
            } else {
                // Rectangle: swap columns
                char encA = matrix.charAt(rowA * 5 + colB);
                char encB = matrix.charAt(rowB * 5 + colA);
                ciphertext.append(encA).append(encB);
            }
        }
        
        return ciphertext.toString();
    }
    
    /**
     * Decrypts ciphertext using Playfair cipher
     * @param ciphertext The text to decrypt
     * @param key The keyword
     * @return The decrypted plaintext
     */
    @Override
    public String decrypt(String ciphertext, String key) {
        ciphertext = ciphertext.toUpperCase();
        key = key.toUpperCase();
        
        String matrix = buildMatrix(key);
        
        // Split into digraphs
        List<String> digraphs = new ArrayList<>();
        for (int i = 0; i < ciphertext.length(); i += 2) {
            if (i + 1 < ciphertext.length()) {
                digraphs.add(ciphertext.substring(i, i + 2));
            } else {
                digraphs.add(ciphertext.substring(i));
            }
        }
        
        StringBuilder plaintext = new StringBuilder();
        
        for (String pair : digraphs) {
            if (pair.length() != 2) {
                plaintext.append(pair);
                continue;
            }
            
            char a = pair.charAt(0);
            char b = pair.charAt(1);
            
            int[] posA = getPosition(a, matrix);
            int[] posB = getPosition(b, matrix);
            
            if (posA == null || posB == null) {
                plaintext.append(pair);
                continue;
            }
            
            int rowA = posA[0], colA = posA[1];
            int rowB = posB[0], colB = posB[1];
            
            if (rowA == rowB) {
                // Same row: shift left
                char decA = matrix.charAt(rowA * 5 + (colA + 4) % 5);
                char decB = matrix.charAt(rowB * 5 + (colB + 4) % 5);
                plaintext.append(decA).append(decB);
            } else if (colA == colB) {
                // Same column: shift up
                char decA = matrix.charAt(((rowA + 4) % 5) * 5 + colA);
                char decB = matrix.charAt(((rowB + 4) % 5) * 5 + colB);
                plaintext.append(decA).append(decB);
            } else {
                // Rectangle: swap columns
                char decA = matrix.charAt(rowA * 5 + colB);
                char decB = matrix.charAt(rowB * 5 + colA);
                plaintext.append(decA).append(decB);
            }
        }
        
        return plaintext.toString();
    }
    
    /**
     * Displays step-by-step Playfair encryption process
     * @param text The plaintext
     * @param key The keyword
     */
    @Override
    public void displaySteps(String text, String key) {
        text = text.toUpperCase();
        key = key.toUpperCase();
        
        String matrix = buildMatrix(key);
        List<String> digraphs = prepareDigraphs(text);
        
        System.out.println("\n--- Step-by-Step Playfair Encryption ---");
        System.out.println("Keyword: " + key);
        System.out.println("\n5x5 Key Matrix:");
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                System.out.print(matrix.charAt(row * 5 + col) + " ");
            }
            System.out.println();
        }
        
        System.out.println("\nPrepared Digraphs:");
        for (String pair : digraphs) {
            System.out.print(pair + " ");
        }
        System.out.println();
        
        System.out.println("\nEncryption for each digraph:");
        for (String pair : digraphs) {
            char a = pair.charAt(0);
            char b = pair.charAt(1);
            int[] posA = getPosition(a, matrix);
            int[] posB = getPosition(b, matrix);
            
            if (posA != null && posB != null) {
                int rowA = posA[0], colA = posA[1];
                int rowB = posB[0], colB = posB[1];
                
                if (rowA == rowB) {
                    System.out.println(String.format("  %s: Same row -> shift right", pair));
                } else if (colA == colB) {
                    System.out.println(String.format("  %s: Same column -> shift down", pair));
                } else {
                    System.out.println(String.format("  %s: Rectangle -> swap columns", pair));
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
        
        // Validate that plaintext contains only alphabetic characters and spaces
        String plaintextUpper = plaintext.toUpperCase();
        String allowedChars = plaintextUpper.replaceAll("[^A-Z ]", "");
        if (allowedChars.replaceAll(" ", "").isEmpty()) {
            System.out.println("Error: Plaintext must contain only alphabetic characters (A-Z, a-z)!");
            return;
        }
        if (!plaintextUpper.equals(allowedChars)) {
            System.out.println("Error: Plaintext must contain only alphabetic characters (A-Z, a-z)!");
            return;
        }
        
        String key = InputValidator.getInput("Enter keyword: ");
        key = key.toUpperCase();
        
        if (!InputValidator.validateNotEmpty(key, "Keyword")) {
            return;
        }
        
        if (!InputValidator.isAlphabetic(key)) {
            System.out.println("Error: Keyword must contain only alphabetic characters!");
            return;
        }
        
        displaySteps(plaintext, key);
        
        String ciphertext = encrypt(plaintext, key);
        
        System.out.println("\n--- Result ---");
        System.out.println("Ciphertext: " + ciphertext);
    }
}

