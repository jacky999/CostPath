package com.code.sample.costPath;

import com.code.sample.costPath.core.MatrixPath;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created on 1/20/2017. for JUnit4 testing
 */
public class MatrixPathTest {

    private MatrixPath matrixPath;


    @Before
    public void setUp() throws Exception {
        matrixPath = new MatrixPath();
    }

    @Test
    public void setInputRow_NormalValue_Passed() throws Exception {
        int input = 8;
        final boolean b  = matrixPath.setInputRow(input);
        Assert.assertTrue(b);
    }


    @Test
    public void setInputRow_Value0_Failed() throws Exception {
        // testing with input row number = 0
        int input = 0;
        final boolean b  = matrixPath.setInputRow(input);
        Assert.assertFalse(b);
    }

    @Test
    public void setInputRow_NegativeValue_Failed () throws Exception {
        // testing with input row number = negative
        int input = -5;

        final boolean b  = matrixPath.setInputRow(input);
        Assert.assertFalse(b);
    }

    @Test
    public void setInputRow_IndexOutOfBounds_Failed() throws Exception {
        // testing with input row number = negative
        int input = 12;

        final boolean b  = matrixPath.setInputRow(input);
        Assert.assertFalse(b);
    }

    @Test
    public void setInputColumn_NormalValue_Passed() throws Exception {
        int input = 5;

        final boolean b  = matrixPath.setInputColumn(input);
        Assert.assertTrue(b);
    }


    @Test
    public void setInputColumn_Value0_Failed() throws Exception {
        // testing with input column number = 0
        int input = 0;

        final boolean b  = matrixPath.setInputColumn(input);
        Assert.assertFalse(b);
    }

    @Test
    public void setInputColumn_NegativeValue_Failed() throws Exception {
        // testing with input column number = negative
        int input = -5;

        final boolean b  = matrixPath.setInputColumn(input);
        Assert.assertFalse(b);
    }

    @Test
    public void setInputColumn_IndexOutOfBounds_Failed() throws Exception {
        // testing with input colum number = negative
        int input = 12;

        final boolean b  = matrixPath.setInputColumn(input);
        Assert.assertFalse(b);
    }


    @Test
    public void setInputMaxCost_NormalValue_Passed() throws Exception {
        int input = 10;

        final boolean b  = matrixPath.setInputMaxCost(input);
        Assert.assertTrue(b);
    }

    @Test
    public void setInputMaxCost_Value0_Failed() throws Exception {
        int input = 0;

        final boolean b  = matrixPath.setInputMaxCost(input);
        Assert.assertFalse(b);
    }

    @Test
    public void setInputMaxCost_NegativeValue_Failed() throws Exception {
        int input = -5;

        final boolean b  = matrixPath.setInputMaxCost(input);
        Assert.assertFalse(b);
    }

    @Test
    public void setInputMatrix_NormalValueIsChecked_Passed() throws Exception {
       int row = 3;
        int column = 5;
        int [][] inputTable = { {1,2,3,4,5},
                                {11,12,13,14,15},
                                {21,22,23,24,25}};

        final boolean b = matrixPath.setInputMatrix(row, column, inputTable);
        Assert.assertTrue(b);
    }

    @Test
    public void setInputMatrix_NormalValueIsSet_Passed() throws Exception {
        int row = 3;
        int column = 5;
        int [][] inputTable = { {1,2,3,4,5},
                                {11,12,13,14,15},
                                {21,22,23,24,25}};

        if (matrixPath.setInputMatrix(row, column, inputTable)) {
            final boolean b = Arrays.deepEquals(inputTable, matrixPath.getInputMatrix());
            Assert.assertTrue(b);
        }

    }

    @Test
    public void setInputMatrix_InputArrayLengthNull_Failed() throws Exception {
        int row = 3;
        int column = 5;
        int [][] inputTable = {{},{}};

        final boolean b = matrixPath.setInputMatrix(row, column, inputTable);
        Assert.assertFalse(b);
    }


    @Test
    public void setInputMatrix_InputWrongArrayRowLength_Failed() throws Exception {
        int row = 3;
        int column = 5;
        int [][] inputTable = { {1,2,3,4,5},
                                {11,12,13,14,15}};

        final boolean b = matrixPath.setInputMatrix(row, column, inputTable);
        Assert.assertFalse(b);
    }

    @Test
    public void setInputMatrix_InputWrongArrayColumnLength_Failed() throws Exception {
        int row = 3;
        int column = 5;
        int [][] inputTable = { {1,2,3,4,5},
                                {11,12,13},
                                {21,22,23,24,25}};

        final boolean b = matrixPath.setInputMatrix(row, column, inputTable);
        Assert.assertFalse(b);
    }


    @Test
    public void bestCostSearch_WithMaxRowMaxColumnInput_Passed() throws Exception {
        int row = 10;
        int column = 10;
        int maxCost = 88;

        int expectedResult = 10;
        String expectedPath = "1, 1, 1, 1, 1, 1, 1, 1, 1, 1"; // path: [00], [11], [22], [33], [44], [55], [66], [77], [88], [99]
        int actual;
        int [][] inputTable = {
                {1,2,3,4,5,6,7,8,9,10},
                {10,1,2,3,4,5,6,7,8,9},
                {9,10,1,2,3,4,5,6,7,8},
                {8,9,10,1,2,3,4,5,6,7},
                {7,8,9,10,1,2,3,4,5,6},
                {6,7,8,9,10,1,2,3,4,5},
                {5,6,7,8,9,10,1,2,3,4},
                {4,5,6,7,8,9,10,1,2,3},
                {3,4,5,6,7,8,9,10,1,2},
                {2,3,4,5,6,7,8,9,10,1}};

        matrixPath.setInputRow(row);
        matrixPath.setInputColumn(column);
        matrixPath.setInputMaxCost(maxCost);
        matrixPath.setInputMatrix(row, column, inputTable);

        String output = matrixPath.bestCostSearch();
        String delimiter = ": ";
        String[] tmpInputString;
        tmpInputString = output.trim().split(delimiter);

        actual = Integer.parseInt(tmpInputString[2]);
        String actualPath = tmpInputString[1].replace("Total Cost", "").trim();

        System.out.println("The Total Cost: " +actual );
        System.out.println("Result Path " + actualPath);

        Assert.assertEquals(expectedResult, actual);
        Assert.assertEquals(expectedPath, actualPath);

        actual = Integer.parseInt(tmpInputString[2]);
        Assert.assertEquals(expectedResult, actual);

    }


    @Test
    public void bestCostSearch_OverMaxCostNoPathFound_Passed() throws Exception {
        // check if the searching is hopping from the top row to the bottom row and verse via
        int row = 3;
        int column = 6;
        int maxCost = 20;

        // int expectedResult = 26; // over the maxCost = 20
        String expectedPath = "No Solutions";

        int [][] inputTable = {
                {5,4,7,8,0,9},
                {4,5,6,7,8,9},
                {3,6,5,6,7,8}};

        matrixPath.setInputRow(row);
        matrixPath.setInputColumn(column);
        matrixPath.setInputMaxCost(maxCost);
        matrixPath.setInputMatrix(row, column, inputTable);

        String output = matrixPath.bestCostSearch();
        String delimiter = ": ";
        String[] tmpInputString;
        tmpInputString = output.trim().split(delimiter);

        System.out.println("Result is: " + tmpInputString[0]);
        Assert.assertEquals(expectedPath, tmpInputString[0]);



    }


    @Test
    public void bestCostSearch_OutOfBoundsSearch_Passed() throws Exception {
        // check if the searching is hopping from the top row to the bottom row and verse via
        int row = 3;
        int column = 6;
        int maxCost = 50;

        int expectedResult = 26;
        String expectedPath = "3, 4, 5, 6, 0, 8"; // in matrix: [20], [01], [22], [23], [04], [25]
        int actual;
        int [][] inputTable = {
                {5,4,7,8,0,9},
                {4,5,6,7,8,9},
                {3,6,5,6,7,8}};

        matrixPath.setInputRow(row);
        matrixPath.setInputColumn(column);
        matrixPath.setInputMaxCost(maxCost);
        matrixPath.setInputMatrix(row, column, inputTable);

        String output = matrixPath.bestCostSearch();
        String delimiter = ": ";
        String[] tmpInputString;
        tmpInputString = output.trim().split(delimiter);

        actual = Integer.parseInt(tmpInputString[2]);
        String actualPath = tmpInputString[1].replace("Total Cost", "").trim();

        System.out.println("The Total Cost: " +actual );
        System.out.println("Result Path " + actualPath);

        Assert.assertEquals(expectedResult, actual);
        Assert.assertEquals(expectedPath, actualPath);

    }

    @Test
    public void bestCostSearch_NegativeCostInput_Passed() throws Exception {
        // check if the searching can handle the negative cost input
        int row = 3;
        int column = 6;
        int maxCost = 50;

        int expectedResult = 1;
        String expectedPath = "3, -5, 5, 6, 0, -8"; // go from [2,0], [0,1], [2,2], [2,3], [0,4], [2,5]
        int actual;
        int [][] inputTable = {
                {5,4,7,8,0,9},
                {4,-5,6,7,8,9},
                {3,6, 5,6,7,-8}};

        matrixPath.setInputRow(row);
        matrixPath.setInputColumn(column);
        matrixPath.setInputMaxCost(maxCost);
        matrixPath.setInputMatrix(row, column, inputTable);

        String output = matrixPath.bestCostSearch();
        String delimiter = ": ";
        String[] tmpInputString;
        tmpInputString = output.trim().split(delimiter);

        actual = Integer.parseInt(tmpInputString[2]);
        String actualPath = tmpInputString[1].replace("Total Cost", "").trim();

        System.out.println("The Total Cost: " +actual );
        System.out.println("Result Path " + actualPath);

        Assert.assertEquals(expectedResult, actual);
        Assert.assertEquals(expectedPath, actualPath);

    }

    @Test
    public void bestCostSearch_InterestingCase_Passed() throws Exception {
        // two paths: the path "1, 1, 1, 1, 1, 1, 1, 1, 1, 1" is bigger than "9, 0, 0, 0, 0, 0, 0, 0, 0, 0";
        int row = 10;
        int column = 10;
        int maxCost = 88;

        int expectedResult = 9;
        String expectedPath = "9, 0, 0, 0, 0, 0, 0, 0, 0, 0"; //
        int actual;
        int [][] inputTable = {
                {9,9,9,9,9,9,9,9,9,9},
                {1,1,1,1,1,1,1,1,1,1},
                {9,9,9,9,9,9,9,9,9,9},
                {9,9,9,9,9,9,9,9,9,9},
                {9,9,9,9,9,9,9,9,9,9},
                {9,0,9,9,9,9,9,9,0,0},
                {9,9,0,9,9,9,9,0,9,9},
                {9,9,9,0,9,0,0,9,9,9},
                {9,9,9,9,0,9,9,9,9,9},
                {9,9,9,9,9,9,9,9,9,9}};

        matrixPath.setInputRow(row);
        matrixPath.setInputColumn(column);
        matrixPath.setInputMaxCost(maxCost);
        matrixPath.setInputMatrix(row, column, inputTable);

        String output = matrixPath.bestCostSearch();
        String delimiter = ": ";
        String[] tmpInputString;
        tmpInputString = output.trim().split(delimiter);

        actual = Integer.parseInt(tmpInputString[2]);
        String actualPath = tmpInputString[1].replace("Total Cost", "").trim();

        System.out.println("The Total Cost: " +actual );
        System.out.println("Result Path " + actualPath);

        Assert.assertEquals(expectedResult, actual);
        Assert.assertEquals(expectedPath, actualPath);

        actual = Integer.parseInt(tmpInputString[2]);
        Assert.assertEquals(expectedResult, actual);

    }



    @Test
    public void bestCostSearch_Sample1Requirement_Passed() throws Exception {
        // Running the sample 1 from the req
        int row = 5;
        int column = 6;
        int maxCost = 50;

        int expectedResult = 16;
        String expectedPath = "3, 1, 3, 3, 2, 4"; // go from [00], [11], [02], [43], [34], [45]
        int actual;
        int [][] inputTable = {
                {3,4,1,2,8,6},
                {6,1,8,2,7,4},
                {5,9,3,9,9,5},
                {8,4,1,3,2,6},
                {3,7,2,8,6,4}};

        matrixPath.setInputRow(row);
        matrixPath.setInputColumn(column);
        matrixPath.setInputMaxCost(maxCost);
        matrixPath.setInputMatrix(row, column, inputTable);

        String output = matrixPath.bestCostSearch();
        String delimiter = ": ";
        String[] tmpInputString;
        tmpInputString = output.trim().split(delimiter);

        actual = Integer.parseInt(tmpInputString[2]);
        String actualPath = tmpInputString[1].replace("Total Cost", "").trim();

        System.out.println("The Total Cost: " +actual );
        System.out.println("Result Path " + actualPath);

        Assert.assertEquals(expectedResult, actual);
        Assert.assertEquals(expectedPath, actualPath);

    }




    @Test
    public void bestCostSearch_Sample2Requirement_Passed() throws Exception {
        // Running the sample 2 from the req
        int row = 5;
        int column = 6;
        int maxCost = 50;

        int expectedResult = 11;
        String expectedPath = "3, 1, 1, 1, 2, 3"; // go from [00], [11], [02], [43], [34], [45]
        int actual;
        int [][] inputTable = {
                {3,4,1,2,8,6},
                {6,1,8,2,7,4},
                {5,9,3,9,9,5},
                {8,4,1,3,2,6},
                {3,7,2,1,2,3}};

        matrixPath.setInputRow(row);
        matrixPath.setInputColumn(column);
        matrixPath.setInputMaxCost(maxCost);
        matrixPath.setInputMatrix(row, column, inputTable);

        String output = matrixPath.bestCostSearch();
        String delimiter = ": ";
        String[] tmpInputString;
        tmpInputString = output.trim().split(delimiter);

        actual = Integer.parseInt(tmpInputString[2]);
        String actualPath = tmpInputString[1].replace("Total Cost", "").trim();

        System.out.println("The Total Cost: " +actual );
        System.out.println("Result Path: " + actualPath);

        Assert.assertEquals(expectedResult, actual);
        Assert.assertEquals(expectedPath, actualPath);

    }


    @After
    public void tearDown() throws Exception {
    }
}