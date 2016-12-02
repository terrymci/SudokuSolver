package com.terrymci.sudoku.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.terrymci.sudoku.Board;
import com.terrymci.sudoku.Cell;
import com.terrymci.sudoku.Coord;
import com.terrymci.sudoku.Matrix;

/**
 * Board class unit tests,
 */
public class BoardTest
{
    @Test
    public void testGetSquare()
    {
        Board board = generateCompleteBoard();
        System.out.println("BoardTest.testGetSquare() board: ");
        System.out.println(board);
        Matrix<Cell> square = board.getSquare(new Coord(1, 2)); 
        System.out.println("BoardTest.testGetSquare() square(1,2): ");
        System.out.println(square);
        assertEquals(square.getItem(0, 0).getValue().intValue(), 8);
        assertEquals(square.getItem(1, 1).getValue().intValue(), 3);
        assertEquals(square.getItem(2, 2).getValue().intValue(), 7);
    }
    
    @Test
    public void testIsComplete()
    {
        Board board = generateCompleteBoard();
        System.out.println("BoardTest.testIsComplete board: ");
        System.out.println(board);
        assertTrue(board.isComplete());
        board.setCell(new Coord(5, 5), new Cell());
        assertTrue(!board.isComplete());
    }

    @Test
    public void testCountFilled()
    {
        Board board1 = generateCompleteBoard();
        assertTrue(board1.getCountFilled() == 81);
        board1.setCell(new Coord(5, 5), new Cell());
        assertTrue(board1.getCountFilled() == 80);
        Board board2 = new Board();
        board2.initCells();
        assertTrue(board2.getCountFilled() == 0);
    }

    
    @Test
    public void testValidate()
    {
        Board board1 = new Board();
        board1.initCells();
        assertTrue(board1.isValid());
        board1.setCell(new Coord(7, 3), new Cell(8));
        assertTrue(board1.isValid());
        
        Board board2 = generateCompleteBoard();
        assertTrue(board2.isValid());
        board2.setCell(new Coord(6, 6), new Cell());
        assertTrue(board2.isValid());
        board2.setCell(new Coord(5, 5), new Cell(9));
        System.out.println("BoardTest.testValidate invalid board: ");
        System.out.println(board2);
        assertTrue(!board2.isValid());
    }

    
    // Test data generation help...
    static public Board generateCompleteBoard()
    {
        Board board = new Board();
        Coord coord = new Coord();
        for (int row = 0; row < Board.DIMENSION_BOARD; ++row)
        {
            for (int col = 0; col < Board.DIMENSION_BOARD; ++col)
            {
                coord.setRowCol(row, col);
                int value = ((col + (row * Board.DIMENSION_SQUARE) + (row / Board.DIMENSION_SQUARE)) % Board.DIMENSION_BOARD) + 1; 
                board.setCell(coord, new Cell(value));
            }
        }
        return board;
    }

    static public Board generatePartialBoard()
    {
        Board board = generateCompleteBoard();
        Coord coord = new Coord();
        for (int row = 0; row < Board.DIMENSION_BOARD; ++row)
        {
            for (int col = 0; col < Board.DIMENSION_BOARD; ++col)
            {
                if ((((row + col) % 2) == 1) ||
                    (((row + col) % 5) == 3))
                {
                    coord.setRowCol(row, col);
                    board.clearCell(coord);
                }
            }
        }
        return board;
    }

}
