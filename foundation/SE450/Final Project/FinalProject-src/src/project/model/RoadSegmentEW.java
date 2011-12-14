package project.model;

import java.util.ArrayList;
import java.util.List;

public final class RoadSegmentEW extends RoadSegment implements CarAcceptor {
	private List<Car> _cars = new ArrayList<Car>();
	private CarAcceptor _nextEWRoad;
	@SuppressWarnings("unused")
	private CarAcceptor _nextRoad;
	private double _roadEnd;

	RoadSegmentEW(double roadEnd) {
		if (roadEnd < 0.0)
			throw new IllegalArgumentException();
		else
			_roadEnd = roadEnd;
	}

	@Override
	public List<Car> getCars() {
		return _cars;
	}

	@Override
	public void setNextNSRoad(CarAcceptor road) {
		throw new IllegalArgumentException();

	}

	@Override
	public void setNextEWRoad(CarAcceptor road) {
		_nextEWRoad = road;
		_nextRoad = road;
	}

	@Override
	public CarAcceptor getNextNSRoad() {
		throw new IllegalArgumentException();
	}

	@Override
	public CarAcceptor getNextEWRoad() {
		return _nextEWRoad;
	}

	@Override
	public double getRoadEnd() {
		return _roadEnd;
	}

	@Override
	public void acceptCar(Car car, double frontPosition) {
		if (car == null)
			throw new IllegalArgumentException();
		_cars.remove(car);
		if (frontPosition > getRoadEnd())
			getNextRoad(car).acceptCar(car, frontPosition - getRoadEnd());
		else {
			car.setCurrentRoad(this);
			car.setFrontPosition(frontPosition);
			_cars.add(car);
			TimeServerLinked.getServer().enqueue(
					TimeServerLinked.getServer().currentTime()
							+ MP.getTimeStep(), car);
		}

	}

	@Override
	public double distanceUntilStop(Car car, double fromPosition) {
		double obstacle = Factory.distanceToCarBack(car, fromPosition, _cars,
				(CarAcceptor) this);
		if (obstacle == Double.POSITIVE_INFINITY)
			obstacle = (getRoadEnd() - fromPosition)
					+ getNextRoad(car).distanceUntilStop(car, 0);
		return obstacle;
	}

	@Override
	public CarAcceptor getNextRoad(Car c) {
		return _nextEWRoad;
	}

	@Override
	public void setNextRoad(CarAcceptor r) {
		_nextRoad = r;

	}
}
