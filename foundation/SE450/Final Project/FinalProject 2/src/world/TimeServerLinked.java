package world;

/*
 * Source mostly taken for SE450 lecture notes.
 */

import java.util.Observable;

public class TimeServerLinked extends Observable implements TimeServer {
	
	private static final class Node {
		final double waketime;
		final Agent agent;
		Node next;
		
		public Node(double waketime, Agent agent, Node next){
			this.waketime = waketime;
			this.agent = agent;
			this.next = next;
		}
	}

	private double _currentTime;
	private int _size;
	private Node _head;
	
	/*
	 * Public Constructor.
	 * @Invarient: _head != null
	 * @Invarient: _head.agent == null
	 * @Incarient: _size == 0 if _head.next == null
	 */
	public TimeServerLinked(){
		_size = 0;
		_head = new Node(0, null, null);
	}
	
	/*
	 * Override toString() method
	 * to display "(waketime,agent);(waketime2,agent2)]"
	 */
	public String toString(){
		StringBuilder sb = new StringBuilder("[");
		Node node = _head.next;
		String sep = "";
		while (node != null){
			sb.append(sep).append("(").append(node.waketime).append(",").append(node.agent).append(")");
			node = node.next;
			sep = ";";
		}
		sb.append("]");
		return (sb.toString());
	}
	
	@Override
	public double currentTime() {
		return _currentTime;
	}

	/*
	 * waketime must be >= _currentTime for it to be added to the queue.
	 */
	public void enqueue(double waketime, Agent thing) 
		throws IllegalArgumentException
		{
		if (waketime < _currentTime)
			throw new IllegalArgumentException();
		Node prevElement = _head;
		while ((prevElement.next != null) && (prevElement.next.waketime <= waketime)){
			prevElement = prevElement.next;
		}
		Node newElement = new Node(waketime, thing, prevElement.next);
		prevElement.next = newElement;
		_size++;
	}

	Agent dequeue(){
		if (_size < 1)
			throw new java.util.NoSuchElementException();
		Agent rval = _head.next.agent;
		_head.next = _head.next.next;
		_size--;
		return rval;
	}
	
	int size() {
		return _size;
	}
	
	boolean empty(){
		return _size == 0;
	}
	@Override
	public void run(double duration) {
		double endtime = _currentTime + duration;
		while ((!empty()) && (_head.next.waketime <= endtime)){
			_currentTime = _head.next.waketime;
			dequeue().run();
		}
		_currentTime = endtime;

	}
	
	public void reset(){
		_head = new Node(0, null, null);
		_currentTime = 0;
		_size = 0;
	}

	

}
