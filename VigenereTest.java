/**
 * Unit Tests for VigenereCipher
 * Using Input Space Partitioning approach
 */
public class VigenereTest {
    
    private static int testCount = 0;
    private static int passCount = 0;
    private static int failCount = 0;
    
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("VigenereCipher Unit Tests");
        System.out.println("Input Space Partitioning Approach");
        System.out.println("========================================\n");
        
        VigenereCipher cipher = new VigenereCipher();
        
        // Run all test categories
        testValidInputs(cipher);
        testDifferentKeyLengths(cipher);
        testSpecialCases(cipher);
        testEdgeCases(cipher);
        testEncryptDecryptRoundTrip(cipher);
        
        // Print summary
        printSummary();
    }
    
    /**
     * Test Category 1: Valid inputs
     */
    private static void testValidInputs(VigenereCipher cipher) {
        System.out.println("--- Test Category 1: Valid Inputs ---");
        
        test("HELLO", "KEY", "RIJVS", "Simple word with short key", cipher);
        test("ATTACKATDAWN", "LEMON", "LXFOPVEFRNHR", "Known Vigenere example", cipher);
        test("ABCDEFGHIJKLMNOPQRSTUVWXYZ", "ABC", "ACEDFHGIKJLNMOQPRTSUWVXZYA", "Alphabet with ABC key", cipher);
    }
    
    /**
     * Test Category 2: Different key lengths
     */
    private static void testDifferentKeyLengths(VigenereCipher cipher) {
        System.out.println("\n--- Test Category 2: Different Key Lengths ---");
        
        test("HELLO", "A", "HELLO", "Single letter key", cipher);
        test("HELLO", "THEQUICKBROWN", "ALPBI", "Long key", cipher);
        test("HELLOWORLD", "KEY", "RIJVSUYVJN", "Key shorter than text", cipher);
    }
    
    /**
     * Test Category 3: Special cases with spaces and mixed case
     */
    private static void testSpecialCases(VigenereCipher cipher) {
        System.out.println("\n--- Test Category 3: Special Cases ---");
        
        test("HELLO WORLD", "KEY", "RIJVS UYVJN", "Text with spaces", cipher);
        test("hello world", "KEY", "RIJVS UYVJN", "Lowercase input", cipher);
        test("HeLLo WoRLd", "KEY", "RIJVS UYVJN", "Mixed case input", cipher);
    }
    
    /**
     * Test Category 4: Edge cases
     */
    private static void testEdgeCases(VigenereCipher cipher) {
        System.out.println("\n--- Test Category 4: Edge Cases ---");
        
        test("", "KEY", "", "Empty string", cipher);
        test("HELLO", "", "HELLO", "Empty key", cipher);
        test("123", "KEY", "123", "Numbers only", cipher);
        test("!@#", "KEY", "!@#", "Special characters only", cipher);
        test("HELLO123WORLD", "KEY", "RIJVS123UYVJN", "Mixed with numbers", cipher);
        test("HELLO!@#WORLD", "KEY", "RIJVS!@#UYVJN", "Mixed with special chars", cipher);
    }
    
    /**
     * Test Category 5: Encrypt-Decrypt round trip
     */
    private static void testEncryptDecryptRoundTrip(VigenereCipher cipher) {
        System.out.println("\n--- Test Category 5: Encrypt-Decrypt Round Trip ---");
        
        roundTripTest("HELLO WORLD", "KEY", "Test 1", cipher);
        roundTripTest("THE QUICK BROWN FOX", "LEMON", "Test 2", cipher);
        roundTripTest("ABCDEFGHIJKLMNOPQRSTUVWXYZ", "SECRET", "Test 3", cipher);
        roundTripTest("ATTACK AT DAWN", "VIGENERE", "Test 4", cipher);
    }
    
    /**
     * Helper method to run a single encryption test
     */
    private static void test(String plaintext, String key, String expectedCiphertext, String description, VigenereCipher cipher) {
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
    private static void roundTripTest(String plaintext, String key, String description, VigenereCipher cipher) {
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

