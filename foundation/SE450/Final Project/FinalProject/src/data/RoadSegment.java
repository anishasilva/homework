package data;

import java.util.Set;

public class RoadSegment implements CarAcceptor {
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
	//TODO
}
