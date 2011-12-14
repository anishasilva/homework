package project.model;

import java.util.List;

public interface CarAcceptor {
	public void setNextNSRoad(CarAcceptor road);

	public void setNextEWRoad(CarAcceptor road);

	public CarAcceptor getNextNSRoad();

	public CarAcceptor getNextEWRoad();

	public double getRoadEnd();

	public void acceptCar(Car car, double frontPosition);

	public List<Car> getCars();

	public double distanceUntilStop(Car car, double fromPosition);

	public CarAcceptor getNextRoad(Car c);

	public void setNextRoad(CarAcceptor r);
}
