public class StudentRecord2
{
	private long idNum;
	private String firstName;
	private String lastName;
	private int totalHwGrade;
	private int midtermGrade;
	private int finalGrade;
	private char letterGrade;
	
	
	//Default constructor
	//Assigns all strings to empty strings
	//Grades to -1 (to indicate unset)
	//LetterGrade to 'U' (also to indicate unset)
	public StudentRecord2()
	{
		firstName = "";
		lastName = "";
		totalHwGrade = -1;
		midtermGrade = -1;
		finalGrade = -1;
		letterGrade = 'U';
	}  //end default constructor
	
	//A constructor that accepts only first and last name and sets remaining fields
	//to default values (strings to "",  numbers to -1,  letterGrade to 'U')
	//You can have constructors with any combination of fields you like AS LONG as the
	//methods all have different numbers or types of arguments.
	//E.g. After creating the following method, you will not be able to create another
	//constructor that takes two arguments of type String.
	public StudentRecord2(String fn, String ln)
	{
		firstName = fn;
		lastName = ln;
		totalHwGrade = -1;
		midtermGrade = -1;
		finalGrade = -1;
		letterGrade = 'U';  //for 'unknown'
	}
	
	public StudentRecord2(String fn, String ln, long id) {
		setFirstName(fn);
		setLastName(ln);
		setIdNum(id);
		totalHwGrade = -1;
		midtermGrade = -1;
		finalGrade = -1;
		letterGrade = 'U';
	}
	
	
	////////////////////////////////////////
	// 		MUTATOR  METHODS
	////////////////////////////////////////
	public void setLetterGrade(char grade) { letterGrade = grade;}
	public void setFirstName(String n) { firstName = n; }
	public void setLastName(String n) { lastName = n; }
	public void setTotalHwGrade(int g) { totalHwGrade = g; }
	public void setIdNum(long n) { idNum = n; }
	public void setFinalGrade(int n) { finalGrade = n; }
	
	//Assigns midGrade to field midtermGrade
	//If midGrade is <-1 or >100, assigns a value of -1
	public void setMidtermGrade(int midGrade)
	{
		if (  midGrade < -1 || midGrade > 100)
		{
			System.out.print("Invalid value for midterm grade. \nAssigning a value of -1.");
			midtermGrade = -1;
		}
		else
			midtermGrade = midGrade;
	}
	// This method could use some improvement,
	// but it serves to show that mutator methods can
	// **************  AND SHOULD   ***************
	// ensure that fields hold only valid data.
	
	
	////////////////////////////////////////
	// 		ACCESSOR  METHODS
	////////////////////////////////////////
	public char getLetterGrade() { return letterGrade; }
	public String getFirstName() { return firstName; }
	public String getLastName() { return lastName; }
	public int getTotalHwGrade() { return totalHwGrade; }
	public int getMidtermGrade() { return midtermGrade; }
	public long getIdNum() { return idNum; }
	public int getFinalGrade() { return finalGrade; }
	
	
	//Percent grade determined by totalHwGrade of 60% and midterm and final grades 20% each
	//Calculates a letter grade:  >=90 A  / >=80 B / >=70 C / >=60 D / <60 F
	//Precondition:  totalHwGrade, midtermGrade, finalGrade must all have values
	public void calcLetterGrade()
	{
		int percentGrade;
		percentGrade = ( totalHwGrade/400*60 +  midtermGrade/100*20 +  finalGrade/100*20 );
		
		if ( percentGrade >= 90)
			letterGrade = 'A';
		else if ( percentGrade >= 80)
			letterGrade = 'B';
		else if (percentGrade >= 70)
			letterGrade = 'C';
		else if (percentGrade >= 60)
			letterGrade = 'D';
		else
			letterGrade = 'F';
	} //end calcLetterGrade
	
	
	//Adds 10% to the totalHwGrade property
	//totalHwGrade must have a value
	void addHwBonus() {
		totalHwGrade = (int) Math.round(1.1*totalHwGrade);
	}
	
	
	//randomly generates a grade between A and F (though not 'E') to letterGrade
	public void generateRandomGrade() {
		int num = (int) (Math.random() * 5);
		//Randomly generates an integer number between 0 and 4
		//and assigns that number to 'num'
		
		if ( num != 4)
			letterGrade = (char) (num+65);
		else
			letterGrade = 'F';
	}  //end of method generateRandomGrade()
	
	
	//Overriding the toString method so that we can use println() with StudentRecord2 objects
	//Precondition: idNum, lastName, firstName, midterm, final, letterGrade must all have values
	public String toString()
	{
		String s="";
		
		s = s + "ID:\t\t" + idNum + "\n";
		s = s + "Name:\t\t" + lastName + ", " + firstName + "\n";  // the '\t' inserts a tab...
		s = s + "Midterm Grade: " + midtermGrade + "\n";
		s = s + "Final Grade\t" + finalGrade + "\n";
		s = s + "Letter Grade:\t" + letterGrade + "\n";
		
		return s;
	}  //end of method toString()
	
} //end class StudentRecord2