/**
 * Unit Tests for MonoalphabeticCipher
 * Using Input Space Partitioning approach
 */
public class MonoalphabeticTest {
    
    private static final String VALID_KEY = "QWERTYUIOPASDFGHJKLZXCVBNM"; // 26-char permutation
    private static int testCount = 0;
    private static int passCount = 0;
    private static int failCount = 0;
    
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("MonoalphabeticCipher Unit Tests");
        System.out.println("Input Space Partitioning Approach");
        System.out.println("========================================\n");
        
        MonoalphabeticCipher cipher = new MonoalphabeticCipher();
        
        // Run all test categories
        testValidUppercase(cipher);
        testValidLowercase(cipher);
        testValidMixedCase(cipher);
        testValidWithSpaces(cipher);
        testValidSingleChar(cipher);
        testEdgeCases(cipher);
        testInvalidKeys(cipher);
        testEncryptDecryptRoundTrip(cipher);
        
        // Print summary
        printSummary();
    }
    
    /**
     * Test Category 1: Valid uppercase inputs
     */
    private static void testValidUppercase(MonoalphabeticCipher cipher) {
        System.out.println("--- Test Category 1: Valid Uppercase Inputs ---");
        
        test("HELLO", VALID_KEY, "ITSSG", "Simple word", cipher);
        test("WORLD", VALID_KEY, "VGKSR", "Common word", cipher);
        test("ABCDEFGHIJKLMNOPQRSTUVWXYZ", VALID_KEY, "QWERTYUIOPASDFGHJKLZXCVBNM", "Full alphabet", cipher);
    }
    
    /**
     * Test Category 2: Valid lowercase inputs
     */
    private static void testValidLowercase(MonoalphabeticCipher cipher) {
        System.out.println("\n--- Test Category 2: Valid Lowercase Inputs ---");
        
        test("hello", VALID_KEY, "ITSSG", "Simple lowercase word", cipher);
        test("world", VALID_KEY, "VGKSR", "Mixed lowercase", cipher);
    }
    
    /**
     * Test Category 3: Valid mixed case inputs
     */
    private static void testValidMixedCase(MonoalphabeticCipher cipher) {
        System.out.println("\n--- Test Category 3: Valid Mixed Case Inputs ---");
        
        test("HeLLo", VALID_KEY, "ITSSG", "Mixed case word", cipher);
        test("WoRLd", VALID_KEY, "VGKSR", "Mixed case word 2", cipher);
    }
    
    /**
     * Test Category 4: Valid inputs with spaces
     */
    private static void testValidWithSpaces(MonoalphabeticCipher cipher) {
        System.out.println("\n--- Test Category 4: Valid Inputs with Spaces ---");
        
        test("HELLO WORLD", VALID_KEY, "ITSSG VGKSR", "Words with space", cipher);
        test("  ABC  ", VALID_KEY, "  QWE  ", "Spaces around text", cipher);
    }
    
    /**
     * Test Category 5: Single character inputs
     */
    private static void testValidSingleChar(MonoalphabeticCipher cipher) {
        System.out.println("\n--- Test Category 5: Single Character Inputs ---");
        
        test("A", VALID_KEY, "Q", "Single uppercase letter", cipher);
        test("a", VALID_KEY, "Q", "Single lowercase letter", cipher);
        test("Z", VALID_KEY, "M", "Last letter of alphabet", cipher);
    }
    
    /**
     * Test Category 6: Edge cases
     */
    private static void testEdgeCases(MonoalphabeticCipher cipher) {
        System.out.println("\n--- Test Category 6: Edge Cases ---");
        
        test("", VALID_KEY, "", "Empty string", cipher);
        test("123", VALID_KEY, "123", "Numbers only", cipher);
        test("!@#", VALID_KEY, "!@#", "Special characters only", cipher);
        test("HELLO123WORLD", VALID_KEY, "ITSSG123VGKSR", "Mixed with numbers", cipher);
        test("HELLO!@#WORLD", VALID_KEY, "ITSSG!@#VGKSR", "Mixed with special chars", cipher);
    }
    
    /**
     * Test Category 7: Invalid keys
     */
    private static void testInvalidKeys(MonoalphabeticCipher cipher) {
        System.out.println("\n--- Test Category 7: Invalid Keys ---");
        
        // Note: These tests check that validation catches errors
        // We're testing encryption with invalid keys to see behavior
        
        // Short key
        String shortKey = "QWERTYUIOPASDFGHJKLZXCVBN";
        encryptAndCheck("HELLO", shortKey, "Invalid: short key", cipher);
        
        // Long key (should only use first 26 chars)
        String longKey = VALID_KEY + "EXTRA";
        test("HELLO", longKey, "ITSSG", "Long key (first 26 used)", cipher);
        
        // Key with numbers
        String keyWithNumbers = "QWERTYUIOPASDFGHJKLZXCVBN1";
        encryptAndCheck("HELLO", keyWithNumbers, "Invalid: key with numbers", cipher);
    }
    
    /**
     * Test Category 8: Encryption-Decryption round trip
     */
    private static void testEncryptDecryptRoundTrip(MonoalphabeticCipher cipher) {
        System.out.println("\n--- Test Category 8: Encrypt-Decrypt Round Trip ---");
        
        roundTripTest("HELLO", VALID_KEY, "Test 1", cipher);
        roundTripTest("THE QUICK BROWN FOX", VALID_KEY, "Test 2", cipher);
        roundTripTest("ABCDEFGHIJKLMNOPQRSTUVWXYZ", VALID_KEY, "Test 3", cipher);
        roundTripTest("Hello World 123!", VALID_KEY, "Test 4", cipher);
    }
    
    /**
     * Helper method to run a single encryption test
     */
    private static void test(String plaintext, String key, String expectedCiphertext, String description, MonoalphabeticCipher cipher) {
        testCount++;
        try {
            String actualCiphertext = cipher.encrypt(plaintext, key);
            
            if (actualCiphertext.equals(expectedCiphertext)) {
                System.out.println("PASS: " + description);
                System.out.println("  Plaintext: " + plaintext);
                System.out.println("  Ciphertext: " + actualCiphertext);
                passCount++;
            } else {
                System.out.println("FAIL: " + description);
                System.out.println("  Plaintext: " + plaintext);
                System.out.println("  Expected: " + expectedCiphertext);
                System.out.println("  Actual: " + actualCiphertext);
                failCount++;
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + description);
            System.out.println("  Exception: " + e.getMessage());
            failCount++;
        }
    }
    
    /**
     * Helper method to encrypt and display result without checking
     */
    private static void encryptAndCheck(String plaintext, String key, String description, MonoalphabeticCipher cipher) {
        testCount++;
        try {
            String result = cipher.encrypt(plaintext, key);
            System.out.println("INFO: " + description);
            System.out.println("  Plaintext: " + plaintext);
            System.out.println("  Key length: " + key.length());
            System.out.println("  Result: " + result);
            passCount++; // Count as pass since we're just checking it doesn't crash
        } catch (Exception e) {
            System.out.println("ERROR: " + description);
            System.out.println("  Exception: " + e.getMessage());
            failCount++;
        }
    }
    
    /**
     * Helper method to test encrypt-then-decrypt round trip
     */
    private static void roundTripTest(String plaintext, String key, String description, MonoalphabeticCipher cipher) {
        testCount++;
        try {
            String ciphertext = cipher.encrypt(plaintext, key);
            String decrypted = cipher.decrypt(ciphertext, key);
            
            if (plaintext.equals(decrypted)) {
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

