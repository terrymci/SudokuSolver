package com.terrymci.sudoku;

/**
 * Represents a single Sudoku cell. Has an integer value or null value applied. 
 */
public class Cell 
{
// METHODS...
    // Construction
    public Cell() 
    {
    }
    
    public Cell(Integer value)
    {
        setValue(value);
    }
    
    public Cell(int value)
    {
        setValue(new Integer(value));
    }

    // Object overrides for set operations
    @Override
    public boolean equals(Object other)
    {
        if (other == null)
        {
            return false;
        }
        Cell otherCell = (Cell)other;
        if (hasValue() == otherCell.hasValue())
        {
            if (!hasValue())
            {
                return true;
            }
            return (getValue().equals(otherCell.getValue()));
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        if (hasValue())
        {
            return _value.intValue();
        }
        return -1;
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        if (_value != null)
        {
            sb.append(_value.intValue());
        }
        else
        {
            sb.append(NULL_CHAR);
        }
        
        return sb.toString();
    }

    // Accessors...
    public Integer getValue()
    {
        return _value;
    }

    public void setValue(Cell other)
    {
        _value = other.getValue();
    }

    public void setValue(Integer value)
    {
        _value = value;
    }
    
    public void setValue(int value)
    {
        _value = value;
    }
    
    public boolean hasValue()
    {
        return (_value != null);
    }
    
    public void clearValue()
    {
        _value = null;
    }
    
// DATA...
    // "Null" value text representation
    public static final char NULL_CHAR = '-';

    // The cell value
    private Integer _value;
}
