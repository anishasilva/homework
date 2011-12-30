
/**
 *
 * @author rayelward
 */
public class StudentInfo {

    //FIELDS
    private String firstName;
    private String lastName;
    private long idNum;
    private boolean fullTime;
    private double gpa;
    private static String school = "DePaul University";

    //DEFAULT CONSTRUCTOR
    //Sets name to blank, Full Time status to false and then ID and GPA to -1
    public StudentInfo() {
        setFirstName("");
        setLastName("");
        setIdNum(-1);
        setFullTime(false);
        setGpa(-1);
    }

    //CONSTRUCTOR that allows you to set all the non-static fields.
    public StudentInfo(String firstNameIn, String lastNameIn, long idNumIn, boolean fullTimeIn, double gpaIn) {
        setFirstName(firstNameIn);
        setLastName(lastNameIn);
        setIdNum(idNumIn);
        setFullTime(fullTimeIn);
        setGpa(gpaIn);
    }

    //COPY CONSTRUCTOR
    //Copys an Object to a new reference in memory
    public StudentInfo(StudentInfo studentInfo) {
        setFirstName(studentInfo.getFirstName());
        setLastName(studentInfo.getLastName());
        setIdNum(studentInfo.getIdNum());
        setFullTime(studentInfo.getFullTime());
        setGpa(studentInfo.getGpa());
    }

    //ACCESSOR METHODS:
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public long getIdNum() {
        return idNum;
    }

    public boolean getFullTime() {
        return fullTime;
    }

    public double getGpa() {
        return gpa;
    }

    public static String getSchool() {
        return school;
    }

    //MUTATOR METHODS:
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setIdNum(long idNum) {
        this.idNum = idNum;
    }

    public void setFullTime(boolean fullTime) {
        this.fullTime = fullTime;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public static void setSchool(String schoolIn) {
        school = schoolIn;
    }

    //Method that returns true if two StudentInfo Objects have the same states in their fields.
    public boolean equals(StudentInfo studentInfo) {
        return (this.getFirstName().equals(studentInfo.getFirstName())
                && this.getLastName().equals(studentInfo.getLastName())
                && this.getIdNum() == studentInfo.getIdNum()
                && this.getClass() == studentInfo.getClass()
                && this.getGpa() == studentInfo.getGpa());
    }

    //Method that returns a string with all the information in a StudentInfo Object.
    public String toString() {
        return String.format("%-20s%s, %s\n%-20s%d\n%-20s%s\n%-20s%.2f\n%-20s%s",
                "Student Name:", getLastName(), getFirstName(),
                "ID Number:", getIdNum(),
                "Full Time Status:", getFullTime(),
                "GPA:", getGpa(),
                "School:", getSchool());
    }
}
