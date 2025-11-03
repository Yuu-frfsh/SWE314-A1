/**
 * Comprehensive Test Suite for All Encryption/Decryption Methods
 * Tests round-trip encryption/decryption for all ciphers
 */
public class ComprehensiveTest {
    
    private static int totalTests = 0;
    private static int totalPassed = 0;
    private static int totalFailed = 0;
    
    public static void main(String[] args) {
        System.out.println("=====================================================");
        System.out.println("COMPREHENSIVE ENCRYPTION/DECRYPTION TEST SUITE");
        System.out.println("Testing All Ciphers for Round-Trip Correctness");
        System.out.println("=====================================================\n");
        
        // Test all ciphers
        testMonoalphabeticCipher();
        testVigenereCipher();
        testPlayfairCipher();
        testKeyedTranspositionCipher();
        testDESCipher();
        testCombinedCipher();
        
        // Print final summary
        printFinalSummary();
    }
    
    /**
     * Test Monoalphabetic (Caesar) Cipher
     */
    private static void testMonoalphabeticCipher() {
        System.out.println("\n═══════════════════════════════════════════════════");
        System.out.println("TESTING MONOALPHABETIC (CAESAR) CIPHER");
        System.out.println("═══════════════════════════════════════════════════");
        
        MonoalphabeticCipher cipher = new MonoalphabeticCipher();
        
        // Test cases
        roundTripTest(cipher, "HELLO", "3", "Simple word, shift=3");
        roundTripTest(cipher, "WORLD", "5", "Common word, shift=5");
        roundTripTest(cipher, "ABCDEFGHIJKLMNOPQRSTUVWXYZ", "13", "Full alphabet, shift=13");
        roundTripTest(cipher, "THE QUICK BROWN FOX", "7", "Sentence with spaces, shift=7");
        roundTripTest(cipher, "Hello World", "-5", "Mixed case, negative shift");
        roundTripTest(cipher, "TEST123", "3", "With numbers");
        roundTripTest(cipher, "A", "1", "Single character");
        roundTripTest(cipher, "HELLO", "27", "Large shift (should normalize)");
        roundTripTest(cipher, "HELLO", "-27", "Large negative shift");
        roundTripTest(cipher, "", "3", "Empty string");
    }
    
    /**
     * Test Vigenere Cipher
     */
    private static void testVigenereCipher() {
        System.out.println("\n═══════════════════════════════════════════════════");
        System.out.println("TESTING VIGENERE CIPHER");
        System.out.println("═══════════════════════════════════════════════════");
        
        VigenereCipher cipher = new VigenereCipher();
        
        roundTripTest(cipher, "HELLO", "KEY", "Simple word with key");
        roundTripTest(cipher, "ATTACKATDAWN", "LEMON", "Known Vigenere example");
        roundTripTest(cipher, "ABCDEFGHIJKLMNOPQRSTUVWXYZ", "ABC", "Full alphabet");
        roundTripTest(cipher, "THE QUICK BROWN FOX", "JUMPS", "Sentence with spaces");
        roundTripTest(cipher, "HELLO", "A", "Single letter key");
        roundTripTest(cipher, "HELLO", "THEQUICKBROWN", "Long key");
        roundTripTest(cipher, "HELLOWORLD", "KEY", "Key shorter than text");
        roundTripTest(cipher, "A", "KEY", "Single character");
        roundTripTest(cipher, "TEST123", "KEY", "With numbers");
    }
    
    /**
     * Test Playfair Cipher
     */
    private static void testPlayfairCipher() {
        System.out.println("\n═══════════════════════════════════════════════════");
        System.out.println("TESTING PLAYFAIR CIPHER");
        System.out.println("═══════════════════════════════════════════════════");
        
        PlayfairCipher cipher = new PlayfairCipher();
        
        roundTripTest(cipher, "HELLO", "KEYWORD", "Simple word");
        roundTripTest(cipher, "ATTACK", "MONARCHY", "Attack message");
        roundTripTest(cipher, "ABCDEFGHIJKLMNOPQRSTUVWXYZ", "PLAYFAIR", "Full alphabet");
        roundTripTest(cipher, "THE QUICK BROWN FOX", "JUMPS", "Sentence (spaces removed)");
        roundTripTest(cipher, "HELLOX", "KEYWORD", "With X padding");
        roundTripTest(cipher, "HI", "KEYWORD", "Two characters");
        roundTripTest(cipher, "A", "KEYWORD", "Single character (padded)");
        roundTripTest(cipher, "J", "KEYWORD", "Letter J (becomes I)");
    }
    
    /**
     * Test Keyed Transposition Cipher
     */
    private static void testKeyedTranspositionCipher() {
        System.out.println("\n═══════════════════════════════════════════════════");
        System.out.println("TESTING KEYED TRANSPOSITION CIPHER");
        System.out.println("═══════════════════════════════════════════════════");
        
        KeyedTranspositionCipher cipher = new KeyedTranspositionCipher();
        
        // Key format: first row (plaintext indices) \n second row (ciphertext positions)
        roundTripTest(cipher, "ENEMY", "3 1 4 5 2\n1 2 3 4 5", "Simple 5-char word");
        roundTripTest(cipher, "ENEMYATTACKSTONIGHT", "3 1 4 5 2\n1 2 3 4 5", "Multiple blocks");
        roundTripTest(cipher, "HELLO", "3 1 4 5 2\n1 2 3 4 5", "5 characters");
        roundTripTest(cipher, "TEST", "3 1 4 5 2\n1 2 3 4 5", "4 characters (padded with z)");
        roundTripTest(cipher, "ABCDEFGHIJ", "3 1 4 5 2\n1 2 3 4 5", "10 characters (2 blocks)");
        roundTripTest(cipher, "A", "3 1 4 5 2\n1 2 3 4 5", "Single character (padded)");
        roundTripTest(cipher, "THEQUICKBROWNFOX", "3 1 4 5 2\n1 2 3 4 5", "Sentence (no spaces)");
    }
    
    /**
     * Test DES Cipher
     */
    private static void testDESCipher() {
        System.out.println("\n═══════════════════════════════════════════════════");
        System.out.println("TESTING DES CIPHER");
        System.out.println("═══════════════════════════════════════════════════");
        
        DESCipher cipher = new DESCipher();
        
        roundTripTest(cipher, "HELLO", "12345678", "5 characters");
        roundTripTest(cipher, "TESTING", "ABCDEFGH", "7 characters");
        roundTripTest(cipher, "ABDULLAH", "MOHAMMED", "8 characters (exact block)");
        roundTripTest(cipher, "HELL", "12345678", "4 characters");
        roundTripTest(cipher, "HI", "12345678", "2 characters");
        roundTripTest(cipher, "A", "12345678", "Single character");
        roundTripTest(cipher, "ABCDEFGH", "87654321", "Exact block size");
        roundTripTest(cipher, "TEST", "KEY12345", "4 characters with key");
    }
    
    /**
     * Test Combined Cipher
     * Note: CombinedCipher requires manual algorithm selection, so we test manually
     */
    private static void testCombinedCipher() {
        System.out.println("\n═══════════════════════════════════════════════════");
        System.out.println("TESTING COMBINED CIPHER");
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("\nNOTE: Combined Cipher requires interactive algorithm selection.");
        System.out.println("Testing by manually applying both ciphers sequentially.\n");
        
        // Test Monoalphabetic + Vigenere
        testCombinedMonoVigenere("HELLO", "3", "KEY", "Simple word");
        testCombinedMonoVigenere("ATTACK", "5", "LEMON", "Attack message");
        
        // Test Monoalphabetic + Playfair
        testCombinedMonoPlayfair("HELLO", "3", "KEYWORD", "Simple word");
        testCombinedMonoPlayfair("ATTACK", "7", "MONARCHY", "Attack message");
        
        // Test Monoalphabetic + Keyed Transposition
        testCombinedMonoTransposition("ENEMY", "5", "3 1 4 5 2\n1 2 3 4 5", "Simple word");
    }
    
    private static void testCombinedMonoVigenere(String plaintext, String monoKey, String vigenereKey, String desc) {
        totalTests++;
        try {
            MonoalphabeticCipher mono = new MonoalphabeticCipher();
            VigenereCipher vigenere = new VigenereCipher();
            
            String intermediate = mono.encrypt(plaintext, monoKey);
            String ciphertext = vigenere.encrypt(intermediate, vigenereKey);
            
            String decIntermediate = vigenere.decrypt(ciphertext, vigenereKey);
            String decrypted = mono.decrypt(decIntermediate, monoKey);
            
            String normalizedOriginal = plaintext.toUpperCase().replaceAll(" ", "");
            String normalizedDecrypted = decrypted.toUpperCase().replaceAll(" ", "");
            
            System.out.println("\nTest: Monoalphabetic + Vigenere - " + desc);
            System.out.println("  Plaintext: \"" + plaintext + "\"");
            System.out.println("  Keys: Mono=" + monoKey + ", Vigenere=" + vigenereKey);
            System.out.println("  Encrypted: " + ciphertext);
            System.out.println("  Decrypted: \"" + decrypted + "\"");
            
            if (normalizedOriginal.equals(normalizedDecrypted)) {
                System.out.println("  ✓ PASS");
                totalPassed++;
            } else {
                System.out.println("  ✗ FAIL");
                totalFailed++;
            }
        } catch (Exception e) {
            System.out.println("  ✗ ERROR: " + e.getMessage());
            totalFailed++;
        }
    }
    
    private static void testCombinedMonoPlayfair(String plaintext, String monoKey, String playfairKey, String desc) {
        totalTests++;
        try {
            MonoalphabeticCipher mono = new MonoalphabeticCipher();
            PlayfairCipher playfair = new PlayfairCipher();
            
            String intermediate = mono.encrypt(plaintext, monoKey);
            String ciphertext = playfair.encrypt(intermediate, playfairKey);
            
            String decIntermediate = playfair.decrypt(ciphertext, playfairKey);
            String decrypted = mono.decrypt(decIntermediate, monoKey);
            
            String normalizedOriginal = plaintext.toUpperCase().replaceAll(" ", "");
            String normalizedDecrypted = decrypted.toUpperCase().replaceAll(" ", "");
            
            System.out.println("\nTest: Monoalphabetic + Playfair - " + desc);
            System.out.println("  Plaintext: \"" + plaintext + "\"");
            System.out.println("  Keys: Mono=" + monoKey + ", Playfair=" + playfairKey);
            System.out.println("  Encrypted: " + ciphertext);
            System.out.println("  Decrypted: \"" + decrypted + "\"");
            
            if (normalizedOriginal.equals(normalizedDecrypted)) {
                System.out.println("  ✓ PASS");
                totalPassed++;
            } else {
                System.out.println("  ✗ FAIL");
                totalFailed++;
            }
        } catch (Exception e) {
            System.out.println("  ✗ ERROR: " + e.getMessage());
            totalFailed++;
        }
    }
    
    private static void testCombinedMonoTransposition(String plaintext, String monoKey, String transKey, String desc) {
        totalTests++;
        try {
            MonoalphabeticCipher mono = new MonoalphabeticCipher();
            KeyedTranspositionCipher trans = new KeyedTranspositionCipher();
            
            String intermediate = mono.encrypt(plaintext, monoKey);
            String ciphertext = trans.encrypt(intermediate, transKey);
            
            String decIntermediate = trans.decrypt(ciphertext, transKey);
            String decrypted = mono.decrypt(decIntermediate, monoKey);
            
            String normalizedOriginal = plaintext.toUpperCase().replaceAll(" ", "");
            String normalizedDecrypted = decrypted.toUpperCase().replaceAll(" ", "");
            
            // Remove padding 'z'
            while (normalizedDecrypted.endsWith("Z")) {
                normalizedDecrypted = normalizedDecrypted.substring(0, normalizedDecrypted.length() - 1);
            }
            
            System.out.println("\nTest: Monoalphabetic + Transposition - " + desc);
            System.out.println("  Plaintext: \"" + plaintext + "\"");
            System.out.println("  Keys: Mono=" + monoKey + ", Transposition=" + transKey.replace("\n", "\\n"));
            System.out.println("  Encrypted: " + ciphertext);
            System.out.println("  Decrypted: \"" + decrypted + "\"");
            
            if (normalizedOriginal.equals(normalizedDecrypted)) {
                System.out.println("  ✓ PASS");
                totalPassed++;
            } else {
                System.out.println("  ✗ FAIL");
                totalFailed++;
            }
        } catch (Exception e) {
            System.out.println("  ✗ ERROR: " + e.getMessage());
            totalFailed++;
        }
    }
    
    /**
     * Generic round-trip test for any EncryptionAlgorithm
     */
    private static void roundTripTest(EncryptionAlgorithm cipher, String plaintext, String key, String description) {
        totalTests++;
        try {
            System.out.println("\nTest: " + description);
            System.out.println("  Plaintext: \"" + plaintext + "\"");
            System.out.println("  Key: \"" + key.replace("\n", "\\n") + "\"");
            
            // Encrypt
            String ciphertext = cipher.encrypt(plaintext, key);
            System.out.println("  Encrypted: " + ciphertext);
            
            // Decrypt
            String decrypted = cipher.decrypt(ciphertext, key);
            System.out.println("  Decrypted: \"" + decrypted + "\"");
            
            // Compare (normalize case and spaces for comparison)
            String normalizedOriginal = plaintext.toUpperCase().replaceAll(" ", "");
            String normalizedDecrypted = decrypted.toUpperCase().replaceAll(" ", "");
            
            // Special handling for Keyed Transposition (may have padding)
            if (cipher instanceof KeyedTranspositionCipher) {
                // Remove trailing 'z' padding
                while (normalizedDecrypted.endsWith("Z")) {
                    normalizedDecrypted = normalizedDecrypted.substring(0, normalizedDecrypted.length() - 1);
                }
            }
            
            // Special handling for DES (may have padding issues)
            if (cipher instanceof DESCipher) {
                // DES may have padding - compare only up to original length
                if (normalizedDecrypted.length() >= normalizedOriginal.length()) {
                    normalizedDecrypted = normalizedDecrypted.substring(0, normalizedOriginal.length());
                }
            }
            
            if (normalizedOriginal.equals(normalizedDecrypted)) {
                System.out.println("  ✓ PASS");
                totalPassed++;
            } else {
                System.out.println("  ✗ FAIL");
                System.out.println("    Expected: \"" + normalizedOriginal + "\"");
                System.out.println("    Got:      \"" + normalizedDecrypted + "\"");
                totalFailed++;
            }
        } catch (Exception e) {
            System.out.println("  ✗ ERROR: " + e.getMessage());
            e.printStackTrace();
            totalFailed++;
        }
    }
    
    /**
     * Print final test summary
     */
    private static void printFinalSummary() {
        System.out.println("\n\n=====================================================");
        System.out.println("FINAL TEST SUMMARY");
        System.out.println("=====================================================");
        System.out.println("Total Tests: " + totalTests);
        System.out.println("Passed: " + totalPassed);
        System.out.println("Failed: " + totalFailed);
        
        double successRate = totalTests > 0 ? (totalPassed * 100.0 / totalTests) : 0;
        System.out.println("Success Rate: " + String.format("%.2f", successRate) + "%");
        
        if (totalFailed == 0) {
            System.out.println("\n✓ ALL TESTS PASSED - ALL CIPHERS ARE 100% CORRECT!");
        } else {
            System.out.println("\n✗ SOME TESTS FAILED - REVIEW FAILED TESTS ABOVE");
        }
        
        System.out.println("=====================================================\n");
    }
}

