package project.model;

import junit.framework.Assert;
import junit.framework.TestCase;

public class CarTEST extends TestCase {
	public CarTEST(String name) {
		super(name);
	}

	double length = MP.getCarLength();
	double velocity = MP.getMaxVelocity();
	double breakDistance = MP.getBrakeDistance();
	double stopDistance = MP.getStopDistance();

	public void testConstructor() {

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

	public void testConstructorExceptions() {
		@SuppressWarnings("unused")
		Car c1;
		try {
			c1 = new Car(0, velocity, breakDistance, stopDistance);
			Assert.fail();
		} catch (IllegalArgumentException e) {
		}
		try {
			c1 = new Car(length, 0, breakDistance, stopDistance);
			Assert.fail();
		} catch (IllegalArgumentException e) {
		}
		try {
			c1 = new Car(length, velocity, 0, stopDistance);
			Assert.fail();
		} catch (IllegalArgumentException e) {
		}
		try {
			c1 = new Car(length, velocity, breakDistance, 0);
			Assert.fail();
		} catch (IllegalArgumentException e) {
		}
	}

	public void testCar() {
		Car car = Factory.newCar();
		CarAcceptor road = Factory.newEWRoad();

		car.setCurrentRoad(road);

		double frontPosition = car.getFrontPosition();
		double length = car.getLength();
		double backPostion = car.getBackPosition();

		Assert.assertEquals(frontPosition - length, backPostion);

		// See SIMPLETEST for car / road interactions

	}

	public void testCarFields() {

		Car car = Factory.newCar();
		CarAcceptor road = Factory.newEWRoad();

		car.setCurrentRoad(road);
		Assert.assertSame(car.getCurrentRoad(), road);

		double frontPosition = car.getFrontPosition();
		double backPosition = car.getBackPosition();
		double length = car.getLength();

		Assert.assertSame((int) frontPosition - (int) length,
				(int) backPosition);

		Assert.assertTrue(car.getMaxVelocity() >= MP.getMaxVelocityMin());
		Assert.assertTrue(car.getMaxVelocity() <= MP.getMaxVelocityMax());

		Assert.assertTrue(car.getBrakeDistance() >= MP.getBrakeDistanceMin());
		Assert.assertTrue(car.getBrakeDistance() <= MP.getBrakeDistanceMax());

		Assert.assertTrue(car.getStopDistance() >= .5);
		Assert.assertTrue(car.getStopDistance() <= 5);

		Car car2 = Factory.newCar();

		Assert.assertNotSame(car.getMaxVelocity(), car2.getMaxVelocity());
	}

}
