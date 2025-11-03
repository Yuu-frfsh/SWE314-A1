/**
 * Unit Tests for MonoalphabeticCipher (Caesar Cipher)
 * Using Input Space Partitioning approach
 */
public class MonoalphabeticTest {
    
    private static int testCount = 0;
    private static int passCount = 0;
    private static int failCount = 0;
    
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("MonoalphabeticCipher (Caesar) Unit Tests");
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
        testLargeShifts(cipher);
        testEncryptDecryptRoundTrip(cipher);
        
        // Print summary
        printSummary();
    }
    
    /**
     * Test Category 1: Valid uppercase inputs with various shifts
     */
    private static void testValidUppercase(MonoalphabeticCipher cipher) {
        System.out.println("--- Test Category 1: Valid Uppercase Inputs ---");
        
        test("HELLO", "3", "KHOOR", "Simple word, shift=3", cipher);
        test("WORLD", "3", "ZRUOG", "Common word, shift=3", cipher);
        test("ABC", "1", "BCD", "Alphabet start, shift=1", cipher);
        test("XYZ", "2", "ZAB", "Alphabet end, shift=2 (wraps)", cipher);
        test("HELLO", "-3", "EBIIL", "Simple word, shift=-3", cipher);
    }
    
    /**
     * Test Category 2: Valid lowercase inputs
     */
    private static void testValidLowercase(MonoalphabeticCipher cipher) {
        System.out.println("\n--- Test Category 2: Valid Lowercase Inputs ---");
        
        test("hello", "3", "KHOOR", "Simple lowercase word, shift=3", cipher);
        test("world", "3", "ZRUOG", "Mixed lowercase, shift=3", cipher);
        test("abc", "-1", "ZAB", "Lowercase shift=-1 (wraps)", cipher);
    }
    
    /**
     * Test Category 3: Valid mixed case inputs
     */
    private static void testValidMixedCase(MonoalphabeticCipher cipher) {
        System.out.println("\n--- Test Category 3: Valid Mixed Case Inputs ---");
        
        test("HeLLo", "3", "KHOOR", "Mixed case word, shift=3", cipher);
        test("WoRLd", "3", "ZRUOG", "Mixed case word 2, shift=3", cipher);
        test("AbC", "-2", "YZA", "Mixed case shift=-2 (wraps)", cipher);
    }
    
    /**
     * Test Category 4: Valid inputs with spaces
     */
    private static void testValidWithSpaces(MonoalphabeticCipher cipher) {
        System.out.println("\n--- Test Category 4: Valid Inputs with Spaces ---");
        
        test("HELLO WORLD", "3", "KHOOR ZRUOG", "Words with space, shift=3", cipher);
        test("  ABC  ", "1", "  BCD  ", "Spaces around text, shift=1", cipher);
        test("TEST MESSAGE", "-5", "OZNO HZNNVBZ", "Words with spaces, shift=-5", cipher);
    }
    
    /**
     * Test Category 5: Single character inputs
     */
    private static void testValidSingleChar(MonoalphabeticCipher cipher) {
        System.out.println("\n--- Test Category 5: Single Character Inputs ---");
        
        test("A", "1", "B", "Single uppercase letter, shift=1", cipher);
        test("a", "1", "B", "Single lowercase letter, shift=1", cipher);
        test("Z", "1", "A", "Last letter of alphabet, shift=1 (wraps)", cipher);
        test("A", "-1", "Z", "First letter, shift=-1 (wraps)", cipher);
        test("A", "0", "A", "Shift=0 (identity)", cipher);
    }
    
    /**
     * Test Category 6: Edge cases
     */
    private static void testEdgeCases(MonoalphabeticCipher cipher) {
        System.out.println("\n--- Test Category 6: Edge Cases ---");
        
        test("", "3", "", "Empty string", cipher);
        test("123", "3", "123", "Numbers only", cipher);
        test("!@#", "3", "!@#", "Special characters only", cipher);
        test("HELLO123WORLD", "3", "KHOOR123ZRUOG", "Mixed with numbers, shift=3", cipher);
        test("HELLO!@#WORLD", "3", "KHOOR!@#ZRUOG", "Mixed with special chars, shift=3", cipher);
    }
    
    /**
     * Test Category 7: Large shifts (should normalize)
     */
    private static void testLargeShifts(MonoalphabeticCipher cipher) {
        System.out.println("\n--- Test Category 7: Large Shifts (>26 or <-26) ---");
        
        test("ABC", "27", "BCD", "Shift=27 (normalized to 1)", cipher);
        test("HELLO", "29", "KHOOR", "Shift=29 (normalized to 3)", cipher);
        test("XYZ", "52", "XYZ", "Shift=52 (normalized to 0)", cipher);
        test("HELLO", "-27", "GDKKN", "Shift=-27 (normalized to -1)", cipher);
        test("HELLO", "-30", "DAHHK", "Shift=-30 (normalized to -4)", cipher);
        test("ABC", "-26", "ABC", "Shift=-26 (normalized to 0)", cipher);
    }
    
    /**
     * Test Category 8: Encryption-Decryption round trip
     */
    private static void testEncryptDecryptRoundTrip(MonoalphabeticCipher cipher) {
        System.out.println("\n--- Test Category 8: Encrypt-Decrypt Round Trip ---");
        
        roundTripTest("HELLO", "3", "Test 1", cipher);
        roundTripTest("THE QUICK BROWN FOX", "5", "Test 2", cipher);
        roundTripTest("ABCDEFGHIJKLMNOPQRSTUVWXYZ", "13", "Test 3", cipher);
        roundTripTest("Hello World 123!", "-7", "Test 4", cipher);
        roundTripTest("TEST", "27", "Test 5 (large shift)", cipher);
        roundTripTest("TEST", "-53", "Test 6 (large negative shift)", cipher);
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
                System.out.println("  Key: " + key);
                System.out.println("  Ciphertext: " + actualCiphertext);
                passCount++;
            } else {
                System.out.println("FAIL: " + description);
                System.out.println("  Plaintext: " + plaintext);
                System.out.println("  Key: " + key);
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
     * Helper method to test encrypt-then-decrypt round trip
     */
    private static void roundTripTest(String plaintext, String key, String description, MonoalphabeticCipher cipher) {
        testCount++;
        try {
            String ciphertext = cipher.encrypt(plaintext, key);
            String decrypted = cipher.decrypt(ciphertext, key);
            
            if (plaintext.toUpperCase().equals(decrypted)) {
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
