package project.model;

public final class LightStateGreen implements LightState {

	@Override
	public LightState getNextState() {
		return new LightStateYellow();

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
