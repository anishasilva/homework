package project.model;

import junit.framework.Assert;
import junit.framework.TestCase;

public class LightTEST extends TestCase {
	public LightTEST(String name) {
		super(name);
	}

	double length = 500;

	public void testConstructorAndAttributes() {

		CarAcceptor light = new Light(length);
		CarAcceptor light2 = Factory.newLight();
		CarAcceptor light3 = Factory.newLight();

		light.setNextEWRoad(light2);
		light2.setNextEWRoad(light3);

		Assert.assertEquals((int) length, (int) light.getRoadEnd());

		Assert.assertNotSame(light, light2);
		Assert.assertSame(light, light);
		Assert.assertSame(light.getNextEWRoad(), light2);
		Assert.assertSame(light2.getNextEWRoad(), light3);
		Assert.assertSame(light3.getNextEWRoad(), null);

		try {
			light.setNextNSRoad(light2);
		} catch (IllegalArgumentException e) {
		}

		try {
			light.getNextNSRoad();
		} catch (IllegalArgumentException e) {
		}

		String a = ((Light) light).getState();

		boolean redLight = false;
		boolean greenLight = false;

		if (a.equalsIgnoreCase("GREEN"))
			greenLight = true;

		if (a.equalsIgnoreCase("RED"))
			redLight = true;

		Assert.assertNotSame(redLight, greenLight);

		// See SIMPLETEST for light/road/car interaction
	}

	public void testConstructorException() {
		try {
			@SuppressWarnings("unused")
			CarAcceptor light = new Light(-1);
			Assert.fail();
		} catch (IllegalArgumentException e) {
		}
	}
}
