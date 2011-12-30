/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package finalexam.domain;

/**
 *
 * @author rayelward
 */
public class Cylinder extends Circle {

    private double height;

    public Cylinder(String idIn, int redIn, int greenIn, int blueIn, double radiusIn, double heightIn) {
        super(idIn, redIn, greenIn, blueIn, radiusIn);
        setHeight(heightIn);
    }

    public double getHeight() {
        return height;
    }

    private void setHeight(double hIn) {
        if (hIn <= 0) {
            System.out.println("Height for Cylinder is less than or equal than zero:" + hIn);
            System.exit(-1);
        }
        height = hIn;
    }

    public double calculateVolume() {
        return super.getArea() * getHeight();
    }

    public double calculateSurfaceArea() {
        return (2 * super.getArea()) + (super.getPerimeter() * getHeight());
    }

    @Override
    public String toString() {
        return String.format("%s%-17s%,.1f\n%-17s%,.1f\n%-17s%,.1f\n",
                super.toString(),
                "Height:", getHeight(),
                "Volume:", calculateVolume(),
                "Surface Area:", calculateSurfaceArea());
    }
}
