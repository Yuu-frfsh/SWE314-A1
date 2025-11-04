# Algorithm Pseudocode vs Implementation Comparison Report

This report documents all differences found between the pseudocode in `AlgorithmsPseudocode.md` and the actual Java implementations.

---

## Summary

**Total Algorithms Compared:** 7  
**Total Differences Found:** 5  
**Status:** ⚠️ Some discrepancies require attention

---

## 1. Monoalphabetic Substitution Cipher (Caesar Cipher)

**Pseudocode Location:** Lines 6-50 in `AlgorithmsPseudocode.md`  
**Java Implementation:** `MonoalphabeticCipher.java`

### Comparison Results: ✅ MATCHES

**Verified:**
- ✅ Encryption logic matches (lines 32-65 in Java)
- ✅ Decryption logic matches (lines 74-106 in Java)
- ✅ `normalizeShift()` helper matches (lines 14-24 in Java)
- ✅ Modulo arithmetic matches: `(index + shift + 26) % 26`
- ✅ Character processing (letters vs non-letters) matches

**Minor Note:**
- Pseudocode uses `getAlphabetPosition(c)` function call
- Java uses `ALPHABET.indexOf(c)` directly (line 53, 94)
- This is functionally equivalent, just a different implementation approach

---

## 2. Vigenere Cipher

**Pseudocode Location:** Lines 54-104 in `AlgorithmsPseudocode.md`  
**Java Implementation:** `VigenereCipher.java`

### Comparison Results: ✅ MATCHES

**Verified:**
- ✅ Encryption logic matches (lines 15-48 in Java)
- ✅ Decryption logic matches (lines 56-89 in Java)
- ✅ Keyword cycling with `keyIndex % key.length()` matches
- ✅ Key index only advances for letters (lines 41, 82 in Java)
- ✅ Empty key handling matches (lines 21-23, 62-64 in Java)
- ✅ Modulo arithmetic matches: `(plaintextPos + keyPos) % 26` and `(ciphertextPos - keyPos + 26) % 26`

---

## 3. Playfair Cipher

**Pseudocode Location:** Lines 108-228 in `AlgorithmsPseudocode.md`  
**Java Implementation:** `PlayfairCipher.java`

### Comparison Results: ✅ MATCHES

**Verified:**
- ✅ `buildMatrix()` helper matches (lines 19-42 in Java)
  - J→I replacement (line 27)
  - Duplicate removal using HashSet (lines 21, 28-30)
  - Alphabet filling (lines 35-39)
- ✅ `prepareDigraphs()` helper matches (lines 62-98 in Java)
  - X padding for last letter (lines 82-85)
  - X insertion for same-letter pairs (lines 86-89)
  - Normal pair handling (lines 91-93)
- ✅ Encryption rules match:
  - Same row: shift right `(colA + 1) % 5` (line 132)
  - Same column: shift down `(rowA + 1) % 5` (line 137)
  - Rectangle: swap columns (lines 141-144)
- ✅ Decryption rules match:
  - Same row: shift left `(colA + 4) % 5` (line 198)
  - Same column: shift up `(rowA + 4) % 5` (line 203)
  - Rectangle: swap columns (same as encryption) (lines 207-210)

---

## 4. Keyed Transposition Cipher (2x5 Matrix)

**Pseudocode Location:** Lines 232-289 in `AlgorithmsPseudocode.md`  
**Java Implementation:** `KeyedTranspositionCipher.java`

### Comparison Results: ⚠️ DISCREPANCY FOUND

**Verified:**
- ✅ Key parsing (two-line format) matches (lines 17-44 in Java)
- ✅ Block processing (5-character blocks) matches (lines 58-83 in Java)
- ✅ 1-indexed to 0-indexed conversion matches (lines 69, 71, 144-145)
- ✅ Decryption reverse logic matches (lines 137-154 in Java)
- ✅ Padding removal in decryption matches (lines 156-160 in Java)

**❌ DISCREPANCY #1: Padding Character**

- **Pseudocode (Line 253):** `block += "X"` (uppercase X)
- **Java Implementation (Line 55):** `cleaned += 'z';` (lowercase z)
- **Location:**
  - Pseudocode: `AlgorithmsPseudocode.md`, line 253
  - Java: `KeyedTranspositionCipher.java`, line 55
- **Impact:** The padding character used is different. This could cause issues if the pseudocode is used as reference.
- **Recommendation:** Update pseudocode line 253 to use `'z'` instead of `"X"` to match the implementation.

---

## 5. Combined Cipher

**Pseudocode Location:** Lines 293-326 in `AlgorithmsPseudocode.md`  
**Java Implementation:** `CombinedCipher.java`

### Comparison Results: ✅ MATCHES

**Verified:**
- ✅ Key format "monoKey|secondKey" matches (lines 23, 50 in Java)
- ✅ Encryption order: Monoalphabetic first, then second algorithm (lines 32-36 in Java)
- ✅ Decryption order: Second algorithm first, then Monoalphabetic (lines 58-63 in Java)
- ✅ Key splitting with pipe separator matches (lines 23, 50 in Java)

**Note:**
- The Java implementation includes additional logic for selecting the second algorithm (lines 176-208), which is not in the pseudocode but is a UI concern, not algorithm logic.

---

## 6. DES Cipher

**Pseudocode Location:** Lines 330-424 in `AlgorithmsPseudocode.md`  
**Java Implementation:** `DESCipher.java`

### Comparison Results: ⚠️ DISCREPANCIES FOUND

**Verified:**
- ✅ Key conversion to 64-bit binary matches (lines 92-100 in Java)
- ✅ Encryption: IP permutation, 16 Feistel rounds, IP^-1 matches (lines 168-192 in Java)
- ✅ Feistel function: Expansion, XOR, S-boxes, P permutation matches (lines 195-243 in Java)
- ✅ Decryption uses reverse key order (K[16] to K[1]) (lines 296-308 in Java)
- ✅ Block size: 64-bit blocks matches
- ✅ Output format: Hexadecimal matches (line 164 in Java)

**❌ DISCREPANCY #2: Method Name for Key Generation**

- **Pseudocode (Line 339, 391):** Uses function name `generateRoundKeys(keyBinary)`
- **Java Implementation (Line 121, 153):** Uses method name `buildKeySchedule(binKey)`
- **Location:**
  - Pseudocode: `AlgorithmsPseudocode.md`, lines 339, 391
  - Java: `DESCipher.java`, lines 121, 153
- **Impact:** Different naming convention, but functionally equivalent.
- **Recommendation:** Either update pseudocode to use `buildKeySchedule` or update Java to use `generateRoundKeys` for consistency.

**❌ DISCREPANCY #3: Helper Function Names**

- **Pseudocode:**
  - `stringTo64BitBinary()` (line 345)
  - `binaryToHex()` (line 366)
  - `hexToBin()` (not explicitly named in pseudocode)
  - `binaryToUTF()` (not explicitly named in pseudocode)
- **Java Implementation:**
  - `utfToBin()` (line 103)
  - `binToHex()` (line 114)
  - `hexToBin()` (line 246)
  - `binToUTF()` (line 255)
- **Location:**
  - Pseudocode: `AlgorithmsPseudocode.md`, lines 345, 366
  - Java: `DESCipher.java`, lines 103, 114, 246, 255
- **Impact:** Different naming conventions, but all functions exist and are functionally equivalent.
- **Recommendation:** Update pseudocode to match Java naming for consistency, or vice versa.

**Note:**
- The pseudocode mentions `encryptWithKeys()` function (line 421) but Java implements this inline in `decrypt()` method (lines 280-283). This is a structural difference but functionally equivalent.

---

## 7. Frequency Analysis

**Pseudocode Location:** Lines 428-614 in `AlgorithmsPseudocode.md`  
**Java Implementation:** `FrequencyAnalysis.java`

### Comparison Results: ⚠️ DISCREPANCY FOUND

**Verified:**
- ✅ `countLetters()` method matches (lines 23-37 in Java)
- ✅ `calculatePercentages()` method matches (lines 44-60 in Java)
- ✅ `detectKey()` method matches (lines 176-198 in Java)
  - Correlation threshold 0.3 matches (line 193 in Java)
- ✅ `calculateCorrelation()` method matches (lines 207-233 in Java)
  - Pearson correlation formula matches
- ✅ `decryptWithKey()` method matches (lines 241-244 in Java)
- ✅ `findMostFrequent()` method matches (lines 122-139 in Java)
- ✅ `suggestSubstitutions()` method matches (lines 145-168 in Java)

**❌ DISCREPANCY #4: Return Value**

- **Pseudocode (Lines 462, 466):** 
  ```
  RETURN (detectedKey, plaintext)  // Tuple return
  RETURN (null, null)               // Tuple return
  ```
- **Java Implementation (Line 66):** 
  ```java
  public void analyze(String text)  // void method, no return
  ```
- **Location:**
  - Pseudocode: `AlgorithmsPseudocode.md`, lines 462, 466
  - Java: `FrequencyAnalysis.java`, line 66 (method signature)
- **Impact:** The pseudocode suggests returning a tuple, but Java implementation is void and only displays results. The functionality is the same (key and plaintext are displayed), but the return mechanism differs.
- **Recommendation:** Update pseudocode to indicate that results are displayed (not returned), or update Java to return a result object if tuple return is needed.

---

## 8. Common Helper Functions

**Pseudocode Location:** Lines 618-651 in `AlgorithmsPseudocode.md`

### Comparison Results: ✅ MATCHES WITH NOTES

**`getAlphabetPosition()` Function:**
- **Pseudocode:** Lines 621-628
- **Used by:** Monoalphabetic Cipher, Vigenere Cipher
- **Java Implementation:** 
  - Monoalphabetic uses `ALPHABET.indexOf(c)` (line 53 in MonoalphabeticCipher.java)
  - Vigenere uses `ALPHABET.indexOf(c)` (lines 31, 35, 72, 76 in VigenereCipher.java)
- **Status:** ✅ Functionally equivalent, though not implemented as a separate helper function

**`permute()` Function:**
- **Pseudocode:** Lines 630-638
- **Used by:** DES Cipher
- **Java Implementation:** Implemented inline in multiple places:
  - `buildKeySchedule()`: PC1, PC2 permutations (lines 126, 144 in DESCipher.java)
  - `encryptBlock()`: IP permutation (line 170)
  - `decryptBlock()`: IP permutation (line 291)
  - `f()`: EXPANSION permutation (line 197)
  - `applySBoxesAndP()`: P permutation (line 238)
- **Status:** ✅ Functionally equivalent, permutation logic is correct

**`XOR()` Function:**
- **Pseudocode:** Lines 640-651
- **Used by:** DES Cipher
- **Java Implementation:** Uses Java's `^` operator:
  - `buildKeySchedule()`: Not directly used (uses bit shifts)
  - `f()`: `xored = m ^ k` (line 201)
  - `encryptBlock()`: `newR = lLong ^ fLong` (line 182)
  - `decryptBlock()`: `newR = lLong ^ fLong` (line 304)
- **Status:** ✅ Functionally equivalent

---

## Summary of Discrepancies

### Critical Issues (Functional Differences)

1. **Keyed Transposition Padding:** Pseudocode says 'X', implementation uses 'z'
   - **File:** `AlgorithmsPseudocode.md`, line 253
   - **Fix:** Change `"X"` to `'z'` in pseudocode

### Minor Issues (Naming/Structure Differences)

2. **DES Key Generation Method Name:** `generateRoundKeys` vs `buildKeySchedule`
   - **Files:** `AlgorithmsPseudocode.md` lines 339, 391; `DESCipher.java` lines 121, 153
   - **Fix:** Standardize naming (recommend updating pseudocode to match Java)

3. **DES Helper Function Names:** Various naming differences
   - **Files:** `AlgorithmsPseudocode.md` lines 345, 366; `DESCipher.java` lines 103, 114, 246, 255
   - **Fix:** Standardize naming for consistency

4. **Frequency Analysis Return Value:** Pseudocode suggests tuple return, Java is void
   - **Files:** `AlgorithmsPseudocode.md` lines 462, 466; `FrequencyAnalysis.java` line 66
   - **Fix:** Update pseudocode to reflect void return with display output

---

## Recommendations

### High Priority
1. **Update Keyed Transposition pseudocode padding** (line 253) from `"X"` to `'z'` to match implementation

### Medium Priority
2. **Standardize DES method names** in pseudocode to match Java implementation (`buildKeySchedule` instead of `generateRoundKeys`)
3. **Update Frequency Analysis pseudocode** to reflect void return type instead of tuple return

### Low Priority
4. **Standardize DES helper function names** for consistency between pseudocode and implementation

---

## Conclusion

Overall, the pseudocode and implementations are **highly aligned** with only minor discrepancies. The main functional difference is the padding character in Keyed Transposition Cipher. All other differences are primarily naming conventions and return value documentation, which do not affect the correctness of the algorithms.

**Recommendation:** Update the pseudocode to match the implementation for consistency, especially for the padding character discrepancy.

---

**Report Generated:** $(date)  
**Files Compared:**
- `AlgorithmsPseudocode.md`
- `MonoalphabeticCipher.java`
- `VigenereCipher.java`
- `PlayfairCipher.java`
- `KeyedTranspositionCipher.java`
- `CombinedCipher.java`
- `DESCipher.java`
- `FrequencyAnalysis.java`

