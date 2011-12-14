import java.util.Collection;

import junit.framework.Assert;
import junit.framework.TestCase;

// TODO: complete the tests
public class InventoryTEST extends TestCase {
  public InventoryTEST(String name) {
    super(name);
  }

  InventorySet s = new InventorySet();
  final VideoObj v1 = new VideoObj( "A", 2000, "B" );
  final VideoObj v2 = new VideoObj( "B", 2000, "B" );

  public void testSize() {
                                 Assert.assertEquals( 0, s.size() );
          s.addNumOwned(v1,  1); Assert.assertEquals( 1, s.size() );
          s.addNumOwned(v1,  2); Assert.assertEquals( 1, s.size() );
          s.addNumOwned(v2,  1); Assert.assertEquals( 2, s.size() );
          s.addNumOwned(v2, -1); Assert.assertEquals( 1, s.size() );
          s.addNumOwned(v1, -3); Assert.assertEquals( 0, s.size() );
    try { s.addNumOwned(v1, -3); } catch ( IllegalArgumentException e ) {}
                                 Assert.assertEquals( 0, s.size() );
  }

  public void testAddNumOwned() {
                                    Assert.assertEquals( 0, s.size() );
          s.addNumOwned(v1, 1);     Assert.assertEquals( v1, s.get(v1).video );
                                    Assert.assertEquals( 1, s.get(v1).numOwned );
                                    //test the exceptions when adding null and negative count.
          try{ 
        	  s.addNumOwned(v1, -2);
        	  fail();
          } catch ( IllegalArgumentException e ) { }
          try{
        	  s.addNumOwned(v1, 0);
        	  fail();
          } catch ( IllegalArgumentException e ) { }
          s.addNumOwned(v2, 10);
          s.checkOut(v2);
          s.checkOut(v2);
          //checks exception throw if trying remove all copies but some are checked out.
          try{
        	  s.addNumOwned(v2, -10);
        	  fail();
          } catch ( IllegalArgumentException e ) { }
   

  }

  public void testCheckOutCheckIn() {
	  s.addNumOwned(v1, 5);
	  s.checkOut(v1);
	  s.checkOut(v1);
	  Assert.assertEquals( 2, s.get(v1).numOut );
	  Assert.assertEquals( 5, s.get(v1).numOwned );
	  
	  s.checkIn(v1);
	  Assert.assertEquals( 1, s.get(v1).numOut );
	  Assert.assertEquals( 5, s.get(v1).numOwned );
	  
	  try {
		  s.checkOut(v2);
		  fail();
	  } catch (IllegalArgumentException e) { }
	  
	  try {
		  s.checkOut(v2);
		  fail();
	  } catch (IllegalArgumentException e) { }
	  
	  
	  
  }

  public void testClear() {
	  s.addNumOwned(v1, 2);
	  s.addNumOwned(v2, 2);
	  s.addNumOwned(v1, 6);
	  Assert.assertEquals( 2, s.size());
	  s.clear();
	  
	  Assert.assertEquals( 0, s.size());
	  Assert.assertEquals( null, s.get(v1) );
	  Assert.assertEquals( null, s.get(v2) );
	  
  }

  public void testGet() {
	  s.addNumOwned(v1, 3);
	  Record r = s.get(v1);
	  Assert.assertEquals( 3, r.numOwned);
	  Assert.assertEquals( 0, r.numOut);
	  Assert.assertEquals( 0, r.numRentals);
	  Assert.assertFalse( r == s.get(v1));
	  
  }

  public void testToCollection() {
    // Be sure to test that changing records in the returned
    // collection does not change the original records in the
    // inventory.  ToCollection should return a COPY of the records,
    // not the records themselves.
	  s.addNumOwned(v1, 2);
	  s.addNumOwned(v2, 2);

	  //shows that each call to toCollection returns a different COPY.
	  Collection<Record> arrRecord = s.toCollection();
	  Collection<Record> arrRecord2 = s.toCollection();
	  
	  Assert.assertTrue( arrRecord != arrRecord2 );
	  
	  Assert.assertFalse( arrRecord.contains(s.get(v1)) );
	  Assert.assertFalse( arrRecord.contains(s.get(v2)) );
	  
	  //checks if the records i added to s are in the new collection.
	  for (Record r : arrRecord){
		  Assert.assertEquals( 2, r.numOwned );
	  }
	  
  }
}
