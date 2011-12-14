package project.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A light has a boolean state.
 */
public class Light implements Agent, CarAcceptor {
  Light() { } // Created only by this package
  
  private LightState _state;
  private List<Car> _cars = new ArrayList<Car>();
  private CarAcceptor _nextRoad;
  private double _end;
  
  Light(double end){
	  if (Math.random() <= 0.5)
		  _state = new LightStateGreen();
	  else
		  _state = new LightStateYellow();
	  if (end < 0.0)
		  throw new IllegalArgumentException();
	  _end = end;
  }

  public LightState getState() {
    return _state;
  }
  public void run(double time) {
    _state.run();
  }
@Override
public void setNextRoad(CarAcceptor road) {
	// TODO Auto-generated method stub
	
}
@Override
public CarAcceptor getNextRoad(Car c) {
	// TODO Auto-generated method stub
	return null;
}
@Override
public List<Car> getCars() {
	return _cars;
}
@Override
public void accept(Car car, double frontPosition) {
	// TODO Auto-generated method stub
	
}
@Override
public double distanceToObstacle(Car car, double frontPosition) {
	// TODO Auto-generated method stub
	return 0;
}
@Override
public double getLength() {
	// TODO Auto-generated method stub
	return 0;
}
}

