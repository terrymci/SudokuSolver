package com.terrymci.sudoku;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Sudoku board class. Has the 9x9 matrix of Cells, functions to apply 
 *   cell values individually or in bulk, and game play operations.  
 */
public class Board
{
// CONSTANTS...
    // Square (3x3) and board (9x9) dimensions...
    public static final int DIMENSION_SQUARE = 3;
    public static final Coord DIMENSIONS_SQUARE = new Coord(DIMENSION_SQUARE,
            DIMENSION_SQUARE);
    public static final int DIMENSION_BOARD = 9;
    public static final Coord DIMENSIONS_BOARD = new Coord(DIMENSION_BOARD,
            DIMENSION_BOARD);


// CLASS METHODS...
    /**
     * Create and initialize a board from a file with cell data.
     *   The data can be in CSV format or "minimal" Sudoku coard text
     * @param fileName - file to read
     * @return - initializaed Board object if successful, null otherwise.
     */
    static public Board createFromFile(String fileName)
    {
        Board board = new Board();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName)))
        {
            List<String> lines = new ArrayList<String>();
            
            String nextLine;
            for (;;)
            {
                nextLine = reader.readLine();
                if (nextLine != null)
                {
                    lines.add(nextLine);
                    if (lines.size() == Board.DIMENSION_BOARD)
                    {
                        break;
                    }
                }
                else
                {
                    break;
                }
            }

            if (lines.size() != Board.DIMENSION_BOARD)
            {
                throw new IllegalArgumentException("Invalid line count");
            }
            
            int row = 0;
            for (String line : lines)
            {
                if (line.contains(","))
                {
                    board.setRowCSV(row, line);
                }
                else
                {
                    board.setRowMinimal(row, line);
                }
                row++;
            }
        }
        catch (FileNotFoundException ex)
        {
            board = null;
        }
        catch (IOException ex)
        {
            board = null;
        }
        catch (IllegalArgumentException ex)
        {
            board = null;
        }
        
        return board;
    }
    
// OBJECT METHODS...
    // Construction...
    public Board()
    {
    }

    @Override
    public String toString()
    {
        final String SEPARATOR_CELL = " ";
        final String SEPARATOR_SQUARE_COL = " ";
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < DIMENSION_BOARD; ++row)
        {
            if (row > 0)
            {
                sb.append(Utils.NEWLINE);
                // Add a spacer line to divide the squares vertically
                if (0 == (row % DIMENSION_SQUARE))
                {
                    sb.append(Utils.NEWLINE);
                }
            }
            List<Cell> rowCells = _cells.getRow(row);
            for (int col = 0; col < rowCells.size(); ++col)
            {
                if (col > 0)
                {
                    sb.append(SEPARATOR_CELL);
                    // Add spacer column for dividing the squares horizontally 
                    if (0 == (col % DIMENSION_SQUARE))
                    {
                        sb.append(SEPARATOR_SQUARE_COL);
                    }
                }
                sb.append(rowCells.get(col));
            }
        }

        return sb.toString();
    }

    /**
     * Initialize the board with all blank cells
     */
    public void initCells()
    {
        Coord coord = new Coord();
        for (int row = 0; row < Board.DIMENSION_BOARD; ++row)
        {
            for (int col = 0; col < Board.DIMENSION_BOARD; ++col)
            {
                coord.setRowCol(row, col);
                setCell(coord, new Cell());
            }
        }
    }

    /**
     * Retrieve the given row of cells 
     * @param row - zero-based row position
     */
    public List<Cell> getRow(int row)
    {
        return _cells.getRow(row);
    }

    /**
     * Retrieve the given column of cells
     * @param col - zero-based column position
     * @return
     */
    public List<Cell> getCol(int col)
    {
        return _cells.getCol(col);
    }

    /**
     * Retrieve the given "square" (3x3 section) of cells.
     * @param coordSquare - Coordinate of square in square position terms.
     *   For example, the square at coordinate of (1,2) has an upper-left
     *   cell at board position (3, 6) 
     */
    public Matrix<Cell> getSquare(Coord coordSquare)
    {
        Coord coordBoard = new Coord(coordSquare.getRow() * DIMENSION_SQUARE,
                coordSquare.getCol() * DIMENSION_SQUARE);
        return _cells.getSubMatrix(coordBoard, DIMENSIONS_SQUARE);
    }

    // Individial cell accessors...
    public Cell getCell(Coord coord)
    {
        return _cells.getItem(coord);
    }

    public void setCell(Coord coord, Cell value)
    {
        _cells.setItem(coord, value);
    }

    public void clearCell(Coord coord)
    {
        _cells.setItem(coord, new Cell());
    }

    /**
     * Sets a row of cells given the Comma-Separated-Value text. Null cell
     *   values can be empty strings or non-numeric characters. 
     *   For example: "1,2,-,3,-,5,-,7,8,-"
     * @param row - zero-based row position
     * @param cellText - CSV text with cell values
     * @throws IllegalArgumentException
     */
    public void setRowCSV(int row, String cellText)
            throws IllegalArgumentException
    {
        // Split the cell values into an array
        String[] cellTextItems = cellText.split(",");
        int col = 0;

        // Must be enough items for each column
        if (cellTextItems.length < DIMENSION_BOARD)
        {
            throw new IllegalArgumentException();
        }

        // Loop through the cell text values and set cells from them
        for (String cellTextItem : cellTextItems)
        {
            Integer valueInteger = null;
            try
            {
                int valueInt = Integer.parseInt(cellTextItem.trim());
                valueInteger = valueInt;
            } catch (NumberFormatException ex)
            {
            }
            _cells.setItem(row, col, new Cell(valueInteger));
            ++col;
            // Stop loop once we've filled up the row
            if (col >= DIMENSION_BOARD)
            {
                break;
            }
        }
    }

    /**
     * Sets a row of cells given the "minimal" text that has single digit
     *   cell values or non-numeric characters for clear cells. 
     *   For example: "12-4-6-78-"
     * @param row - zero-based row position
     * @param cellText - text with cell values
     * @throws IllegalArgumentException
     */
    public void setRowMinimal(int row, String cellText)
            throws IllegalArgumentException
    {
        if (cellText.length() < DIMENSION_BOARD)
        {
            throw new IllegalArgumentException();
        }
        for (int col = 0; col < DIMENSION_BOARD; ++col)
        {
            String cellTextItem = cellText.substring(col, col + 1);
            Integer valueInteger = null;
            try
            {
                int valueInt = Integer.parseInt(cellTextItem);
                valueInteger = valueInt;
            } catch (NumberFormatException ex)
            {
            }
            _cells.setItem(row, col, new Cell(valueInteger));
        }

    }

    /**
     * Returns true if the board is now completely filled up.
     */
    public boolean isComplete()
    {
        Coord coord = new Coord();
        for (int row = 0; row < DIMENSIONS_BOARD.getRow(); ++row)
        {
            for (int col = 0; col < DIMENSIONS_BOARD.getCol(); ++col)
            {
                coord.setRowCol(row, col);
                if (!_cells.getItem(coord).hasValue())
                {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Returns the number of cells with non-null values set.
     */
    public int getCountFilled()
    {
        int result = 0;
        Coord coord = new Coord();
        for (int row = 0; row < DIMENSIONS_BOARD.getRow(); ++row)
        {
            for (int col = 0; col < DIMENSIONS_BOARD.getCol(); ++col)
            {
                coord.setRowCol(row, col);
                if (_cells.getItem(coord).hasValue())
                {
                    ++result;
                }
            }
        }

        return result;
    }

    /**
     * Returns true if the board has no violations of cell value
     *   uniqueness, per the rules of Sudoku.
     */
    public boolean isValid()
    {
        // Check all rows
        for (int row = 0; row < Board.DIMENSION_BOARD; ++row)
        {
            if (!validateCells(getRow(row)))
            {
                System.out.println("Board failed validation at row: " + row);
                return false;
            }
        }
        
        // Check all columns
        for (int col = 0; col < Board.DIMENSION_BOARD; ++col)
        {
            if (!validateCells(getCol(col)))
            {
                System.out.println("Board failed validation at col: " + col);
                return false;
            }
        }

        // Check all 3x3 squares
        Coord coordSquare = new Coord();
        for (int sqRow = 0; sqRow < Board.DIMENSION_SQUARE; ++sqRow)
        {
            for (int sqCol = 0; sqCol < Board.DIMENSION_SQUARE; ++sqCol)
            {
                coordSquare.setRowCol(sqRow, sqCol);
                if (!validateCells(getSquare(coordSquare).getAll()))
                {
                    System.out.println("Board failed validation at square: "
                            + coordSquare);
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Tries to solve a board by cycling through the given list of
     *   game play tactics.
     * @param tactics - Polymorphic collection of game play tactics  
     * @return - true if the board is solved, else false
     */
    public boolean runGame(List<Tactic> tactics)
    {
        int countFilledBefore = getCountFilled();
        int countFilledAfter = 0;
        Output.printLine("Starting game, count filled = " + countFilledBefore, 
                Output.Level.VERBOSE);
        Output.printLine(Output.Level.VERBOSE);
        Output.printLine(toString(), Output.Level.VERBOSE);
        Output.printLine(Output.Level.VERBOSE);
        // Loop until complete, invalid, or no more progress
        boolean stop = false;
        for (int pass = 0; !stop; ++pass)
        {
            if (pass > 0)
            {
                countFilledBefore = getCountFilled();
            }
            Output.printLine("Pass " + (pass + 1), Output.Level.VERBOSE);
            Output.printLine(Output.Level.VERBOSE);
            
            // Loop through tactics
            for (Tactic tactic : tactics)
            {
                // Run tactic
                tactic.run(this);

                countFilledAfter = getCountFilled();
                Output.printLine("After " + tactic.getName() + ", count filled = " + countFilledAfter, 
                        Output.Level.VERBOSE);
                Output.printLine(Output.Level.VERBOSE);
                Output.printLine(toString(), Output.Level.VERBOSE);
                Output.printLine(Output.Level.VERBOSE);
                
                // Break now if board is complete
                if (isComplete())
                {
                    Output.printLine("Game complete", Output.Level.VERBOSE);
                    Output.printLine(Output.Level.VERBOSE);
                    stop = true;
                    break;
                }
                
                // If board is invalid - give up
                if (!isValid())
                {
                    Output.printLine("Board is invalid! Game abandoned!", Output.Level.VERBOSE);
                    Output.printLine(Output.Level.VERBOSE);
                    stop = true;
                    break;
                }
            }

            // If no progress made on this pass, give up
            if (countFilledBefore == countFilledAfter)
            {
                Output.printLine("No further progress! Game abandoned!", Output.Level.VERBOSE);
                Output.printLine(Output.Level.VERBOSE);
                stop = true;
                break;
            }
        }
        
        return isComplete();
    }
    
    /**
     * Returns true if the given group of cells has all unique values among 
     *   the non-blank ones, else false.
     */
    static private boolean validateCells(List<Cell> cells)
    {
        Set<Integer> cellSet = new HashSet<Integer>();
        for (Cell cell : cells)
        {
            if (!cell.hasValue())
            {
                continue;
            }
            if (cellSet.contains(cell.getValue()))
            {
                return false;
            }
            cellSet.add(cell.getValue());
        }
        return true;
    }

    // DATA...

    private Matrix<Cell> _cells = new Matrix<Cell>(DIMENSIONS_BOARD);
}
