# Functional Requirements - Educational Encryption Tool

## 1. Introduction

This document outlines the functional requirements for the Educational Encryption/Decryption Software Tool. The tool is designed to help users learn about cryptography and cryptanalysis through interactive demonstrations.

## 2. Core Requirements

### 2.1 User Interface Requirements

**FR-1.1:** The system shall provide a menu-driven console interface
- User can select from available encryption/decryption options
- Menu is displayed clearly with numbered options
- User can exit the system at any time

**FR-1.2:** The system shall accept user inputs for:
- Plaintext/ciphertext
- Encryption/decryption keys
- Algorithm selection

**FR-1.3:** The system shall validate all user inputs
- Check for empty inputs
- Validate key formats and lengths
- Provide clear error messages

### 2.2 Educational Display Requirements

**FR-2.1:** The system shall display step-by-step encryption processes
- Show character-by-character transformations
- Display intermediate results
- Explain the algorithm rules being applied

**FR-2.2:** The system shall display step-by-step decryption processes
- Reverse the encryption steps
- Show how decryption undoes encryption
- Maintain educational clarity

**FR-2.3:** The system shall show algorithm-specific information:
- Monoalphabetic: Character mappings
- Vigenere: Key alignment and shift amounts
- Playfair: 5x5 matrix and digraph handling
- Transposition: Grid layout and column ordering
- DES: XOR operations and intermediate values

## 3. Algorithm-Specific Requirements

### 3.1 Monoalphabetic Substitution Cipher

**FR-3.1.1:** The system shall encrypt using monoalphabetic substitution
- **Input:** Plaintext, 26-character substitution key
- **Output:** Ciphertext
- **Process:** Map each letter through substitution table

**FR-3.1.2:** The system shall decrypt monoalphabetic ciphertext
- **Input:** Ciphertext, 26-character substitution key
- **Output:** Plaintext
- **Process:** Reverse the substitution mapping

**FR-3.1.3:** The system shall validate monoalphabetic keys
- Key must be exactly 26 characters
- Key must contain unique letters only
- Key must be alphabetic only

**FR-3.1.4:** The system shall preserve non-alphabetic characters
- Spaces, numbers, and special characters remain unchanged

### 3.2 Vigenere Cipher

**FR-3.2.1:** The system shall encrypt using Vigenere cipher
- **Input:** Plaintext, keyword (any length)
- **Output:** Ciphertext
- **Process:** Shift each letter by corresponding key letter

**FR-3.2.2:** The system shall decrypt Vigenere ciphertext
- **Input:** Ciphertext, keyword
- **Output:** Plaintext
- **Process:** Reverse shift using keyword

**FR-3.2.3:** The system shall handle keyword repetition
- If keyword shorter than plaintext, repeat it
- Cycle through key letters as needed

**FR-3.2.4:** The system shall show key alignment
- Display plaintext with aligned keyword
- Show shift calculations

### 3.3 Playfair Cipher

**FR-3.3.1:** The system shall build a 5x5 key matrix
- **Input:** Keyword
- **Process:** Remove duplicates, fill remaining alphabet
- **Output:** 25-character matrix string

**FR-3.3.2:** The system shall encrypt using Playfair cipher
- **Input:** Plaintext, keyword
- **Output:** Ciphertext (digraphs)
- **Process:** Process letter pairs according to Playfair rules

**FR-3.3.3:** The system shall handle Playfair special cases
- Double letters: Insert X between them
- Odd length: Add X padding
- J and I: Treat J as I

**FR-3.3.4:** The system shall decrypt Playfair ciphertext
- Reverse the digraph transformations
- Remove padding where appropriate

**FR-3.3.5:** The system shall display the matrix
- Show 5x5 grid layout
- Display digraph pairing
- Explain rules for same row/column/rectangle

### 3.4 Keyed Transposition Cipher

**FR-3.4.1:** The system shall encrypt using keyed transposition
- **Input:** Plaintext, numeric/alphabetic key
- **Output:** Ciphertext
- **Process:** Write in rows, read columns by key order

**FR-3.4.2:** The system shall decrypt keyed transposition
- **Input:** Ciphertext, key
- **Output:** Plaintext
- **Process:** Reconstruct grid using key ordering

**FR-3.4.3:** The system shall handle padding
- Add X characters to fill grid
- Remove padding during decryption

**FR-3.4.4:** The system shall display grid layout
- Show row/column arrangement
- Display column reading order

### 3.5 Combined Cipher

**FR-3.5.1:** The system shall support combined encryption
- Apply Monoalphabetic first
- Then apply another algorithm (Vigenere, Playfair, or Transposition)

**FR-3.5.2:** The system shall support combined decryption
- Apply second algorithm decryption first
- Then apply Monoalphabetic decryption

**FR-3.5.3:** The system shall allow algorithm selection
- User chooses second algorithm from menu
- System prompts for appropriate keys

**FR-3.5.4:** The system shall show both transformations
- Display Monoalphabetic result
- Show second algorithm application
- Display final result

### 3.6 DES Cipher (Simplified)

**FR-3.6.1:** The system shall implement simplified DES
- **WARNING:** This is NOT secure, educational only
- **Input:** Up to 8 characters, 8-character key
- **Output:** Hexadecimal ciphertext

**FR-3.6.2:** The system shall encrypt using simplified DES
- XOR plaintext with key
- Convert to hex format

**FR-3.6.3:** The system shall decrypt simplified DES
- Parse hex input
- XOR to recover plaintext

**FR-3.6.4:** The system shall clearly warn users
- Display that this is NOT secure DES
- Mark as educational version only

### 3.7 Frequency Analysis (Cryptanalysis)

**FR-3.7.1:** The system shall perform frequency analysis
- **Input:** Ciphertext
- **Output:** Letter frequency table and suggestions

**FR-3.7.2:** The system shall count letter frequencies
- Calculate absolute counts
- Calculate percentages
- Display in formatted table

**FR-3.7.3:** The system shall compare with English frequencies
- Store standard English letter frequencies
- Display expected vs actual percentages
- Highlight significant deviations

**FR-3.7.4:** The system shall suggest substitutions
- Identify most frequent ciphertext letters
- Match with most frequent English letters
- Provide substitution suggestions

## 4. Input/Output Requirements

### 4.1 Input Handling

**FR-4.1:** All text inputs shall be accepted
- Plaintext, ciphertext, keys

**FR-4.2:** Inputs shall be validated
- Check for empty strings
- Validate formats (numeric keys, alphabetic keys, etc.)
- Check lengths as required by algorithms

**FR-4.3:** Special characters shall be handled
- Spaces may be preserved or removed (algorithm-dependent)
- Numbers preserved where appropriate
- Special characters handled according to algorithm

### 4.2 Output Formatting

**FR-4.4:** All outputs shall be clear and educational
- Show input and output clearly
- Display steps in readable format
- Use consistent formatting

**FR-4.5:** ASCII-friendly output
- Text-based console output
- Tables for matrices and frequencies
- Character-by-character details when needed

## 5. Security and Educational Requirements

### 5.1 Educational Focus

**FR-5.1:** The system shall emphasize education over security
- Clear displays of algorithm workings
- No claims of production-grade security
- Warnings on simplified implementations

**FR-5.2:** The system shall be interactive
- Users can try multiple examples
- Step-by-step demonstrations
- Clear explanations

### 5.3 Limitations Disclosure

**FR-5.3:** The system shall disclose limitations
- DES is NOT secure
- Console-only interface
- Character handling differences

## 6. Error Handling Requirements

**FR-6.1:** The system shall validate keys before processing
- Check length requirements
- Check format requirements
- Check for invalid characters/duplicates

**FR-6.2:** The system shall provide error messages
- Clear, descriptive messages
- Guidance on how to fix issues
- Non-technical language where possible

**FR-6.3:** The system shall handle exceptions gracefully
- Avoid crashes on invalid input
- Return to menu when errors occur
- Maintain user state

## 7. Documentation Requirements

**FR-7.1:** The system shall include README
- Installation/compilation instructions
- Usage guide
- Algorithm descriptions

**FR-7.2:** The system shall include Test Plan
- List of test cases
- Results summary
- Debugging examples

**FR-7.3:** The system shall include Functional Requirements
- This document
- All system requirements listed
- Algorithm specifications

## 8. Testing Requirements

**FR-8.1:** All algorithms shall have unit tests
- Input Space Partitioning approach
- Round trip verification
- Edge case coverage

**FR-8.2:** Tests shall document results
- Pass/fail for each test
- Success rate percentage
- Debugging information

**FR-8.3:** Tests shall be executable independently
- Each algorithm has separate test file
- Tests compile and run standalone
- Clear test output

## 9. Non-Functional Requirements

### 9.1 Performance

**NFR-1:** System shall respond quickly to user input
- Console operations should be immediate
- No noticeable delays for educational text sizes

**NFR-2:** System shall handle reasonable text sizes
- Support texts up to several hundred characters
- Educational examples only

### 9.2 Usability

**NFR-3:** System shall be easy to use
- Clear menu navigation
- Intuitive prompts
- Helpful error messages

**NFR-4:** System shall be educational
- Step-by-step displays aid learning
- Algorithm workings clearly visible

### 9.3 Maintainability

**NFR-5:** Code shall be well-organized
- Clear class structure
- Consistent naming conventions
- Comments explaining complex logic

**NFR-6:** Code shall be modular
- Each algorithm in separate class
- Common interface for algorithms
- Reusable utilities

## 10. Implementation Requirements

### 10.1 Technology Stack

**IM-1:** Language: Java
**IM-2:** Interface: Console/Command Line
**IM-3:** Testing: Unit tests with Input Space Partitioning

### 10.2 Code Organization

**IM-4:** Follow object-oriented principles
**IM-5:** Implement proper interfaces
**IM-6:** Validate inputs consistently
**IM-7:** Handle errors appropriately

## 11. Summary

This educational encryption tool implements six encryption algorithms, decryption functionality, and cryptanalysis tools. The focus is on learning and understanding cryptographic concepts through interactive, step-by-step demonstrations. All implementations are verified through comprehensive testing using Input Space Partitioning methodology.

The system successfully meets its educational objectives while maintaining code quality, clear documentation, and thorough testing as required by the assignment specifications.

