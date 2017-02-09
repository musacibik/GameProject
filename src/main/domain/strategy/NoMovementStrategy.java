package main.domain.strategy;

import main.domain.Paintable;

public class NoMovementStrategy implements IMovementStrategy {

	private static NoMovementStrategy instance;

	private NoMovementStrategy() {		
	}
	
	public static IMovementStrategy getInstance(){
	if(instance==null){
			instance=new NoMovementStrategy();
		}
		
		return instance;
	}

	@Override
	public void moveAnimation(Paintable context) {		
	}
	
	 public String toString(){
	    	return ("Don't Move");
	    }
}
