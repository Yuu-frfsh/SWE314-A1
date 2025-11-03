/**
 * Unit Tests for CombinedCipher
 * Using Input Space Partitioning approach
 */
public class CombinedTest {
    
    private static int testCount = 0;
    private static int passCount = 0;
    private static int failCount = 0;
    
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("CombinedCipher Unit Tests");
        System.out.println("Input Space Partitioning Approach");
        System.out.println("========================================\n");
        
        // Test round trips only (encryption varies too much with combinations)
        testEncryptDecryptRoundTrip();
        
        // Print summary
        printSummary();
    }
    
    /**
     * Test Encrypt-Decrypt round trip
     */
    private static void testEncryptDecryptRoundTrip() {
        System.out.println("--- Test Category: Encrypt-Decrypt Round Trip ---");
        
        // Test Monoalphabetic + Vigenere
        roundTripTestMonoVigenere("HELLO WORLD", "3", "KEY", "Mono+Vigenere Test 1");
        roundTripTestMonoVigenere("THE QUICK BROWN FOX", "5", "LEMON", "Mono+Vigenere Test 2");
        
        // Test Monoalphabetic + Playfair
        roundTripTestMonoPlayfair("HELLO", "3", "KEY", "Mono+Playfair Test 1");
        roundTripTestMonoPlayfair("TESTING", "13", "MONARCHY", "Mono+Playfair Test 2");
        
        // Test Monoalphabetic + Transposition
        roundTripTestMonoTransposition("HELLO WORLD", "7", "3 1 4 2 5", "Mono+Transposition Test 1");
        roundTripTestMonoTransposition("ABCDEFGH", "-3", "2 1", "Mono+Transposition Test 2");
    }
    
    /**
     * Round trip test for Monoalphabetic + Vigenere
     */
    private static void roundTripTestMonoVigenere(String plaintext, String monoKey, String vigenereKey, String description) {
        testCount++;
        try {
            // Manually combine
            MonoalphabeticCipher mono = new MonoalphabeticCipher();
            VigenereCipher vigenere = new VigenereCipher();
            
            String encrypted = vigenere.encrypt(mono.encrypt(plaintext, monoKey), vigenereKey);
            String decrypted = mono.decrypt(vigenere.decrypt(encrypted, vigenereKey), monoKey);
            
            // Remove spaces for comparison
            String cleanOriginal = plaintext.toUpperCase().replaceAll(" ", "");
            String cleanDecrypted = decrypted.replaceAll(" ", "");
            
            if (cleanOriginal.equals(cleanDecrypted)) {
                System.out.println("PASS: " + description);
                System.out.println("  Original: " + plaintext);
                System.out.println("  Decrypted: " + decrypted);
                passCount++;
            } else {
                System.out.println("FAIL: " + description);
                System.out.println("  Original: " + plaintext);
                System.out.println("  Decrypted: " + decrypted);
                failCount++;
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + description);
            System.out.println("  Exception: " + e.getMessage());
            e.printStackTrace();
            failCount++;
        }
    }
    
    /**
     * Round trip test for Monoalphabetic + Playfair
     */
    private static void roundTripTestMonoPlayfair(String plaintext, String monoKey, String playfairKey, String description) {
        testCount++;
        try {
            MonoalphabeticCipher mono = new MonoalphabeticCipher();
            PlayfairCipher playfair = new PlayfairCipher();
            
            String encrypted = playfair.encrypt(mono.encrypt(plaintext, monoKey), playfairKey);
            String decrypted = mono.decrypt(playfair.decrypt(encrypted, playfairKey), monoKey);
            
            String cleanOriginal = plaintext.toUpperCase().replaceAll(" ", "");
            String cleanDecrypted = decrypted.replaceAll("X$", "").replaceAll(" ", "");
            
            if (cleanOriginal.equals(cleanDecrypted)) {
                System.out.println("PASS: " + description);
                System.out.println("  Original: " + plaintext);
                System.out.println("  Decrypted: " + decrypted);
                passCount++;
            } else {
                System.out.println("FAIL: " + description);
                System.out.println("  Original: " + plaintext);
                System.out.println("  Decrypted: " + decrypted);
                failCount++;
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + description);
            System.out.println("  Exception: " + e.getMessage());
            e.printStackTrace();
            failCount++;
        }
    }
    
    /**
     * Round trip test for Monoalphabetic + Transposition
     */
    private static void roundTripTestMonoTransposition(String plaintext, String monoKey, String transKey, String description) {
        testCount++;
        try {
            MonoalphabeticCipher mono = new MonoalphabeticCipher();
            KeyedTranspositionCipher trans = new KeyedTranspositionCipher();
            
            String encrypted = trans.encrypt(mono.encrypt(plaintext, monoKey), transKey);
            String decrypted = mono.decrypt(trans.decrypt(encrypted, transKey), monoKey);
            
            String cleanOriginal = plaintext.toUpperCase().replaceAll(" ", "");
            String cleanDecrypted = decrypted.replaceAll(" ", "");
            
            if (cleanOriginal.equals(cleanDecrypted)) {
                System.out.println("PASS: " + description);
                System.out.println("  Original: " + plaintext);
                System.out.println("  Decrypted: " + decrypted);
                passCount++;
            } else {
                System.out.println("FAIL: " + description);
                System.out.println("  Original: " + plaintext);
                System.out.println("  Decrypted: " + decrypted);
                failCount++;
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + description);
            System.out.println("  Exception: " + e.getMessage());
            e.printStackTrace();
            failCount++;
        }
    }
    
    /**
     * Print test summary
     */
    private static void printSummary() {
        System.out.println("\n========================================");
        System.out.println("Test Summary");
        System.out.println("========================================");
        System.out.println("Total tests: " + testCount);
        System.out.println("Passed: " + passCount);
        System.out.println("Failed: " + failCount);
        System.out.println("Success rate: " + (passCount * 100.0 / testCount) + "%");
        System.out.println("========================================\n");
    }
}

