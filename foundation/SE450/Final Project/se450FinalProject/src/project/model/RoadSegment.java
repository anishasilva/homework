package project.model;

import java.util.List;
import java.util.ArrayList;

/**
 * A road holds cars.
 */
public class RoadSegment implements CarAcceptor {
  RoadSegment() { } // Created only by this package
  
  List<Car> _cars = new ArrayList<Car>();
  private double _length;
  private CarAcceptor _nextRoad;
  
  RoadSegment(double length){
	  if (length <= 0.0)
		  throw new IllegalArgumentException();
	  _length = length;
  }

  public void accept(Car car, double frontPosition) {
  	if (car == null){
  		throw new IllegalArgumentException();
  	}
  	_cars.remove(car);
  	if (frontPosition > getLength())
  		getNextRoad(car).accept(car, frontPosition - getLength());
  	else{
  		car.setCurrentRoad(this);
  		car.setFrontPosition(frontPosition);
  		_cars.add(car);
  		TimeServerLinked.getServer().enqueue(TimeServerLinked.getServer().currentTime() + MP.getTimeStep(), car);
  	}
  }

	public double getLength() {
		return _length;
	}

  	public void accept(Car d) {
	  if (d == null) { throw new IllegalArgumentException(); }
    	_cars.add(d);
  	}
  	public List<Car> getCars() {
	  	return _cars;
  	}
	public CarAcceptor getNextRoad(Car c) {
		return _nextRoad;
	}
	public void setNextRoad(CarAcceptor road) {
		_nextRoad = road;
	}
	public double distanceToObstacle(Car car, double position) {
		double obstacle = distanceToCar(car, position);
		if (obstacle == Double.POSITIVE_INFINITY)
			obstacle = (getLength() - position) + getNextRoad(car).distanceToObstacle(car, 0.0);
		return obstacle;
	}

	private double distanceToCar(Car car, double frontPosition) {
		double carBackPosition = Double.POSITIVE_INFINITY;
		if (car.getCurrentRoad() == this){
			for (Car c : _cars)
				if (c != car && c.getBackPosition() >= frontPosition && c.getBackPosition() < carBackPosition)
					carBackPosition = c.getBackPosition() - 1;
		} else {
			for (Car c : _cars){
				if(c.getBackPosition() <= 1)
					carBackPosition = c.getBackPosition();
				else if (c.getBackPosition() >= frontPosition && c.getBackPosition() < carBackPosition)
					carBackPosition = c.getBackPosition() -1;
			}
		}
		return carBackPosition - frontPosition + car.getCurrentRoad().getLength();
	}
}
