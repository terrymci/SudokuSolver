package com.terrymci.sudoku.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.terrymci.sudoku.Cell;

/**
 * Cell class unit tests.
 */
public class CellTest
{
    @Test
    public void testEquals()
    {
        Cell cellBlank = new Cell();
        Cell cellClear = new Cell(123);
        cellClear.clearValue();
        Cell cellA = new Cell(1);
        Cell cellB = new Cell(2);
        
        assertTrue(cellBlank.equals(cellClear));
        assertTrue(!cellBlank.equals(cellA));
        assertTrue(!cellA.equals(cellClear));
        assertTrue(!cellA.equals(cellB));
        assertTrue(cellA.equals(cellA));
        cellB.setValue(cellA);
        assertTrue(cellA.equals(cellB));
    }

    @Test
    public void testHasValue()
    {
        Cell cellBlank = new Cell();
        Cell cellClear = new Cell(123);
        cellClear.clearValue();
        Cell cellA = new Cell(1);
        assertTrue(!cellBlank.hasValue());
        assertTrue(!cellClear.hasValue());
        assertTrue(cellA.hasValue());
        Integer nullInt = null;
        cellA.setValue(nullInt);
        assertTrue(!cellA.hasValue());
       
    }
    
}
