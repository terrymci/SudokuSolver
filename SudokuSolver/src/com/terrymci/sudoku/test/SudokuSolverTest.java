package com.terrymci.sudoku.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Unit test suite for project
 */
@RunWith(Suite.class)
@SuiteClasses({
   CellTest.class,
   MatrixTest.class, 
   BoardTest.class, 
   TacticBruteForceTest.class,
   TacticSquareScanTest.class
   })
public class SudokuSolverTest
{

}
