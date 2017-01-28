package com.code.sample.costPath.base;

/**
 * Created on 1/27/2017.
 * Singleton for Row Numbers, Column Numbers, Max Cost from user inputs
 */


public class UserInputs {

    private static UserInputs instance;
    private int row;
    private int column;
    private int maxCost;

    //no outer class can initialize this class's object
    private UserInputs() {
        row = 0;
        column = 0;
        maxCost = 0;
    }

    public static UserInputs Instance()
    {
        //if no instance is initialized yet then create new instance
        //else return stored instance
        if (instance == null)
        {
            instance = new UserInputs();
        }
        return instance;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getMaxCost() {
        return maxCost;
    }

    public void setMaxCost(int maxCost) {
        this.maxCost = maxCost;
    }
}

