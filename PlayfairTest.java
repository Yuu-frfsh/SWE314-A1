/**
 * Unit Tests for PlayfairCipher
 * Using Input Space Partitioning approach
 */
public class PlayfairTest {
    
    private static int testCount = 0;
    private static int passCount = 0;
    private static int failCount = 0;
    
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("PlayfairCipher Unit Tests");
        System.out.println("Input Space Partitioning Approach");
        System.out.println("========================================\n");
        
        PlayfairCipher cipher = new PlayfairCipher();
        
        // Run all test categories
        testValidInputs(cipher);
        testDifferentKeys(cipher);
        testSpecialCases(cipher);
        testEdgeCases(cipher);
        testEncryptDecryptRoundTrip(cipher);
        
        // Print summary
        printSummary();
    }
    
    /**
     * Test Category 1: Valid inputs
     */
    private static void testValidInputs(PlayfairCipher cipher) {
        System.out.println("--- Test Category 1: Valid Inputs ---");
        
        test("HELLO", "PLAYFAIR", "KBQAVKSOY", "Simple word", cipher);
        test("INSTRUMENTS", "MONARCHY", "GATLMZCLRQXA", "Known Playfair example", cipher);
        test("ABCDEFG", "EXAMPLE", "XORANGBG", "Multiple digraphs", cipher);
    }
    
    /**
     * Test Category 2: Different keys
     */
    private static void testDifferentKeys(PlayfairCipher cipher) {
        System.out.println("\n--- Test Category 2: Different Keys ---");
        
        test("HELLO", "A", "IBDBC", "Single letter key", cipher);
        test("HELLO", "THEQUICKBROWN", "YBLLB", "Long key", cipher);
        test("TEST", "KEYWORD", "XYWB", "Short key", cipher);
    }
    
    /**
     * Test Category 3: Special cases
     */
    private static void testSpecialCases(PlayfairCipher cipher) {
        System.out.println("\n--- Test Category 3: Special Cases ---");
        
        test("hello", "PLAYFAIR", "KBQAVKSOY", "Lowercase input", cipher);
        test("HeLLo", "PLAYFAIR", "KBQAVKSOY", "Mixed case input", cipher);
        test("AA", "PLAYFAIR", "XS", "Double letter", cipher);
        test("HELLOJ", "PLAYFAIR", "KBQAVKSOOX", "Contains J (treats as I)", cipher);
    }
    
    /**
     * Test Category 4: Edge cases
     */
    private static void testEdgeCases(PlayfairCipher cipher) {
        System.out.println("\n--- Test Category 4: Edge Cases ---");
        
        test("", "PLAYFAIR", "", "Empty string", cipher);
        test("A", "PLAYFAIR", "X", "Single character", cipher);
        test("HELLO123", "PLAYFAIR", "KBQAVKSOY", "With numbers (removed)", cipher);
    }
    
    /**
     * Test Category 5: Encrypt-Decrypt round trip
     */
    private static void testEncryptDecryptRoundTrip(PlayfairCipher cipher) {
        System.out.println("\n--- Test Category 5: Encrypt-Decrypt Round Trip ---");
        
        roundTripTest("HELLO", "PLAYFAIR", "Test 1", cipher);
        roundTripTest("INSTRUMENTS", "MONARCHY", "Test 2", cipher);
        roundTripTest("ABCDEFGHIJKLMNOPQRSTUVWXYZ", "KEYWORD", "Test 3", cipher);
        roundTripTest("THEQUICKBROWN", "EXAMPLE", "Test 4", cipher);
    }
    
    /**
     * Helper method to run a single encryption test
     */
    private static void test(String plaintext, String key, String expectedCiphertext, String description, PlayfairCipher cipher) {
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
            e.printStackTrace();
            failCount++;
        }
    }
    
    /**
     * Helper method to test encrypt-then-decrypt round trip
     */
    private static void roundTripTest(String plaintext, String key, String description, PlayfairCipher cipher) {
        testCount++;
        try {
            String ciphertext = cipher.encrypt(plaintext, key);
            String decrypted = cipher.decrypt(ciphertext, key);
            
            // Playfair may have padding Xs that need to be removed for comparison
            String cleanOriginal = plaintext.toUpperCase().replaceAll("[^A-Z]", "");
            String cleanDecrypted = decrypted.replaceAll("X$", ""); // Remove trailing X from padding
            
            // Check if decrypted matches original (ignoring case and padding)
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

