package com.terrymci.sudoku;

import java.util.List;

/**
 * Miscellaneous help code
 */
public class Utils
{
// CONSTANTS...
    static public final String NEWLINE = "\n";

// METHODS...
    /**
     * Grows a collection to the specified size, adding null values as needed. 
     */
    static public <T> void growList(List<T> list, int size)
    {
        while (list.size() < size)
        {
            list.add(null);
        }
    }
    
}
