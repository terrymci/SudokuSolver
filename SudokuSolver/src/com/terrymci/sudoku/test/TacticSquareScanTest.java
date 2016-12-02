package com.terrymci.sudoku.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.terrymci.sudoku.Board;
import com.terrymci.sudoku.Cell;
import com.terrymci.sudoku.Coord;
import com.terrymci.sudoku.TacticSquareScan;

/**
 * TacticSquareScan class unit tests 
 */
public class TacticSquareScanTest
{
    @Test
    public void testRunTactic()
    {
        Board board = new Board();
        board.setRowMinimal(0, "-826-4--9");
        board.setRowMinimal(1, "--5-2----");
        board.setRowMinimal(2, "--4-78--3");
        
        board.setRowMinimal(3, "-18-----6");
        board.setRowMinimal(4, "3--------");
        board.setRowMinimal(5, "5-----29-");
        
        board.setRowMinimal(6, "8--53-1--");
        board.setRowMinimal(7, "----1-9--");
        board.setRowMinimal(8, "4--2-763-");
        
        TacticSquareScan tactic = new TacticSquareScan();
        tactic.run(board);
        System.out.println("TacticSquareScanTest, after tactic");
        System.out.println(board);
        assertTrue(board.getCell(new Coord(2, 7)).equals(new Cell(2)));
        assertTrue(board.getCell(new Coord(3, 6)).equals(new Cell(3)));
        assertTrue(board.getCell(new Coord(8, 2)).equals(new Cell(1)));
    }

}
