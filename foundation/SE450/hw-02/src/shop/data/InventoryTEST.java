package shop.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import junit.framework.Assert;
import junit.framework.TestCase;

public class InventoryTEST extends TestCase {
  public InventoryTEST(String name) {
    super(name);
  }
  InventorySet s = new InventorySet();
  final VideoObj v1 = new VideoObj( "A", 2000, "B" );
  final VideoObj v1copy = new VideoObj( "A", 2000, "B" );
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
      s.addNumOwned(v1, 1);     Assert.assertEquals( v1, s.get(v1).video() );
                                Assert.assertEquals( 1, s.get(v1).numOwned() );
      s.addNumOwned(v1, 2);     Assert.assertEquals( 3, s.get(v1).numOwned() );
      s.addNumOwned(v1, -1);    Assert.assertEquals( 2, s.get(v1).numOwned() );
      s.addNumOwned(v2, 1);     Assert.assertEquals( 2, s.get(v1).numOwned() );
      s.addNumOwned(v1copy, 1); Assert.assertEquals( 3, s.get(v1).numOwned() );
                                Assert.assertEquals( 2, s.size() );
      s.addNumOwned(v1, -3);    
                                Assert.assertEquals( 1, s.size() );
try { s.addNumOwned(null, 1);   Assert.fail(); } catch ( IllegalArgumentException e ) {}

  }

  public void testCheckOutCheckIn() {
	    try { s.checkOut(null);     Assert.fail(); } catch ( IllegalArgumentException e ) {}
	    try { s.checkIn(null);      Assert.fail(); } catch ( IllegalArgumentException e ) {}
	          s.addNumOwned(v1, 2); Assert.assertTrue( s.get(v1).numOut() == 0 && s.get(v1).numRentals() == 0 );
	          s.checkOut(v1);       Assert.assertTrue( s.get(v1).numOut() == 1 && s.get(v1).numRentals() == 1 );
	    try { s.addNumOwned(v1,-3); Assert.fail(); } catch ( IllegalArgumentException e ) {}
	    try { s.addNumOwned(v1,-2); Assert.fail(); } catch ( IllegalArgumentException e ) {}
	          s.addNumOwned(v1,-1); Assert.assertTrue( s.get(v1).numOut() == 1 && s.get(v1).numRentals() == 1 );
	          s.addNumOwned(v1, 1); Assert.assertTrue( s.get(v1).numOut() == 1 && s.get(v1).numRentals() == 1 );
	          s.checkOut(v1);       Assert.assertTrue( s.get(v1).numOut() == 2 && s.get(v1).numRentals() == 2 );
	    try { s.checkOut(v1);       Assert.fail(); } catch ( IllegalArgumentException e ) {}
	          s.checkIn(v1);        Assert.assertTrue( s.get(v1).numOut() == 1 && s.get(v1).numRentals() == 2 );
	          s.checkIn(v1copy);    Assert.assertTrue( s.get(v1).numOut() == 0 && s.get(v1).numRentals() == 2 );
	    try { s.checkIn(v1);        Assert.fail(); } catch ( IllegalArgumentException e ) {}
	    try { s.checkOut(v2);       Assert.fail(); } catch ( IllegalArgumentException e ) {}
	          s.checkOut(v1);       Assert.assertTrue( s.get(v1).numOut() == 1 && s.get(v1).numRentals() == 3 );
  }

  public void testClear() {
      s.addNumOwned(v1, 2); Assert.assertEquals( 1, s.size() );
      s.addNumOwned(v2, 2); Assert.assertEquals( 2, s.size() );
      s.clear();            Assert.assertEquals( 0, s.size() );
      try { s.checkOut(v2);       Assert.fail(); } catch ( IllegalArgumentException e ) {}
  }

  public void testGet() {
	    s.addNumOwned(v1, 1);
	    Record r1 = s.get(v1);
	    Record r2 = s.get(v1);
	    Assert.assertFalse( r1.equals(r2) );
	    Assert.assertTrue( r1 != r2 );
  }

public void testIterator1() {
	HashSet<Video> hSet = new HashSet<Video>();
	InventorySet iSet = new InventorySet();
	VideoObj v3 = new VideoObj("C", 2000, "C");
	VideoObj v4 = new VideoObj("D", 2000, "D");
	iSet.addNumOwned(v1, 2); iSet.addNumOwned(v2, 2);
	iSet.addNumOwned(v3, 2); iSet.addNumOwned(v4, 2);
	hSet.add(v1); hSet.add(v2); hSet.add(v3); hSet.add(v4);
	Iterator<Record> i = iSet.iterator();
	while (i.hasNext()){
		Record r = i.next();
		Assert.assertTrue( hSet.contains( r.video() ) );
		hSet.remove(r.video());
	}
	Assert.assertTrue(hSet.isEmpty());
}
  public void testIterator2() {
	
	class NumOwnedComparator implements java.util.Comparator<Record> {
		public int compare (Record r1, Record r2) {
			return r2.numOwned() - r1.numOwned();
		}
	}
	ArrayList<Video> aList = new ArrayList<Video>();

	InventorySet iSet = new InventorySet();
	VideoObj v3 = new VideoObj("C", 2000, "C");
	VideoObj v4 = new VideoObj("D", 2000, "D");
	iSet.addNumOwned(v4, 2); iSet.addNumOwned(v3, 2);
	iSet.addNumOwned(v2, 2); iSet.addNumOwned(v1, 2);
	aList.add(v1); aList.add(v2); aList.add(v3); aList.add(v4);
	Iterator<Record> i = iSet.iterator(new NumOwnedComparator());
	
	while (i.hasNext()){
		Record r = i.next();
		Assert.assertTrue( aList.contains( r.video() ) );
		aList.remove(r.video());
	}
	Assert.assertTrue(aList.isEmpty());
  }

}