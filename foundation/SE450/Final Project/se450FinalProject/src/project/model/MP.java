package project.model;

import java.util.Random;

/**
 * Static class for model parameters.
 */
public class MP {
  private MP() {}
  /** Length of cars, in meters */
  //public static double carLength = 10;
  /** Length of roads, in meters */
  //public static double roadLength = 200;
  /** Maximum car velocity, in meters/second */
  //public static double maxVelocity = 6;
  
  //test 1, 2, 3
  /*
   * Ray defined fields for whole project.
   */
  	private static Random generator = new Random(System.currentTimeMillis());
	private static double getRandomNum(double min, double max){
		return (generator.nextDouble() * (max - min)) + min;
	}
	
	/*
	 * Car information
	 * Car stop distance
	 */
	
	public static double stopDistance = 2;
	private static double carStopDistanceMin = .5;
	private static double carStopDistanceMax = 5;
	public static double getStopDistance() {
		return getRandomNum(carStopDistanceMin, carStopDistanceMax);
	}
	public static double getCarStopDistanceMin(){
		return carStopDistanceMin;
	}
	public static double getCarStopDistanceMax(){
		return carStopDistanceMax;
	}
	public static void setCarStopDistanceMin(double min){
		carStopDistanceMin = min;
	}
	public static void setCarStopDistanceMax(double max){
		carStopDistanceMax = max;
	}
	
	/*
	 * Car length:
	 */
	public static double carLength = 10;
	private static double carLengthMin = 5.0;
	private static double carLengthMax = 10.0;
	public static double getCarLength() {
		return getRandomNum(carLengthMin, carLengthMax);
	}
	public static double getCarLengthMin(){
		return carLengthMin;
	}
	public static double getCarLengthMax(){
		return carLengthMax;
	}
	public static void setCarLengthMin(double min){
		carLengthMin = min;
	}
	public static void setCarLengthMax(double max){
		carLengthMax = max;
	}
	
	/*
	 * car maxVelocity
	 */
	public static double maxVelocity = 20;
	private static double maxVelocityMin = 10;
	private static double maxVelocityMax = 30;
	public static double getMaxVelocity() {
		return getRandomNum(maxVelocityMin, maxVelocityMax);
	}
	public static double getMaxVelocityMin(){
		return maxVelocityMin;
	}
	public static double getMaxVelocityMax(){
		return maxVelocityMax;
	}
	public static void setMaxVelocityMin(double min){
		maxVelocityMin = min;
	}
	public static void setMaxVelocityMax(double max){
		maxVelocityMax = max;
	}
	/*
	 * car brake distance:
	 */
	public static double brakeDistance = 9;
	private static double brakeDistanceMin = 9;
	private static double brakeDistanceMax = 10;
	public static double getBrakeDistance() {
		return getRandomNum(brakeDistanceMin, brakeDistanceMax);
	}
	public static double getBrakeDistanceMin(){
		return brakeDistanceMin;
	}
	public static double getBrakeDistanceMax(){
		return brakeDistanceMax;
	}
	public static void setBrakeDistanceMin(double min){
		brakeDistanceMin = min;
	}
	public static void setBrakeDistanceMax(double max){
		brakeDistanceMax = max;
	}
	
	/*
	 * Lights:
	 * Greenlight time:
	 */
	public static double greenTime = 90.0;
	private static double greenTimeMin = 30.0;
	private static double greenTimeMax = 180.0;
	public static double getGreenTime(){
		return getRandomNum(greenTimeMin, greenTimeMax);
	}
	public static double getGreenTimeMin(){
		return greenTimeMin;
	}
	public static double getGreenTimeMax(){
		return greenTimeMax;
	}
	public static void setGreenTimeMin(double min){
		greenTimeMin = min;
	}
	public static void setGreenTimeMax(double max){
		greenTimeMax = max;
	}
	
	/*
	 * yellow light time:
	 */
	public static double yellowTime = 5.0;
	private static double yellowTimeMin = 4.0;
	private static double yellowTimeMax = 5.0;
	public static double getYellowTime(){
		return getRandomNum(yellowTimeMin, yellowTimeMax);
	}
	public static double getYellowTimeMin(){
		return yellowTimeMin;
	}
	public static double getYellowTimeMax(){
		return yellowTimeMax;
	}
	public static void setYellowTimeMin(double min){
		yellowTimeMin = min;
	}
	public static void setYellowTimeMax(double max){
		yellowTimeMax = max;
	}
	
	/*
	 * traffic pattern type:(set as boolean type)
	 * alternating = true
	 * simple = false
	 */
	private static boolean traffic = true;
	public static void setTrafficPattern(boolean input){
		if (input)
			traffic = true;
		else
			traffic = false;
	}
	public static boolean getTrafficPattern(){
		return traffic;
	}
	public static String trafficPatternToString(){
		if (traffic)
			return "alternating";
		else
			return "simple";
	}
	/*
	 * intersection length
	 * in meters
	 */
	public static double intersectionLength = 10;
	private static double intersectionLengthMin = 10;
	private static double intersectionLengthMax = 15;
	public static double getIntersectionLength(){
		return getRandomNum(intersectionLengthMin, intersectionLengthMax);
	}
	public static double getIntersectionLengthMin(){
		return intersectionLengthMin;
	}
	public static double getIntersectionLengthMax(){
		return intersectionLengthMax;
	}
	public static void setIntersectionLengthMin(double min){
		intersectionLengthMin = min;
	}
	public static void setIntersectionLengthMax(double max){
		intersectionLengthMax = max;
	}
	/*
	 * roadsegment length.
	 * in meters
	 */
	
	public static double roadLength = 300;
	private static double roadLengthMin = 200;
	private static double roadLengthMax = 500;
	public static double getRoadSegmentLength(){
		return getRandomNum(roadLengthMin, roadLengthMax);
	}
	public static double getRoadSegmentLengthMin(){
		return roadLengthMin;
	}
	public static double getRoadSegmentLengthMax(){
		return roadLengthMax;
	}
	public static void setRoadSegmentLengthMin(double min){
		roadLengthMin = min;
	}
	public static void setRoadSegmentLengthMax(double max){
		roadLengthMax = max;
	}
	/*
	 * Car entry rate:
	 * (second/car)
	 */
	public static double carEntryRate = 10;
	private static double carEntryRateMin = 2.0;
	private static double carEntryRateMax = 25.0;
	public static double getCarEntryRate(){
		return getRandomNum(carEntryRateMin, carEntryRateMax);
	}
	public static double getCarEntryRateMin(){
		return carEntryRateMin;
	}
	public static double getCarEntryRateMax(){
		return carEntryRateMax;
	}
	public static void setCarEntryRateMin(double min){
		carEntryRateMin = min;
	}
	public static void setCarEntryRateMax(double max){
		carEntryRateMax = max;
	}
	/*
	 * Car generation delay
	 * Each car source generates cars at a fixed rate, but the sources may vary. You should set the delay
	 * between car generations by each source to be a random number between the min and max chosen here. 
	 */
	private static double carGenerationDelayMin = 2.0;
	private static double carGenerationDelayMax = 25.0;
	public static double getCarGenerationDelay(){
		return getRandomNum(carGenerationDelayMin, carGenerationDelayMax);
	}
	public static double getCarGenerationDelayMin(){
		return carGenerationDelayMin;
	}
	public static double getCarGenerationDelayMax(){
		return carGenerationDelayMax;
	}
	public static void setCarGenerationDelayMin(double min){
		carGenerationDelayMin = min;
	}
	public static void setCarGenerationDelayMax(double max){
		carGenerationDelayMax = max;
	}
	/*
	 * Grid size:
	 * standard 2x3
	 */
	public static int rows = 2;
	public static int columns = 3;
	public static void setGridRows(int row){
		rows = row;
	}
	public static int getGridRows(){
		return rows;
	}
	public static void setGridColumns(int column){
		columns = column;
	}
	public static int getGridColumns(){
		return columns;
	}
	
	/*
	 * default runtime 1000.0 seconds
	 */
	private static double runtime = 1000.0;
	public static double getRuntime(){
		return runtime;
	}
	public static void setRuntime(double runt){
		runtime = runt;
	}
	
	/*
	 * default timestep is .1 seconds.
	 * This indicates how much model time elapses between each simulation step. 
	 * You can use this to adjust the granularity of the simulation. 
	 */
	private static double timeStep = .1;
	public static double getTimeStep(){
		return timeStep;
	}
	public static void setTimeStep(double time){
		timeStep = time;
	}
	
	public static String output(){
		StringBuilder out = new StringBuilder();
		out.append("Simulation time step (seconds)\t\t[").append(getTimeStep()).append("]\n");
		out.append("Simulation run time (seconds)\t\t[").append(getRuntime()).append("]\n");
		out.append("Grid size (number of roads)\t\t[rows=").append(getGridRows()).append(",columns=").append(getGridColumns()).append("]\n");
		out.append("Traffic Pattern\t\t\t\t[").append(trafficPatternToString()).append("]\n");
		out.append("Car entry rate (seconds/car)\t\t[min=").append(getCarEntryRateMin()).append(",max=").append(getCarEntryRateMax()).append("]\n");
		out.append("Road segment length (meters)\t\t[min=").append(getRoadSegmentLengthMin()).append(",max=").append(getRoadSegmentLengthMax()).append("]\n");
		out.append("Intersection length (meters)\t\t[min=").append(getIntersectionLengthMin()).append(",max=").append(getIntersectionLengthMax()).append("]\n");
		out.append("Car length (meters)\t\t\t[min=").append(getCarLengthMin()).append(",max=").append(getCarLengthMax()).append("]\n");
		out.append("Car maximum velocity (meters/second)\t[min=").append(getMaxVelocityMin()).append(",max=").append(getMaxVelocityMax()).append("]\n");
		out.append("Car stop distance (meters)\t\t[min=").append(getCarStopDistanceMin()).append(",max=").append(getCarStopDistanceMax()).append("]\n");
		out.append("Car brake distance (meters)\t\t[min=").append(getBrakeDistanceMin()).append(",max=").append(getBrakeDistanceMax()).append("]\n");
		out.append("Traffic light green time (seconds)\t[min=").append(getGreenTimeMin()).append(",max=").append(getGreenTimeMax()).append("]\n");
		out.append("Traffic light yellow time (seconds)\t[min=").append(getYellowTimeMin()).append(",max=").append(getYellowTimeMax()).append("]\n");

		return out.toString();
		
	}
  
}  

