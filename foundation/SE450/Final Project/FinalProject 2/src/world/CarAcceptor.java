package world;

public interface CarAcceptor {
	public boolean accept(Car c, double frontPosition);
	public double distanceToObstacle(Car c, double fromPosition);
	
}
