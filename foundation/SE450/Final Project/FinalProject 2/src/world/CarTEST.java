package world;

import junit.framework.Assert;
import junit.framework.TestCase;

public class CarTEST extends TestCase {
	public CarTEST(String name){
		super(name);
	}
	
	double length = Data.getCarLength();
	double velocity = Data.getCarVelocity();
	double breakDistance = Data.getBrakeDistance();
	double stopDistance = Data.getStopDistance();
	
	
	public void testConstructor(){
		Car c1 = new Car(length, velocity, breakDistance, stopDistance);
		
		Assert.assertEquals(length, c1.getLength());
		Assert.assertEquals(velocity, c1.getMaxVelocity());
		Assert.assertEquals(breakDistance, c1.getBrakeDistance());
		Assert.assertEquals(stopDistance, c1.getStopDistance());
		
		Car c2 = Factory.newCar();
		
		Assert.assertNotSame(c1, c2);
		Assert.assertSame(c1, c1);
		Assert.assertSame(c2, c2);
		
		Car c3 = Factory.newCar();
		
		Assert.assertNotSame(c2, c3);
	}

	public void testConstructorExceptions(){
		@SuppressWarnings("unused")
		Car c1;
		try {
			c1 = new Car(0, velocity, breakDistance, stopDistance);
			Assert.fail();
		} catch (IllegalArgumentException e){ }
		try {
			c1 = new Car(length, 0, breakDistance, stopDistance);
			Assert.fail();
		} catch(IllegalArgumentException e){ }
		try {
			c1 = new Car(length, velocity, 0, stopDistance);
			Assert.fail();
		}catch (IllegalArgumentException e){ }
		try {
			c1 = new Car(length, velocity, breakDistance, 0);
			Assert.fail();
		}catch (IllegalArgumentException e) { }
	}
	public void testCar(){
		Car car = Factory.newCar();
		Road road = Factory.newRoad();
		
		car.setCurrentRoad(road);
		
		double frontPosition = car.getFrontPosition();
		double length = car.getLength();
		double backPostion = car.getBackPosition();
		
		Assert.assertEquals(frontPosition - length, backPostion);
		
		//TODO check with road when road fully implemented.
		
	}
	
}
