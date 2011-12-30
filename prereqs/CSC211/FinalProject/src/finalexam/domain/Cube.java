/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package finalexam.domain;

/**
 *
 * @author rayelward
 */
public class Cube extends Rectangle {

    private double height;

    public Cube(String idIn, int redIn, int greenIn, int blueIn, double lengthIn, double widthIn, double heightIn) {
        super(idIn, redIn, greenIn, blueIn, lengthIn, widthIn);
        setHeight(heightIn);
    }

    public double getHeight() {
        return height;
    }

    private void setHeight(double hIn) {
        if (hIn <= 0) {
            System.out.println("Height of cube is less then or equal to zero: " + getHeight());
            System.exit(-1);
        }
        height = hIn;
    }

    public double calculateVolume() {
        return super.getLength() * super.getWidth() * getHeight();
    }

    public double calculateSurfaceArea() {
        return (2 * super.getLength() * super.getWidth())
                + (2 * super.getWidth() * getHeight())
                + (2 * super.getLength() * getHeight());
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
