# Discrepancy Locations - Quick Reference

This document lists all exact locations where differences were found between pseudocode and Java implementations.

---

## 1. Keyed Transposition Cipher - Padding Character

**Type:** Functional Difference (Critical)

**Pseudocode:**
- **File:** `AlgorithmsPseudocode.md`
- **Line:** 253
- **Content:** `block += "X"` (uppercase X)

**Java Implementation:**
- **File:** `KeyedTranspositionCipher.java`
- **Line:** 55
- **Content:** `cleaned += 'z';` (lowercase z)

**Issue:** Padding character mismatch - pseudocode uses "X" but implementation uses 'z'

---

## 2. DES Cipher - Key Generation Method Name

**Type:** Naming Difference (Minor)

**Pseudocode:**
- **File:** `AlgorithmsPseudocode.md`
- **Lines:** 339, 391
- **Content:** Function name `generateRoundKeys(keyBinary)`

**Java Implementation:**
- **File:** `DESCipher.java`
- **Lines:** 121, 153
- **Content:** Method name `buildKeySchedule(binKey)`

**Issue:** Different method/function names for the same functionality

---

## 3. DES Cipher - Helper Function Names

**Type:** Naming Difference (Minor)

**Pseudocode:**
- **File:** `AlgorithmsPseudocode.md`
- **Line:** 345
- **Content:** Function name `stringTo64BitBinary()`

**Java Implementation:**
- **File:** `DESCipher.java`
- **Line:** 103
- **Content:** Method name `utfToBin()`

**Also:**
- **Pseudocode Line 366:** `binaryToHex()`
- **Java Line 114:** `binToHex()`
- **Java Line 246:** `hexToBin()` (not explicitly named in pseudocode)
- **Java Line 255:** `binToUTF()` (not explicitly named in pseudocode)

**Issue:** Different naming conventions for binary conversion functions

---

## 4. Frequency Analysis - Return Value Type

**Type:** Structure Difference (Minor)

**Pseudocode:**
- **File:** `AlgorithmsPseudocode.md`
- **Lines:** 462, 466
- **Content:** 
  ```
  RETURN (detectedKey, plaintext)  // Tuple return
  RETURN (null, null)               // Tuple return
  ```

**Java Implementation:**
- **File:** `FrequencyAnalysis.java`
- **Line:** 66
- **Content:** 
  ```java
  public void analyze(String text)  // void method, no return
  ```

**Issue:** Pseudocode suggests tuple return, but Java implementation is void and only displays results

---

## Summary Table

| # | Algorithm | Pseudocode File | Pseudocode Line | Java File | Java Line | Issue Type |
|---|-----------|----------------|-----------------|-----------|-----------|------------|
| 1 | Keyed Transposition | AlgorithmsPseudocode.md | 253 | KeyedTranspositionCipher.java | 55 | Padding character: "X" vs 'z' |
| 2 | DES Cipher | AlgorithmsPseudocode.md | 339, 391 | DESCipher.java | 121, 153 | Method name: generateRoundKeys vs buildKeySchedule |
| 3 | DES Cipher | AlgorithmsPseudocode.md | 345, 366 | DESCipher.java | 103, 114, 246, 255 | Helper function naming differences |
| 4 | Frequency Analysis | AlgorithmsPseudocode.md | 462, 466 | FrequencyAnalysis.java | 66 | Return type: tuple vs void |

---

## All Affected Files

### Pseudocode File:
- `AlgorithmsPseudocode.md` (lines: 253, 339, 345, 366, 391, 462, 466)

### Java Implementation Files:
- `KeyedTranspositionCipher.java` (line: 55)
- `DESCipher.java` (lines: 103, 114, 121, 153, 246, 255)
- `FrequencyAnalysis.java` (line: 66)

---

**Total Files with Discrepancies:** 4 files  
**Total Discrepancy Locations:** 10 locations

