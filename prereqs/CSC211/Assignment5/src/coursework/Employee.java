
package coursework;

/**
 *
 * @author RayElward
 */
public class Employee
{

    //data members
    private String firstName;
    private String lastName;
    private int employeeId;
    private double hourlyRate;
    private Timecard timecard;

    //constructor
    public Employee(String fName, String lName, int eId, double hRate, int[] daysIn)
    {
        //setting corresponding Employee data members

        setFirstName(fName);
        setLastName(lName);
        setEmployeeId(eId);
        setHourlyRate(hRate);
        setTimecard(new Timecard(daysIn));

    }

    // private modifiers(set methods)
    private void setFirstName(String firstNameIn)
    {
        if (firstNameIn == null  || firstNameIn.isEmpty() || firstNameIn.length() >= 20)
        {
            System.out.println("First Name is too long or nonexistant: " + firstNameIn);
            System.exit(-1);
        }
        firstName = firstNameIn;
    }
    private void setLastName(String lastNameIn)
    {
        if (lastNameIn == null  || lastNameIn.isEmpty() || lastNameIn.length() >= 20)
        {
            System.out.println("Last Name is to long or nonexistant: " + lastNameIn);
            System.exit(-1);
        }
        lastName = lastNameIn;
    }
    private void setEmployeeId(int employeeIdIn)
    {
        if (employeeIdIn < 1000 || employeeIdIn > 9999)
        {
            System.out.println("Bad value passed in for Employee ID: " + employeeIdIn);
            System.exit(-1);
        }
        employeeId = employeeIdIn;
    }
    private void setHourlyRate(double hourlyRateIn)
    {
        if (hourlyRateIn <= 0)
        {
            System.out.println("Hourly rate must be greater than 0: " + hourlyRateIn);
            System.exit(-1);
        }
        hourlyRate = hourlyRateIn;
    }
    private void setTimecard(Timecard timecardIn)
    {
        if (timecardIn == null)
        {
            System.out.println("timecard in is null!");
            System.exit(-1);
        }
        timecard = timecardIn;
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
    private Timecard getTimecard()
    {
        return timecard;
    }

    //Functional method
    private double getWeeklyPay()
    {
        return getHourlyRate() * getTimecard().getWeeklyHours();
    }

    //printing function
    @Override
    public String toString()
    {
        return String.format("%-14s %s %s \n%-14s %d \n%-14s $%,.2f \n%s%-14s $%,.2f\n",
                "Name:", getFirstName(), getLastName(),
                "Id:", getEmployeeId(),
                "Hourly Rate:", getHourlyRate(),
                getTimecard(), 
                "Weekly Pay:", getWeeklyPay());
    }
}
