package project.model;

import java.util.ArrayList;
import java.util.List;

public class CarSource implements CarAcceptor, Agent {

	private List<Car> _cars = new ArrayList<Car>();
	private CarAcceptor _nextEWRoad;
	private CarAcceptor _nextNSRoad;
	private CarAcceptor _nextRoad;
	private double _roadEnd;
	private double _generationRate;

	CarSource(double roadEnd) {
		if (roadEnd < 0.0)
			throw new IllegalArgumentException("");
		else
			_roadEnd = roadEnd;
		_generationRate = MP.getCarGenerationDelay();
	}

	@Override
	public void run() {
		Car car = Factory.newCar();
		car.setCurrentRoad(_nextRoad);
		car.setNSCar(getNSCarDirection());
		if (_nextRoad.distanceUntilStop(car, 1) >= 1) {
			_nextRoad.acceptCar(car, 1);
		}
		TimeServerLinked.getServer().enqueue(
				TimeServerLinked.getServer().currentTime() + _generationRate,
				this);
		_generationRate = MP.getCarEntryRate();
	}

	public boolean getNSCarDirection() {
		if (this._nextEWRoad == null)
			return true;
		else
			return false;
	}

	@Override
	public void setNextNSRoad(CarAcceptor road) {
		_nextNSRoad = road;
		_nextRoad = road;

	}

	@Override
	public void setNextEWRoad(CarAcceptor road) {
		_nextEWRoad = road;
		_nextRoad = road;

	}

	@Override
	public CarAcceptor getNextNSRoad() {
		return _nextNSRoad;
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
		// Spawns cars doesn't need to calculate any positions
		_cars.remove(car);
		_nextRoad.acceptCar(car, frontPosition);

	}

	@Override
	public List<Car> getCars() {
		return _cars;
	}

	@Override
	public double distanceUntilStop(Car car, double fromPosition) {
		throw new IllegalArgumentException("Sink distance called!");
	}

	@Override
	public CarAcceptor getNextRoad(Car c) {
		if (c.getNSCar())
			return getNextNSRoad();
		else
			return getNextEWRoad();
	}

	@Override
	public void setNextRoad(CarAcceptor r) {
		_nextRoad = r;

	}

}
