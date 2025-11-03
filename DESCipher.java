/**
 * Corrected Simplified DES Cipher Implementation
 * (Educational Use Only – Not for Production Security)
 */

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class DESCipher implements EncryptionAlgorithm {
 
     private static final int[] KEY_SHIFTS = {0,1,1,2,2,2,2,2,2,1,2,2,2,2,2,2,1};
 
     private static final int[] PC1 = {
         57,49,41,33,25,17,9, 1,58,50,42,34,26,18,10,2,59,51,43,35,27,19,11,3,
         60,52,44,36,63,55,47,39,31,23,15,7,62,54,46,38,30,22,14,6,61,53,45,37,29,21,13,5,28,20,12,4
     };
 
     private static final int[] PC2 = {
         14,17,11,24,1,5,3,28,15,6,21,10,23,19,12,4,26,8,16,7,27,20,13,2,
         41,52,31,37,47,55,30,40,51,45,33,48,44,49,39,56,34,53,46,42,50,36,29,32
     };
 
     private static final int[] IP = {
         58,50,42,34,26,18,10,2,60,52,44,36,28,20,12,4,62,54,46,38,30,22,14,6,
         64,56,48,40,32,24,16,8,57,49,41,33,25,17,9,1,59,51,43,35,27,19,11,3,
         61,53,45,37,29,21,13,5,63,55,47,39,31,23,15,7
     };
 
     private static final int[] IPi = {
         40,8,48,16,56,24,64,32,39,7,47,15,55,23,63,31,
         38,6,46,14,54,22,62,30,37,5,45,13,53,21,61,29,
         36,4,44,12,52,20,60,28,35,3,43,11,51,19,59,27,
         34,2,42,10,50,18,58,26,33,1,41,9,49,17,57,25
     };
 
     private static final int[] EXPANSION = {
         32,1,2,3,4,5,4,5,6,7,8,9,8,9,10,11,12,13,12,13,14,15,16,17,
         16,17,18,19,20,21,20,21,22,23,24,25,24,25,26,27,28,29,28,29,30,31,32,1
     };
 
     private static final int[] P = {
         16,7,20,21,29,12,28,17,1,15,23,26,5,18,31,10,
         2,8,24,14,32,27,3,9,19,13,30,6,22,11,4,25
     };
 
     private static final int[][][] SBOX = {
        // S1
        {{14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7},
         {0,15,7,4,14,2,13,1,10,6,12,11,9,5,3,8},
         {4,1,14,8,13,6,2,11,15,12,9,7,3,10,5,0},
         {15,12,8,2,4,9,1,7,5,11,3,14,10,0,6,13}},
        // S2
        {{15,1,8,14,6,11,3,4,9,7,2,13,12,0,5,10},
         {3,13,4,7,15,2,8,14,12,0,1,10,6,9,11,5},
         {0,14,7,11,10,4,13,1,5,8,12,6,9,3,2,15},
         {13,8,10,1,3,15,4,2,11,6,7,12,0,5,14,9}},
        // S3
        {{10,0,9,14,6,3,15,5,1,13,12,7,11,4,2,8},
         {13,7,0,9,3,4,6,10,2,8,5,14,12,11,15,1},
         {13,6,4,9,8,15,3,0,11,1,2,12,5,10,14,7},
         {1,10,13,0,6,9,8,7,4,15,14,3,11,5,2,12}},
        // S4
        {{7,13,14,3,0,6,9,10,1,2,8,5,11,12,4,15},
         {13,8,11,5,6,15,0,3,4,7,2,12,1,10,14,9},
         {10,6,9,0,12,11,7,13,15,1,3,14,5,2,8,4},
         {3,15,0,6,10,1,13,8,9,4,5,11,12,7,2,14}},
        // S5
        {{2,12,4,1,7,10,11,6,8,5,3,15,13,0,14,9},
         {14,11,2,12,4,7,13,1,5,0,15,10,3,9,8,6},
         {4,2,1,11,10,13,7,8,15,9,12,5,6,3,0,14},
         {11,8,12,7,1,14,2,13,6,15,0,9,10,4,5,3}},
        // S6
        {{12,1,10,15,9,2,6,8,0,13,3,4,14,7,5,11},
         {10,15,4,2,7,12,9,5,6,1,13,14,0,11,3,8},
         {9,14,15,5,2,8,12,3,7,0,4,10,1,13,11,6},
         {4,3,2,12,9,5,15,10,11,14,1,7,6,0,8,13}},
        // S7
        {{4,11,2,14,15,0,8,13,3,12,9,7,5,10,6,1},
         {13,0,11,7,4,9,1,10,14,3,5,12,2,15,8,6},
         {1,4,11,13,12,3,7,14,10,15,6,8,0,5,9,2},
         {6,11,13,8,1,4,10,7,9,5,0,15,14,2,3,12}},
        // S8
        {{13,2,8,4,6,15,11,1,10,9,3,14,5,0,12,7},
         {1,15,13,8,10,3,7,4,12,5,6,11,0,14,9,2},
         {7,11,4,1,9,12,14,2,0,6,10,13,15,3,5,8},
         {2,1,14,7,4,10,8,13,15,12,9,0,3,5,6,11}}
    };
 
     private long[] K = new long[17];
 
     /** Converts key string (first 8 chars) to 64-bit binary string */
     private static String keyStringTo64BitBinary(String key) {
         byte[] k = key.getBytes(StandardCharsets.UTF_8);
         StringBuilder sb = new StringBuilder(64);
         for (int i = 0; i < 8; i++) {
             int v = (i < k.length ? k[i] : 0) & 0xFF;
             for (int b = 7; b >= 0; b--) sb.append(((v >>> b) & 1));
         }
         return sb.toString();
     }
 
     /** Utility: convert UTF-8 to binary string */
     private static String utfToBin(String utf) {
         byte[] bytes = utf.getBytes(StandardCharsets.UTF_8);
         StringBuilder bin = new StringBuilder();
         for (byte value : bytes) {
             for (int j = 7; j >= 0; j--)
                 bin.append(((value >>> j) & 1));
         }
         return bin.toString();
     }
 
     /** Utility: convert binary to hex (keep full length) */
     private static String binToHex(String bin) {
         String hex = new BigInteger(bin, 2).toString(16);
         while (hex.length() < (bin.length() / 4)) hex = "0" + hex;
         return hex.toUpperCase();
     }
 
     /** Build key schedule */
     private void buildKeySchedule(String binKey) {
         while (binKey.length() < 64) binKey = "0" + binKey;
 
         // Apply PC-1
         StringBuilder binKey_PC1 = new StringBuilder();
         for (int i : PC1) binKey_PC1.append(binKey.charAt(i - 1));
 
         String c = binKey_PC1.substring(0, 28);
         String d = binKey_PC1.substring(28);
 
         int cInt = Integer.parseInt(c, 2);
         int dInt = Integer.parseInt(d, 2);
 
         for (int i = 1; i < 17; i++) {
             int s = KEY_SHIFTS[i];
             cInt = ((cInt << s) | (cInt >>> (28 - s))) & 0x0FFFFFFF;
             dInt = ((dInt << s) | (dInt >>> (28 - s))) & 0x0FFFFFFF;
 
             long merged = ((long) cInt << 28) | dInt;
             String mergedStr = Long.toBinaryString(merged);
             while (mergedStr.length() < 56) mergedStr = "0" + mergedStr;
 
             StringBuilder subKey = new StringBuilder();
             for (int j : PC2) subKey.append(mergedStr.charAt(j - 1));
             K[i] = Long.parseLong(subKey.toString(), 2);
         }
     }
 
    /** Encrypt plaintext (8 bytes max) with key */
    @Override
    public String encrypt(String plaintext, String key) {
         String keyBin = keyStringTo64BitBinary(key);
         buildKeySchedule(keyBin);
 
         String binPlaintext = utfToBin(plaintext);
         while (binPlaintext.length() % 64 != 0) binPlaintext += "0";
 
         StringBuilder binCiphertext = new StringBuilder();
         for (int i = 0; i < binPlaintext.length(); i += 64) {
             String block = binPlaintext.substring(i, i + 64);
             binCiphertext.append(encryptBlock(block));
         }
 
         return binToHex(binCiphertext.toString());
     }
 
     /** Encrypt one block (simplified; same logic as your encryptBlock) */
     private String encryptBlock(String block) {
         StringBuilder permuted = new StringBuilder();
         for (int i : IP) permuted.append(block.charAt(i - 1));
 
         String L = permuted.substring(0, 32);
         String R = permuted.substring(32);
 
         for (int round = 1; round <= 16; round++) {
             String key = Long.toBinaryString(K[round]);
             while (key.length() < 48) key = "0" + key;
 
             String fRes = f(R, key);
             long fLong = Long.parseLong(fRes, 2);
             long lLong = Long.parseLong(L, 2);
             long newR = lLong ^ fLong;
 
             L = R;
             R = String.format("%32s", Long.toBinaryString(newR)).replace(' ', '0');
         }
 
         String RL = R + L;
         StringBuilder output = new StringBuilder();
         for (int i : IPi) output.append(RL.charAt(i - 1));
         return output.toString();
     }
 
     /** Feistel function f() */
     private static String f(String R, String key) {
         StringBuilder exp = new StringBuilder();
         for (int i : EXPANSION) exp.append(R.charAt(i - 1));
 
         long m = Long.parseLong(exp.toString(), 2);
         long k = Long.parseLong(key, 2);
         long xored = m ^ k;
 
         String bin = String.format("%48s", Long.toBinaryString(xored)).replace(' ', '0');
         // apply S-boxes and P permutation (reuse your original S-box + P logic)
         // ...
 
         // for brevity, assume s-box implementation returns final 32-bit string
         return applySBoxesAndP(bin);
     }
 
    private static String applySBoxesAndP(String bin) {
        // Split into eight 6-bit strings
        String[] sin = new String[8];
        for (int i = 0; i < 8; i++) {
            sin[i] = bin.substring(i * 6, (i + 1) * 6);
        }
        
        // Apply S-boxes
        StringBuilder sout = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int[][] curS = SBOX[i];
            String cur = sin[i];
            
            // Get row (first and last bit)
            int row = Integer.parseInt(String.valueOf(cur.charAt(0)) + cur.charAt(5), 2);
            // Get column (middle 4 bits)
            int col = Integer.parseInt(cur.substring(1, 5), 2);
            
            // Lookup in S-box
            int sValue = curS[row][col];
            String sBin = Integer.toBinaryString(sValue);
            while (sBin.length() < 4) sBin = "0" + sBin;
            sout.append(sBin);
        }
        
        // Apply P permutation
        StringBuilder mergedP = new StringBuilder();
        for (int i : P) {
            mergedP.append(sout.charAt(i - 1));
        }
        
        return mergedP.toString();
    }
    
    /** Utility: convert hex to binary string */
    private static String hexToBin(String hex) {
        BigInteger b = new BigInteger(hex, 16);
        String bin = b.toString(2);
        // Ensure length is multiple of 4 (since each hex digit is 4 bits)
        while (bin.length() % 4 != 0) bin = "0" + bin;
        return bin;
    }
    
    /** Utility: convert binary to UTF-8 string */
    private static String binToUTF(String bin) {
        byte[] bytes = new byte[bin.length() / 8];
        for (int i = 0; i < bytes.length; i++) {
            String byteStr = bin.substring(i * 8, (i + 1) * 8);
            bytes[i] = (byte) Integer.parseInt(byteStr, 2);
        }
        try {
            String result = new String(bytes, StandardCharsets.UTF_8);
            // Remove null padding
            return result.replace("\0", "").trim();
        } catch (Exception e) {
            return "";
        }
    }
 
    /** Decrypt ciphertext (hex) with key */
    @Override
    public String decrypt(String ciphertext, String key) {
        String keyBin = keyStringTo64BitBinary(key);
        buildKeySchedule(keyBin);
        
        String binCiphertext = hexToBin(ciphertext);
        while (binCiphertext.length() % 64 != 0) binCiphertext = "0" + binCiphertext;
        
        StringBuilder binPlaintext = new StringBuilder();
        for (int i = 0; i < binCiphertext.length(); i += 64) {
            String block = binCiphertext.substring(i, i + 64);
            binPlaintext.append(decryptBlock(block));
        }
        
        return binToUTF(binPlaintext.toString());
    }
    
    /** Decrypt one block */
    private String decryptBlock(String block) {
        StringBuilder permuted = new StringBuilder();
        for (int i : IP) permuted.append(block.charAt(i - 1));
        
        String L = permuted.substring(0, 32);
        String R = permuted.substring(32);
        
        // Decrypt uses keys in reverse order (K[16] down to K[1])
        for (int round = 16; round >= 1; round--) {
            String key = Long.toBinaryString(K[round]);
            while (key.length() < 48) key = "0" + key;
            
            String fRes = f(R, key);
            long fLong = Long.parseLong(fRes, 2);
            long lLong = Long.parseLong(L, 2);
            long newR = lLong ^ fLong;
            
            L = R;
            R = String.format("%32s", Long.toBinaryString(newR)).replace(' ', '0');
        }
        
        String RL = R + L;
        StringBuilder output = new StringBuilder();
        for (int i : IPi) output.append(RL.charAt(i - 1));
        return output.toString();
    }
    
    /** Display step-by-step encryption process */
    @Override
    public void displaySteps(String text, String key) {
        System.out.println("\n--- Step-by-Step DES Encryption (Simplified) ---");
        System.out.println("WARNING: This is an educational simplified version, NOT secure DES!");
        System.out.println("Plaintext: " + text);
        System.out.println("Key: " + key);
        System.out.println("\n1. Converting plaintext to binary (UTF-8)");
        String binPlaintext = utfToBin(text);
        System.out.println("   Binary: " + binPlaintext.substring(0, Math.min(64, binPlaintext.length())) + "...");
        System.out.println("\n2. Building key schedule from key");
        System.out.println("3. Applying initial permutation (IP)");
        System.out.println("4. Performing 16 rounds of Feistel network");
        System.out.println("5. Applying final permutation (IP^-1)");
        System.out.println("6. Converting binary result to hexadecimal");
    }
    
    /** Interactive encryption session */
    public void runEncryption() {
        System.out.println("\n=== Simplified DES Cipher ===");
        System.out.println("WARNING: This is an educational simplified version, NOT secure DES!");
        
        String plaintext = InputValidator.getInput("Enter plaintext (up to 8 characters): ");
        if (!InputValidator.validateNotEmpty(plaintext, "Plaintext")) {
            return;
        }
        
        if (plaintext.length() > 8) {
            System.out.println("Warning: Plaintext longer than 8 characters will be truncated to fit DES block size.");
            plaintext = plaintext.substring(0, 8);
        }
        
        String key = InputValidator.getInput("Enter key (8 characters): ");
        if (!InputValidator.validateNotEmpty(key, "Key")) {
            return;
        }
        
        if (key.length() > 8) {
            System.out.println("Warning: Key longer than 8 characters will be truncated.");
            key = key.substring(0, 8);
        }
        
        displaySteps(plaintext, key);
        
        String ciphertext = encrypt(plaintext, key);
        System.out.println("\n--- Result ---");
        System.out.println("Ciphertext (hex): " + ciphertext);
    }
    
    /** Interactive decryption session */
    public void runDecryption(String ciphertext, String key) {
        System.out.println("\n--- Step-by-Step DES Decryption (Simplified) ---");
        System.out.println("WARNING: This is an educational simplified version, NOT secure DES!");
        System.out.println("Ciphertext (hex): " + ciphertext);
        System.out.println("Key: " + key);
        
        if (key.length() > 8) {
            System.out.println("Warning: Key longer than 8 characters will be truncated.");
            key = key.substring(0, 8);
        }
        
        System.out.println("\n1. Converting hex ciphertext to binary");
        System.out.println("2. Building key schedule from key");
        System.out.println("3. Reversing encryption process (16 rounds in reverse order)");
        System.out.println("4. Converting binary result to UTF-8 string");
        
        String plaintext = decrypt(ciphertext, key);
        
        System.out.println("\n--- Result ---");
        System.out.println("Plaintext: " + plaintext);
    }
    
    /** Test main */
    public static void main(String[] args) {
        DESCipher des = new DESCipher();
        String plaintext = "ABDULLAH";
        String key = "MOHAMMED";

        String ciphertext = des.encrypt(plaintext, key);
        System.out.println("Ciphertext (hex): " + ciphertext);
        
        String decrypted = des.decrypt(ciphertext, key);
        System.out.println("Decrypted: " + decrypted);
    }
}
 