package project.model;

/**
 * A car remembers its position from the beginning of its road. Cars have random
 * velocity and random movement pattern: when reaching the end of a road, the
 * dot either resets its position to the beginning of the road, or reverses its
 * direction.
 */
public class Car implements Agent {
	Car() {
	}

	private double _length;
	private double _maxVelocity;
	private double _brakeDistance;
	private double _stopDistance;
	private CarAcceptor _currentRoad;
	private double _frontPosition;
	private boolean _NSCar;

	private java.awt.Color _color = new java.awt.Color((int) Math.ceil(Math
			.random() * 255), (int) Math.ceil(Math.random() * 255),
			(int) Math.ceil(Math.random() * 255));

	// private java.awt.Color _color = java.awt.Color.WHITE;

	public Car(double length, double maxVelocity, double brakeDistance,
			double stopDistance) {
		if (length <= 0.0 || maxVelocity <= 0.0 || brakeDistance <= 0.0
				|| stopDistance <= 0.0) {
			throw new IllegalArgumentException();
		} else {
			_length = length;
			_maxVelocity = maxVelocity;
			_brakeDistance = brakeDistance;
			_stopDistance = stopDistance;
			_currentRoad = null;
			_frontPosition = 0;
			_NSCar = true;
		}
	}

	public CarAcceptor getCurrentRoad() {
		return _currentRoad;
	}

	public void setCurrentRoad(CarAcceptor newCurrentRoad) {
		_currentRoad = newCurrentRoad;
	}

	public CarAcceptor getNextRoad() {
		if (_NSCar) {
			return _currentRoad.getNextNSRoad();
		} else
			return _currentRoad.getNextEWRoad();
	}

	public double getPosition() {

		return getFrontPosition() - _length / 2;
	}

	public double getFrontPosition() {
		return _frontPosition;
	}

	public boolean getNSCar() {
		return _NSCar;
	}

	public void setNSCar(boolean NSDirection) {
		_NSCar = NSDirection;
	}

	public void setFrontPosition(double newFrontPosition) {
		_frontPosition = newFrontPosition;
	}

	double getBackPosition() {
		return _frontPosition - getLength();
	}

	public double getLength() {
		return _length;
	}

	public double getMaxVelocity() {
		return _maxVelocity;
	}

	public double getBrakeDistance() {
		return _brakeDistance;
	}

	public double getStopDistance() {
		return _stopDistance;
	}

	public java.awt.Color getColor() {
		return _color;
	}

	private double calculateVelocity(double distanceToObsticle) {
		double carVelocity;
		carVelocity = (_maxVelocity / (_brakeDistance - _stopDistance))
				* (distanceToObsticle - _stopDistance);
		carVelocity = Math.max(0.0, carVelocity);
		carVelocity = Math.min(_maxVelocity, carVelocity);
		while (carVelocity > distanceToObsticle)
			carVelocity = carVelocity / 2; // carVelocity = 0;
		return carVelocity;
	}

	public void run() {

		// Find closest obstacle
		double distanceToObstacle = _currentRoad.distanceUntilStop(this,
				_frontPosition) - getCurrentRoad().getRoadEnd();

		// Calculate New Velocity
		double carVelocity;
		if (distanceToObstacle > _maxVelocity) {
			carVelocity = _maxVelocity;
		} else if (distanceToObstacle <= _stopDistance) {
			carVelocity = 0;
		} else {
			carVelocity = calculateVelocity(distanceToObstacle);
		}
		_currentRoad.acceptCar(this,
				_frontPosition + carVelocity * MP.getTimeStep());
	}

}
