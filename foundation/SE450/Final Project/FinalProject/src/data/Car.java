/**
 * 
 */
package data;

/**
 * 
 * Comprises Length, Velocity ect..
 *
 */
public interface Car {

	/*
	 * Length of the car (in meters)
	 */
	public int getLength();
	
	/*
	 * The maximum velocity of the car (in meters/second)
	 */
	public double getMaxVelocity();
	
	/*
	 * If distace to nearest obstacle is <= brakeDistance,
	 * then the car will start to slow down (in meters)
	 */
	public double getBrakeDistance();
	
	/*
	 * If distance to nearest obstacle is == stopDistance,
	 * then the car will stop (in meters)
	 */
	public double getStopDistance();
	
	public String toString();

	public void setFrontPosition(double frontPosition);

	public void setCurrentRoad(RoadSegment roadSegment);

}
