/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package finalexam.domain;

/**
 *
 * @author rayelward
 */
public class Rectangle extends Shape {

    private double length;
    private double width;

    public Rectangle(String shapeId, int red, int green, int blue, double lengthIn, double widthIn) {
        super(shapeId, red, green, blue);
        setLength(lengthIn);
        setWidth(widthIn);
    }

    public double getWidth() {
        return width;
    }

    public double getLength() {
        return length;
    }

    private void setLength(double lIn) {
        if (lIn <= 0) {
            System.out.println("Input length is less than or equal to zero: " + lIn);
            System.exit(-1);
        }
        length = lIn;
    }

    private void setWidth(double wIn) {
        if (wIn <= 0) {
            System.out.println("Input width is less than or equal to zero: " + wIn);
            System.exit(-1);
        }
        width = wIn;
    }

    @Override
    public double getArea() {
        return getLength() * getWidth();
    }

    @Override
    public double getPerimeter() {
        return (getLength() * 2) + (getWidth() * 2);
    }

    @Override
    public String toString() {
        return String.format("%s%-17s%,.1f\n%-17s%,.1f\n",
                super.toString(),
                "Length:", getLength(),
                "Width:", getWidth());
    }
}
