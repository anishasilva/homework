
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
        // Get input data values from the user (via the keyboard)
        Scanner userInput = new Scanner(System.in);

        // Now declare local variables to hold user inputs
        // needed by the Employee constructor
        String firstNameInput;
        String lastNameInput;
        int idInput;
        double rateInput;

        // Create a local int array with the same number of
        // elements as the Timecard's "numDays" attrbiute
        int[] workedDays = new int[Timecard.numDays];

        // Get the values needed by Employee
        // constructor from the user (via the keyboard)
        System.out.println("Employee First Name: ");
        firstNameInput = userInput.next();

        System.out.println("Employee Last Name: ");
        lastNameInput = userInput.next();

        System.out.println("Employee Id: " );
        idInput = userInput.nextInt();

        System.out.println("Employee Hourly Rate: ");
        rateInput = userInput.nextDouble();

        // Now, in a "for" loop, query the user for each days hours and
        // store the response in the individual int array elements
        for (int i = 0; i < Timecard.numDays; i++)
        {
            System.out.println("Enter Hours for day " + (i + 1) + ": ");
            workedDays[i] = userInput.nextInt();
        }

        // Now allocate a new instance of an Employee, passing the test
        // data (and the int array we created above) to the constructor.
        Employee e = new Employee(firstNameInput, lastNameInput, idInput, rateInput, workedDays);

        // Print the Employee - this will invoke the Employee's "toString()" method.
        System.out.println("\nEmployee:\n------------------------------------");
        System.out.println(e);
    }
}
