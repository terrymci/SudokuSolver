# SudokuSolver
Java project for a basic Sudoku puzzle solving engine. va
Refer to https://en.wikipedia.org/wiki/Sudoku for basic info about the game rules.
This project represents a Sudoku board as a 9x9 matrix of Cells, which can have an assigned integer value or a null value. 
The 3x3 subsections of the Board are called "Squares".
Game play is represented by "Tactics" - a particular kind of operation to analyze the current board state, and fill in empty cells where possible.
This project currently has two tactics:

"Brute Force"
For any empty cell, look at the combined set of non-empty cells that are in the same row, coliumn and 3x3 square. 
If that set lacks just one of the possible integer values, set that cell to be that value.

"Square Scan"
For any 3x3 square lacking a particular target integer value, make a copy of the square for analysis. 
Look for the target integer value in the board rows and columns that intersect the square. 
If the value is present in the row or colum, set the overlapping cells for that row/column in the analysis square to an arbitrary value (this uses zero). 
If the analysis square is left with only one blank cell, set that cell in the actual game board to be the target integer value. 
Repeat this for all integer values 1 - 9.

# Core Classes

*Coord - simple 2-dimensional coordinate class (row and column) used for board/square locations.
*Matrix - Generic matrix class. Can hold a <rows> x <columns> matric of a parameterized type.
*Cell - Sudoku cell thatg can hold an integer value or blank (nul.l) value
*Board - A 9x9 Matrix of Cells, with operations that support game play.
*Tactic - Abstract base class for specific game play tactics.
*TacticSquareScan - "Square Scan" tactic implementation, as descibed above.
*TacticBruteForce - "Brute Force" tactic, as described above.

# Other Classes
*Utils - Miscellaneous utility code.
*Output - Console output support.
*SudokuSolverApp - Main application class.
*Unit test classes - JUnit tests of core classes. 

# Dependencies
The JUnit tests use JUnit 4.10