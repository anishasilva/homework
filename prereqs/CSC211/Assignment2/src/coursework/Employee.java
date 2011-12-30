/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package coursework;

/**
 *
 * @author RayElward
 */
public class Employee
{

    //declaring 4 data members of my class (step 6 of assignment)
    public String firstName;
    public String lastName;
    public int employeeId;
    public double hourlyRate;

    //Add constructor
    //constructor should be Employee(java.lang.String,java.lang.String,int,double);
    public Employee(String fName, String lName, int eId, double hRate)
    {
        //setting corresponding Employee data members
        firstName = fName;
        lastName = lName;
        employeeId = eId;
        hourlyRate = hRate;
    }


    public static void main(String[] args)
    {
        // First we create a variable of type Employee and set it to a new
        // instance of an Employee, passing some test data to the constructor.
        Employee e = new Employee("Rachael", "Flatt", 2092, 12.59);

        // Print out the data with nice titles
        System.out.println("Name: " + e.firstName + " " + e.lastName);
        System.out.println("Emp. Id: " + e.employeeId);
        System.out.println("Hourly Rate: $" + e.hourlyRate);
        System.out.println(); // This will simply create a blank line

        // We're done with this employee, so now we'll re-use the Employee variable
        // called "e" by once again setting it to a new instance of an Employee,
        // passing different test data to the constructor.
        e = new Employee("Lindsey", "Vonn", 6283, 14.25);

        // Print out the data with nice titles
        System.out.println("Name: " + e.firstName + " " + e.lastName);
        System.out.println("Emp. Id: " + e.employeeId);
        System.out.println("Hourly Rate: $" + e.hourlyRate);
        System.out.println(); // This will simply create a blank line
    }
}
