/**
 * 
 */
package data;


/**
 * @author rayelward
 *
 */
final class CarObj implements Car, Agent {
	
	private int _length;
	private double _maxVelocity;
	private double _brakeDistance;
	private double _stopDistance;
	
	CarObj(double maxVelocity, double brakeDistance, double stopDistance) {
		try {
			setMaxVelocity(maxVelocity);
			setBrakeDistance(brakeDistance);
			setStopDistance(stopDistance);
		}catch (IllegalArgumentException e){
			throw e;
		}
		
	}

	
	
	private void setMaxVelocity(double maxVelocity) {
		if (maxVelocity < 0 || maxVelocity > 100)
			throw new IllegalArgumentException();
		_maxVelocity = maxVelocity;
	}



	private void setBrakeDistance(double brakeDistance) {
		if (brakeDistance < 0 || brakeDistance > 50)
			throw new IllegalArgumentException();
		_brakeDistance = brakeDistance;
	}



	private void setStopDistance(double stopDistance) {
		if (stopDistance < 0 || stopDistance > 50)
			throw new IllegalArgumentException();
		_stopDistance = stopDistance;
	}



	public int getLength() {
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



	@Override
	public void setFrontPosition(double frontPosition) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void setCurrentRoad(RoadSegment roadSegment) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void run() {
		// TODO calculate new front position
		
	}

}
