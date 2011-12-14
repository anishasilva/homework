package project.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * A light has a boolean state.
 */
public class Light implements Agent, CarAcceptor {

	private LightState lightState;
	private List<Car> _cars = new ArrayList<Car>();
	private CarAcceptor _nextNSRoad;
	private CarAcceptor _nextEWRoad;
	@SuppressWarnings("unused")
	private CarAcceptor _nextRoad;
	private double _roadEnd;
	double greenLightTime;
	double yellowLightTime;

	Light(double roadEnd) {
		if (Math.random() <= 0.5)
			lightState = new LightStateGreen();
		else
			lightState = new LightStateRed();

		if (roadEnd < 0.0)
			throw new IllegalArgumentException("Road can not be <= 0");
		else
			_roadEnd = roadEnd;

		greenLightTime = MP.getGreenTime();
		yellowLightTime = MP.getYellowTime();

	}

	public String getState() {
		return lightState.getState();
	}

	public Color getColor() {
		return lightState.getColor();
	}

	public void run() {

		lightState = lightState.next();
		TimeServerLinked.getServer().enqueue(
				TimeServerLinked.getServer().currentTime()
						+ lightState.time(greenLightTime, yellowLightTime),
				this);

	}

	public void acceptCar(Car d, double frontPosition) {
		if (d == null) {
			throw new IllegalArgumentException("Null Car");
		}
		_cars.remove(d);
		if (frontPosition > getRoadEnd()) {
			getNextRoad(d).acceptCar(d, frontPosition - getRoadEnd());
		} else {
			d.setCurrentRoad(this);
			d.setFrontPosition(frontPosition);
			_cars.add(d);
			TimeServerLinked.getServer().enqueue(
					TimeServerLinked.getServer().currentTime()
							+ MP.getTimeStep(), d);
		}
	}

	public void removeCar(Car d) {
		if (d == null) {
			throw new IllegalArgumentException("Null Car");
		}
		_cars.remove(d);
	}

	public List<Car> getCars() {
		return _cars;
	}

	public void setNextEWRoad(CarAcceptor road) {
		_nextEWRoad = road;
	}

	public void setNextNSRoad(CarAcceptor road) {
		_nextNSRoad = road;
	}

	public CarAcceptor getNextEWRoad() {
		return _nextEWRoad;
	}

	public CarAcceptor getNextNSRoad() {
		return _nextNSRoad;
	}

	public CarAcceptor getNextRoad(Car c) {
		if (c.getNSCar())
			return getNextNSRoad();
		else
			return getNextEWRoad();
	}

	public void setNextRoad(CarAcceptor r) {
		_nextRoad = r;
	}

	public double getRoadEnd() {
		return _roadEnd;
	}

	public double distanceUntilStop(Car car, double frontPosition) {

		double obstacleDist = lightState.obstacleDistance(car, frontPosition,
				this);

		double obstacle = Factory.distanceToCarBack(car, frontPosition, _cars,
				(CarAcceptor) this);
		obstacle = Math.min(obstacle, obstacleDist);
		if (obstacle == Double.POSITIVE_INFINITY)
			obstacle = (getRoadEnd() - frontPosition + getNextRoad(car)
					.distanceUntilStop(car, 0));

		return obstacle;
	}
}
