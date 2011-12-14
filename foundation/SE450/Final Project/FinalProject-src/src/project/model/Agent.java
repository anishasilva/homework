package project.model;

/**
 * Interface for active model objects.
 */
public interface Agent {
	/*
	 * Lets the agent update itself and requeue itself back in the TimeServer if
	 * need be.
	 */

	public void run();
}
