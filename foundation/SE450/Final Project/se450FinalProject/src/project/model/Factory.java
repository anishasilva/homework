package project.model;

public class Factory {
	private Factory() {}
	public static Car newCar(){
		return (new Car(MP.getCarLength(), MP.getMaxVelocity(), MP.getBrakeDistance(), MP.getStopDistance()));
	}
	
	public static CarAcceptor newRoad(){
		return new RoadSegment(MP.getRoadSegmentLength());
	}
	public static Light newGreenLight(){
		Light temp = new Light();
		return temp;
	}
}
