package com.code.sample.costPath.base;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 1/27/2017.
 * Define the com.code.sample.costPath.base.Element class including
 *  - position X
 *  - position Y
 *  - it's cost value
 *  - total cost value from the root
 *  - it's path
 */

public class Element {

    private int x;
    private int y;
    private int value;
    private int total;

    private List<Element> path;


    public Element(int x, int y, int value, int total) {
        this.x = x;
        this.y = y;
        this.value = value;
        this.total = total;

        path = new ArrayList<>();
    }

    public Element(Element element) {
        this.x = element.getX();
        this.y = element.getY();
        this.value = element.getValue();
        this.total = element.getTotal();

        path = new ArrayList<>();
        path = (List) ((ArrayList) element.getPath()).clone();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

   private int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getTotal() {
        return total;
    }

    /** Calculate the total Cost Value on Path */
    public void calculateTotal(int total) {
        this.total = this.total + total;
    }

    private List<Element> getPath() {
        return path;
    }

    /** Add the new com.code.sample.costPath.base.Element to the Path */
    public void addToPath(Element element) {
        Element e = new Element(element.getX(), element.getY(), element.getValue(), element.getValue());
        path.add(e);
    }


    /** Print the element's position */
    public String printString() {
       return ("[" + getX() + getY() + "], ");
    }

    /** Print the com.code.sample.costPath.base.Element's path */
    public String printPath() {
        String tmp = "";
        for (Element e : path) {
            tmp = tmp + e.printString();
        }
        return tmp;
    }

    /** Print the com.code.sample.costPath.base.Element's Value on the path */
    public String printValuesInPath() {

        String tmp = "";
        for (Element e : path) {
            tmp = tmp + e.getValue() + ", ";
        }
        return tmp;
    }
}
