package project.model;

import java.util.ArrayList;
import java.util.List;

public final class CarSource implements Agent, CarAcceptor {

	private List<Car> _cars = new ArrayList<Car>();
	private CarAcceptor _nextRoad;
	private double _end;
	
	CarSource(double end){
		if (end < 0.0)
			throw new IllegalArgumentException();
		else
			_end = end;
	}
	
	@Override
	public void setNextRoad(CarAcceptor road) {
		_nextRoad = road;

	}

	@Override
	public CarAcceptor getNextRoad(Car c) {
		return _nextRoad;
	}

	@Override
	public List<Car> getCars() {
		return _cars;
	}

	@Override
	public void accept(Car car, double frontPosition) {
		/*
		 * not used in source, cars are generated and passed to roads from here.
		 */
	}

	@Override
	public double distanceToObstacle(Car car, double frontPosition) {
		throw new IllegalArgumentException("shouldn't be called");
	}

	@Override
	public double getLength() {
		return _end;
	}

	@Override
	public void run(double time) {
		Car car = Factory.newCar();
		car.setCurrentRoad(_nextRoad);
		if (_nextRoad.distanceToObstacle(car, 1) >= 1)
			_nextRoad.accept(car, 1);
		TimeServerLinked.getServer().enqueue(TimeServerLinked.getServer().currentTime() + MP.getCarEntryRate(), this);

	}

}
