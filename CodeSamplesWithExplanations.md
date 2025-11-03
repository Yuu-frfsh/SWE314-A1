# Code Samples with Detailed Explanations
## SWE 314 - Educational Encryption Tool

This document shows actual code from the encryption algorithms with explanations of what each piece does.

---

## Sample 1: Monoalphabetic Cipher (Caesar Cipher)

### Code
```java
public String encrypt(String plaintext, String key) {
    int shift;
    try {
        shift = Integer.parseInt(key.trim());
    } catch (NumberFormatException e) {
        return "Error: Invalid key format. Expected an integer.";
    }
    
    shift = normalizeShift(shift);
    
    plaintext = plaintext.toUpperCase();
    
    StringBuilder ciphertext = new StringBuilder();
    
    for (char c : plaintext.toCharArray()) {
        if (Character.isLetter(c)) {
            int index = ALPHABET.indexOf(c);
            if (index != -1) {
                int shiftedIndex = (index + shift + 26) % 26;
                ciphertext.append(ALPHABET.charAt(shiftedIndex));
            }
        } else {
            ciphertext.append(c);
        }
    }
    
    return ciphertext.toString();
}
```

### Explanation
This code implements the Caesar cipher. It parses the key as an integer and normalizes it so shifts wrap correctly (e.g., 27 -> 1). The plaintext is uppercased. For each letter, it finds the alphabet index, adds the shift, wraps via modulo, and appends the letter at the shifted index. Non-letters are appended unchanged.

---

## Sample 2: Vigenere Cipher

### Code
```java
public String encrypt(String plaintext, String key) {
    plaintext = plaintext.toUpperCase();
    key = key.toUpperCase();
    
    if (key.isEmpty()) {
        return plaintext;
    }
    
    StringBuilder ciphertext = new StringBuilder();
    int keyIndex = 0;
    
    for (char c : plaintext.toCharArray()) {
        if (Character.isLetter(c)) {
            int plaintextPos = ALPHABET.indexOf(c);
            
            char keyChar = key.charAt(keyIndex % key.length());
            int keyPos = ALPHABET.indexOf(keyChar);
            
            int ciphertextPos = (plaintextPos + keyPos) % 26;
            ciphertext.append(ALPHABET.charAt(ciphertextPos));
            
            keyIndex++;
        } else {
            ciphertext.append(c);
        }
    }
    
    return ciphertext.toString();
}
```

### Explanation
This code encrypts with the Vigenère cipher. Both inputs are uppercased. For each plaintext letter, it takes the letter from the repeating keyword (wrapped with modulo), adds the positions, wraps with modulo 26, and appends the letter at the result index. The key index advances only for letters; non-letters are copied unchanged.

---

## Sample 3: Keyed Transposition Cipher

### Code
```java
public String encrypt(String plaintext, String key) {
    String[] keyLines = key.trim().split("\n");
    if (keyLines.length < 2) {
        keyLines = key.trim().split("\\|");
    }
    if (keyLines.length < 2) {
        return plaintext;
    }
    
    String[] plaintextIndicesStr = keyLines[0].trim().split("\\s+");
    String[] ciphertextPositionsStr = keyLines[1].trim().split("\\s+");
    
    if (plaintextIndicesStr.length != 5 || ciphertextPositionsStr.length != 5) {
        return plaintext;
    }
    
    int[] plaintextIndices = new int[5];
    int[] ciphertextPositions = new int[5];
    
    try {
        for (int i = 0; i < 5; i++) {
            plaintextIndices[i] = Integer.parseInt(plaintextIndicesStr[i]);
            ciphertextPositions[i] = Integer.parseInt(ciphertextPositionsStr[i]);
        }
    } catch (NumberFormatException e) {
        return plaintext;
    }
    
    String cleaned = plaintext.toLowerCase().replaceAll(" ", "");
    
    if (cleaned.length() == 0) {
        return plaintext;
    }
    
    while (cleaned.length() % 5 != 0) {
        cleaned += 'z';
    }
    
    StringBuilder ciphertext = new StringBuilder();
    int blockCount = cleaned.length() / 5;
    
    for (int block = 0; block < blockCount; block++) {
        String currentBlock = cleaned.substring(block * 5, (block + 1) * 5);
        
        char[] cipherBlock = new char[5];
        for (int i = 0; i < 5; i++) {
            int plainIndex = plaintextIndices[i] - 1;
            int cipherPos = ciphertextPositions[i] - 1;
            
            if (plainIndex >= 0 && plainIndex < 5 && cipherPos >= 0 && cipherPos < 5) {
                cipherBlock[cipherPos] = currentBlock.charAt(plainIndex);
            }
        }
        
        for (char c : cipherBlock) {
            ciphertext.append(Character.toUpperCase(c));
        }
    }
    
    return ciphertext.toString();
}
```

### Explanation
This code applies a keyed transposition: it reads two rows (splitting on newlines or pipes), validates 5 values each, parses integers, normalizes the input, pads to multiples of 5, then processes 5-character blocks. For each block, it maps plaintext indices to ciphertext positions (converting from 1-based to 0-based), builds the permuted block, uppercases, and appends.

---

**End of Code Samples**

