import java.util.Arrays;

/**
 * Frequency Analysis for Cryptanalysis
 * Analyzes letter frequency in ciphertext to help break ciphers
 */
public class FrequencyAnalysis {
    
    // English language letter frequencies (approximate percentages)
    private static final double[] ENGLISH_FREQUENCY = {
        8.167, 1.492, 2.782, 4.253, 12.702, 2.228, 2.015, 6.094, 6.966, 0.153,
        0.772, 4.025, 2.406, 6.749, 7.507, 1.929, 0.095, 5.987, 6.327, 9.056,
        2.758, 0.978, 2.360, 0.150, 1.974, 0.074
    };
    
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    
    /**
     * Count letter frequencies in text
     * @param text The text to analyze
     * @return Array of counts for each letter (A-Z)
     */
    private int[] countLetters(String text) {
        int[] counts = new int[26];
        text = text.toUpperCase();
        
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                int index = c - 'A';
                if (index >= 0 && index < 26) {
                    counts[index]++;
                }
            }
        }
        
        return counts;
    }
    
    /**
     * Calculate percentage frequencies
     * @param counts The letter counts
     * @return Array of percentages
     */
    private double[] calculatePercentages(int[] counts) {
        int total = 0;
        for (int count : counts) {
            total += count;
        }
        
        if (total == 0) {
            return new double[26];
        }
        
        double[] percentages = new double[26];
        for (int i = 0; i < 26; i++) {
            percentages[i] = (counts[i] * 100.0) / total;
        }
        
        return percentages;
    }
    
    /**
     * Display frequency analysis results
     * @param text The ciphertext to analyze
     */
    public void analyze(String text) {
        text = text.toUpperCase();
        
        System.out.println("\n--- Frequency Analysis ---");
        System.out.println("Ciphertext: " + text);
        
        int[] counts = countLetters(text);
        double[] percentages = calculatePercentages(counts);
        
        int total = 0;
        for (int count : counts) {
            total += count;
        }
        
        System.out.println("\nLetter Frequency Count:");
        System.out.println("Letter | Count | Percentage | Expected%");
        System.out.println("------------------------------------------");
        
        for (int i = 0; i < 26; i++) {
            System.out.println(String.format("   %c   |  %3d  |   %6.2f   |   %6.2f", 
                ALPHABET.charAt(i), 
                counts[i], 
                percentages[i],
                ENGLISH_FREQUENCY[i]));
        }
        
        System.out.println("------------------------------------------");
        System.out.println("Total letters: " + total);
        
        // Find most frequent letters
        System.out.println("\nMost frequent letters in ciphertext:");
        findMostFrequent(percentages, 5);
        
        // Suggest possible substitutions for monoalphabetic
        System.out.println("\nPossible monoalphabetic substitutions:");
        suggestSubstitutions(percentages);
    }
    
    /**
     * Find the most frequent letters
     * @param percentages The frequency percentages
     * @param topN Number of top letters to display
     */
    private void findMostFrequent(double[] percentages, int topN) {
        IndexValue[] sorted = new IndexValue[26];
        for (int i = 0; i < 26; i++) {
            sorted[i] = new IndexValue(i, percentages[i]);
        }
        
        // Sort by frequency
        Arrays.sort(sorted, (a, b) -> Double.compare(b.value, a.value));
        
        for (int i = 0; i < Math.min(topN, 26); i++) {
            if (sorted[i].value > 0) {
                System.out.println(String.format("%d. %c (%.2f%%)", 
                    i + 1, 
                    ALPHABET.charAt(sorted[i].index), 
                    sorted[i].value));
            }
        }
    }
    
    /**
     * Suggest possible letter substitutions based on frequency
     * @param percentages The frequency percentages
     */
    private void suggestSubstitutions(double[] percentages) {
        // Get top 5 most frequent in ciphertext
        IndexValue[] cipher = new IndexValue[26];
        for (int i = 0; i < 26; i++) {
            cipher[i] = new IndexValue(i, percentages[i]);
        }
        Arrays.sort(cipher, (a, b) -> Double.compare(b.value, a.value));
        
        // Get top 5 most frequent in English
        IndexValue[] english = new IndexValue[26];
        for (int i = 0; i < 26; i++) {
            english[i] = new IndexValue(i, ENGLISH_FREQUENCY[i]);
        }
        Arrays.sort(english, (a, b) -> Double.compare(b.value, a.value));
        
        System.out.println("Cipher -> English (most likely matches):");
        for (int i = 0; i < 5; i++) {
            if (cipher[i].value > 0) {
                System.out.println(String.format("  %c -> %c", 
                    ALPHABET.charAt(cipher[i].index),
                    ALPHABET.charAt(english[i].index)));
            }
        }
    }
    
    /**
     * Helper class for sorting by value
     */
    private static class IndexValue {
        int index;
        double value;
        
        IndexValue(int index, double value) {
            this.index = index;
            this.value = value;
        }
    }
    
    /**
     * Interactive frequency analysis session
     */
    public void runAnalysis() {
        System.out.println("\n=== Frequency Analysis (Cryptanalysis) ===");
        System.out.println("Analyzes letter frequency to help break substitution ciphers");
        
        String ciphertext = InputValidator.getInput("Enter ciphertext to analyze: ");
        if (!InputValidator.validateNotEmpty(ciphertext, "Ciphertext")) {
            return;
        }
        
        analyze(ciphertext);
        
        System.out.println("\nTip: Compare these frequencies with English letter frequencies");
        System.out.println("to help break monoalphabetic substitution ciphers!");
    }
}

