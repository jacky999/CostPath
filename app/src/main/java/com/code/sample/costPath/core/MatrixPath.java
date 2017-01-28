package com.code.sample.costPath.core;

import android.util.Log;

import com.code.sample.costPath.base.Constants;
import com.code.sample.costPath.base.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 1/27/2017.
 * MatrixPathClass is used for bestPathSearch, it have the inputs of CostMatrix's Row Number, Column Number, the Max Cost and the CostMatrix; 
 * the result will be the Yes or for the solution, the Total Cost and the Cost Path. 
 */

public class MatrixPath {
    private static final String TAG = MatrixPath.class.getSimpleName();

    private int inputRow;
    private int inputColumn;
    private int inputMaxCost;
    private int[][] inputMatrix = new int[Constants.MAX_MATRIX][Constants.MAX_MATRIX];


    /** explicitly initialization */
    public MatrixPath() {
        this.inputRow = 0;
        this.inputColumn = 0;
        this.inputMaxCost = 0;
        for (int i = 0; i < Constants.MAX_MATRIX; i++) {
            for (int j = 0; j < Constants.MAX_MATRIX; j++) {
                this.inputMatrix[i][j] = 0;
            }
        }
    }


    /** Get teh input Row Numbers, useful for unit testing */
    int getInputRow() {
        return inputRow;
    }

    public boolean setInputRow(int inputRow) {
        boolean result = false;
        if ((0 < inputRow) && (inputRow <= Constants.MAX_MATRIX)) {
            this.inputRow = inputRow;
            result = true;
        }
        Log.d(TAG, "setInputRow: result: " + result);
        return result;
    }

    /** Get the input column numbers, useful for unit testing */
    int getInputColumn() {
        return inputColumn;
    }

    /** build validation logic inside setter */
    public boolean setInputColumn(int inputColumn) {
        boolean result = false;
        if ((0 < inputColumn) && (inputColumn <= Constants.MAX_MATRIX)) {
            this.inputColumn = inputColumn;
            result = true;
        }
        Log.d(TAG, "setInputColumn: result: " + result);
        return result;
    }

    /**
     * Get the Max Cost, useful for unit testing  */
    int getInputMaxCost() {
        return inputMaxCost;
    }

    // build validation logic inside setter
    public boolean setInputMaxCost(int inputMaxCost) {
        boolean result = false;
        if (inputMaxCost > 0) {
            this.inputMaxCost = inputMaxCost;
            result = true;
        }
        Log.d(TAG, "setInputMaxCost: result: " + result);
        return result;
    }

    public int[][] getInputMatrix() {
        return inputMatrix;
    }

    /** Set the Input Matrix, also build validation logic inside setter  */
    public boolean setInputMatrix(int row, int column, int[][] inputCostMatrix) {
        boolean result = false;
        if (isValidInputMatrix(row, column, inputCostMatrix)) {
            this.inputMatrix = clone2DArray(inputCostMatrix);
            result = true;
        }
        Log.d(TAG, "setInputMatrix: result: " + result);
        return result;
    }

    /** Validation of the Input Matrix */
    private boolean isValidInputMatrix(int row, int column, int[][] inputCostMatrix) {
        //boolean result = false;

        if ((inputCostMatrix.length == 0) || (inputCostMatrix[0].length == 0)) {
            return false;
        }

        if (inputCostMatrix.length != row) {
            Log.d(TAG, "isValidInputMatrix: inputCostMatrix Row length is not right: Row length = "
                        + inputCostMatrix.length + ", supposed row = " + row);
            return false;
        }

        //if (inputCostMatrix[0].length != column)
        // for jagged array checking
        for (int i = 0; i < row; i++) {
            if (inputCostMatrix[i].length != column) {
                Log.d(TAG, "isValidInputMatrix: inputCostMatrix Column length is not right: Column length = "
                            + inputCostMatrix[i].length + ", supposed column = " + column);
                return false;
            }
        }

        return true;
    }

    private int[][] clone2DArray(int[][] source) {
        int length = source.length;
        int[][] target = new int[length][source[0].length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(source[i], 0, target[i], 0, source[i].length);
        }
        return target;
    }

    /** Searching for the Min Cost Path */
    public String bestCostSearch() {
        Log.d(TAG, "bestCostSearch: Starts");
        // review if these four inputs ok
        Log.d(TAG, "bestCostSearch: Input Row No: " + inputRow + " Input Column No: " + inputColumn + " Input Cost Limit: " + inputMaxCost);

        final int NEXT_CHOICE_RANGE = 3;
        String theFinalResults;

        List<Element> parents = new ArrayList<>();
        List<Element> children = new ArrayList<>();
        List<Element> allResults = new ArrayList<>();

        for (int i = 0; i < inputRow; i++) {
            Element root = new Element(i, 0, inputMatrix[i][0], inputMatrix[i][0]);
            parents.add(root);

            for (int j = 0; j < inputColumn - 1; j++) {

                for (int l = 0; l < parents.size(); l++) {
                    int currentParentPosition = parents.get(l).getX();

                    for (int k = -1; k < NEXT_CHOICE_RANGE - 1; k++) {
                        int rowNumber = currentParentPosition + k;
                        if (rowNumber < 0) {
                            rowNumber = (rowNumber + inputRow) % inputRow;
                        }
                        if (rowNumber > inputRow - 1) {
                            rowNumber = rowNumber % inputRow;
                        }
                        Element newElement = new Element(parents.get(l));
                        newElement.setX(rowNumber);
                        newElement.setY(j + 1);
                        newElement.setValue(inputMatrix[rowNumber][j + 1]);
                        newElement.calculateTotal(inputMatrix[rowNumber][j + 1]);

                        if (newElement.getTotal() <= inputMaxCost) {
                            Element currentParent = new Element(parents.get(l));
                            newElement.addToPath(currentParent);
                            children.add(newElement);
                        } else {
                            Log.d(TAG, "bestCostSearch: the path is abandoned because it exceeds the total cost!");
                        }
                    }
                }
                parents.clear();
                parents = (List) ((ArrayList) children).clone();
                children.clear();
            }

            for (int m = 0; m < parents.size(); m++) {
                Element tmp = new Element(parents.get(m));
                allResults.add(tmp);
            }
            parents.clear();
        }

        if (allResults.size() > 0) {
            int theWinner = 0;
            for (int current = 0; current < allResults.size(); current++) {
                if (allResults.get(current).getTotal() < allResults.get(theWinner).getTotal()) {
                    theWinner = current;
                }
            }
            theFinalResults = " YES: " + allResults.get(theWinner).printValuesInPath() + allResults.get(theWinner).getValue()
                    + " Total Cost: " + allResults.get(theWinner).getTotal();
            Log.d(TAG, "bestCostSearch: The solution: " + allResults.get(theWinner).printString() + "The Path: " + allResults.get(theWinner).printPath()
                    + " Total Cost: " + allResults.get(theWinner).getTotal());
        } else {
            theFinalResults = " No Solutions";
            Log.d(TAG, "bestCostSearch: No Solutions");
        }
        return theFinalResults;

    }

}