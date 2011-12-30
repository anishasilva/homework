
/**
 *
 * @author rayelward
 */
public class Book {

    //Fields:
    private String authorLastName;
    private String authorFirstName;
    private String title;
    private boolean checkedOut;
    private int yearOfPublication;

    //constructors:
    //default constuctor
    //will set the Authors name and Title of the book to blank Strings, while
    //setting the checked out status to false and the year of publication to -1
    public Book() {
        setAuthorLastName("");
        setAuthorFirstName("");
        setTitle("");
        setCheckedOut(false);
        setYearOfPublication(-1);
    }//end default constructor

    //Constructor that takes in and sets all the fields from the users. Last Name(String),
    //First Name(String), Title(String), checked out status(boolean), and year of publication(int).
    public Book(String authorLastNameIn, String authorFirstNameIn, String titleIn, boolean checkedOutIn, int yearOfPublicationIn) {
        setAuthorLastName(authorLastNameIn);
        setAuthorFirstName(authorFirstNameIn);
        setTitle(titleIn);
        setCheckedOut(checkedOutIn);
        setYearOfPublication(yearOfPublicationIn);
    }//end constructor

    //COPY CONSTRUCTOR:
    public Book(Book bookIn) {
        setAuthorLastName(bookIn.getAuthorLastName());
        setAuthorFirstName(bookIn.getAuthorFirstName());
        setTitle(bookIn.getTitle());
        setCheckedOut(bookIn.getCheckedOut());
        setYearOfPublication(bookIn.getYearOfPublication());
    }//end copy constructor

    //Accessor Methods:
    public String getAuthorLastName() {
        return authorLastName;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public String getTitle() {
        return title;
    }

    public boolean getCheckedOut() {
        return checkedOut;
    }

    public int getYearOfPublication() {
        return yearOfPublication;
    }

    //Mutator methods:
    public void setAuthorLastName(String authorLastName) {
        this.authorLastName = authorLastName;
    }

    public void setAuthorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCheckedOut(boolean checkedOut) {
        this.checkedOut = checkedOut;
    }

    public void setYearOfPublication(int yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    //Equals method: checks to see if two book classes have the same state in their fields:
    public boolean equals(Book bookIn) {
        return (this.getAuthorLastName().equals(bookIn.getAuthorLastName())
                && this.getAuthorFirstName().equals(bookIn.getAuthorFirstName())
                && this.getTitle().equals(bookIn.getTitle())
                && this.getCheckedOut() == bookIn.getCheckedOut()
                && this.getYearOfPublication() == bookIn.getYearOfPublication());
    }//end equals method.

    //toString for displaying the fields of an object of the class in a nice and neat way.
    public String toString() {
        return String.format("%-18s%s, %s\n%-18s%s\n%-18s%s\n%-18s%d\n",
                "Aurthor:", this.getAuthorLastName(), this.getAuthorFirstName(),
                "Title:", this.getTitle(),
                "Checked Out:", this.getCheckedOut(),
                "Year of Publication:", this.getYearOfPublication());
    }
}//end book class

