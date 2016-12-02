package com.terrymci.sudoku;

import java.util.List;

/**
 * Game play tactic that looks for cell values to set within a 3x square based on 
 *   the current cells filled in that square, and any overlap of a given cell value 
 *   under consideration in the cells of board rows/colums that intersect that square.
 */
public class TacticSquareScan extends Tactic
{
    @Override
    public String getName()
    {
        return "SquareScan";
    }

    @Override
    public void runTactic(Board board)
    {
        Coord coordSquare = new Coord();
        
        // For each number value 1 to 9
        for (int target = 1; target <= Board.DIMENSION_BOARD; ++target)
        {
            // For each board square row/column
            for (int row = 0; row < Board.DIMENSION_SQUARE; ++row)
            {
                for (int col = 0; col < Board.DIMENSION_SQUARE; ++col)
                {
                    coordSquare.setRowCol(row, col);
                    runSquareScan(board, target, coordSquare);
                }
            }
        }
    }
    
    private void runSquareScan(Board board, int target, Coord coordSquare)
    {
        outDebug("TacticSquareScan.runSquareScan(), target=" + target + " coordSquare=" + coordSquare);
        
        // If the target number is in the square, stop now
        Matrix<Cell> square = board.getSquare(coordSquare);
        Cell targetCell = new Cell(target);
        if (square.contains(targetCell))
        {
            outDebug("  Target already in square");
            return;
        }
        
        // For each board row the square is part of:
        int rowStart = coordSquare.getRow() * Board.DIMENSION_SQUARE;
        int rowStop = rowStart + Board.DIMENSION_SQUARE;
        for (int row = rowStart; row < rowStop; ++row)
        {
            // If the target number is in that row
            List<Cell> boardRow = board.getRow(row);
            if (boardRow.contains(targetCell))
            {
                // Fill in any blank cells of the square for that row with 
                //   an arbitrary placeholder value
                fillSquareRow(square, row - rowStart);
            }
        }
        
        // For each board column the square is part of:
        int colStart = coordSquare.getCol() * Board.DIMENSION_SQUARE;
        int colStop = colStart + Board.DIMENSION_SQUARE;
        for (int col = colStart; col < colStop; ++col)
        {
            // If the target number is in that column
            List<Cell> boardCol = board.getCol(col);
            if (boardCol.contains(targetCell))
            {
                // Fill in any blank cells of the square for that column with 
                //   an arbitrary placeholder value
                fillSquareCol(square, col - colStart);
            }
        }

        // If there is a single unfilled cell in the square, that
        //   has to be the target value. Set that cell in the board.
        Coord coordBlank = findOnlyBlank(square);
        if (coordBlank != null)
        {
            Coord coordBoard = 
                new Coord(rowStart + coordBlank.getRow(),
                          colStart + coordBlank.getCol());
            board.setCell(coordBoard, targetCell);
            outDebug("  target set in board at " + coordBoard);
        }
        else
        {
            outDebug("  no target match");
        }
    }

    /**
     * Fills any blank cells of a square's row with an arbitrary (zero)
     *   cell value. 
     */
    private void fillSquareRow(Matrix<Cell> square, int row)
    {
        Cell filler = new Cell(0);
        Coord coord = new Coord();
        coord.setRow(row);
        for (int col = 0; col < Board.DIMENSION_SQUARE; ++col)
        {
            coord.setCol(col);
            square.setItem(coord, filler);
        }
    }
    
    /**
     * Fills any blank cells of a square's column with an arbitrary (zero)
     *   cell value. 
     */
    private void fillSquareCol(Matrix<Cell> square, int col)
    {
        Cell filler = new Cell(0);
        Coord coord = new Coord();
        coord.setCol(col);
        for (int row = 0; row < Board.DIMENSION_SQUARE; ++row)
        {
            coord.setRow(row);
            square.setItem(coord, filler);
        }
    }
    
    /**
     * Returns the coordinate of the matrix cell that is the only one
     *   in the matrix wiht a blank value. Retunrs null if there is no such
     *   cell (if more than one cell is blank, or none are.)
     * @param square
     * @return
     */
    private Coord findOnlyBlank(Matrix<Cell> square)
    {
        Coord coordLoop = new Coord();
        Coord coordResult = new Coord();
        boolean oneBlank = false;
        
        // Loop through the matrix cells
        for (int row = 0; row < Board.DIMENSION_SQUARE; ++row)
        {
            for (int col = 0; col < Board.DIMENSION_SQUARE; ++col)
            {
                coordLoop.setRowCol(row, col);
                // hit a cell with blank value
                if (!square.getItem(coordLoop).hasValue())
                {
                    // Save off the coordinates if this is the first one
                    if (!oneBlank)
                    {
                        coordResult.setCoord(coordLoop);
                        oneBlank = true;
                    }
                    // Not the first? return unsuccessful result now.
                    else
                    {
                        return null;
                    }
                }
            }
        }
 
        // Return the result coordinates, if one found
        if (oneBlank)
        {
            return coordResult;
        }
        else
        {
            return null;
        }
    }

}
