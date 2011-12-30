
package coursework;

/**
 *
 * @author RayElward
 */
public class Employee
{

    //declaring 4 data members of my class (step 6 of assignment2)
    private String firstName;
    private String lastName;
    private int employeeId;
    private double hourlyRate;

    //Create private modifiers
    private void setFirstName(String firstNameIn)
    {
        if (firstNameIn == null  || firstNameIn.isEmpty() || firstNameIn.length() >= 20)
        {
            System.out.println("First Name is too long or nonexistant: " + firstNameIn);
            System.exit(-1);
        }
        else firstName = firstNameIn;
    }
    private void setLastName(String lastNameIn)
    {
        if (lastNameIn == null  || lastNameIn.isEmpty() || lastNameIn.length() >= 20)
        {
            System.out.println("Last Name is to long or nonexistant: " + lastNameIn);
            System.exit(-1);
        }
        else lastName = lastNameIn;
    }
    private void setEmployeeId(int employeeIdIn)
    {
        if (employeeIdIn <= 1000 || employeeIdIn >= 9999)
        {
            System.out.println("Bad value passed in for Employee ID: " + employeeIdIn);
            System.exit(-1);
        }
        else employeeId = employeeIdIn;
    }
    private void setHourlyRate(double hourlyRateIn)
    {
        if (hourlyRateIn < 0)
        {
            System.out.println("Hourly rate must be greater than 0: " + hourlyRateIn);
            System.exit(-1);
        }
        else hourlyRate = hourlyRateIn;
    }

    //accessors
    public String getFirstName()
    {
        return firstName;
    }
    public String getLastName()
    {
        return lastName;
    }
    public int getEmployeeId()
    {
        return employeeId;
    }
    public double getHourlyRate()
    {
        return hourlyRate;
    }

    //constructor should be Employee(java.lang.String,java.lang.String,int,double);
    public Employee(String fName, String lName, int eId, double hRate)
    {
        //setting corresponding Employee data members
      
        setFirstName(fName);
        setLastName(lName);
        setEmployeeId(eId);
        setHourlyRate(hRate);

    }
}
