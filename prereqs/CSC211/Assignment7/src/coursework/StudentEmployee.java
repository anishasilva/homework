/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package coursework;

/**
 *
 * @author rayelward
 */
public class StudentEmployee extends Employee
{
    private int creditHours;
    private String studentMajor;

    public StudentEmployee(String fName, String lName, int eId, double hRate, int[] daysIn, int cHours, String sMajor)
    {
        super(fName, lName, eId, hRate, daysIn);
        setCreditHours(cHours);
        setStudentMajor(sMajor);
    }
    //public accessors
    public int getCreditHours()
    {
        return creditHours;
    }
    public String getStudentMajor()
    {
        return studentMajor;
    }
    //private modifiers
    private void setCreditHours(int creditIn)
    {
        if (creditIn < 0)
        {
            System.out.println("Credit hours are less than zero: " + creditIn);
            System.exit(-1);
        }
        creditHours = creditIn;
    }

    private void setStudentMajor(String majorIn)
    {
        if (majorIn == null || majorIn.isEmpty())
        {
            System.out.println("Major being passed in is null: " + majorIn);
            System.exit(-1);
        }
        studentMajor = majorIn;
    }
    
    @Override
    public double getWeeklyPay()
    {
        return super.getWeeklyPay() * 0.50;
    }


    @Override
    public String toString()
    { 
        String s = super.toString();
        s += String.format("%-14s %d\n%-14s %s\n",
            "Credit Hours:", getCreditHours(),
            "Major:", getStudentMajor());
        return s;
    }

}
