/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.domain;

import java.util.ArrayList;

import main.domain.strategy.BallMovementStrategy;
import main.domain.strategy.IMovementStrategy;
import main.ui.GameWindow;

 /** OVERVIEW: This class represents a ball object that has coordinates and velocity
 * and collides with every objects in the board.
 *  @since 0.1
 *  @version 0.1
 *  @author JusticeLeague
 */
public class Ball extends Paintable {

	private double x;
	private double y;
	private double xVel;
	private double yVel;
	private double diameter;
	public static final double radius=0.5;
	private IMovementStrategy movementStrategy;
	private int lastTouch;
	private Cezmi collidedWith;
	private String collisionType;
	private String collisionSide;
	private String collisionWall;
	private boolean withInteraction1;
	private boolean withInteraction2;
	private int ballNumber;
	private boolean goal;
	private ArrayList<Point> borderPoints;
	
	/**
	 * Create a Ball with default values.
	 * @modifies this
	 */
	public Ball(){

		/*do {
			xVel = Math.random();
			yVel = Math.random();
		}
		while(xVel < 0.4 || yVel < 0.4  || xVel > 0.6 || yVel > 0.6);	*/
		
		setxVel(0);
		setyVel(0);
		
		ballNumber = 0;
		diameter=1;
		movementStrategy= BallMovementStrategy.getInstance();	
		lastTouch = 0;
		collisionType = "";
		collisionSide = "";
		collisionWall = "";
		withInteraction1 = true;
		withInteraction2 = true;
		borderPoints = new ArrayList<Point>();
		setGoal(false);
	}
	
	

	/**
	 * @return the borderPoints
	 */
	public ArrayList<Point> getBorderPoints() {
		return borderPoints;
	}



	/**
	 * @param borderPoints the borderPoints to set
	 */
	public void setBorderPoints(ArrayList<Point> borderPoints) {
		this.borderPoints = borderPoints;
	}



	/**
	 * takes ball id
	 * @effects the ballNumber
	 */
	public int getBallNumber() {
		return ballNumber;
	}

	/**
	 * sets ball id
	 * @modifies ballNumber
	 */
	public void setBallNumber(int ballNumber) {
		this.ballNumber = ballNumber;
	}
	
	/**
	 * takes movementStrategy of this
	 * @effects the movementStrategy
	 */
	public IMovementStrategy getMovementStrategy() {
		return movementStrategy;
	}

	/**
	 * sets movementStrategy specifying how to move for this
	 * @modifies movementStrategy
	 */
	public void setMovementStrategy(IMovementStrategy movementStrategy) {
		this.movementStrategy = movementStrategy;
	}

	/**
	 * takes x coordinates of this
	 * @effects the x
	 */
	public double getX() {
		return x;
	}

	/**
	 * takes y coordinates of this
	 * @modifies x
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * takes y coordinates of this
	 * @effects the y
	 */
	public double getY() {
		return y;
	}

	/**
	 * sets y coordinates of this
	 * @modifies y
	 */
	public void setY(double y) {
		this.y = y;
		borderPoints = new ArrayList<Point>();
		int xCord1 = ((int)getX()/GameWindow.getL()*GameWindow.getL());
		int yCord1 = ((int)y/GameWindow.getL()*GameWindow.getL());
		borderPoints.add(new Point(xCord1, yCord1));
		int xCord2 = ((int)(getX()+GameWindow.getL()*getDiameter()-1)/GameWindow.getL()*GameWindow.getL());
		int yCord2 = ((int)y/GameWindow.getL()*GameWindow.getL());
		borderPoints.add(new Point(xCord2, yCord2));
		int xCord3 = ((int)(getX()+GameWindow.getL()*getDiameter()-1)/GameWindow.getL()*GameWindow.getL());
		int yCord3 = ((int)(y+GameWindow.getL()*getDiameter()-1)/GameWindow.getL()*GameWindow.getL());
		borderPoints.add(new Point(xCord3, yCord3));
		int xCord4 = ((int)getX()/GameWindow.getL()*GameWindow.getL());
		int yCord4 = ((int)(y+GameWindow.getL()*getDiameter()-1)/GameWindow.getL()*GameWindow.getL());
		borderPoints.add(new Point(xCord4, yCord4));
	}

	/**
	 * takes xVel of this
	 * @effects the xVel
	 */
	public double getxVel() {
		return xVel;
	}

	/**
	 * sets xVel of this
	 * @modifies xVel
	 */
	public void setxVel(double xVel) {
		this.xVel = xVel;
	}

	/**
	 * takes yVel of this
	 * @effects the yVel
	 */
	public double getyVel() {
		return yVel;
	}

	/**
	 * sets yVel of this
	 * @modifies yVel
	 */
	public void setyVel(double yVel) {
		this.yVel = yVel;
	}

	/**
	 * takes diameter of this
	 * @effects the diameter
	 */
	public double getDiameter() {
		return diameter;
	}

	/**
	 * sets diameter of this
	 * @modifies diameter
	 */
	public void setDiameter(double diameter) {
		this.diameter = diameter;
	}

	/**
	 * takes collidedWith
	 * @effects the collidedWith
	 */
	public Cezmi getCollidedWith() {
		return collidedWith;
	}

	/**
	 * sets collidedWith determining which Cezmi was hit by this
	 * @modifies collidedWith
	 */
	public void setCollidedWith(Cezmi collidedWith) {
		this.collidedWith = collidedWith;
	}

	/**
	 * takes lastTouch
	 * @effects the lastTouch
	 */
	public int getLastTouch() {
		return lastTouch;
	}

	/**
	 * If Player 1 touches ball lastTouch = 1,
	 * else if Player 2 touches ball lastTouch = 2
	 * @modifies lastTouch
	 */
	public void setLastTouch(int lastTouch) {
		this.lastTouch = lastTouch;
	}

	/**
	 * takes collisionType
	 * @effects the collisionType
	 */
	public String getCollisionType() {
		return collisionType;
	}

	/**
	 * sets collisionType specifying which type of obstacle was hit by this
	 * @modifies collisionType
	 */
	public void setCollisionType(String collisionType) {
		this.collisionType = collisionType;
	}

	/**
	 * takes collisionSide
	 * @effects the collisionSide
	 */
	public String getCollisionSide() {
		return collisionSide;
	}

	/**
	 * sets collisionSide specifying in which side this hits
	 * @modifies collisionSide
	 */
	public void setCollisionSide(String collisionSide) {
		this.collisionSide = collisionSide;
	}

	/**
	 * takes collisionWall
	 * @effects the collisionWall
	 */
	public String getCollisionWall() {
		return collisionWall;
	}

	/**
	 * sets collitionWall determining which wall this hits
	 * @modifies collisionWall
	 */
	public void setCollisionWall(String collisionWall) {
		this.collisionWall = collisionWall;
	}

	/**
	 * takes withInteraction1
	 * @effects the withInteraction1
	 */
	public boolean isWithInteraction1() {
		return withInteraction1;
	}

	/**
	 * sets withInteraction1 for Player1. it is true when ball hits left side gizmos and then hits right wall
	 * otherwise false
	 * @modifies withInteraction
	 */
	public void setWithInteraction1(boolean withInteraction) {
		this.withInteraction1 = withInteraction;
	}

	/**
	 * takes withInteraction2
	 * @effects the withInteraction2
	 */
	public boolean isWithInteraction2() {
		return withInteraction2;
	}

	/**
	 * sets withInteraction2 for Player2. it is true when ball hits right side gizmos and then hits left wall
	 * otherwise false
	 * @modifies withInteraction2
	 */
	public void setWithInteraction2(boolean withInteraction2) {
		this.withInteraction2 = withInteraction2;
	}

	/**
	 * @effects toString method of this
	 */
	@Override
	public String toString() {
		return "Ball " + getBallNumber() + ": \n" + "X: " + getX() + ": \n" + "Y: " + getY() + ": \n"
				+ "X Velocity: " + getxVel() + ": \n" + "Y Velocity: " + getyVel() + ": \n" + "Diameter: " + getDiameter();
	}
	
	public boolean repOK() {
		if(getX()<0 || getY()<0 || getX()>GameWindow.getL()*Board.BOARD_SIZE || getY()>GameWindow.getL()*Board.BOARD_SIZE
				|| getxVel()<0.4 || getyVel()<0.4 || getxVel()>1 || getyVel()>1) {
			return false;
		}
		if(getDiameter()<0.2 || getDiameter()>1) {
			return false;
		}
		return true;
	}

	public boolean isGoal() {
		return goal;
	}

	public void setGoal(boolean goal) {
		this.goal = goal;
	}
}
