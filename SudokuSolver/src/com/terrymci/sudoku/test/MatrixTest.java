package com.terrymci.sudoku.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.terrymci.sudoku.Coord;
import com.terrymci.sudoku.Matrix;

/**
 * Matrix class unit tests
 * 
 */
public class MatrixTest
{
    @Test
    public void testClone()
    {
        Matrix<Integer> matrix1 = generateMatrix(2, 3);
        @SuppressWarnings("unchecked")
        Matrix<Integer> matrix2 = (Matrix<Integer>)matrix1.clone();
        assertTrue(matrix1.getDimensions().equals(matrix2.getDimensions()));
        assertEquals(matrix1.getItem(0, 0), matrix2.getItem(0, 0));
        assertEquals(matrix1.getItem(1, 2), matrix2.getItem(1, 2));
    }

    @Test
    public void testGetRow()
    {
        Matrix<Integer> matrix = generateMatrix(3, 3);
        List<Integer> row = matrix.getRow(1);
        assertEquals(row.get(0).intValue(), 3);
        assertEquals(row.get(1).intValue(), 4);
        assertEquals(row.get(2).intValue(), 5);
    }

    @Test
    public void testGetCol()
    {
        Matrix<Integer> matrix = generateMatrix(3, 3);
        List<Integer> col = matrix.getCol(1);
        assertEquals(col.get(0).intValue(), 1);
        assertEquals(col.get(1).intValue(), 4);
        assertEquals(col.get(2).intValue(), 7);
    }

    @Test
    public void testGetAll()
    {
        Matrix<Integer> matrix = generateMatrix(3, 3);
        List<Integer> items = matrix.getAll();
        assertEquals(items.get(0).intValue(), 0);
        assertEquals(items.get(5).intValue(), 5);
        assertEquals(items.get(8).intValue(), 8);
    }

    @Test
    public void testContains()
    {
        Matrix<Integer> matrix = generateMatrix(4, 4);
        assertTrue(matrix.contains(new Integer(0)));
        assertTrue(matrix.contains(new Integer(8)));
        assertTrue(matrix.contains(new Integer(15)));
        assertTrue(!matrix.contains(new Integer(-1)));
        assertTrue(!matrix.contains(new Integer(16)));
    }

    @Test
    public void testGetSubMatrix()
    {
        Matrix<Integer> matrix = generateMatrix(9, 9);
        System.out.println("MatrixTest.testGetSubMatrix(), main matrix:");
        System.out.println(matrix);
        Coord coordLoc = new Coord(3, 6);
        Coord coordDim = new Coord(2, 3);
        Matrix<Integer> subMatrix = matrix.getSubMatrix(coordLoc, coordDim);
        System.out.println("MatrixTest.testGetSubMatrix(), sub matrix:");
        System.out.println(subMatrix);
        assertEquals(subMatrix.getItem(0, 0).intValue(), 33);
        assertEquals(subMatrix.getItem(1, 1).intValue(), 43);
    }

    // Test data generation help
    private Matrix<Integer> generateMatrix(int rows, int cols)
    {
        Matrix<Integer> matrix = new Matrix<Integer>(rows, cols);
        Integer value = 0;
        for (int row = 0; row < rows; ++row)
        {
            for (int col = 0; col < cols; ++col)
            {
                matrix.setItem(row, col, value);
                value = value + 1;
            }
        }
        return matrix;
    }
    
}
