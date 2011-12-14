package project.model;

import junit.framework.Assert;
import junit.framework.TestCase;

public class RoadTEST extends TestCase {
	public RoadTEST(String name){
		super(name);
	}
	
	double length = MP.getRoadSegmentLength();
	
	public void testConstructor(){
		RoadSegment road = (RoadSegment) Factory.newRoad();
		
		RoadSegment road2 = new RoadSegment(length);
		
		Assert.assertFalse(road == road2);
		
		try{
			RoadSegment road3 = new RoadSegment(0);
			Assert.fail();
		}catch (IllegalArgumentException e){}
		
		
		
	}
}
