package project.model;

/**
 * A car remembers its position from the beginning of its road.
 * Cars have random velocity and random movement pattern:
 * when reaching the end of a road, the dot either resets its position
 * to the beginning of the road, or reverses its direction.
 */
public class Car implements Agent {
	Car() { } // Created only by this package
	
	private java.awt.Color _color = new java.awt.Color((int)Math.ceil(Math.random()*255),(int)Math.ceil(Math.random()*255),(int)Math.ceil(Math.random()*255));
	
	
	private double _length;
	private double _frontPosition;
	private double _brakeDistance;
	private double _stopDistance;
	private double _maxVelocity;

	private CarAcceptor _currentRoad;
	
	Car(double length, double maxVelocity, double brakeDistance, double stopDistance){
		if (length <= 0.0 || maxVelocity <= 0.0 || brakeDistance <= 0.0 || stopDistance <= 0.0)
			throw new IllegalArgumentException();
		else {
			_length = length;
			_maxVelocity = maxVelocity;
			_brakeDistance = brakeDistance;
			_stopDistance = stopDistance;
			_currentRoad = null;
			_frontPosition = 0;
		}
	}
	
	public CarAcceptor getCurrentRoad(){
		return _currentRoad;
	}
	public void setCurrentRoad(CarAcceptor road){
		_currentRoad = road;
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
	
	public double getPosition(){
		return ( getFrontPosition() - (getLength() / 2) );
	}
	public CarAcceptor getNextRoad(){
		return _currentRoad.getNextRoad(this);
	}
	
	public java.awt.Color getColor(){
		return _color;
	}
  
	private double calculateVelocity(double distanceToObstacle){
		double velocity = (getMaxVelocity() / (getBrakeDistance() - getStopDistance())) * (distanceToObstacle - getStopDistance());
		
		return velocity;
	}
	
	public void run(){
		double distanceToObstacle = getCurrentRoad().distanceToObstacle(this, getFrontPosition()) - getCurrentRoad().getLength();
		double carVelocity;
		if (distanceToObstacle > getMaxVelocity())
			carVelocity = getMaxVelocity();
		else if (distanceToObstacle <= getStopDistance() )
			carVelocity = 0;
		else
			carVelocity = calculateVelocity(distanceToObstacle);
		getCurrentRoad().accept(this, getFrontPosition() + ( carVelocity * MP.getTimeStep() ));
		
	}
  
  /*
  private boolean _backAndForth = Math.round(Math.random())==1 ? true : false;
  private double _position = 0;
  private double _velocity = (int) Math.ceil(Math.random() * MP.maxVelocity);
  private java.awt.Color _color = new java.awt.Color((int)Math.ceil(Math.random()*255),(int)Math.ceil(Math.random()*255),(int)Math.ceil(Math.random()*255));
  
  public double getPosition() {
    return _position;
  }
  public java.awt.Color getColor() {
    return _color;
  }*/
  public void run(double time) {
    if (Math.random() > 0.5) {
      if (((_frontPosition + _maxVelocity) < 0) || ((_frontPosition + _maxVelocity) > (MP.roadLength-MP.carLength)))
    	  _maxVelocity *= -1;
    } else {
      if ((_frontPosition + _maxVelocity) > (MP.roadLength-MP.carLength))
    	  _frontPosition = 0;
    }
    _frontPosition += _maxVelocity;
  }
  
  
}
