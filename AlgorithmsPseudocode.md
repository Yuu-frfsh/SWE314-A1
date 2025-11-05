# Encryption Algorithms Pseudo-code
## SWE 314 - Educational Encryption Tool

---

## 1. Monoalphabetic Substitution Cipher (Caesar Cipher)

### Encryption
```
FUNCTION encrypt(plaintext, shift):
    shift = normalizeShift(shift)  // Handle shifts beyond ±26
    ciphertext = ""
    
    FOR each character c in plaintext:
        IF c is a letter:
            position = getAlphabetPosition(c)  // A=0, B=1, ..., Z=25
            newPosition = (position + shift + 26) MOD 26
            ciphertext += ALPHABET[newPosition]
        ELSE:
            ciphertext += c  // Keep non-letters as-is
    
    RETURN ciphertext
```

### Decryption
```
FUNCTION decrypt(ciphertext, shift):
    shift = normalizeShift(shift)
    plaintext = ""
    
    FOR each character c in ciphertext:
        IF c is a letter:
            position = getAlphabetPosition(c)
            newPosition = (position - shift + 26) MOD 26
            plaintext += ALPHABET[newPosition]
        ELSE:
            plaintext += c
    
    RETURN plaintext
```

### Helper Function
```
FUNCTION normalizeShift(shift):
    WHILE shift > 25:
        shift = shift - 26
    WHILE shift < -25:
        shift = shift + 26
    RETURN shift
```

---

## 2. Vigenere Cipher

### Encryption
```
FUNCTION encrypt(plaintext, keyword):
    IF keyword is empty:
        RETURN plaintext
    
    ciphertext = ""
    keyIndex = 0
    
    FOR each character c in plaintext:
        IF c is a letter:
            plainPos = getAlphabetPosition(c)
            keyChar = keyword[keyIndex MOD length(keyword)]
            keyPos = getAlphabetPosition(keyChar)
            
            newPos = (plainPos + keyPos) MOD 26
            ciphertext += ALPHABET[newPos]
            
            keyIndex = keyIndex + 1
        ELSE:
            ciphertext += c  // Non-letters don't advance key
    
    RETURN ciphertext
```

### Decryption
```
FUNCTION decrypt(ciphertext, keyword):
    IF keyword is empty:
        RETURN ciphertext
    
    plaintext = ""
    keyIndex = 0
    
    FOR each character c in ciphertext:
        IF c is a letter:
            cipherPos = getAlphabetPosition(c)
            keyChar = keyword[keyIndex MOD length(keyword)]
            keyPos = getAlphabetPosition(keyChar)
            
            newPos = (cipherPos - keyPos + 26) MOD 26
            plaintext += ALPHABET[newPos]
            
            keyIndex = keyIndex + 1
        ELSE:
            plaintext += c
    
    RETURN plaintext
```

---

## 3. Playfair Cipher

### Encryption
```
FUNCTION encrypt(plaintext, keyword):
    matrix = buildMatrix(keyword)
    digraphs = prepareDigraphs(plaintext)
    ciphertext = ""
    
    FOR each digraph in digraphs:
        char1 = digraph[0]
        char2 = digraph[1]
        
        position1 = getPosition(char1, matrix)  // Returns [row, col]
        position2 = getPosition(char2, matrix)
        
        row1 = position1[0]
        col1 = position1[1]
        row2 = position2[0]
        col2 = position2[1]
        
        IF row1 == row2:  // Same row
            // Shift right (wrap around)
            encChar1 = matrix[row1][(col1 + 1) MOD 5]
            encChar2 = matrix[row2][(col2 + 1) MOD 5]
        
        ELSE IF col1 == col2:  // Same column
            // Shift down (wrap around)
            encChar1 = matrix[(row1 + 1) MOD 5][col1]
            encChar2 = matrix[(row2 + 1) MOD 5][col2]
        
        ELSE:  // Rectangle
            // Swap columns
            encChar1 = matrix[row1][col2]
            encChar2 = matrix[row2][col1]
        
        ciphertext += encChar1 + encChar2
    
    RETURN ciphertext
```

### Helper Functions
```
FUNCTION buildMatrix(keyword):
    matrix = 5x5 grid
    used = empty set
    
    // Add keyword letters first (skip duplicates, J→I)
    FOR each character c in keyword:
        IF c == 'J':
            c = 'I'
        IF c is letter AND c not in used:
            ADD c to matrix
            ADD c to used
    
    // Fill remaining with alphabet (A-Z except J)
    FOR each letter in "ABCDEFGHIKLMNOPQRSTUVWXYZ":
        IF letter not in used:
            ADD letter to matrix
    
    RETURN matrix


FUNCTION prepareDigraphs(plaintext):
    text = removeNonLetters(plaintext)
    text = replaceJ_with_I(text)
    digraphs = []
    i = 0
    
    WHILE i < length(text):
        IF i == length(text) - 1:
            // Last letter, add X padding
            digraphs.ADD(text[i] + "X")
            i = i + 1
        
        ELSE IF text[i] == text[i+1]:
            // Same letter pair, insert X
            digraphs.ADD(text[i] + "X")
            i = i + 1
        
        ELSE:
            // Normal pair
            digraphs.ADD(text[i] + text[i+1])
            i = i + 2
    
    RETURN digraphs
```

### Decryption
```
FUNCTION decrypt(ciphertext, keyword):
    matrix = buildMatrix(keyword)
    digraphs = splitIntoDigraphs(ciphertext)
    plaintext = ""
    
    FOR each digraph in digraphs:
        position1 = getPosition(digraph[0], matrix)
        position2 = getPosition(digraph[1], matrix)
        
        row1 = position1[0], col1 = position1[1]
        row2 = position2[0], col2 = position2[1]
        
        IF row1 == row2:  // Same row
            // Shift left
            decChar1 = matrix[row1][(col1 + 4) MOD 5]
            decChar2 = matrix[row2][(col2 + 4) MOD 5]
        
        ELSE IF col1 == col2:  // Same column
            // Shift up
            decChar1 = matrix[(row1 + 4) MOD 5][col1]
            decChar2 = matrix[(row2 + 4) MOD 5][col2]
        
        ELSE:  // Rectangle
            // Swap columns (same as encryption)
            decChar1 = matrix[row1][col2]
            decChar2 = matrix[row2][col1]
        
        plaintext += decChar1 + decChar2
    
    RETURN plaintext
```

---

## 4. Keyed Transposition Cipher (2x5 Matrix)

### Encryption
```
FUNCTION encrypt(plaintext, key):
    // Key format: two rows of 5 numbers each
    // Row 1: plaintext indices (1-5)
    // Row 2: ciphertext positions (1-5)
    
    plaintextIndices = parseFirstRow(key)   // e.g., [1,2,3,4,5]
    ciphertextPositions = parseSecondRow(key)  // e.g., [3,1,4,2,5]
    
    text = removeNonLetters(plaintext)
    ciphertext = ""
    
    // Process text in blocks of 5
    FOR i = 0 to length(text) STEP 5:
        block = text[i : i+5]
        
        // Pad if needed
        WHILE length(block) < 5:
            block += 'z'
        
        // Create cipher block using transposition
        cipherBlock = array of 5 characters
        FOR j = 0 to 4:
            sourceIndex = plaintextIndices[j] - 1  // Convert to 0-based
            targetPosition = ciphertextPositions[j] - 1
            cipherBlock[targetPosition] = block[sourceIndex]
        
        ciphertext += cipherBlock
    
    RETURN ciphertext
```

### Decryption
```
FUNCTION decrypt(ciphertext, key):
    plaintextIndices = parseFirstRow(key)
    ciphertextPositions = parseSecondRow(key)
    
    plaintext = ""
    
    // Process in blocks of 5
    FOR i = 0 to length(ciphertext) STEP 5:
        cipherBlock = ciphertext[i : i+5]
        
        // Reverse the transposition
        plainBlock = array of 5 characters
        FOR j = 0 to 4:
            sourceIndex = plaintextIndices[j] - 1
            targetPosition = ciphertextPositions[j] - 1
            plainBlock[sourceIndex] = cipherBlock[targetPosition]
        
        plaintext += plainBlock
    
    RETURN plaintext
```

---

## 5. Combined Cipher

### Encryption
```
FUNCTION encrypt(plaintext, combinedKey):
    // combinedKey format: "monoKey|secondKey"
    keys = split(combinedKey, "|")
    monoKey = keys[0]
    secondKey = keys[1]
    
    // Step 1: Apply Monoalphabetic (Caesar)
    intermediate = monoalphabeticEncrypt(plaintext, monoKey)
    
    // Step 2: Apply second algorithm
    ciphertext = secondAlgorithm.encrypt(intermediate, secondKey)
    
    RETURN ciphertext
```

### Decryption
```
FUNCTION decrypt(ciphertext, combinedKey):
    keys = split(combinedKey, "|")
    monoKey = keys[0]
    secondKey = keys[1]
    
    // Step 1: Reverse second algorithm
    intermediate = secondAlgorithm.decrypt(ciphertext, secondKey)
    
    // Step 2: Reverse Monoalphabetic (Caesar)
    plaintext = monoalphabeticDecrypt(intermediate, monoKey)
    
    RETURN plaintext
```

---

## 6. DES Cipher (Simplified)

### Encryption
```
FUNCTION encrypt(plaintext, key):
    // Convert key string to 64-bit binary
    keyBinary = keyStringTo64BitBinary(key)
    
    // Generate 16 round keys
    roundKeys = buildKeySchedule(keyBinary)
    
    ciphertext = ""
    
    // Process each 8-character block
    FOR each 8-char block in plaintext:
        blockBinary = utfToBin(block)
        
        // Initial Permutation
        permuted = permute(blockBinary, IP)
        
        // Split into left and right halves
        L = permuted[0:32]
        R = permuted[32:64]
        
        // 16 rounds of Feistel network
        FOR round = 1 to 16:
            prevR = R
            R = XOR(L, feistelFunction(R, roundKeys[round]))
            L = prevR
        
        // Swap and combine
        combined = R + L
        
        // Final Permutation
        cipherBlock = permute(combined, IPinverse)
        
        ciphertext += binToHex(cipherBlock)
    
    RETURN ciphertext
```

### Feistel Function
```
FUNCTION feistelFunction(rightHalf, roundKey):
    // Expansion (32 bits → 48 bits)
    expanded = permute(rightHalf, EXPANSION)
    
    // XOR with round key
    xored = XOR(expanded, roundKey)
    
    // S-Box substitution (48 bits → 32 bits)
    substituted = applySBoxes(xored)
    
    // Permutation
    result = permute(substituted, P)
    
    RETURN result
```

### Key Generation
```
FUNCTION buildKeySchedule(key64bit):
    // PC1 permutation (64 → 56 bits)
    key56 = permute(key64bit, PC1)
    
    // Split into left and right halves
    C = key56[0:28]
    D = key56[28:56]
    
    roundKeys = array of 17 keys
    
    FOR round = 1 to 16:
        // Left circular shift
        C = leftShift(C, SHIFTS[round])
        D = leftShift(D, SHIFTS[round])
        
        // Combine and apply PC2 (56 → 48 bits)
        combined = C + D
        roundKeys[round] = permute(combined, PC2)
    
    RETURN roundKeys
```

### Decryption
```
FUNCTION decrypt(ciphertext, key):
    // Same as encryption but with round keys in reverse order
    roundKeys = buildKeySchedule(key)
    reverseRoundKeys = reverse(roundKeys)
    
    // Apply same process with reversed keys
    plaintext = encryptWithKeys(ciphertext, reverseRoundKeys)
    
    RETURN plaintext
```

---

## 7. Frequency Analysis (Cryptanalysis)

### Main Analysis Function
```
FUNCTION analyze(ciphertext):
    // Convert to uppercase
    ciphertext = toUpperCase(ciphertext)
    
    // Count letter frequencies
    letterCounts = countLetters(ciphertext)
    percentages = calculatePercentages(letterCounts)
    
    // Display frequency table
    DISPLAY "--- Frequency Analysis ---"
    DISPLAY "Ciphertext: " + ciphertext
    DISPLAY "Letter Frequency Count:"
    DISPLAY frequency table with counts, percentages, and expected percentages
    
    // Find most frequent letters
    DISPLAY "Most frequent letters in ciphertext:"
    findMostFrequent(percentages, 5)
    
    // Suggest possible substitutions
    DISPLAY "Possible monoalphabetic substitutions:"
    suggestSubstitutions(percentages)
    
    // Automatically detect key and decrypt
    DISPLAY "--- Auto-Decryption (Monoalphabetic Cipher Only) ---"
    detectedKey = detectKey(percentages)
    
    IF detectedKey != null:
        plaintext = decryptWithKey(ciphertext, detectedKey)
        DISPLAY "Detected Key (Shift): " + detectedKey
        DISPLAY "Decrypted Plaintext: " + plaintext
        // Note: Results are displayed, method does not return a value
    ELSE:
        DISPLAY "Could not automatically detect the key."
        DISPLAY "The ciphertext may not be monoalphabetic, or sample size is too small."
        // Note: Method does not return a value when key cannot be detected
```

### Count Letters
```
FUNCTION countLetters(text):
    letterCounts = array of 26 zeros
    text = toUpperCase(text)
    
    FOR each character c in text:
        IF c is a letter:
            index = getAlphabetPosition(c)
            IF index >= 0 AND index < 26:
                letterCounts[index] = letterCounts[index] + 1
    
    RETURN letterCounts
```

### Calculate Percentages
```
FUNCTION calculatePercentages(counts):
    total = 0
    FOR each count in counts:
        total += count
    
    percentages = array of 26
    
    IF total == 0:
        RETURN array of 26 zeros
    
    FOR i = 0 to 25:
        percentages[i] = (counts[i] * 100.0) / total
    
    RETURN percentages
```

### Key Detection (with Correlation Analysis)
```
FUNCTION detectKey(percentages):
    // English letter frequencies (known percentages)
    englishFrequencies = [8.167, 1.492, 2.782, 4.253, 12.702, 2.228, 2.015, 
                          6.094, 6.966, 0.153, 0.772, 4.025, 2.406, 6.749, 
                          7.507, 1.929, 0.095, 5.987, 6.327, 9.056, 2.758, 
                          0.978, 2.360, 0.150, 1.974, 0.074]  // A-Z
    
    bestShift = 0
    maxCorrelation = -1.0
    
    // Try all 26 possible shifts
    FOR shift = 0 to 25:
        // Calculate Pearson correlation coefficient
        correlation = calculateCorrelation(percentages, shift)
        
        IF correlation > maxCorrelation:
            maxCorrelation = correlation
            bestShift = shift
    
    // Return shift only if correlation is above threshold (0.3)
    IF maxCorrelation > 0.3:
        RETURN bestShift
    ELSE:
        RETURN null
```

### Calculate Correlation (Pearson Correlation)
```
FUNCTION calculateCorrelation(cipherPercentages, shift):
    sumProduct = 0.0
    sumCipherSq = 0.0
    sumEnglishSq = 0.0
    
    FOR i = 0 to 25:
        // Calculate what plaintext letter this cipher letter represents
        // If cipher letter at index i, and shift is s, 
        // then plaintext is at (i - s + 26) MOD 26
        plainIndex = (i - shift + 26) MOD 26
        
        cipherFreq = cipherPercentages[i]
        englishFreq = ENGLISH_FREQUENCY[plainIndex]
        
        // Pearson correlation coefficient components
        sumProduct += cipherFreq * englishFreq
        sumCipherSq += cipherFreq * cipherFreq
        sumEnglishSq += englishFreq * englishFreq
    
    // Avoid division by zero
    IF sumCipherSq == 0.0 OR sumEnglishSq == 0.0:
        RETURN 0.0
    
    // Return Pearson correlation coefficient
    RETURN sumProduct / SQRT(sumCipherSq * sumEnglishSq)
```

### Auto-Decryption
```
FUNCTION decryptWithKey(ciphertext, detectedKey):
    // Decrypt ciphertext using detected shift key
    plaintext = ""
    
    FOR each character c in ciphertext:
        IF c is a letter:
            index = getAlphabetPosition(c)
            decryptedIndex = (index - detectedKey + 26) MOD 26
            plaintext += ALPHABET[decryptedIndex]
        ELSE:
            plaintext += c  // Keep non-letters as-is
    
    RETURN plaintext
```

### Find Most Frequent Letters
```
FUNCTION findMostFrequent(percentages, topN):
    sorted = array of IndexValue pairs (index, percentage)
    
    FOR i = 0 to 25:
        sorted[i] = (i, percentages[i])
    
    // Sort by frequency (descending)
    SORT sorted by percentage DESCENDING
    
    FOR i = 0 to min(topN, 26) - 1:
        IF sorted[i].percentage > 0:
            DISPLAY (i+1) + ". " + ALPHABET[sorted[i].index] + " (" + 
                    sorted[i].percentage + "%)"
```

### Substitution Suggestions
```
FUNCTION suggestSubstitutions(percentages):
    // Get top 5 most frequent in ciphertext
    cipherSorted = array of IndexValue pairs
    FOR i = 0 to 25:
        cipherSorted[i] = (i, percentages[i])
    SORT cipherSorted by percentage DESCENDING
    
    // Get top 5 most frequent in English
    englishSorted = array of IndexValue pairs
    FOR i = 0 to 25:
        englishSorted[i] = (i, ENGLISH_FREQUENCY[i])
    SORT englishSorted by frequency DESCENDING
    
    DISPLAY "Cipher -> English (most likely matches):"
    FOR i = 0 to 4:
        IF cipherSorted[i].percentage > 0:
            cipherLetter = ALPHABET[cipherSorted[i].index]
            englishLetter = ALPHABET[englishSorted[i].index]
            DISPLAY cipherLetter + " -> " + englishLetter
```

---

## Common Helper Functions

### Alphabet Position
**Used by:** Monoalphabetic Cipher, Vigenere Cipher
```
FUNCTION getAlphabetPosition(character):
    // Returns 0-25 for A-Z (case insensitive)
    upperChar = toUpperCase(character)
    position = ASCII_value(upperChar) - ASCII_value('A')
    RETURN position
```

### Permutation
**Used by:** DES Cipher
```
FUNCTION permute(input, permutationTable):
    output = ""
    FOR each index in permutationTable:
        output += input[index - 1]  // Tables are 1-indexed
    RETURN output
```

### XOR Operation
**Used by:** DES Cipher
```
FUNCTION XOR(binary1, binary2):
    result = ""
    FOR i = 0 to length(binary1) - 1:
        IF binary1[i] == binary2[i]:
            result += "0"
        ELSE:
            result += "1"
    RETURN result
```

---

## Notes

- All algorithms convert text to uppercase for processing
- Non-alphabetic characters are typically preserved as-is
- Caesar cipher uses modulo 26 arithmetic for wrapping
- Playfair treats 'J' as 'I' to fit the 5x5 matrix
- DES operates on binary representations (bits)
- Frequency analysis works best on longer ciphertexts

---

**End of Pseudo-code Documentation**

