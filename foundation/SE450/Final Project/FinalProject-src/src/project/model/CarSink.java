package project.model;

import java.util.ArrayList;
import java.util.List;

public class CarSink implements CarAcceptor {
	private List<Car> _cars = new ArrayList<Car>();
	private double _roadEnd;
	@SuppressWarnings("unused")
	private CarAcceptor _nextRoad;
	@SuppressWarnings("unused")
	private CarAcceptor _nextEWRoad;
	@SuppressWarnings("unused")
	private CarAcceptor _nextNSRoad;

	CarSink(double roadEnd) {
		if (roadEnd < 0.0)
			throw new IllegalArgumentException("Road is < 0");
		else
			_roadEnd = roadEnd;
		_nextRoad = this;
		_nextEWRoad = this;
		_nextNSRoad = this;
	}

	@Override
	public void setNextNSRoad(CarAcceptor road) {
		throw new IllegalArgumentException("sink road!");

	}

	@Override
	public void setNextEWRoad(CarAcceptor road) {
		throw new IllegalArgumentException("sink road!");

	}

	@Override
	public CarAcceptor getNextNSRoad() {
		return this;
	}

	@Override
	public CarAcceptor getNextEWRoad() {
		return this;
	}

	@Override
	public double getRoadEnd() {
		return _roadEnd;
	}

	@Override
	public void acceptCar(Car car, double frontPosition) {
		if (car == null)
			throw new IllegalArgumentException("null car");
		_cars.remove(car);
	}

	@Override
	public List<Car> getCars() {
		return _cars;
	}

	@Override
	public double distanceUntilStop(Car car, double fromPosition) {
		return Double.POSITIVE_INFINITY;
	}

	@Override
	public CarAcceptor getNextRoad(Car c) {
		return this;
	}

	@Override
	public void setNextRoad(CarAcceptor r) {
		throw new IllegalArgumentException("sink road!");

	}

}
