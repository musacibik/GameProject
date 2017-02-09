package main.domain.strategy;

import java.util.ArrayList;
import java.util.List;

import main.domain.Paintable;
import main.domain.gizmos.LeftTokat;
import main.domain.gizmos.RightTokat;
import main.physics.Angle;
import main.physics.Circle;
import main.physics.Geometry;
import main.physics.LineSegment;
import main.physics.Vect;

public class SlapStrategy implements IMovementStrategy {
	private static SlapStrategy instance;
	
	private SlapStrategy() {
	}
	
	public static IMovementStrategy getInstance(){
		if(instance==null){
				instance=new SlapStrategy();
			}
			
			return instance;
		}
	
	@Override
	public void moveAnimation(Paintable context) {
		if(context instanceof LeftTokat){
			LeftTokat leftTokat=(LeftTokat) context;
			
			Vect center = leftTokat.getCircleList().get(0).getCenter();
			Angle angVel = new Angle(Math.toRadians(LeftTokat.getAngVel()));
			List<LineSegment> lineSegmentList = leftTokat.getLineSegmentList();
			List<LineSegment> newLineSegmentList = new ArrayList<>();
			List<Circle> newCircleList = new ArrayList<>(); 
			
			for(LineSegment segment : lineSegmentList) {
				LineSegment newSegment = Geometry.rotateAround(segment, center, angVel);
				newLineSegmentList.add(newSegment);
			}
			leftTokat.setLineSegmentList(newLineSegmentList);
			
			for(Circle circle : leftTokat.getCircleList()) {
				Circle newCircle = Geometry.rotateAround(circle, center, angVel);
				newCircleList.add(newCircle);
			}
			leftTokat.setCircleList(newCircleList);
		}
		
		
		else if(context instanceof RightTokat){
			RightTokat rightTokat=(RightTokat) context;
			
			Vect center = rightTokat.getCircleList().get(0).getCenter();
			Angle angVel = new Angle(Math.toRadians(RightTokat.getAngVel()));
			List<LineSegment> lineSegmentList = rightTokat.getLineSegmentList();
			List<LineSegment> newLineSegmentList = new ArrayList<>();
			List<Circle> newCircleList = new ArrayList<>(); 
			
			for(LineSegment segment : lineSegmentList) {
				LineSegment newSegment = Geometry.rotateAround(segment, center, angVel);
				newLineSegmentList.add(newSegment);
			}
			rightTokat.setLineSegmentList(newLineSegmentList);
			
			for(Circle circle : rightTokat.getCircleList()) {
				Circle newCircle = Geometry.rotateAround(circle, center, angVel);
				newCircleList.add(newCircle);
			}
			rightTokat.setCircleList(newCircleList);
		}
	}

	 public String toString(){
	    	return ("Tokat Movement");
	    }
}
