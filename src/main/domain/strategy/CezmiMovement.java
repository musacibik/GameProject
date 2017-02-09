package main.domain.strategy;

import main.domain.Board;
import main.domain.Cezmi;
import main.domain.Engel;
import main.domain.Paintable;
import main.ui.GameWindow;

public class CezmiMovement implements IMovementStrategy {

	private static CezmiMovement instance;

	private CezmiMovement() {
	}
	
	public static IMovementStrategy getInstance(){
		if(instance==null){
			instance=new CezmiMovement();
		}

		return instance;
	}

	public String toString(){
		return ("Cezmi Movement");
	}

	/**
	 * @Override
	 */
	public void moveAnimation(Paintable context) {
		Cezmi pointer = (Cezmi) context;
		if(Board.getInstance().getLevel() == 1){
			if(pointer.isDirection() == true){
				if(pointer.getX()+ GameWindow.getL() >= Board.BOARD_SIZE * GameWindow.getL() - GameWindow.getL()){
					return;
				}
				if(pointer.getBelongsTo().equals(Board.getInstance().getPlayers().get(0))){
					if(pointer.getX()+ GameWindow.getL() >= Board.BOARD_SIZE*GameWindow.getL()/2 - 1.2*GameWindow.getL()){
						return;
					}
				}
				pointer.setX(pointer.getX()+ pointer.getxVel());

			}
			if(pointer.isDirection() == false){
				if(pointer.getBelongsTo().equals(Board.getInstance().getPlayers().get(1))){
					if(pointer.getX()+ GameWindow.getL() <= Board.BOARD_SIZE*GameWindow.getL()/2 + 1.15*GameWindow.getL()){
						return;
					}
				}
				if(pointer.getX()+ GameWindow.getL() <= GameWindow.getL()){
					return;
				}
				pointer.setX(pointer.getX() - pointer.getxVel());
			}
		}else if(Board.getInstance().getLevel() == 2){
			if(pointer.isDirection() == true){
				if(pointer.getBelongsTo().equals(Board.getInstance().getPlayers().get(0))){
					if(pointer.getX()+ GameWindow.getL() >= Board.BOARD_SIZE*GameWindow.getL()/2 - 1.2*GameWindow.getL()){
						return;
					}
				}
				if(pointer.getX()+ GameWindow.getL() >= Board.BOARD_SIZE * GameWindow.getL()){
					if(GameWindow.getL() * 25 - (Engel.ENGEL_HEIGHT)*GameWindow.getL() * 2 < pointer.getY()){
						pointer.setY(pointer.getY() - pointer.getyVel());
					}
					return;
				}
				if(pointer.getY() == GameWindow.getL()*Board.BOARD_SIZE-GameWindow.getL()){
					pointer.setX(pointer.getX()+ pointer.getxVel());
				}else{
					pointer.setY(pointer.getY() + pointer.getyVel());
				}

			}
			if(pointer.isDirection() == false){
				if(pointer.getBelongsTo().equals(Board.getInstance().getPlayers().get(1))){
					if(pointer.getX()+ GameWindow.getL() <= Board.BOARD_SIZE*GameWindow.getL()/2 + 1.15*GameWindow.getL()){
						return;
					}
				}
				if(pointer.getX()+ GameWindow.getL() <= 0){
					if(GameWindow.getL() * 25 - (Engel.ENGEL_HEIGHT)*GameWindow.getL() * 2 < pointer.getY()){
						pointer.setY(pointer.getY() - pointer.getyVel());
					}
					return;
				}
				if(pointer.getY() == GameWindow.getL()*Board.BOARD_SIZE-GameWindow.getL()){
					pointer.setX(pointer.getX()- pointer.getxVel());
				}else{
					pointer.setY(pointer.getY() + pointer.getyVel());
				}
			}
		}else{
			System.err.println("Cezmi Movement Error");
		}

	}
}
