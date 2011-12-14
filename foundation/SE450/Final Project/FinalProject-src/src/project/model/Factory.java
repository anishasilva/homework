package project.model;

import java.util.List;

public final class Factory {

	/*
	 * spawning instances of cars
	 */
	public static Car newCar() {
		return new Car(MP.getCarLength(), MP.getMaxVelocity(),
				MP.getBrakeDistance(), MP.getStopDistance());
	}

	/*
	 * spawning instances of Roads
	 */
	public static CarAcceptor newEWRoad() {
		return new RoadSegmentEW(MP.roadLength);
	}

	public static CarAcceptor newNSRoad() {
		return new RoadSegmentNS(MP.roadLength);
	}

	public static CarAcceptor newSink() {
		return new CarSink(1);
	}

	public static CarAcceptor newSource() {
		return new CarSource(0);
	}

	public static CarAcceptor newLight() {
		return new Light(MP.getIntersectionLength());
	}

	/*
	 * package private. static calculation method to help CarAcceptors tell
	 * there cars where they should be.
	 */

	static double distanceToCarBack(Car car, double fromPosition,
			List<Car> cars, CarAcceptor road) {
		double carBackPosition = Double.POSITIVE_INFINITY;
		if (car.getCurrentRoad() == road) {
			for (Car c : cars)
				if (c != car && c.getBackPosition() >= fromPosition
						&& c.getBackPosition() < carBackPosition)
					carBackPosition = c.getBackPosition() - 1;
		} else {
			for (Car c1 : cars) {
				if (c1.getBackPosition() <= 1)
					carBackPosition = c1.getBackPosition();
				else if (c1.getBackPosition() >= fromPosition
						&& c1.getBackPosition() < carBackPosition)
					carBackPosition = c1.getBackPosition() - 1;
			}
		}
		return carBackPosition - fromPosition
				+ car.getCurrentRoad().getRoadEnd();
	}
}
