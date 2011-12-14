package project.model;

import java.util.List;

/**
 * A road holds cars.
 */
public abstract class RoadSegment implements CarAcceptor {

	/*
	 * public void accept(Car d) { if (d == null) { throw new
	 * IllegalArgumentException(); } _cars.add(d); }
	 */

	public abstract List<Car> getCars();

	@Override
	public abstract void setNextNSRoad(CarAcceptor road);

	@Override
	public abstract void setNextEWRoad(CarAcceptor road);

	@Override
	public abstract CarAcceptor getNextNSRoad();

	@Override
	public abstract CarAcceptor getNextEWRoad();

	@Override
	public abstract double getRoadEnd();

	@Override
	public abstract void acceptCar(Car car, double frontPosition);

	@Override
	public abstract double distanceUntilStop(Car car, double fromPosition);

	@Override
	public abstract CarAcceptor getNextRoad(Car c);

	@Override
	public abstract void setNextRoad(CarAcceptor r);
}
