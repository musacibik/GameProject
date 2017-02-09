package main.domain.strategy;

import java.util.ArrayList;
import java.util.List;

import main.domain.Paintable;
import main.domain.gizmos.Firildak;
import main.physics.Geometry;
import main.physics.LineSegment;
import main.physics.Vect;

public class RotateStrategy implements IMovementStrategy  {
	
	private static RotateStrategy instance;
	
	private RotateStrategy() {
	}
	
	public static IMovementStrategy getInstance(){
	if(instance==null){
			instance=new RotateStrategy();
		}
		
		return instance;
	}

	@Override
	public void moveAnimation(Paintable context) {
		Firildak firildak=(Firildak) context;
		
		Vect center = new Vect(firildak.getX()+firildak.getLengthPixel()/2, firildak.getY()+firildak.getLengthPixel()/2);
		List<LineSegment> lineSegmentList = firildak.getLineSegmentList();
		List<LineSegment> newLineSegmentList = new ArrayList<>();
		
		for(LineSegment segment : lineSegmentList) {
			LineSegment newSegment = Geometry.rotateAround(segment, center, firildak.getRadian());
			newLineSegmentList.add(newSegment);
			((Firildak) context).setAngle((((Firildak) context).getAngle()+((Firildak) context).getAngularVelocity()) % 360);
			
		}
		((Firildak) context).setLineSegmentList(newLineSegmentList);
	}

	 public String toString(){
	    	return ("Rotates");
	    }

}
