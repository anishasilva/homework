
/**
 *
 * @author rayelward
 */
public class Car {

    //FIELDS
    private String make;
    private String model;
    private int numCylinders;
    private String color;
    private int maxSpeed;
    private double zeroToSixty;

    //DEFAULT CONSTRUCTOR
    //Sets the make, model and color to blank strings.  Sets the number of cylinders, max speed and zero to sixty speed to -1.
    public Car() {
        setMake("");
        setModel("");
        setNumCylinders(-1);
        setColor("");
        setMaxSpeed(-1);
        setZeroToSixty(-1);
    }
    //CONSTRUCTOR
    //Lets user set all the fields.

    public Car(String makeIn, String modelIn, int numCylindersIn, String colorIn, int maxSpeedIn, double zeroToSixtyIn) {
        setMake(makeIn);
        setModel(modelIn);
        setNumCylinders(numCylindersIn);
        setColor(colorIn);
        setMaxSpeed(maxSpeedIn);
        setZeroToSixty(zeroToSixtyIn);
    }

    //COPY CONSTRUCTOR
    //lets you copy all states of all fields into another Car object
    public Car(Car carIn) {
        setMake(carIn.getMake());
        setModel(carIn.getModel());
        setNumCylinders(carIn.getNumCylinders());
        setColor(carIn.getColor());
        setMaxSpeed(carIn.getMaxSpeed());
        setZeroToSixty(carIn.getZeroToSixty());
    }

    //ACCESSOR METHODS
    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getNumCylinders() {
        return numCylinders;
    }

    public String getColor() {
        return color;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public double getZeroToSixty() {
        return zeroToSixty;
    }

    //MUTATOR METHODS
    public void setMake(String makeIn) {
        make = makeIn;
    }

    public void setModel(String modelIn) {
        model = modelIn;
    }

    public void setNumCylinders(int numCylindersIn) {
        numCylinders = numCylindersIn;
    }

    public void setColor(String colorIn) {
        color = colorIn;
    }

    public void setMaxSpeed(int maxSpeedIn) {
        maxSpeed = maxSpeedIn;
    }

    public void setZeroToSixty(double zeroToSixtyIn) {
        zeroToSixty = zeroToSixtyIn;
    }

    //Method that return True if the two car objects have the same states in their fields
    public boolean equals(Car car) {
        return (this.getMake().equals(car.getMake())
                && this.getModel().equals(car.getModel())
                && this.getNumCylinders() == car.getNumCylinders()
                && this.getColor().equals(car.getColor())
                && this.getMaxSpeed() == car.getMaxSpeed()
                && this.getZeroToSixty() == car.getZeroToSixty());
    }

    //Method that returns a string with all the information in a Car Object.
    public String toString() {
        return String.format("%-27s%s\n%-27s%s\n%-27s%d\n%-27s%s\n%-27s%d\n%-27s%.2f\n",
                "Make:", getMake(),
                "Model:", getModel(),
                "Number of Cylinders:", getNumCylinders(),
                "Color:", getColor(),
                "Max Speed:", getMaxSpeed(),
                "Zero to Sixty (in Seconds):", getZeroToSixty());
    }
}
