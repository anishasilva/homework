package project.model;

public interface TimeServerInterface {
	public double currentTime();

	public void enqueue(double waketime, Agent thing);

	public void reset();

	public void run(double duration);
}
