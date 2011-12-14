/**
 * 
 */
package world;


/**
 * @author rayelward
 *
 */
final class Car implements Agent {
	
	private Road _currentRoad;
	
	
	private double _length;
	private double _maxVelocity;
	private double _brakeDistance;
	private double _stopDistance;
	private double _frontPosition;
	
	Car(double length, double maxVelocity, double brakeDistance, double stopDistance) {
		if (length <= 0.0 || maxVelocity <= 0.0 || brakeDistance <= 0.0 || stopDistance <= 0.0)
			throw new IllegalArgumentException();
		else{
			_length = length;
			_maxVelocity = maxVelocity;
			_brakeDistance = brakeDistance;
			_stopDistance = stopDistance;
			_currentRoad = null;
			_frontPosition = 0;
		}	
	}
	
	
	public Road getCurrentRoad(){
		return _currentRoad;
	}
	public void setCurrentRoad(Road roadSegment) {
		_currentRoad = roadSegment;
	}
	public double getFrontPosition(){
		return _frontPosition;
	}
	public void setFrontPosition(double frontPosition){
		_frontPosition = frontPosition;
	}
	public double getBackPosition() {
		return (_frontPosition - _length);
	}
	public double getLength(){
		return _length;
	}
	public double getMaxVelocity(){
		return _maxVelocity;
	}
	public double getBrakeDistance(){
		return _brakeDistance;
	}
	public double getStopDistance(){
		return _stopDistance;
	}
	
	
	private double calculateVelocity(double distanceToObstacle){
		double velocity = (_maxVelocity / (_brakeDistance - _stopDistance)) * (distanceToObstacle - _stopDistance);
		velocity = Math.max(0.0, velocity);
		velocity = Math.min(_maxVelocity, velocity);
		while (velocity > distanceToObstacle){
			velocity = velocity / 2;
		}
		return velocity;

	}
	public void run() {
		double distanceToNextObstacle = _currentRoad.distanceToObstacle(this, _frontPosition) - _currentRoad.getRoadEnd();
		double carVelocity;
		if (distanceToNextObstacle > _maxVelocity)
			carVelocity = _maxVelocity;
		else if (distanceToNextObstacle <= _stopDistance)
			carVelocity = 0;
		else  
			carVelocity = calculateVelocity(distanceToNextObstacle);
		_currentRoad.accept(this, _frontPosition + carVelocity * Data.timeStep);
	}

}
