package data;

import junit.framework.Assert;
import junit.framework.TestCase;

public class CarTEST extends TestCase {
	public CarTEST(String name){
		super(name);
	}
	public void test1(){
		Car c1 = new CarObj(30.0, 20.0, 1.0);
		Assert.assertEquals(c1.getBrakeDistance(), 20.0);
		Assert.assertEquals(c1.getMaxVelocity(), 30.0);
		Assert.assertEquals(c1.getStopDistance(), 1.0);
		Car c2 = new CarObj(30.0, 20.0, 1.0);
		
		Assert.assertFalse(c1.equals(c2));
		
		try{
			c2 = new CarObj(-1.0, 20.0, 1.0);
			Assert.fail();
		} catch (IllegalArgumentException e){}
		try{
			c2 = new CarObj(30.0, -1.0, 1.0);
			Assert.fail();
		} catch (IllegalArgumentException e){}
		try{
			c2 = new CarObj(30.0, 20.0, -1.0);
			Assert.fail();
		} catch (IllegalArgumentException e){}
	}
}
