
package coursework;

/**
 *
 * @author rayelward
 */
public class Timecard
{
    //data members!!!
    public static final int numDays = 5;
    private int[] daysOfTheWeek = new int[numDays];

    //constructor
    public Timecard(int[] dOfTheWeek)
    {
        if(dOfTheWeek == null || dOfTheWeek.length != numDays)
        {
            System.out.println("Array passed into constructor is null or not the exact size of my array: " + dOfTheWeek.length);
            System.exit(-1);
        }
        for(int x = 0; x < numDays; x++)
        {
            setHoursByDay(x, dOfTheWeek[x]);
        }
    }

    //accessor
    public int getHoursByDay(int dayInGet)
    {
        if(dayInGet < 0 || dayInGet >= numDays)
        {
            System.out.println("Wrong day being get.  Needs to be a work day: " + dayInGet);
            System.exit(-1);
        }
        return daysOfTheWeek[dayInGet];
    }

    //set modifier
    public void setHoursByDay(int daysIn, int hoursIn)
    {
        if(daysIn < 0 || daysIn >= numDays)
        {
            System.out.println("Wrong day being set.  Needs to be a work day: " + daysIn);
            System.exit(-1);
        }
        if(hoursIn < 0 || hoursIn > 24)
        {
            System.out.println("Wrong hours being set.  Needs to be between 0 and 24: " + hoursIn);
            System.exit(-1);
        }
        daysOfTheWeek[daysIn] = hoursIn;
    }

    //functional method adding up the individual portions of the array
    public int getWeeklyHours()
    {
        int sumOfWeeklyHours = 0;
        for(int x = 0; x < numDays; x++)
        {
            sumOfWeeklyHours += getHoursByDay(x);
        }
        return sumOfWeeklyHours;
    }

    //turns gathered information into a string and returns it when called
    @Override
    public String toString()
    {
        String stringForPrint = String.format("%-14s %d\n", "Weekly Hours:", getWeeklyHours());
        for(int x = 0; x < numDays; x++)
        {
            stringForPrint += String.format("%11s", "Day ") + (x+1) + ": " + getHoursByDay(x) + "\n";
        }
        return stringForPrint;
    }
}
