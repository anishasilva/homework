package project.model;

import java.awt.Color;

final class LightStateGreen implements LightState {

	@Override
	public String getState() {
		return "GREEN";
	}

	@Override
	public Color getColor() {
		return Color.GREEN;
	}

	@Override
	public LightState next() {
		return new LightStateYellow();
	}

	@Override
	public double time(double green, double yellow) {
		return green;
	}

	@Override
	public double obstacleDistance(Car car, double frontPosition, Light light) {
		double distance = Double.POSITIVE_INFINITY;
		if (car.getNSCar() && car.getCurrentRoad() != light) {
			distance = car.getCurrentRoad().getRoadEnd() - frontPosition;
		}
		return distance;
	}

}
