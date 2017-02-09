package main.domain;

import java.util.ArrayList;

import main.domain.gizmos.Gizmo;
import main.ui.GameWindow;

/**
 *  OVERVIEW: Board is responsible of holding the data that is going 
 *  to be printed on the JPanel.
 *  @since 0.1
 *  @version 0.1
 *  @author JusticeLeague
 */
public class Board {

	private static Board instance;

	public static final int BOARD_SIZE = 25;
	public static double FRICTION_1 = .025; 
	public static double FRICTION_2 = .025;
	public static double GRAVITY = 25.0;

	private ArrayList<Gizmo> gizmos = new ArrayList<>();
	private ArrayList<Player> players = new ArrayList<>();
	private ArrayList<Ball> balls = new ArrayList<>();
	private Ball ball1, ball2;
	private Player player1, player2;
	private int level;

	/**
	 * Invokes the private constructor of the Board class, by the rules of the singleton pattern only one Board object can exist
	 * and is immutable. 
	 * @effects return an instance of a board object.
	 */
	public static synchronized Board getInstance() {
		if (instance == null) {
			instance = new Board();
		}
		return instance;
	}
	
	/**
	 * Initial constructor for the board class.
	 */
	private Board() {
		player1 = new Player("Player1", 1);
		player2 = new Player("Player2", 2);
		players.add(player1);
		players.add(player2);
		
		ball1 = new Ball();
		ball1.setX(player1.getCezmi().getX() + GameWindow.getL() - GameWindow.getL()*ball1.getDiameter()/2);
		ball1.setY(player1.getCezmi().getY() - 5*GameWindow.getL());
		ball1.setxVel(0);
		ball1.setBallNumber(1);
		balls.add(ball1);
	}
	
	/**
	 * A representation of a Board object that is immutable. Scans all of its fields and returns a proper string 
	 * that is ready to be printed or modified.
	 * @Override Object class
	 * @effects returns a String representation of the Board object.
	 * @modifies the gizmos ArrayList of the Board
	 */
	public String toString(){
		String output = "";
		int j = 1;
		for(Gizmo i: this.getGizmos()){
			/*
			output = output + j + ".\nGizmo x :" + i.getX() +"\nGizmo y : "+ i.getY()+"\n\n";
			j++;
			*/
			output = output + i.toString() +"";
		}
		for(Ball i: this.getBalls()){
			output = output + "Ball x :" + i.getX() +"\nBall y : "+ i.getY()+"\n\n";
		}
		j = 1;
		for(Player i: this.getPlayers()){
			output = output + "Player "+j+" info:\n"+"Name :" +i.getName()+"\nScore : "+i.getScore() +"\n"+i.getName()+"'s Cezmi :\n"+i.getCezmi().toString();
			j++;
		}
		output = output + "\nBoard Level : "+ this.getLevel()+"\n";
		return output;
	}
	
	/**
	 * Adds a Gizmo object to the Board
	 * @param gizmo a Gizmo instance, which is going to be added to the Board
	 * @modifies the gizmos ArrayList of the Board
	 */
	public void addGizmo(Gizmo gizmo){
		Board.getInstance().gizmos.add(gizmo);
	}
	
	/**
	 * Removes a Gizmo from the Board.
	 * @param gizmo a Gizmo instance, which is going to be removed from the Board
	 * @modifies the gizmos ArrayList of the Board
	 */
	public void removeGizmo(Gizmo gizmo){
		for(Gizmo g: gizmos){
			if(gizmo.getX() == g.getX() && gizmo.getY() == g.getY())
				Board.getInstance().gizmos.remove(gizmo);		
		}
	}
	/**
	 * Rotates the initial Gizmo with a static arc.
	 * @modifies gizmo the Gizmo object, which is going to be rotated.
	 */
	public void rotateGizmo(Gizmo gizmo){
		for(int i = 0; i< gizmos.size(); i++){
			if(gizmo.getX() == gizmos.get(i).getX() && gizmo.getY() == gizmos.get(i).getY()){
				Board.getInstance().gizmos.get(i).rotateClockwise();
			
			}
		}			
	}
	
	public void updateBalls(){
		if(level == 1){
			if(this.getBalls().size() == 2){
				this.getBalls().remove(1);
			}
		} 
		
		if(level == 2){
			if(this.getBalls().size() == 1){
				ball2 = new Ball();
				ball2.setX(player2.getCezmi().getX() + GameWindow.getL()- GameWindow.getL()*ball2.getDiameter()/2);
				ball2.setY(player2.getCezmi().getY() - 5*GameWindow.getL());
				ball2.setxVel(0);
				ball2.setBallNumber(2);
				balls.add(ball2);
			}
		}
	}

	/**
	 * 
	 * @effects returns the Gizmo ArrayList
	 */
	public ArrayList<Gizmo> getGizmos() {
		return gizmos;
	}

	/**
	 * 
	* @modifies gizmos (Gizmo ArrayList) of the board
	 */
	public void setGizmos(ArrayList<Gizmo> gizmos) {
		this.gizmos = gizmos;
	}

	/**
	 * 
	 * @effects returns the player ArrayList
	 */
	public ArrayList<Player> getPlayers() {
		return players;
	}

	/**
	 * 
	 * @modifies players (Player ArrayList) of the board
	 */
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	/**
	 * 
	 * @effects returns the Ball ArrayList
	 */
	public ArrayList<Ball> getBalls() {
		return balls;
	}

	/**
	 * 
	 * @modifies balls (Ball ArrayList) of the board
	 */
	public void setBalls(ArrayList<Ball> balls) {
		this.balls = balls;
	}

	/**
	 * 
	 * @effects returns the current Level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * 
	 * @modifies level of the Board
	 */
	public void setLevel(int level) {
		this.level = level;
	}
	
}
