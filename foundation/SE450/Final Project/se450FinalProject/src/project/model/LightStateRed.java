package project.model;

public class LightStateRed implements LightState {

	@Override
	public LightState getNextState() {
		return new LightStateGreen();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
