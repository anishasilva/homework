/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package finalexam.domain;

/**
 *
 * @author rayelward
 */
public abstract class Shape {

    private String shapeId;
    private ColorValue colorValue;

    public Shape(String shapeIdIn, int redIn, int greenIn, int blueIn) {
        setShapeId(shapeIdIn);
        setColorValue(new ColorValue(redIn, greenIn, blueIn));
    }

    public String getShapeId() {
        return shapeId;
    }

    private ColorValue getColorValue() {
        return colorValue;
    }

    private void setShapeId(String sId) {
        if (sId == null || sId.isEmpty()) {
            System.out.println("Shape ID entered in is null or empty: " + sId);
            System.exit(-1);
        }
        shapeId = sId;
    }

    private void setColorValue(ColorValue cValue) {
        if (cValue == null) {
            System.out.println("Color Value is null!");
            System.exit(-1);
        }
        colorValue = cValue;
    }

    public abstract double getArea();

    public abstract double getPerimeter();

    @Override
    public String toString() {
        return String.format("%-17s%s\n%s%-17s%,.1f\n%-17s%,.1f\n",
                "Shape Id:", getShapeId(),
                getColorValue(),
                "Area:", getArea(),
                "Perimeter:", getPerimeter());
    }
}
