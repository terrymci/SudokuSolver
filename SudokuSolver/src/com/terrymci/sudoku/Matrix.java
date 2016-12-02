package com.terrymci.sudoku;

import java.util.ArrayList;
import java.util.List;

/**
 * Generic matrix collection than can have <rows> x <columns> of a
 *   parameterized type. 
 * @param <T> - the matrix cell type
 */
public class Matrix<T> implements Cloneable
{
// METHODS...
    // Construction...
    public Matrix() {}
    
    public Matrix(Coord dimensions) 
    {
        setDimensions(dimensions);
    }
    public Matrix(int rows, int cols) 
    {
        setDimensions(rows, cols);
    }

    @Override
    public Object clone()
    {
        return getSubMatrix(new Coord(0, 0), getDimensions());
    }
    
    @Override
    public String toString()
    {
        return toString(",");
    }


    /**
     * toString implementation with the given horizontal separator character
     */
    public String toString(String separator)
    {
        StringBuilder sb = new StringBuilder();
        Coord dimensions = getDimensions();
        for (int row = 0; row < dimensions.getRow(); ++row)
        {
            for (int col = 0; col < dimensions.getCol(); ++col)
            {
                if (col > 0)
                {
                    sb.append(separator);
                }
                T item = getItem(row, col);
                String itemText = " ";
                if (item != null)
                {
                    itemText = item.toString();
                }
                sb.append(itemText);
            }
            sb.append(Utils.NEWLINE);
        }
        return sb.toString();
    }

    /**
     * Sets the matrix dimensions: <rows> x <columns>
     */
    public void setDimensions(Coord dimensions)
    {
        setDimensions(dimensions.getRow(), dimensions.getCol());
    }

    /**
     * Sets the matrix dimensions: <rows> x <columns>
     */
    public void setDimensions(int rows, int cols)
    {
        for (int row = 0; row < rows; ++row)
        {
            ArrayList<T> rowItems = new ArrayList<T>();
            Utils.growList(rowItems, cols);
            _items.add(rowItems);
        }
    }

    /**
     * Returns the matrix dimensions: <rows> x <columns>
     */
    public Coord getDimensions()
    {
        int rows = _items.size();
        int cols = 0;
        if (rows > 0)
        {
            cols = _items.get(0).size();
        }
        Coord coord = new Coord(rows, cols);
        return coord;
    }

    // Matric cell accessors...
    public T getItem(Coord coord)
    {
        return getItem(coord.getRow(), coord.getCol());
    }
    
    public T getItem(int row, int col)
    {
        List<T> rowList = _items.get(row);
        return rowList.get(col);
    }
    
    public void setItem(Coord coord, T value)
    {
        setItem(coord.getRow(), coord.getCol(), value);
    }

    public void setItem(int row, int col, T value)
    {
        List<T> rowList = _items.get(row);
        rowList.set(col, value);
    }


    /**
     * Retrieves a row of matrix elements. 
     * @param row - zero-based row position
     */
    public List<T> getRow(int row)
    {
        List<T> result = _items.get(row);
        return result;
    }
    
    /**
     * Retrieves a column of matrix elements. 
     * @param col - zero-based column position
     */
    public List<T> getCol(int col)
    {
        ArrayList<T> result = new ArrayList<T>();
        Utils.growList(result, _items.size());
        for (int row = 0; row < _items.size(); ++ row)
        {
            result.set(row, getItem(new Coord(row, col)));
        }
        
        return result;
    }
    
    // Retrieves all matrix elements.
    public List<T> getAll()
    {
        List<T> result = new ArrayList<T>();
        for (int row = 0; row < _items.size(); ++ row)
        {
            result.addAll(_items.get(row));
        }
        
        return result;
    }

    /**
     * Returns true if the given target value is present in the matrix,
     *   else false.
     */
    public boolean contains(T target)
    {
        for (int row = 0; row < _items.size(); ++ row)
        {
            if (_items.get(row).contains(target))
            {
                return true;
            }
        }
        
        return false;
    }

    /**
     * Returns a new matrix that is a sub-section of this matrix.
     *   This can entail the entire original matrix.
     * @param location - Coordinate of subsection upper-left cell 
     * @param dimensions - Dimensions of sub-section to retrieve
     * @return
     */
    public Matrix<T> getSubMatrix(Coord location, Coord dimensions)
    {
        // Create new matric to return
        Matrix<T> result = new Matrix<T>(dimensions);
        
        // Determine range on this matrix to copy into the result matrix
        int rowStart = location.getRow();
        int rowStop = rowStart + dimensions.getRow();
        int colStart = location.getCol();
        int colStop = colStart + dimensions.getCol();
        Coord coordSource = new Coord();
        Coord coordDest = new Coord();
        
        // Iterate over target ara and copy cells into result matrix
        for (int row = rowStart; row < rowStop; ++row)
        {
            for (int col = colStart; col < colStop; ++col)
            {
                coordSource.setRowCol(row, col);
                T value = getItem(coordSource);
                coordDest.setRowCol(row - rowStart, col - colStart);
                result.setItem(coordDest, value);
            }
        }
        return result;
    }
    
// DATA...
    // The matrix values: a list of list of <T> objects
    private List<ArrayList<T>> _items = new ArrayList< ArrayList<T> >();  
}
