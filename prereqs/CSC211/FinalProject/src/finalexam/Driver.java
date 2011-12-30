//
// You may need to change this package statement to match your package name(s)
//
package finalexam;

//
// You may need to change these package statements to match your package name(s)
//
import finalexam.domain.Circle;
import finalexam.domain.Rectangle;
import finalexam.domain.Square;
import finalexam.domain.Cube;
import finalexam.domain.Cylinder;
import finalexam.domain.Shape;

public class Driver {

    public static void main(String args[]) {


        Shape shape = null;
        System.out.println("Creating Shapes...");


        shape = new Circle("Circle1", 50, 75, 225, 7.0);
        System.out.printf("%s%n%n", shape);


        shape = new Rectangle("Rectangle1", 200, 200, 100, 12.0, 4.0);
        System.out.printf("%s%n%n", shape);


        shape = new Circle("Circle2", 42, 255, 255, 10.0);
        System.out.printf("%s%n%n", shape);


        shape = new Rectangle("Rectangle2", 44, 22, 50, 2.0, 8.0);
        System.out.printf("%s%n%n", shape);


        shape = new Circle("Circle3", 20, 30, 40, 5.0);
        System.out.printf("%s%n%n", shape);


        shape = new Rectangle("Rectangle3", 100, 111, 70, 4.0, 8.0);
        System.out.printf("%s%n%n", shape);


        shape = new Circle("Circle4", 77, 128, 200, 5.5);
        System.out.printf("%s%n%n", shape);


        shape = new Rectangle("Rectangle4", 200, 200, 255, 3.5, 7.75);
        System.out.printf("%s%n%n", shape);


        shape = new Circle("Circle5", 0, 70, 80, 122.0);
        System.out.printf("%s%n%n", shape);


        shape = new Rectangle("Rectangle5", 44, 22, 78, 40.0, 100.0);
        System.out.printf("%s%n%n", shape);


        shape = new Square("Square1", 234, 12, 198, 12);
        System.out.printf("%s%n%n", shape);


        shape = new Square("Square2", 188, 76, 5, 32);
        System.out.printf("%s%n%n", shape);


        shape = new Cube("Cube1", 123, 45, 211, 12, 6, 8);
        System.out.printf("%s%n%n", shape);


        shape = new Cube("Cube2", 255, 0, 120, 5, 5, 5);
        System.out.printf("%s%n%n", shape);


        shape = new Cylinder("Cylinder1", 211, 145, 111, 9, 11);
        System.out.printf("%s%n%n", shape);


        shape = new Cylinder("Cylinder2", 250, 55, 35, 5, 20);
        System.out.printf("%s%n%n", shape);


        System.out.println("Done Creating Shapes");

    }
}
