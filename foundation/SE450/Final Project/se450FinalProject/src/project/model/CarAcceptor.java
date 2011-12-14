package project.model;

import java.util.List;

interface CarAcceptor {
	
	public void setNextRoad(CarAcceptor road);
	
	public CarAcceptor getNextRoad(Car c);
	
	public List<Car> getCars();
	
	public void accept(Car car, double frontPosition);

	public double distanceToObstacle(Car car, double frontPosition);

	public double getLength();

	
}
