package project.model;

import java.util.NoSuchElementException;

public final class TimeServerLinked implements TimeServerInterface {

	private static final class Node {

		final double waketime;
		final Agent agent;
		Node next;

		public Node(double waketime, Agent agent, Node next) {
			this.waketime = waketime;
			this.agent = agent;
			this.next = next;
		}
	}

	private static double _currentTime;
	private static int _size;
	private static Node _head = new Node(0, null, null);
	private volatile static TimeServerInterface timeServerQueue;

	TimeServerLinked() {
	}

	// This is a Double-Checked locking Singleton
	public static TimeServerInterface getServer() {
		if (timeServerQueue == null) {
			synchronized (TimeServerLinked.class) {
				if (timeServerQueue == null) {
					timeServerQueue = new TimeServerLinked();
				}
			}
		}
		return timeServerQueue;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		Node node = _head.next;
		String sep = "";
		while (node != null) {
			sb.append(sep).append("(").append(node.waketime).append(",")
					.append(node.agent).append(")");
			node = node.next;
			sep = ";";
		}
		sb.append("]");
		return (sb.toString());
	}

	public double currentTime() {
		return _currentTime;
	}

	public void enqueue(double waketime, Agent agent)
			throws IllegalArgumentException {
		if (waketime < _currentTime) {
			throw new IllegalArgumentException();
		}
		Node prevElement = _head;
		while ((prevElement.next != null)
				&& (prevElement.next.waketime <= waketime)) {
			prevElement = prevElement.next;
		}
		Node newElement = new Node(waketime, agent, prevElement.next);
		prevElement.next = newElement;
		_size++;
	}

	Agent dequeue() {
		if (_size < 1) {
			throw new NoSuchElementException();
		} else {
			Agent rval = _head.next.agent;
			_head.next = _head.next.next;
			_size--;
			return rval;
		}
	}

	int size() {
		return _size;
	}

	boolean empty() {
		return size() == 0;
	}

	public void reset() {
		timeServerQueue = null;
		_head = new Node(0, null, null);
		_size = 0;
		_currentTime = 0;
	}

	public void run(double duration) {
		double endtime = _currentTime + duration;
		while ((!empty()) && (_head.next.waketime <= endtime)) {
			// System.out.println(_currentTime);
			_currentTime = _head.next.waketime;
			dequeue().run();
		}
		_currentTime = endtime;
	}
}
