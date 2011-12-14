package world;

public final class Data {
	static TimeServer _timeServer = new TimeServerLinked();
	static double timeStep = 1.0;
	
	static TimeServer getTS(){
		return _timeServer;
	}

	
	/*
	 * Car information
	 */
	private static double stopDistance = 2;
	public static double getStopDistance() {
		return stopDistance;
	}
	private static double length = 10;
	public static double getCarLength() {
		return length;
	}
	private static double maxVelocity = 20;
	public static double getCarVelocity() {
		return maxVelocity;
	}
	private static double brakeDistance = 9;
	public static double getBrakeDistance() {
		return brakeDistance;
	}
}
