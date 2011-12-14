package project.model;

import java.util.ArrayList;
import java.util.List;

public final class CarSink implements CarAcceptor {

	private List<Car> _cars = new ArrayList<Car>();
	private double _end;
	
	CarSink(double end){
		if (end < 0.0)
			throw new IllegalArgumentException();
		else {
			_end = end;
		}
	}
	@Override
	public void setNextRoad(CarAcceptor road) {
		throw new IllegalArgumentException();

	}

	@Override
	public CarAcceptor getNextRoad(Car c) {
		return this;
	}

	@Override
	public List<Car> getCars() {
		return _cars;
	}

	@Override
	public void accept(Car car, double frontPosition) {
		if (car == null)
			throw new IllegalArgumentException();
		_cars.remove(car);

	}

	@Override
	public double distanceToObstacle(Car car, double frontPosition) {
		return Double.POSITIVE_INFINITY;
	}

	@Override
	public double getLength() {
		return _end;
	}

}
