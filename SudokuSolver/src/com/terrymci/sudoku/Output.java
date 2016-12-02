package com.terrymci.sudoku;

/**
 * Display output support. 
 * Currently just supports console output via System.out.println 
 */
public class Output
{
    // Output verbosity levels
    public enum Level 
    {
        NORMAL,
        VERBOSE,
        DEBUG,
    }
    
    /**
     * Sets the output verbosity level
     * @param level
     */
    public static void setLevel(Level level)
    {
        _level = level;
    }
    
    /**
     * Prints a blank line for the given output level.
     */
    public static void printLine(Level level)
    {
        if (_level.compareTo(level) >= 0)
        {
            printLine("");
        }
    }

    /**
     * Prints a line of text for the given output level
     */
    public static void printLine(String line, Level level)
    {
        if (_level.compareTo(level) >= 0)
        {
            printLine(line);
        }
    }

    /**
     * Actually prints a line to the output channel
     * @param line
     */
    private static void printLine(String line)
    {
        System.out.println(line);
    }
    
    // Just use static methods
    private Output() {}
    
// DATA...
    // The active output level
    private static Level _level = Level.NORMAL;
}
