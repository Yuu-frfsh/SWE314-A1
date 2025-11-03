Page 1 of 3
King Saud University
College of Computer and Information Sciences
Department of Software Engineering
SWE 314 – SOFTWARE SECURITY ENGINEERING 3(3-0-1)
Fall 2025-2026
Assignment #1 – Educational Encryption / Decryption Software Tool
(Done in groups of 3-5 students)
Delivery date: Wednesday, October 22
nd
, 2025.
Requirements:
The objective of this mini-project is to design and implement an encryption/decryption software
tool for educational purposes. Users unfamiliar with cryptography or cryptanalysis can use this tool
to learn their basics. This tool should contain a list of encryption algorithms and decryption
algorithms as described below. The objective is to offer the user different choices of
encryption/decryption techniques, always for educational purposes.
Educational purposes mean that the tool must be designed to allow interaction with the user during
the process of encryption or decryption. The steps during the encryption/decryption must be shown
to the users interactively so that they understand how encryption/decryption works.
As software engineers, we will follow the “Analyze-Design-Implement-Test” process to ensure we
produce a tool of good quality. The process to follow:
1. Produce a list of functional requirements that need to be considered to build the software
tool.
2. Produce the following UML diagrams:
- Produce a use case model (diagram + descriptions when appropriate) describing how the
users will use the tool.
- Complement the use cases by specifying the different encryption/decryption algorithms
in pseudo-code (to be discussed in class).
- Produce a class diagram showing the main classes, including methods and attributes
needed.
Page 2 of 3
3. Implement the different use cases identified in 2 by considering the class diagram.
4. Test your implementation at the Unit Testing level by using the Input Space Partitioning
approach (to be discussed in class).
5. Debug your implementation to ensure your code is bug-free. Consider unit and integration
testing.
6. Run different execution cases to show that your implementation works fine on different
algorithms in various situations.
7. Use the programming language of your choice.
8.
The Encryption Algos to consider are:
- Encryption based on monoalphabetic substitution.
- Encryption using the Playfair Algorithm
- Encryption using the Vigenere Algorithm
- Encryption using the Keyed Transposition
- Encryption by combining monoalphabetic substitution and one of the three algorithms
(Playfair, Vigenere, or Keyed Transposition)
- DES
The Decryption Algos to consider are:
- Monoalphabetic substitution
- Vigenere OR Playfair OR Keyed Transposition.
+
- Consider one of the cryptanalysis techniques (could be the linguistic frequency) and try
it on simple examples.
Deliverables:
Report + Code:
- Readme file (explaining your implementation and how your code is organized)
- The code on a flash disk (or through shared folder).
- The report (soft and hard copies) shall contain the following:
 Cover page
 Summary
 Introduction/Overview
Page 3 of 3
 List of functional requirements
 Use case model
 Class diagram
 Pseudo-code of the different algorithms
 Samples of code with appropriate explanations
 A complete test plan (Unit test cases) for each algorithm along with
execution of test cases and the results obtained (pass/fail).
 Show examples of debugging you have done to fix the bugs identified
with unit testing
 A series of executions showing the input, the steps, and the output of the
program, for each algorithm.
 Limitations (what has been done and what has not be done; what works
and what does not work)
 Conclusion