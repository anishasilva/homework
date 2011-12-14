package project.model;

public class LightStateYellow implements LightState {

	@Override
	public LightState getNextState() {
		return new LightStateRed();

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
