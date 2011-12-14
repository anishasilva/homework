package project.model;

import junit.framework.Assert;
import junit.framework.TestCase;

public class RoadSegmentTEST extends TestCase {
	public RoadSegmentTEST(String name) {
		super(name);
	}

	double length = 500;

	public void testConstructorAndAttributesEW() {

		CarAcceptor road = new RoadSegmentEW(length);
		CarAcceptor road2 = Factory.newEWRoad();
		CarAcceptor road3 = Factory.newEWRoad();

		road.setNextEWRoad(road2);
		road2.setNextEWRoad(road3);

		Assert.assertEquals((int) length, (int) road.getRoadEnd());

		Assert.assertNotSame(road, road2);
		Assert.assertSame(road, road);

		Assert.assertSame(road.getNextEWRoad(), road2);
		Assert.assertSame(road2.getNextEWRoad(), road3);
		Assert.assertSame(road3.getNextEWRoad(), null);

		try {
			road.setNextNSRoad(road2);
			Assert.fail();
		} catch (IllegalArgumentException e) {
		}

		try {
			road.getNextNSRoad();
			Assert.fail();
		} catch (IllegalArgumentException e) {
		}
	}

	public void testConstructorAndAttributesNS() {

		CarAcceptor road = new RoadSegmentNS(length);
		CarAcceptor road2 = Factory.newNSRoad();
		CarAcceptor road3 = Factory.newNSRoad();

		road.setNextNSRoad(road2);
		road2.setNextNSRoad(road3);

		Assert.assertEquals((int) length, (int) road.getRoadEnd());

		Assert.assertNotSame(road, road2);
		Assert.assertSame(road, road);

		Assert.assertSame(road.getNextNSRoad(), road2);
		Assert.assertSame(road2.getNextNSRoad(), road3);
		Assert.assertSame(road3.getNextNSRoad(), null);

		try {
			road.setNextEWRoad(road2);
			Assert.fail();
		} catch (IllegalArgumentException e) {
		}

		try {
			road.getNextEWRoad();
			Assert.fail();
		} catch (IllegalArgumentException e) {
		}
	}

	public void testConstructorException() {
		try {
			@SuppressWarnings("unused")
			RoadSegment road = new RoadSegmentEW(-1);
			Assert.fail();
		} catch (IllegalArgumentException e) {
		}
		try {
			@SuppressWarnings("unused")
			RoadSegment road = new RoadSegmentNS(-1);
			Assert.fail();
		} catch (IllegalArgumentException e) {
		}
	}
}
