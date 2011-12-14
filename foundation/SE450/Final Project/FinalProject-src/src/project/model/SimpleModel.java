package project.model;

import java.util.Observable;
import project.util.Animator;

/**
 * An example to model for a simple visualization. The model contains two roads.
 * See {@link #SimpleModel(AnimatorBuilder)}.
 */
public class SimpleModel extends Observable {
	private Animator _animator;
	private boolean _disposed;
	private double _time;

	/**
	 * Creates a model to be visualized using the <code>builder</code>. If the
	 * builder is null, no visualization is performed. Each road has one
	 * {@link Car}.
	 * 
	 */
	public SimpleModel(AnimatorBuilder builder) {
		if (builder == null) {
			builder = new NullAnimatorBuilder();
		}
		setup(builder);
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
	}

	/**
	 * Throw away this model.
	 */
	public void dispose() {
		_animator.dispose();
		_disposed = true;
	}

	private void setup(AnimatorBuilder builder) {
		CarAcceptor r1 = Factory.newEWRoad();
		CarAcceptor r2 = Factory.newEWRoad();
		CarAcceptor r3 = Factory.newNSRoad();
		CarAcceptor r4 = Factory.newNSRoad();
		CarAcceptor source2 = Factory.newSource();
		CarAcceptor sink2 = Factory.newSink();
		CarAcceptor sink = Factory.newSink();
		CarAcceptor source = Factory.newSource();
		CarAcceptor light = Factory.newLight();
		source.setNextEWRoad(r1);
		r1.setNextEWRoad(light);
		light.setNextEWRoad(r2);
		r2.setNextEWRoad(sink);
		source2.setNextNSRoad(r3);
		r3.setNextNSRoad(light);
		light.setNextNSRoad(r4);
		r4.setNextNSRoad(sink2);

		TimeServerLinked.getServer().enqueue(0, (Agent) source);
		TimeServerLinked.getServer().enqueue(0, (Agent) source2);
		TimeServerLinked.getServer().enqueue(0, (Agent) light);
		builder.addVerticalRoad((RoadSegment) r3, 0, 1, false);
		builder.addVerticalRoad((RoadSegment) r4, 1, 1, false);
		builder.addHorizontalRoad((RoadSegment) r1, 0, 1, false);
		builder.addLight((Light) light, 0, 1);
		builder.addHorizontalRoad((RoadSegment) r2, 0, 2, false);

	}
}
