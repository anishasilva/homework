package world;

import java.util.Set;

public class Road implements CarAcceptor {
	Set<Car> _cars;
	double _endPosition;
	CarAcceptor _nextRoad;
	
	public boolean accept(Car c, double frontPosition) {
		_cars.remove(c);
		if (frontPosition > _endPosition)
			return _nextRoad.accept(c, frontPosition - _endPosition);
		else {
			c.setCurrentRoad(this);
			c.setFrontPosition(frontPosition);
			_cars.add(c);
			return true;
		}
	}
	double getRoadEnd(){
		return _endPosition;
	}
	//TODO
	@SuppressWarnings("unused")
	private double distanceToCarBack(Car c, double fromPosition){
		double carBackPosition = Double.POSITIVE_INFINITY;
		for (Car x : _cars){
			if (c.getBackPosition() >= fromPosition && c.getBackPosition() < carBackPosition)
				carBackPosition = c.getBackPosition();
		}
		return carBackPosition;
	}
	public double distanceToObstacle(Car c, double fromPosition){
		double obstaclePosition = this.distanceToCarBack(c, fromPosition);
		if (obstaclePosition == Double.POSITIVE_INFINITY){
			@SuppressWarnings("unused")
			double distanceToEnd = fromPosition - this._endPosition;
			obstaclePosition = _nextRoad.distanceToObstacle(c, fromPosition - this._endPosition);
		}
		
		return (obstaclePosition - fromPosition);
	}
}
