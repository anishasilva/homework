package world;

import java.util.ArrayList;
import java.util.List;

public class CarSink implements CarAcceptor {
	private List<Car> _cars = new ArrayList<Car>();
	@Override
	public boolean accept(Car c, double frontPosition) {
		if ( ( _cars.remove(c) ) )
			return true;
		return false;
	}

	@Override
	public double distanceToObstacle(Car c, double fromPosition) {
		return Double.POSITIVE_INFINITY;
	}

}
