
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

    //Functional method for getting an employee's weekly pay
    public double getWeeklyPay()
    {
        int regularHours = 0, overtimeHours = 0;
        double basePay = 0.0, weeklyPay = 0.0;

        //STEP A
        int x = 0;
        while(x < Timecard.numDays)
        {
            if (getTimecard().getHoursByDay(x) > 8)
            {
                regularHours += 8;
                overtimeHours += (getTimecard().getHoursByDay(x) - 8);
            }
            else regularHours += getTimecard().getHoursByDay(x);
            x++;
        }

        //STEP B
        basePay = (regularHours * getHourlyRate()) + (overtimeHours * getHourlyRate() * 1.5);

        //STEP C
        String s = String.format("%d", getEmployeeId());
        char c = s.charAt(0);
        switch (c)
        {
            case '0':
            case '2':
            case '9':
            weeklyPay = basePay * 1.10;
            break;

            case '3':
            weeklyPay = basePay * 0.90;
            break;

            case '8':
            weeklyPay = basePay * 1.20;
            break;

            default:
            weeklyPay = basePay;
        }

        //STEP D
        if (regularHours > 34)
        {
            weeklyPay -= (weeklyPay * 0.06);
        }
        //return money calculation and adjustments.
         
        return weeklyPay;
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
