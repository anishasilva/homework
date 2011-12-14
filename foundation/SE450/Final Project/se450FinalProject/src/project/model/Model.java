package project.model;

import java.util.*;
import project.util.Animator;

/**
 * An example to model for a simple visualization.
 * The model contains roads organized in a matrix.
 * See {@link #Model(AnimatorBuilder, int, int)}.
 */
public class Model extends Observable {
  private List<Agent> _agents;
  private Animator _animator;
  private boolean _disposed;
  private double _time;

  /** Creates a model to be visualized using the <code>builder</code>.
   *  If the builder is null, no visualization is performed.
   *  The number of <code>rows</code> and <code>columns</code>
   *  indicate the number of {@link Light}s, organized as a 2D
   *  matrix.  These are separated and surrounded by horizontal and
   *  vertical {@link RoadSegment}s.  For example, calling the constructor with 1
   *  row and 2 columns generates a model of the form:
   *  <pre>
   *     |  |
   *   --@--@--
   *     |  |
   *  </pre>
   *  where <code>@</code> is a {@link Light}, <code>|</code> is a
   *  vertical {@link RoadSegment} and <code>--</code> is a horizontal {@link RoadSegment}.
   *  Each road has one {@link Car}.
   *
   *  <p>
   *  The {@link AnimatorBuilder} is used to set up an {@link
   *  Animator}.
   *  {@link AnimatorBuilder#getAnimator()} is registered as
   *  an observer of this model.
   *  <p>
   */
  public Model(AnimatorBuilder builder, int rows, int columns) {
    if (rows < 0 || columns < 0 || (rows == 0 && columns == 0)) {
      throw new IllegalArgumentException();
    }
    if (builder == null) {
      builder = new NullAnimatorBuilder();
    }
    _agents = new ArrayList<Agent>();
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
    for (int i=0; i<duration; i++) {
      _time++;
      // iterate through a copy because _agents may change during iteration...
      for (Agent a : _agents.toArray(new Agent[0])) {
        a.run(_time);
      }
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

  /**
   * Construct the model, establishing correspondences with the visualizer.
   */
  private void setup(AnimatorBuilder builder, int rows, int columns) {
    List<RoadSegment> roads = new ArrayList<RoadSegment>();
    Light[][] intersections = new Light[rows][columns];
    @SuppressWarnings("unused")
	Boolean reverse;

    // Add Lights
    for (int i=0; i<rows; i++) {
      for (int j=0; j<columns; j++) {
        intersections[i][j] = new Light();
        builder.addLight(intersections[i][j], i, j);
        _agents.add(intersections[i][j]);
      }
    }

    // Add Horizontal Roads
    boolean eastToWest = false;
    for (int i=0; i<rows; i++) {
      for (int j=0; j<=columns; j++) {
        RoadSegment l = new RoadSegment();
        builder.addHorizontalRoad(l, i, j, eastToWest);
        roads.add(l);
      }
      eastToWest = !eastToWest;
    }

    // Add Vertical Roads
    boolean southToNorth = false;
    for (int j=0; j<columns; j++) {
      for (int i=0; i<=rows; i++) {
        RoadSegment l = new RoadSegment();
        builder.addVerticalRoad(l, i, j, southToNorth);
        roads.add(l);
      }
      southToNorth = !southToNorth;
    }

    // Add Cars
    for (RoadSegment l : roads) {
      Car car = new Car();
      _agents.add(car);
      l.accept(car);
    }
  }
}
