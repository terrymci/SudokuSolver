package com.terrymci.sudoku;

import java.util.ArrayList;
import java.util.List;

/**
 * Main application class.
 */
/*
   args[] parameters:
     [0] :file with initial board data (can be CSV or "minial" representation. Required.
     [1] : -v or -V flag to specify berbose output that shows intermediate 
           board states
 */
public class SudokuSolverApp
{
    /**
     * main function
     */
    public static void main(String[] args)
    {
        // Show usage
        if (args.length < 1)
        {
            System.out.println("Usage: java -jar SudokuSolver.jar <filename> [-v]");
            System.out.println("  -v : verbose mode");
            return;
        }
        
        // First parameter must be file name
        String fileName = args[0];
        
        // Optional verbose output flag after that
        if (args.length > 1)
        {
            if (args[1].compareToIgnoreCase("-v") == 0)
            {
                Output.setLevel(Output.Level.VERBOSE);
            }
        }
        
        // Read in the board data
        Board board = Board.createFromFile(fileName);

        // Bad board data
        if (board == null)
        {
            System.out.println("Error: invalid board file - " + fileName);
            return;
        }
        
        // Starting output
        Output.printLine("START:", Output.Level.NORMAL);
        Output.printLine(board.toString(), Output.Level.NORMAL);
        Output.printLine("", Output.Level.NORMAL);
        
        // Set up tactic collectionm
        List<Tactic> tactics = new ArrayList<Tactic>();
        tactics.add(new TacticSquareScan());
        tactics.add(new TacticBruteForce());
        
        // Run them
        board.runGame(tactics);

        // Ending board state outoput
        Output.printLine("END:", Output.Level.NORMAL);
        Output.printLine(board.toString(), Output.Level.NORMAL);
        boolean isComplete = board.isComplete();
        boolean isValid = board.isValid();
        Output.printLine("", Output.Level.NORMAL);
        Output.printLine("Board complete = " + isComplete, Output.Level.NORMAL);
        Output.printLine("Board valid = " + isValid, Output.Level.NORMAL);
        if (!isComplete)
        {
            Output.printLine("Board fill count = " + board.getCountFilled(), Output.Level.NORMAL);
        }
    }    
    
}
