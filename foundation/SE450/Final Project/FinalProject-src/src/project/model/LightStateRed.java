package project.model;

import java.awt.Color;

final class LightStateRed implements LightState {

	@Override
	public String getState() {
		return "RED";
	}

	@Override
	public Color getColor() {
		return Color.RED;
	}

	@Override
	public LightState next() {
		return new LightStateYelNS();
	}

	@Override
	public double time(double green, double yellow) {
		return (green - yellow);
	}

	@Override
	public double obstacleDistance(Car car, double frontPosition, Light light) {
		double distance = Double.POSITIVE_INFINITY;

		if (!car.getNSCar() && car.getCurrentRoad() != light) {
			distance = car.getCurrentRoad().getRoadEnd() - frontPosition;
		}

		return distance;
	}

}
