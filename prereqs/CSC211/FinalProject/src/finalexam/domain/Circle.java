/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package finalexam.domain;

/**
 *
 * @author rayelward
 */
public class Circle extends Shape {

    private double radius;

    public Circle(String shapeIdIn, int redIn, int greenIn, int blueIn, double radiusIn) {
        super(shapeIdIn, redIn, greenIn, blueIn);
        setRadius(radiusIn);
    }

    public double getRadius() {
        return radius;
    }

    private void setRadius(double rIn) {
        if (rIn <= 0) {
            System.out.println("Radius input is less then or equal to zero: " + rIn);
            System.exit(-1);
        }
        radius = rIn;
    }

    @Override
    public double getArea() {
        return (Math.PI * (getRadius() * getRadius()));
    }

    @Override
    public double getPerimeter() {
        return (2 * Math.PI * getRadius());
    }

    @Override
    public String toString() {
        return String.format("%s%-17s%,.1f\n",
                super.toString(),
                "Radius:", getRadius());
    }
}
