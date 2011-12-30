/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package finalexam.domain;

/**
 *
 * @author rayelward
 */
public class ColorValue {

    private int red;
    private int green;
    private int blue;

    public ColorValue(int redIn, int greenIn, int blueIn) {
        setRed(redIn);
        setGreen(greenIn);
        setBlue(blueIn);
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    private void setRed(int redNum) {
        if (redNum < 0 || redNum > 255) {
            System.out.println("Wrong red value entered: " + redNum);
            System.exit(-1);
        }
        red = redNum;
    }

    private void setGreen(int greenNum) {
        if (greenNum < 0 || greenNum > 255) {
            System.out.println("Wrong green value entered: " + greenNum);
            System.exit(-1);
        }
        green = greenNum;
    }

    private void setBlue(int blueNum) {
        if (blueNum < 0 || blueNum > 255) {
            System.out.println("Wrong blue value entered: " + blueNum);
            System.exit(-1);
        }
        blue = blueNum;
    }

    @Override
    public String toString() {
        return String.format("%-17s%d, %d, %d\n",
                "ColorValue RGB:", getRed(), getGreen(), getBlue());
    }
}
