package com.terrymci.sudoku;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Game play tactic that simply looks at empt cells and their containing 
 * rows, columns and squares. If the cumulative set of non-blank cell values
 * has just one missing out of all possible values, that cell is set to that value.
 */
public class TacticBruteForce extends Tactic
{
    @Override
    public String getName()
    {
        return "BruteForce";
    }
    
    @Override
    protected void runTactic(Board board)
    {
        Coord coord = new Coord();
        // Loop through each board cell row/column
        for (int row = 0; row < Board.DIMENSION_BOARD; ++row)
        {
            for (int col = 0; col < Board.DIMENSION_BOARD; ++col)
            {
                coord.setRowCol(row, col);
                checkCell(board, coord);
            }
        }
    }

    /**
     * Applies the brute force tacit to a single cell
     * @param board - The board
     * @param coord - Coordinate coi fcell under consideration
     */
    private void checkCell(Board board, Coord coord)
    {
        // If the current cell is not blank, stop now
        if (board.getCell(coord).hasValue())
        {
            return;
        }
        
        // Get the union set of the cell's board row, board column and square
        List<Cell> rowCells = board.getRow(coord.getRow());
        List<Cell> colCells = board.getCol(coord.getCol());
        int rowSquare = coord.getRow() / Board.DIMENSION_SQUARE;
        int colSquare = coord.getCol() / Board.DIMENSION_SQUARE;
        Coord coordSquare = new Coord(rowSquare, colSquare);
        Matrix<Cell> squareCells = board.getSquare(coordSquare);
        Set<Cell> setCells = new HashSet<Cell>();
        setCells.addAll(rowCells);
        setCells.addAll(colCells);
        setCells.addAll(squareCells.getAll());
        Cell emptyCell = new Cell();
        setCells.remove(emptyCell);
        
        // If that set has all but one value
        if (setCells.size() == (Board.DIMENSION_BOARD - 1))
        {
            outDebug("TacticBruteForce.checkCell() found match for coord " + coord);
            // The target cell must be that missing value
            for (int targetValue = 1; targetValue <= Board.DIMENSION_BOARD; ++targetValue)
            {
                Cell targetCell = new Cell(targetValue);
                if (!setCells.contains(targetCell))
                {
                    board.setCell(coord, targetCell);
                    outDebug("TacticBruteForce.checkCell() setting cell " + coord + " to value " + targetCell);
                    break;
                }
            }
        }
    }
    
}
