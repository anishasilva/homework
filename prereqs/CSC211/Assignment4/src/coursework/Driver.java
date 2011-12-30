
package coursework;

import java.util.Scanner;

/**
 *
 * @author rayelward
 *
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

        // Here create an “int” array to pass to the Timecard constructor
        int[] workedDays = new int[Timecard.numDays];

        // Now read keyboard input for each of the array slots.
        for (int i = 0; i < Timecard.numDays; i++)
        {
            System.out.println("Enter Hours for day " + (i + 1) + ": ");
            workedDays[i] = userInput.nextInt();
        }

        // Create the Timecd using the newly-filled array
        Timecard t = new Timecard(workedDays);

        // Print the daily hours values from the Timecard to be sure they're as expected
        for (int i = 0; i < Timecard.numDays; i++)
        {
            System.out.println("Value of hours for day " + (i + 1) + ": " + t.getHoursByDay(i));
        }

        // Now print the total of the weekly hours to make sure our method works properly
        System.out.println("Total Weekly Hours: " + t.getWeeklyHours());

        

    }
}
