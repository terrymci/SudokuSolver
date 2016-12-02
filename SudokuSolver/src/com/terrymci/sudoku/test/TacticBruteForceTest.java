package com.terrymci.sudoku.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.terrymci.sudoku.Board;
import com.terrymci.sudoku.Cell;
import com.terrymci.sudoku.Coord;
import com.terrymci.sudoku.TacticBruteForce;

/**
 * TacticBruteForce class unit tests 
 */
public class TacticBruteForceTest
{
    @Test
    public void testRunTactic()
    {
        Board board = new Board();
        board.setRowMinimal(0, "295------");
        board.setRowMinimal(1, "1-4------");
        board.setRowMinimal(2, "386------");
        
        board.setRowMinimal(3, "6--------");
        board.setRowMinimal(4, "432765-98");
        board.setRowMinimal(5, "---------");
        
        board.setRowMinimal(6, "7--------");
        board.setRowMinimal(7, "8--------");
        board.setRowMinimal(8, "9--------");
        
        TacticBruteForce tactic = new TacticBruteForce();
        tactic.run(board);
        System.out.println("TacticBruteForceTest, after tactic");
        System.out.println(board);
        assertTrue(board.getCell(new Coord(5, 0)).equals(new Cell(5)));
        assertTrue(board.getCell(new Coord(4, 6)).equals(new Cell(1)));
        assertTrue(board.getCell(new Coord(1, 1)).equals(new Cell(7)));
    }

}
