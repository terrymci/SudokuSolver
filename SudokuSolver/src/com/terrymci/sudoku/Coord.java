package com.terrymci.sudoku;

/**
 * Simple 2-dimensional coordinate class for row/column position. 
 *
 */
public class Coord
{
// METHODS...    
    // Construction...
    public Coord()
    {
        setRowCol(0, 0);
    }
    
    public Coord(int row, int col)
    {
        setRowCol(row, col);
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(_row);
        sb.append(",");
        sb.append(_col);
        sb.append("]");
        return sb.toString();
    }
    
    // Accessors...
    public int getRow()
    {
        return _row;
    }
    
    public void setRow(int row)
    {
        _row = row;
    }

    public int getCol()
    {
        return _col;
    }
    
    public void setCol(int col)
    {
        _col = col;
    }
    
    public void setRowCol(int row, int col)
    {
        setRow(row);
        setCol(col);
    }
    
    public void setCoord(Coord coord)
    {
        _row = coord.getRow();
        _col = coord.getCol(); 
    }

    // Equality comparison
    @Override
    public boolean equals(Object other)
    {
        return equals((Coord)other);
    }
    
    public boolean equals(Coord other)
    {
        return ((getRow() == other.getRow()) &&
                (getCol() == other.getCol()));
    }
    
// DATA...
    // The row/column coordinates
    private int _row;
    private int _col;
}
