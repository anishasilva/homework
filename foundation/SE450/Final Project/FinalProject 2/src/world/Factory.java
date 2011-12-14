package world;

public final class Factory {
	private Factory() {}
	
	public static Car newCar(){
		return new Car( Data.getCarLength(), Data.getCarVelocity(), Data.getBrakeDistance(), Data.getStopDistance() );
	}
	
	public static Road newRoad(){
		return new Road();
	}
}
