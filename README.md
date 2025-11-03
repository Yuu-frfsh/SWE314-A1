# Educational Encryption/Decryption Software Tool

## Overview
This is an educational tool designed to help users learn the basics of cryptography and cryptanalysis. The tool implements various encryption and decryption algorithms with step-by-step demonstrations.

**Course:** SWE 314 - Software Security Engineering  
**Institution:** King Saud University  
**Semester:** Fall 2025-2026  
**Assignment:** #1 - Educational Encryption / Decryption Software Tool

## Features
- **Multiple Encryption Algorithms:** Monoalphabetic substitution, Vigenere, Playfair, Keyed Transposition, Combined ciphers, and DES (simplified)
- **Interactive Learning:** Step-by-step display of encryption/decryption processes
- **Cryptanalysis:** Frequency analysis tool to help break monoalphabetic ciphers
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

### Testing
- `MonoalphabeticTest.java` - Unit tests for Monoalphabetic cipher
- `VigenereTest.java` - Unit tests for Vigenere cipher
- `PlayfairTest.java` - Unit tests for Playfair cipher
- `KeyedTranspositionTest.java` - Unit tests for Keyed Transposition
- `CombinedTest.java` - Unit tests for Combined cipher
- `DESTest.java` - Unit tests for DES cipher
- `FrequencyAnalysisTest.java` - Unit tests for Frequency Analysis

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

### Running Individual Tests
To test specific algorithms:
```bash
# Monoalphabetic tests
java MonoalphabeticTest

# Vigenere tests
java VigenereTest

# Playfair tests
java PlayfairTest

# Keyed Transposition tests
java KeyedTranspositionTest

# Combined cipher tests
java CombinedTest

# DES tests
java DESTest

# Frequency Analysis tests
java FrequencyAnalysisTest
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
6. DES (simplified)

**Decryption:**
7. Decrypt Text

**Cryptanalysis:**
8. Frequency Analysis

**Exit:**
0. Exit

### Example Usage

#### Monoalphabetic Substitution
1. Select option 1 from main menu
2. Enter plaintext: `HELLO`
3. Enter 26-character substitution key: `QWERTYUIOPASDFGHJKLZXCVBNM`
4. View step-by-step encryption process
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

#### Frequency Analysis
1. Select option 8 from main menu
2. Enter ciphertext to analyze
3. View letter frequency table
4. See suggested letter substitutions for monoalphabetic ciphers

## Algorithm Details

### Monoalphabetic Substitution
- **Key:** 26-character permutation of the alphabet
- **Process:** Each letter is replaced by its corresponding letter in the key
- **Example:** A->Q, B->W, C->E, etc.

### Vigenere Cipher
- **Key:** Keyword (any length)
- **Process:** Each letter is shifted by its corresponding key letter position
- **Features:** The key repeats if shorter than plaintext

### Playfair Cipher
- **Key:** Keyword used to build 5x5 matrix
- **Process:** Encrypts digraphs (pairs of letters) using matrix rules
- **Features:** Handles doubled letters and odd-length text

### Keyed Transposition
- **Key:** Numeric or alphabetic ordering
- **Process:** Writes text in rows, reads columns in key order
- **Features:** Padding with X when needed

### Combined Cipher
- **Process:** Applies Monoalphabetic, then another cipher of your choice
- **Options:** Vigenere, Playfair, or Keyed Transposition
- **Security:** Multiple layers of encryption

### DES (Simplified)
- **WARNING:** This is NOT secure DES - for educational purposes only!
- **Key:** 8 characters
- **Process:** Simplified XOR-based demonstration
- **Output:** Hexadecimal format

### Frequency Analysis
- **Purpose:** Help break monoalphabetic ciphers
- **Features:** Compares ciphertext frequencies with English letter frequencies
- **Suggestions:** Provides likely letter substitutions

## Testing Approach

All algorithms are tested using **Input Space Partitioning** methodology:

1. **Valid Inputs** - Normal usage cases
2. **Different Key Lengths** - Testing boundary conditions
3. **Special Cases** - Mixed case, spaces, special characters
4. **Edge Cases** - Empty strings, single characters, invalid keys
5. **Round Trips** - Verify decrypt(encrypt(text)) == text

## Limitations

1. **DES Implementation:** The DES cipher is severely simplified for education and is NOT secure
2. **Console Interface:** Currently text-based only (no GUI)
3. **Character Handling:** Some algorithms treat J as I (Playfair)
4. **Padding:** Some ciphers add X padding that may affect decryption results
5. **Case Sensitivity:** All algorithms convert to uppercase

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

