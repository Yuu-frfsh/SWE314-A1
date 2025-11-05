# Educational Encryption/Decryption Software Tool

## Overview
This is an educational tool designed to help users learn the basics of cryptography and cryptanalysis. The tool implements various encryption and decryption algorithms with step-by-step demonstrations.

**Course:** SWE 314 - Software Security Engineering  
**Institution:** King Saud University  
**Semester:** Fall 2025-2026  
**Assignment:** #1 - Educational Encryption / Decryption Software Tool

## Features
- **Multiple Encryption Algorithms:** Monoalphabetic substitution (Caesar Cipher), Vigenere, Playfair, Keyed Transposition, Combined ciphers, and DES (educational implementation)
- **Interactive Learning:** Step-by-step display of encryption/decryption processes
- **Cryptanalysis:** Frequency analysis tool with automatic key detection and decryption for monoalphabetic ciphers
- **Educational Focus:** Designed to teach cryptographic concepts through hands-on experience

## Project Structure

### Main Classes
- `main.java` - Entry point with menu-driven interface
- `EncryptionAlgorithm.java` - Interface for all encryption algorithms
- `InputValidator.java` - Utility class for input validation

### Encryption Algorithms
- `MonoalphabeticCipher.java` - Monoalphabetic substitution cipher
- `VigenereCipher.java` - Vigenere cipher
- `PlayfairCipher.java` - Playfair cipher
- `KeyedTranspositionCipher.java` - Keyed transposition cipher
- `CombinedCipher.java` - Combines Monoalphabetic with another cipher
- `DESCipher.java` - Simplified educational DES (NOT secure)

### Cryptanalysis
- `FrequencyAnalysis.java` - Letter frequency analysis tool

## How to Compile and Run

### Prerequisites
- Java Development Kit (JDK) installed
- Command line access

### Compilation
To compile all Java files:
```bash
javac *.java
```

### Running the Main Program
```bash
java main
```

## Usage Guide

### Main Menu
Upon running the program, you'll see a menu with the following options:

**Encryption:**
1. Monoalphabetic Substitution
2. Vigenere Cipher
3. Playfair Cipher
4. Keyed Transposition
5. Combined Encryption
6. DES

**Decryption:**
7. Decrypt Text

**Cryptanalysis:**
8. Frequency Analysis

**Exit:**
0. Exit

### Example Usage

#### Monoalphabetic Substitution (Caesar Cipher)
1. Select option 1 from main menu
2. Enter plaintext: `HELLO`
3. Enter integer shift key (e.g., `3` for Caesar shift of 3)
4. View step-by-step encryption process showing each letter shifted
5. See encrypted result

#### Vigenere Cipher
1. Select option 2 from main menu
2. Enter plaintext: `HELLO WORLD`
3. Enter keyword: `KEY`
4. View character-by-character transformations
5. See encrypted result

#### Playfair Cipher
1. Select option 3 from main menu
2. Enter plaintext: `HELLO`
3. Enter keyword: `PLAYFAIR`
4. View 5x5 matrix and digraph processing
5. See encrypted result

#### Keyed Transposition
1. Select option 4 from main menu
2. Enter plaintext: `HELLO`
3. Enter 2x5 permutation key:
   - First row (plaintext indices): `3 1 4 5 2`
   - Second row (ciphertext positions): `1 2 3 4 5`
4. View block-by-block processing (messages >5 chars are processed in blocks)
5. See encrypted result

#### Decryption
1. Select option 7 from main menu
2. Choose algorithm to decrypt:
   - Option 1: Monoalphabetic Substitution
   - Option 2: Keyed Transposition
3. Enter ciphertext
4. Enter key:
   - For Monoalphabetic: integer shift value
   - For Keyed Transposition: two-line key (2x5 permutation matrix)
5. View step-by-step decryption process
6. See decrypted plaintext

#### Frequency Analysis
1. Select option 8 from main menu
2. Enter ciphertext to analyze
3. View letter frequency table and analysis
4. System automatically detects the encryption key using correlation analysis
5. System automatically decrypts the ciphertext and displays the detected key and plaintext

## Algorithm Details

### Monoalphabetic Substitution (Caesar Cipher)
- **Key:** Integer shift value (e.g., 3, -5, 25)
- **Process:** Each letter is shifted by the key value positions in the alphabet
- **Normalization:** Shift values beyond ±26 are automatically normalized to range [-25, 25]
- **Example:** With shift 3, A->D, B->E, C->F, etc.
- **Decryption:** Use negative shift value (e.g., shift -3 to decrypt text encrypted with shift 3)

### Vigenere Cipher
- **Key:** Keyword (any length)
- **Process:** Each letter is shifted by its corresponding key letter position
- **Features:** The key repeats if shorter than plaintext

### Playfair Cipher
- **Key:** Keyword used to build 5x5 matrix
- **Process:** Encrypts digraphs (pairs of letters) using matrix rules
- **Features:** Handles doubled letters and odd-length text

### Keyed Transposition
- **Key:** 2x5 permutation matrix provided in two lines:
  - First row: Plaintext indices (1-5, e.g., `3 1 4 5 2`)
  - Second row: Ciphertext positions (1-5, e.g., `1 2 3 4 5`)
- **Process:** 
  - Messages are processed in blocks of 5 characters
  - Each block is permuted according to the 2x5 matrix
  - Longer messages are split into multiple blocks
- **Padding:** Messages not divisible by 5 are padded with 'z' characters
- **Example:** Plaintext "HELLO" with key rows `3 1 4 5 2` and `1 2 3 4 5`

### Combined Cipher
- **Process:** Applies Monoalphabetic (Caesar Cipher) first, then another cipher of your choice
- **Options:** Vigenere, Playfair, or Keyed Transposition
- **Key Format:** 
  - First key: Integer shift value for Monoalphabetic
  - Second key: Algorithm-specific key (keyword for Vigenere/Playfair, 2x5 matrix for Keyed Transposition)
- **Security:** Multiple layers of encryption

### DES (Educational Implementation)
- **WARNING:** This is an educational implementation for learning purposes only - NOT secure for production use!
- **Key:** 8 characters (64 bits, with parity bits)
- **Process:** 
  - Full DES algorithm implementation with 16 rounds
  - Feistel network structure
  - S-box substitutions (8 S-boxes)
  - Key schedule with 16 round keys
  - Initial permutation (IP) and final permutation (IP⁻¹)
  - Expansion permutation (E) and permutation (P)
- **Output:** Hexadecimal format
- **Features:** Step-by-step display of all DES operations including key schedule, round operations, and S-box substitutions

### Frequency Analysis
- **Purpose:** Automatically detect keys and decrypt monoalphabetic substitution ciphers
- **Features:** 
  - Analyzes letter frequency in ciphertext
  - Compares ciphertext frequencies with English letter frequencies
  - Uses correlation analysis (Pearson correlation) to test all 26 possible shifts
  - Automatically detects the most likely encryption key
  - Automatically decrypts the ciphertext using the detected key
- **Output:** 
  - Letter frequency table
  - Suggested letter substitutions
  - Detected key (shift value)
  - Decrypted plaintext
- **Best Results:** Works optimally with monoalphabetic substitution (Caesar Cipher) text

## Testing Approach

All algorithms are tested using **Input Space Partitioning** methodology:

1. **Valid Inputs** - Normal usage cases
2. **Different Key Lengths** - Testing boundary conditions
3. **Special Cases** - Mixed case, spaces, special characters
4. **Edge Cases** - Empty strings, single characters, invalid keys
5. **Round Trips** - Verify decrypt(encrypt(text)) == text

## Limitations

1. **DES Implementation:** The DES cipher is an educational implementation and is NOT secure for production use
2. **Decryption Menu:** Only Monoalphabetic and Keyed Transposition are available through the main decryption menu (option 7)
3. **Console Interface:** Currently text-based only (no GUI)
4. **Character Handling:** Some algorithms treat J as I (Playfair)
5. **Padding:** Keyed Transposition uses 'z' padding for messages not divisible by 5
6. **Case Sensitivity:** All algorithms convert input to uppercase/lowercase as appropriate
7. **Frequency Analysis:** Auto-detection works best with monoalphabetic substitution ciphers; results may be inaccurate for other cipher types

## Future Improvements

- GUI implementation for better visualization
- Additional algorithms (AES, RSA simplified versions)
- More cryptanalysis techniques (brute force, pattern analysis)
- Import/export functionality for encrypted files
- Visual step-by-step animations
- Algorithm comparison tools

## Team Information

**Course:** SWE 314 - Software Security Engineering  
**Assignment:** #1  
**Deliverable Date:** Wednesday, October 22nd, 2025

## License

This is an educational project for academic purposes.

## Contact

For questions or issues with this implementation, please contact the development team.

