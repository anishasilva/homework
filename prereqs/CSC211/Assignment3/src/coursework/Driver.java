/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package coursework;

import java.util.Scanner;

/**
 *
 * @author rayelward
 */
public class Driver
{
    public static void main(String[] args)
    {
        //First we variable of type Employee.
        //We always set this to null as good practice
        Employee e = null;

        //Declare variables to hold user inputs needed
        //by the Employee constructor
        String firstNameInput;
        String lastNameInput;
        int idInput;
        double rateInput;

        //Get input data calues from the user (via the keyboard)
        Scanner userInput = new Scanner(System.in);

        System.out.println("Employee First Name: ");
        firstNameInput = userInput.next();

        System.out.println("Employee Last Name: ");
        lastNameInput = userInput.next();

        System.out.println("Employee Id: ");
        idInput = userInput.nextInt();

        System.out.println("Employee Hourly Rate: ");
        rateInput = userInput.nextDouble();

        //Now allocate a new instance of an Employee
        //passing test data to the constructor
        e = new Employee(firstNameInput, lastNameInput, idInput, rateInput);

        //Print out the data with nice titles
        System.out.println();
        System.out.println("Name: " + e.getFirstName() + " " + e.getLastName());
        System.out.println("Emp. Id: " + e.getEmployeeId());
        System.out.println("Hourly Rate: $" + e.getHourlyRate());

        //We're done with this employee, so now free up the space we allocated
        //for that employee by setting the "e" variable back to null.
        e = null;

        System.out.println();

    }
}
