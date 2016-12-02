package com.terrymci.sudoku;

/**
 * Abstract base class for a Sudoku tactic. Includes method to apply the
 *   tactic to a game board. 
 */
public abstract class Tactic
{
    /**
     * Apply this tactic to a game board. 
     * 
     */
    public void run(Board board)
    {
        // Don't bother if board is complete or invalid
        if (board.isComplete())
        {
            return;
        }
        if (!board.isValid())
        {
            return;
        }
        
        // Call protected abstract method that does the real work
        runTactic(board);
    }

    /**
     * Returns a name of the tactic for output
     */
    public abstract String getName();
    
    /**
     * Abstract method for derived classes to apply their tactic to a gae board.
     */
    protected abstract void runTactic(Board board);

    /**
     * Shorthand method for debug level output.
     */
    protected void outDebug(String line)
    {
        Output.printLine(line, Output.Level.DEBUG);
    }
   
}
