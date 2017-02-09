package main.domain.strategy;

import java.util.ArrayList;

import main.domain.Paintable;
import main.domain.gizmos.Cezerye;
import main.physics.LineSegment;

public class BlinkStrategy implements IMovementStrategy {
	
	private static BlinkStrategy instance;
	
	private BlinkStrategy() {		
	}
	
	public static IMovementStrategy getInstance(){
	if(instance==null){
			instance=new BlinkStrategy();
		}
		
		return instance;
	}
	
	 public String toString(){
	    	return ("Cezerye Movement");
	    }
	
	@Override
	public void moveAnimation(Paintable context) {
		if( ((Cezerye)context).isVisible()){
			((Cezerye)context).random();
		}
		
		else if(!((Cezerye)context).isVisible()){
	
			if(((Cezerye)context).getLineSegmentList().size() != 0) {
				((Cezerye)context).setLineSegmentList(new ArrayList<LineSegment>());
			}
			
		}
	}

}
