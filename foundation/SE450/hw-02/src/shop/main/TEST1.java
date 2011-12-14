package shop.main;

import junit.framework.Assert;
import junit.framework.TestCase;
import shop.command.Command;
import shop.data.Data;
import shop.data.Record;
import shop.data.Video;
import shop.data.Inventory;

import java.util.Iterator;
import java.util.HashSet;

// write an integration test that tests the data classes.
// add in some videos, check out, check in, delete videos, etc.
// check that errors are reported when necessary.
// check that things are going as expected.
public class TEST1 extends TestCase {
  private Inventory _inventory = Data.newInventory();
  public TEST1(String name) {
    super(name);
  }
  private void expect(Video v, String s) {
    Assert.assertEquals(s,_inventory.get(v).toString());
  }
  private void expect(Record r, String s) {
    Assert.assertEquals(s,r.toString());
  }
  public void test1() {
    Command clearCmd = Data.newClearCmd(_inventory);
    clearCmd.run();
    
    Video v1 = Data.newVideo("Title1", 2000, "Director1");
    Assert.assertEquals(0, _inventory.size());
    Assert.assertTrue(Data.newAddCmd(_inventory, v1, 5).run());
    Assert.assertEquals(1, _inventory.size());
    Assert.assertTrue(Data.newAddCmd(_inventory, v1, 5).run());
    Assert.assertEquals(1, _inventory.size());
    // System.out.println(_inventory.get(v1));
    expect(v1,"Title1 (2000) : Director1 [10,0,0]");
    expect(_inventory.get(v1), "Title1 (2000) : Director1 [10,0,0]");
    
    Video v2;
    try { 
    	v2 = Data.newVideo("", 2010, "director");
    	Assert.fail();
    }
    catch (IllegalArgumentException e){ }
    try { 
    	v2 = Data.newVideo("title", 2010, "");
    	Assert.fail();
    }
    catch (IllegalArgumentException e){ }
    
    Video vid2 = Data.newVideo("title2", 2011, "director2");
    Command c1 = Data.newAddCmd(_inventory, vid2, 3);
    c1.run();
    
    c1 = Data.newAddCmd(_inventory, v1, 5);
    c1.run();
    c1 = Data.newOutCmd(_inventory, v1);
    c1.run();
    expect(_inventory.get(v1), "Title1 (2000) : Director1 [15,1,1]");
    c1 = Data.newInCmd(_inventory, v1);
    c1.run();
    expect(_inventory.get(v1), "Title1 (2000) : Director1 [15,0,1]");
    c1 = Data.newOutCmd(_inventory, vid2);
    c1.run();
    
    expect(_inventory.get(vid2),"title2 (2011) : director2 [3,1,1]");
    
    
    
    //testing the iterator with the new factory commands.
	HashSet<Video> hSet = new HashSet<Video>();
	Inventory iSet = Data.newInventory();
	v2 = Data.newVideo("B", 2000, "B");
	Video v3 = Data.newVideo("C", 2000, "C");
	Video v4 = Data.newVideo("D", 2000, "D");
	Command cmdAdd = Data.newAddCmd(iSet, v1, 2);
	cmdAdd.run();
	cmdAdd = Data.newAddCmd(iSet, v2, 2);
	cmdAdd.run();
	cmdAdd = Data.newAddCmd(iSet, v3, 2);
	cmdAdd.run();
	cmdAdd = Data.newAddCmd(iSet, v4, 2);
	cmdAdd.run();
	
	hSet.add(v1); hSet.add(v2); hSet.add(v3); hSet.add(v4);
	Iterator<Record> i = iSet.iterator();
	while (i.hasNext()){
		Record r = i.next();
		Assert.assertTrue( hSet.contains( r.video() ) );
		hSet.remove(r.video());
	}
	Assert.assertTrue(hSet.isEmpty());
  }
}
