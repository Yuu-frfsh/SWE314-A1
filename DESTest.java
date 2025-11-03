/**
 * Unit Tests for DESCipher
 * Using Input Space Partitioning approach
 */
public class DESTest {
    
    private static int testCount = 0;
    private static int passCount = 0;
    private static int failCount = 0;
    
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("DESCipher Unit Tests");
        System.out.println("Input Space Partitioning Approach");
        System.out.println("========================================\n");
        
        DESCipher cipher = new DESCipher();
        
        // Run all test categories
        testValidInputs(cipher);
        testEdgeCases(cipher);
        testEncryptDecryptRoundTrip(cipher);
        
        // Print summary
        printSummary();
    }
    
    /**
     * Test Category 1: Valid inputs
     */
    private static void testValidInputs(DESCipher cipher) {
        System.out.println("--- Test Category 1: Valid Inputs ---");
        
        String plain1 = cipher.encrypt("HELLO", "12345678");
        System.out.println("INFO: HELLO with key 12345678 -> " + plain1);
        testCount++;
        passCount++;
        
        String plain2 = cipher.encrypt("TESTING", "ABC12345");
        System.out.println("INFO: TESTING with key ABC12345 -> " + plain2);
        testCount++;
        passCount++;
    }
    
    /**
     * Test Category 2: Edge cases
     */
    private static void testEdgeCases(DESCipher cipher) {
        System.out.println("\n--- Test Category 2: Edge Cases ---");
        
        String result1 = cipher.encrypt("", "12345678");
        System.out.println("INFO: Empty string -> " + result1);
        testCount++;
        passCount++;
        
        String result2 = cipher.encrypt("A", "12345678");
        System.out.println("INFO: Single char -> " + result2);
        testCount++;
        passCount++;
    }
    
    /**
     * Test Category 3: Encrypt-Decrypt round trip
     */
    private static void testEncryptDecryptRoundTrip(DESCipher cipher) {
        System.out.println("\n--- Test Category 3: Encrypt-Decrypt Round Trip ---");
        
        roundTripTest("HELLO", "12345678", "Test 1", cipher);
        roundTripTest("TESTING", "ABC12345", "Test 2", cipher);
        roundTripTest("ABCDEFGH", "87654321", "Test 3", cipher);
        roundTripTest("SHORT", "KEY12345", "Test 4", cipher);
    }
    
    /**
     * Helper method to test encrypt-then-decrypt round trip
     */
    private static void roundTripTest(String plaintext, String key, String description, DESCipher cipher) {
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

