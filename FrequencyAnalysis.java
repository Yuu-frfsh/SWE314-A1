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
        
        // Automatically detect key and decrypt
        System.out.println("\n--- Auto-Decryption (Monoalphabetic Cipher Only) ---");
        Integer detectedKey = detectKey(percentages);
        
        if (detectedKey != null) {
            String plaintext = decryptWithKey(text, detectedKey);
            System.out.println("Detected Key (Shift): " + detectedKey);
            System.out.println("Decrypted Plaintext: " + plaintext);
        } else {
            System.out.println("Could not automatically detect the key.");
            System.out.println("The ciphertext may not be monoalphabetic, or sample size is too small.");
        }
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
     * Detect the encryption key by comparing ciphertext frequencies to English frequencies
     * Uses correlation between shifted cipher frequencies and expected English frequencies
     * @param percentages The frequency percentages of ciphertext
     * @return The detected shift/key value, or null if cannot determine
     */
    private Integer detectKey(double[] percentages) {
        double maxCorrelation = -1.0;
        int bestShift = 0;
        
        // Test each possible shift (0-25)
        for (int shift = 0; shift < 26; shift++) {
            // Calculate correlation between cipher frequencies (shifted back) and English frequencies
            double correlation = calculateCorrelation(percentages, shift);
            
            if (correlation > maxCorrelation) {
                maxCorrelation = correlation;
                bestShift = shift;
            }
        }
        
        // If correlation is reasonable (above threshold), return the shift
        // Threshold set to 0.3 to ensure reasonable match
        if (maxCorrelation > 0.3) {
            return bestShift;
        }
        
        return null;
    }
    
    /**
     * Calculate correlation between ciphertext frequencies (when shifted back by shift)
     * and expected English letter frequencies
     * @param cipherPercentages Ciphertext letter frequencies
     * @param shift The shift to test
     * @return Correlation score (0-1, higher is better match)
     */
    private double calculateCorrelation(double[] cipherPercentages, int shift) {
        double sumProduct = 0.0;
        double sumCipherSq = 0.0;
        double sumEnglishSq = 0.0;
        
        for (int i = 0; i < 26; i++) {
            // Calculate what plaintext letter this cipher letter represents
            // If cipher letter at index i, and shift is s, then plaintext is at (i - s + 26) % 26
            int plainIndex = (i - shift + 26) % 26;
            
            double cipherFreq = cipherPercentages[i];
            double englishFreq = ENGLISH_FREQUENCY[plainIndex];
            
            // Pearson correlation coefficient components
            sumProduct += cipherFreq * englishFreq;
            sumCipherSq += cipherFreq * cipherFreq;
            sumEnglishSq += englishFreq * englishFreq;
        }
        
        // Avoid division by zero
        if (sumCipherSq == 0.0 || sumEnglishSq == 0.0) {
            return 0.0;
        }
        
        // Return correlation coefficient
        return sumProduct / Math.sqrt(sumCipherSq * sumEnglishSq);
    }
    
    /**
     * Automatically decrypt ciphertext using detected key
     * @param ciphertext The ciphertext to decrypt
     * @param detectedKey The detected shift key
     * @return The decrypted plaintext
     */
    private String decryptWithKey(String ciphertext, int detectedKey) {
        MonoalphabeticCipher cipher = new MonoalphabeticCipher();
        return cipher.decrypt(ciphertext, String.valueOf(detectedKey));
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
        System.out.println("Automatically detects key and decrypts monoalphabetic substitution ciphers");
        System.out.println("by analyzing letter frequency patterns.");
        
        String ciphertext = InputValidator.getInput("Enter ciphertext to analyze: ");
        if (!InputValidator.validateNotEmpty(ciphertext, "Ciphertext")) {
            return;
        }
        
        analyze(ciphertext);
        
        System.out.println("\nNote: This method works best with monoalphabetic substitution ciphers.");
        System.out.println("For other cipher types, the detected key may be incorrect.");
    }
}

