import junit.framework.Assert;
import junit.framework.TestCase;

// TODO: complete the tests
public class VideoTEST extends TestCase {
  public VideoTEST(String name) {
    super(name);
  }
  public void testConstructorAndAttributes() {
    String title1 = "XX";
    String director1 = "XY";
    String title2 = " XX ";
    String director2 = " XY ";
    int year = 2002;

    VideoObj v1 = new VideoObj(title1, year, director1);
    Assert.assertSame(title1, v1.title());
    Assert.assertEquals(year, v1.year());
    Assert.assertSame(director1, v1.director());

    VideoObj v2 = new VideoObj(title2, year, director2);
    Assert.assertEquals(title1, v2.title());
    Assert.assertEquals(director1, v2.director());
  }

  public void testConstructorExceptionYear() {
    try {
      new VideoObj("X", 1800, "Y");
      fail();
    } catch (IllegalArgumentException e) { }
    try {
      new VideoObj("X", 5000, "Y");
      fail();
    } catch (IllegalArgumentException e) { }
    try {
      new VideoObj("X", 1801, "Y");
      new VideoObj("X", 4999, "Y");
    } catch (IllegalArgumentException e) {
      fail();
    }
  }

  public void testConstructorExceptionTitle() {
    try {
      new VideoObj(null, 2002, "Y");
      fail();
    } catch (IllegalArgumentException e) { }
    try {
      new VideoObj("", 2002, "Y");
      fail();
    } catch (IllegalArgumentException e) { }
    try {
      new VideoObj(" ", 2002, "Y");
      fail();
    } catch (IllegalArgumentException e) { }
  }

  public void testConstructorExceptionDirector() {
    // TODO
	  try {
	      new VideoObj("Y", 2002, null);
	      fail();
	    } catch (IllegalArgumentException e) { }
	    try {
	      new VideoObj("Y", 2002, "");
	      fail();
	    } catch (IllegalArgumentException e) { }
	    try {
	      new VideoObj("Y", 2002, " ");
	      fail();
	    } catch (IllegalArgumentException e) { }
	  
  }

  public void testHashCode() {
	  
	//created a more precise test below. 
    //Assert.assertEquals(-1098649164, new VideoObj("None", 2009, "Zebra").hashCode());
    //Assert.assertEquals(-1888981547, new VideoObj("Blah", 1954, "Cante").hashCode());
    
    //added test to see if two objects with the same states come to the same hash code.
    Assert.assertEquals
    	(new VideoObj("Blah", 1954, "Cante").hashCode(),
    	new VideoObj("Blah", 1954, "Cante").hashCode());
    //added test to see if two different objects have different hash codes.
    VideoObj p1 = new VideoObj("Blah", 1999, "Elward");
    VideoObj p2 = new VideoObj("Blah", 1999, "Smelward");
    Assert.assertFalse(p1.hashCode() == p2.hashCode() );
  }

  public void testEquals() { 
    // TODO
	VideoObj p1 = new VideoObj("Blah", 1990, "Elward");
	VideoObj p2 = new VideoObj("Blah", 1990, "Elward");
	VideoObj p3 = new VideoObj("Blank", 1990, "Elward");
	VideoObj p4 = new VideoObj("Blah", 2000, "Elward");
	VideoObj p5 = new VideoObj("Blah", 1990, "Edwardo");
	Assert.assertTrue(p1.equals(p1));
	Assert.assertTrue(p1.equals(p2));
	Assert.assertFalse(p1.equals(p3));   
	Assert.assertFalse(p1.equals(p4));
	Assert.assertFalse(p1.equals(p5));
	Assert.assertFalse(p1.equals(new Object()));
  }

  public void testCompareTo() { 
    // TODO
	VideoObj p1 = new VideoObj("Blah", 1990, "Elward");
	VideoObj p2 = new VideoObj("Blah", 1990, "Elward");
	VideoObj p3 = new VideoObj("Blank", 1990, "Elward");
	VideoObj p4 = new VideoObj("Blah", 1990, "Zues");
    Assert.assertTrue(0 == p1.compareTo(p1));
    Assert.assertTrue(0 == p1.compareTo(p2));
    Assert.assertTrue(0 >  p1.compareTo(p3));
    Assert.assertTrue(0 >  p1.compareTo(p4));
    Assert.assertTrue(0 == p2.compareTo(p1));
    Assert.assertTrue(0 <  p3.compareTo(p1));
    Assert.assertTrue(0 <  p4.compareTo(p1));
  }

  public void testToString() { 
    // TODO
	  Assert.assertTrue(
			  new VideoObj("Blah", 1990, "Elward").toString().equals("Blah (1990) : Elward"));
	  Assert.assertTrue(
			  new VideoObj(" Mad Max ", 2020, "Marcos").toString().equals("Mad Max (2020) : Marcos"));
  }
}
