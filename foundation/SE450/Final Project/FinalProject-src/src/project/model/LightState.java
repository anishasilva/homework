package project.model;

import java.awt.Color;

interface LightState {
	String getState();

	Color getColor();

	LightState next();

	double time(double green, double yellow);

	double obstacleDistance(Car car, double frontPosition, Light light);
}
