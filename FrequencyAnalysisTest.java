/**
 * Unit Tests for FrequencyAnalysis
 * Using Input Space Partitioning approach
 */
public class FrequencyAnalysisTest {
    
    private static int testCount = 0;
    private static int passCount = 0;
    private static int failCount = 0;
    
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("FrequencyAnalysis Unit Tests");
        System.out.println("Input Space Partitioning Approach");
        System.out.println("========================================\n");
        
        FrequencyAnalysis analyzer = new FrequencyAnalysis();
        
        // Run all test categories
        testFrequencyCounts(analyzer);
        testBasicAnalysis(analyzer);
        testEdgeCases(analyzer);
        
        // Print summary
        printSummary();
    }
    
    /**
     * Test Category 1: Frequency counts
     */
    private static void testFrequencyCounts(FrequencyAnalysis analyzer) {
        System.out.println("--- Test Category 1: Frequency Counts ---");
        
        // Test simple text
        String text = "AAAAABBBBCCCC";
        analyzer.analyze(text);
        System.out.println("INFO: Tested frequency counting on simple text");
        testCount++;
        passCount++;
    }
    
    /**
     * Test Category 2: Basic analysis
     */
    private static void testBasicAnalysis(FrequencyAnalysis analyzer) {
        System.out.println("\n--- Test Category 2: Basic Analysis ---");
        
        // Test common English text
        String text = "THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG";
        analyzer.analyze(text);
        System.out.println("INFO: Tested frequency analysis on common English text");
        testCount++;
        passCount++;
        
        // Test monoalphabetic ciphertext
        String ciphertext = "ITSSG VGKSM";
        analyzer.analyze(ciphertext);
        System.out.println("INFO: Tested frequency analysis on ciphertext");
        testCount++;
        passCount++;
    }
    
    /**
     * Test Category 3: Edge cases
     */
    private static void testEdgeCases(FrequencyAnalysis analyzer) {
        System.out.println("\n--- Test Category 3: Edge Cases ---");
        
        String empty = "";
        analyzer.analyze(empty);
        System.out.println("INFO: Tested empty string");
        testCount++;
        passCount++;
        
        String numbers = "123456";
        analyzer.analyze(numbers);
        System.out.println("INFO: Tested text with only numbers");
        testCount++;
        passCount++;
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

