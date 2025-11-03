/**
 * Unit Tests for KeyedTranspositionCipher
 * Using Input Space Partitioning approach
 */
public class KeyedTranspositionTest {
    
    private static int testCount = 0;
    private static int passCount = 0;
    private static int failCount = 0;
    
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("KeyedTranspositionCipher Unit Tests");
        System.out.println("Input Space Partitioning Approach");
        System.out.println("========================================\n");
        
        KeyedTranspositionCipher cipher = new KeyedTranspositionCipher();
        
        // Run all test categories
        testValidInputs(cipher);
        testDifferentKeys(cipher);
        testEdgeCases(cipher);
        testEncryptDecryptRoundTrip(cipher);
        
        // Print summary
        printSummary();
    }
    
    /**
     * Test Category 1: Valid inputs
     */
    private static void testValidInputs(KeyedTranspositionCipher cipher) {
        System.out.println("--- Test Category 1: Valid Inputs ---");
        
        test("HELLO", "3 1 4 2 5", "LHOEL", "Simple word with numeric key", cipher);
        test("THEQUICKBROWN", "3 1 4 2 5", "EQBRUNHCKIOTW", "Longer text", cipher);
    }
    
    /**
     * Test Category 2: Different keys
     */
    private static void testDifferentKeys(KeyedTranspositionCipher cipher) {
        System.out.println("\n--- Test Category 2: Different Keys ---");
        
        test("HELLO", "1 2 3", "HLOEL", "Simple numeric key", cipher);
        test("ABCDEF", "3 2 1", "FCADEB", "Reverse key", cipher);
    }
    
    /**
     * Test Category 3: Edge cases
     */
    private static void testEdgeCases(KeyedTranspositionCipher cipher) {
        System.out.println("\n--- Test Category 3: Edge Cases ---");
        
        test("", "3 1 4 2 5", "", "Empty string", cipher);
        test("A", "3 1 4 2 5", "AX", "Single character", cipher);
    }
    
    /**
     * Test Category 4: Encrypt-Decrypt round trip
     */
    private static void testEncryptDecryptRoundTrip(KeyedTranspositionCipher cipher) {
        System.out.println("\n--- Test Category 4: Encrypt-Decrypt Round Trip ---");
        
        roundTripTest("HELLO WORLD", "3 1 4 2 5", "Test 1", cipher);
        roundTripTest("THE QUICK BROWN FOX", "2 4 1 3", "Test 2", cipher);
        roundTripTest("ABCDEFGHIJKLMNOPQRSTUVWXYZ", "5 3 1 4 2", "Test 3", cipher);
        roundTripTest("TESTING", "1 2 3", "Test 4", cipher);
    }
    
    /**
     * Helper method to run a single encryption test
     */
    private static void test(String plaintext, String key, String expectedCiphertext, String description, KeyedTranspositionCipher cipher) {
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
    private static void roundTripTest(String plaintext, String key, String description, KeyedTranspositionCipher cipher) {
        testCount++;
        try {
            String ciphertext = cipher.encrypt(plaintext, key);
            String decrypted = cipher.decrypt(ciphertext, key);
            
            // Remove spaces for comparison (transposition cipher removes them)
            String cleanOriginal = plaintext.toUpperCase().replaceAll(" ", "");
            String cleanDecrypted = decrypted.replaceAll("X$", ""); // Remove trailing padding
            
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

