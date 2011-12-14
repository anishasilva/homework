package project.model;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Observable;
import project.util.Animator;

/**
 * An example to model for a simple visualization. The model contains roads
 * organized in a matrix. See {@link #Model(AnimatorBuilder, int, int)}.
 */
public class Model extends Observable {

	private Animator _animator;
	private boolean _disposed;
	private double _time;

	/**
	 * Creates a model to be visualized using the <code>builder</code>. If the
	 * builder is null, no visualization is performed. The number of
	 * <code>rows</code> and <code>columns</code> indicate the number of
	 * {@link Light}s, organized as a 2D matrix. These are separated and
	 * surrounded by horizontal and vertical {@link RoadSegment}s. For example,
	 * calling the constructor with 1 row and 2 columns generates a model of the
	 * form:
	 * 
	 * <pre>
	 *     |  |
	 *   --@--@--
	 *     |  |
	 * </pre>
	 * 
	 * where <code>@</code> is a {@link Light}, <code>|</code> is a vertical
	 * {@link RoadSegment} and <code>--</code> is a horizontal
	 * {@link RoadSegment}. Each road has one {@link Car}.
	 * 
	 * <p>
	 * The {@link AnimatorBuilder} is used to set up an {@link Animator}.
	 * {@link AnimatorBuilder#getAnimator()} is registered as an observer of
	 * this model.
	 * <p>
	 */
	public Model(AnimatorBuilder builder, int rows, int columns) {
		if (rows < 0 || columns < 0 || (rows == 0 && columns == 0)) {
			throw new IllegalArgumentException();
		}
		if (builder == null) {
			builder = new NullAnimatorBuilder();
		}

		setup(builder, rows, columns);
		_animator = builder.getAnimator();
		super.addObserver(_animator);
	}

	/**
	 * Run the simulation for <code>duration</code> model seconds.
	 */
	public void run(double duration) {
		if (_disposed)
			throw new IllegalStateException();
		for (int i = 0; i < duration; i++) {

			_time += MP.getTimeStep();

			TimeServerLinked.getServer().run(MP.getTimeStep());

			super.setChanged();
			super.notifyObservers();
		}
		TimeServerLinked.getServer().reset();
	}

	/**
	 * Throw away this model.
	 */
	public void dispose() {
		_animator.dispose();
		_disposed = true;
	}

	/**
	 * Construct the model, establishing correspondences with the visualizer.
	 */
	private void setup(AnimatorBuilder builder, int rows, int columns) {

		CarAcceptor[][] intersections = new Light[rows][columns];

		// Add Lights
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {

				intersections[i][j] = Factory.newLight();
				builder.addLight((Light) intersections[i][j], i, j);

				TimeServerLinked.getServer().enqueue(0,
						(Agent) intersections[i][j]);
			}
		}

		// Add Horizontal Roads
		boolean eastToWest = false;
		for (int i = 0; i < rows; i++) {
			List<CarAcceptor> roads = new ArrayList<CarAcceptor>();
			for (int j = 0; j <= columns; j++) {

				CarAcceptor l = Factory.newEWRoad();

				roads.add(l);
				builder.addHorizontalRoad((RoadSegment) l, i, j, eastToWest);
				if (j < columns)
					roads.add(intersections[i][j]);
			}

			if (eastToWest) {
				Collections.reverse(roads);
			}
			/*
			 * putting all the East/west carAccptors together:
			 */
			Iterator<CarAcceptor> it = roads.iterator();
			CarAcceptor first = it.next();
			CarAcceptor firstRoad = first;
			CarAcceptor nextElement = null;
			while (it.hasNext()) {
				nextElement = it.next();
				first.setNextEWRoad(nextElement);
				first = nextElement;
			}
			CarAcceptor source = Factory.newSource();
			source.setNextEWRoad(firstRoad);
			TimeServerLinked.getServer().enqueue(0, (Agent) source);

			CarAcceptor sink = Factory.newSink();
			nextElement.setNextEWRoad(sink);

			if (MP.getTrafficPattern())
				eastToWest = !eastToWest;

		}

		// Add Vertical Roads
		boolean southToNorth = false;
		for (int j = 0; j < columns; j++) {
			List<CarAcceptor> roads = new ArrayList<CarAcceptor>();
			for (int i = 0; i <= rows; i++) {
				CarAcceptor l = Factory.newNSRoad();
				roads.add(l);
				builder.addVerticalRoad((RoadSegment) l, i, j, southToNorth);
				if (i < rows) {
					roads.add(intersections[i][j]);
				}
			}
			if (southToNorth) {
				Collections.reverse(roads);
			}

			/*
			 * putting together the NS lights and roads
			 */
			Iterator<CarAcceptor> it = roads.iterator();
			CarAcceptor first = it.next();
			CarAcceptor firstRoad = first;
			CarAcceptor nextElement = null;
			while (it.hasNext()) {
				nextElement = it.next();
				first.setNextNSRoad(nextElement);
				first = nextElement;
			}
			CarAcceptor source = Factory.newSource();
			source.setNextNSRoad(firstRoad);
			TimeServerLinked.getServer().enqueue(0, (Agent) source);

			CarAcceptor sink = Factory.newSink();
			nextElement.setNextNSRoad(sink);

			if (MP.getTrafficPattern())
				southToNorth = !southToNorth;

		}
	}
}
